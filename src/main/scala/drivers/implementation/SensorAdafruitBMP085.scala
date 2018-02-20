package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.DriverBase
import com.artenc.robotic.drivers.SensorTrait
import com.artenc.robotic.drivers.I2CController

import com.artenc.utility.BitUtility

class SensorAdafruitBMP085(i2c: I2CController) extends DriverBase with SensorTrait
{
    private val I2C_ADDRESS = 0x77

    private val PRECISION_MODE : Int = 3

    private var coeff_ac1 : Int = 0
	private var coeff_ac2 : Int = 0
	private var coeff_ac3 : Int = 0
	private var coeff_ac4 : Int = 0
	private var coeff_ac5 : Int = 0
	private var coeff_ac6 : Int = 0
	private var coeff_b1 : Int = 0
	private var coeff_b2 : Int = 0
	private var coeff_mc : Int = 0
	private var coeff_md : Int = 0

    private var nextTemperatureGetTime : Long = 0
    private var nextPressureGetTime : Long = 0

    private var temperatureRaw : Int = 0
    private var pressureRaw : Int = 0
    private var b5 : Int = 0

    private var isTemperatureSaved : Boolean = false
    private var isPressureSaved : Boolean = false
    private var isB5Computed : Boolean = false

    protected override def init() {
        this.i2c.initDriver()

        // Who am I
        this.i2c.write(I2C_ADDRESS, 0xD0.toByte)

        val buffer = new Array[Byte](1)
        this.i2c.read(I2C_ADDRESS, buffer, 0, 1)

        if (buffer(0) != 0x55.toByte)
            throw new Exception("Failed to initialize, this is not the right device.")

        // Get coefficients
        this.coeff_ac1 = this.getCoefficient(0xAA.toByte)
        this.coeff_ac2 = this.getCoefficient(0xAC.toByte)
        this.coeff_ac3 = this.getCoefficient(0xAE.toByte)
        this.coeff_ac4 = this.getCoefficient(0xB0.toByte, true)
        this.coeff_ac5 = this.getCoefficient(0xB2.toByte, true)
        this.coeff_ac6 = this.getCoefficient(0xB4.toByte, true)
        this.coeff_b1 = this.getCoefficient(0xB6.toByte)
        this.coeff_b2 = this.getCoefficient(0xB8.toByte)
        this.coeff_mc = this.getCoefficient(0xBC.toByte)
        this.coeff_md = this.getCoefficient(0xBE.toByte)
    }

    protected override def process() {
        val currentTime = this.getMillis()

        if (this.nextTemperatureGetTime == 0 && this.nextPressureGetTime == 0) {
    		requestTemperature()
    		this.nextTemperatureGetTime = currentTime + 5
    		return
    	}

    	if (this.nextTemperatureGetTime > 0 && this.nextTemperatureGetTime + 1 < currentTime) {
    		saveTemperature()
    		this.nextTemperatureGetTime = 0

    		requestPressure()
    		this.nextPressureGetTime = currentTime + 26

    		return
    	}

    	if (this.nextPressureGetTime > 0 && this.nextPressureGetTime + 1 < currentTime) {
    		savePressure()
    		this.nextPressureGetTime = 0

    		requestTemperature()
    		this.nextTemperatureGetTime = currentTime + 5
    	}
    }

    def getTemperatureCelsius() : Float = {
    	this.getTemperatureCelsius(this.getB5())
    }
    private def getTemperatureCelsius(b5: Int) : Float = {
    	if (!this.isTemperatureSaved)
    		return 0

    	val b5Local = b5 + 8

    	val r : Int = b5Local & 0xF
    	var t : Float = b5Local >> 4
    	t += 0.0625f * r

    	t /= 10

    	return t
    }

    def getPressureKPA() : Int = {
    	this.getPressureKPA(this.getB5())
    }
    private def getPressureKPA(b5: Int) : Int = {
    	if (!this.isPressureSaved)
    		return 0

    	/* Pressure compensation */
    	val b6 : Int = b5 - 4000
    	var x1 : Int = (this.coeff_b2 * ((b6 * b6) >> 12)) >> 11
    	var x2 : Int = (this.coeff_ac2 * b6) >> 11
    	var x3 : Int = x1 + x2
    	val b3 : Int = (((this.coeff_ac1 * 4 + x3) << PRECISION_MODE) + 2) >> 2
    	x1 = (this.coeff_ac3 * b6) >> 13
    	x2 = (this.coeff_b1 * ((b6 * b6) >> 12)) >> 16
    	x3 = ((x1 + x2) + 2) >> 2
    	val b4 : Long = BitUtility.intAsUnsigned(this.coeff_ac4 * (x3 + 32768)) >> 15
    	val b7 : Long = BitUtility.intAsUnsigned((this.pressureRaw - b3) * (50000 >> PRECISION_MODE))
    	val p : Int = (if (b7 < 2147483648l) (b7 << 1) / b4 else (b7 / b4) << 1).toInt

    	x1 = (p >> 8) * (p >> 8)
    	x1 = (x1 * 3038) >> 16
    	x2 = (-7357 * p) >> 16

    	return p + ((x1 + x2 + 3791) >> 4)
    }

    private def requestTemperature() {
    	this.i2c.write(I2C_ADDRESS, 0xF4, 0x2E.toByte)
    }

    private def requestPressure() {
    	this.i2c.write(I2C_ADDRESS, 0xF4, (0x34 + (PRECISION_MODE << 6)).toByte)
    }

    private def saveTemperature() {
    	this.i2c.write(I2C_ADDRESS, 0xF6.toByte)

    	val buffer = new Array[Byte](2)
    	this.i2c.read(I2C_ADDRESS, buffer, 0, 2)

    	this.temperatureRaw = ((buffer(0) & 0xFF) << 8) | (buffer(1) & 0xFF)
    	this.isTemperatureSaved = true
    	this.isB5Computed = false
    }

    private def savePressure() {
    	val buffer = new Array[Byte](2)

    	this.i2c.write(I2C_ADDRESS, 0xF6.toByte)
    	this.i2c.read(I2C_ADDRESS, buffer, 0, 2)

        var p32 : Int = (((buffer(0) & 0xFF) << 8) | (buffer(1) & 0xFF)) << 8

    	this.i2c.write(I2C_ADDRESS, (0xF6 + 2).toByte)
    	this.i2c.read(I2C_ADDRESS, buffer, 0, 1)

    	p32 += buffer(0) & 0xFF
    	p32 >>= (8 - PRECISION_MODE)

    	this.pressureRaw = p32 & 0xFFFF
    	this.isPressureSaved = true
    }

    private def getB5() : Int = {
    	if (this.isB5Computed)
    		return this.b5

    	if (!this.isTemperatureSaved)
    		return 0

    	val x1 : Int = (this.temperatureRaw - this.coeff_ac6) * this.coeff_ac5 >> 15
    	val x2 : Int = (this.coeff_mc << 11) / (x1 + this.coeff_md)
    	this.b5 = x1 + x2

    	this.isB5Computed = true
    	return this.b5
    }

    private def getCoefficient(key: Byte, unsigned: Boolean = false) : Int = {
        val coeffBuffer = new Array[Byte](2)

        this.i2c.write(I2C_ADDRESS, key)
        this.i2c.read(I2C_ADDRESS, coeffBuffer, 0, 2)

        ((if (unsigned) coeffBuffer(0) & 0xFF else coeffBuffer(0)) << 8) | (coeffBuffer(1) & 0xFF)
    }

    private def getMillis() : Long = {
        System.nanoTime() / 1000000
    }
}
