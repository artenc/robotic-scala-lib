package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.GyroscopeSensor
import com.artenc.robotic.drivers.I2CController

import com.artenc.utility.TimeTrackUtility
import com.artenc.utility.BitUtility

class GyroscopeSensorAdafruitL3GD20(i2c: I2CController) extends GyroscopeSensor
{
    private val I2C_ADDRESS = 0x6B

    private val SENSITIVITY_COMPENSATION : Float = 0.00875f;

    private var x : Float = 0
    private var y : Float = 0
    private var z : Float = 0

    protected override def init() {
        this.i2c.initDriver()

        // Who am I
        this.i2c.write(I2C_ADDRESS, 0x0F.toByte)

        val buffer = new Array[Byte](1)
        this.i2c.read(I2C_ADDRESS, buffer, 0, 1)

        if (buffer(0) != 0xD4.toByte && buffer(0) != 0xD7.toByte)
            throw new Exception("Failed to initialize, this is not the right device.")

        // Enable gyro
        this.i2c.write(I2C_ADDRESS, 0x20, 0x0F.toByte)

        // Set resolution to 250DPS
        this.i2c.write(I2C_ADDRESS, 0x23, 0x00.toByte)
    }

    protected override def process() {
        this.i2c.write(I2C_ADDRESS, (0x28 | 0x80).toByte)

        val buffer = new Array[Byte](6)
        this.i2c.read(I2C_ADDRESS, buffer, 0, 6)

    	val xlo = buffer(0) & 0xFF
        val xhi = buffer(1)
    	val ylo = buffer(2) & 0xFF
    	val yhi = buffer(3)
    	val zlo = buffer(4) & 0xFF
    	val zhi = buffer(5)

        this.x = (xlo | (xhi << 8)) * SENSITIVITY_COMPENSATION
        this.y = (ylo | (yhi << 8)) * SENSITIVITY_COMPENSATION * -1
        this.z = (zlo | (zhi << 8)) * SENSITIVITY_COMPENSATION * -1
    }

    override def getDpsX() : Float = {
        this.x
    }
    override def getDpsY() : Float = {
        this.y
    }
    override def getDpsZ() : Float = {
        this.z
    }
}
