package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.AccelerometerSensor
import com.artenc.robotic.drivers.I2CController

class AccelerometerSensorAdafruitLSM303(i2c: I2CController) extends AccelerometerSensor
{
    private val I2C_ADDRESS = 0x32 >>> 1

    private var x : Float = 0
    private var y : Float = 0
    private var z : Float = 0

    protected override def init() {
        this.i2c.initDriver()
        this.i2c.write(I2C_ADDRESS, 0x20, 0x27.toByte)
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

    	this.x = (xlo | (xhi << 8)) >> 4
    	this.y = (ylo | (yhi << 8)) >> 4
    	this.z = (zlo | (zhi << 8)) >> 4
    }

    protected override def getRawX() : Float = {
        this.x
    }
    protected override def getRawY() : Float = {
        this.y
    }
    protected override def getRawZ() : Float = {
        this.z
    }
}
