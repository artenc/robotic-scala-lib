package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.I2CController

class I2CControllerMock extends I2CController
{
    override protected def init() {}

    override def write(addr: Int, b: Byte) {
    }
    override def write(addr: Int, buffer: Array[Byte], offset: Int, size: Int) {
    }
    override def write(addr: Int, buffer: Array[Byte]) {
    }
    override def write(addr: Int, key: Int, b: Byte) {
    }
    override def write(addr: Int, key: Int, buffer: Array[Byte], offset: Int, size: Int) {
    }
    override def write(addr: Int, key: Int, buffer: Array[Byte]) {
    }

    override def read(addr: Int) : Byte = {
        0
    }
    override def read(addr: Int, buffer: Array[Byte], offset: Int, size: Int) : Int = {
        0
    }
    override def read(addr: Int, key: Int) : Byte = {
        0
    }
    override def read(addr: Int, key: Int, buffer: Array[Byte], offset: Int, size: Int) : Int = {
        0
    }
}
