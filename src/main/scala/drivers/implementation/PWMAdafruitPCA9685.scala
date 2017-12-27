package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.DriverBase
import com.artenc.robotic.drivers.I2CController

import scala.math.floor

class PWMAdafruitPCA9685(pwmFreq: Int, i2c: I2CController) extends DriverBase
{
    private val DEVICE_ADDR = 0x40
    private val MODE1 = 0x0
    private val PRESCALE = 0xFE
    private val LED_ON_L = 0x6

    override protected def init()
    {
        this.i2c.initDriver()

        reset()
        setPWMFreq(this.pwmFreq)
    }

    private def reset() {
        this.i2c.write(DEVICE_ADDR, MODE1, 0x0.toByte)
    }

    def getPWMFreq() : Int = this.pwmFreq

    private def setPWMFreq(pwmFreq: Int) {
        var freqF : Float = pwmFreq.toFloat;
    	freqF = freqF * 0.9f // Correct for overshoot in the frequency setting
    	var prescaleVal : Float = 25000000
    	prescaleVal /= 4096
    	prescaleVal /= freqF
    	prescaleVal -= 1

    	val prescale : Byte = floor(prescaleVal + 0.5).toByte

    	this.i2c.write(DEVICE_ADDR, MODE1.toByte)
    	val oldMode : Byte = this.i2c.read(DEVICE_ADDR)
    	val newMode : Byte = ((oldMode & 0x7F) | 0x10).toByte;

    	this.i2c.write(DEVICE_ADDR, MODE1, newMode) // go to sleep
    	this.i2c.write(DEVICE_ADDR, PRESCALE, prescale); // set the prescaler
    	this.i2c.write(DEVICE_ADDR, MODE1, oldMode);

        Thread.sleep(5)

    	this.i2c.write(DEVICE_ADDR, MODE1, (oldMode | 0xA1).toByte) //  sets the MODE1 register to turn on auto increment
    }

    def setChannelPWM(channel: Short, on: Int, off: Int) {
        val buffer = Array[Byte](5)
    	buffer(0) = (LED_ON_L + 4 * channel).toByte
    	buffer(1) = on.toByte
    	buffer(2) = (on >>> 8).toByte
    	buffer(3) = off.toByte
    	buffer(4) = (off >>> 8).toByte

    	this.i2c.write(DEVICE_ADDR, buffer)
    }
}
