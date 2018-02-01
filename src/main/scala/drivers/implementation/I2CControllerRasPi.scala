package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.I2CController

import com.pi4j.platform.Platform
import com.pi4j.platform.PlatformManager
import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory

class I2CControllerRasPi extends I2CController
{
    private var i2c : I2CBus = null
    private var i2cDevices = scala.collection.mutable.Map[Int, I2CDevice]()

    override protected def init() {
        PlatformManager.setPlatform(Platform.RASPBERRYPI)
        this.i2c = I2CFactory.getInstance(I2CBus.BUS_1);
    }

    private def getDevice(addr: Int) : I2CDevice = {
        if (!this.i2cDevices.contains(addr))
            this.i2cDevices(addr) = this.i2c.getDevice(addr)
        return this.i2cDevices(addr)
    }

    override def write(addr: Int, b: Byte) {
        getDevice(addr).write(b)
    }
    override def write(addr: Int, buffer: Array[Byte], offset: Int, size: Int) {
        getDevice(addr).write(buffer, offset, size)
    }
    override def write(addr: Int, buffer: Array[Byte]) {
        getDevice(addr).write(buffer)
    }
    override def write(addr: Int, key: Int, b: Byte) {
        getDevice(addr).write(key.toByte, b)
    }
    override def write(addr: Int, key: Int, buffer: Array[Byte], offset: Int, size: Int) {
        getDevice(addr).write(key, buffer, offset, size)
    }
    override def write(addr: Int, key: Int, buffer: Array[Byte]) {
        getDevice(addr).write(key, buffer)
    }

    override def read(addr: Int) : Byte = {
        getDevice(addr).read().toByte
    }
    override def read(addr: Int, buffer: Array[Byte], offset: Int, size: Int) : Int = {
        getDevice(addr).read(buffer, offset, size)
    }
    override def read(addr: Int, key: Int) : Byte = {
        getDevice(addr).read(key).toByte
    }
    override def read(addr: Int, key: Int, buffer: Array[Byte], offset: Int, size: Int) : Int = {
        getDevice(addr).read(key, buffer, offset, size)
    }
}
