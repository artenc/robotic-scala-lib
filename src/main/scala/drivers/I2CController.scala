package com.artenc.robotic.drivers

abstract class I2CController extends DriverBase
{
    def write(addr: Int, b: Byte)
    def write(addr: Int, buffer: Array[Byte], offset: Int, size: Int)
    def write(addr: Int, buffer: Array[Byte])
    def write(addr: Int, key: Int, b: Byte)
    def write(addr: Int, key: Int, buffer: Array[Byte], offset: Int, size: Int)
    def write(addr: Int, key: Int, buffer: Array[Byte])

    def read(addr: Int) : Byte
    def read(addr: Int, buffer: Array[Byte], offset: Int, size: Int) : Int
    def read(addr: Int, key: Int) : Byte
    def read(addr: Int, key: Int, buffer: Array[Byte], offset: Int, size: Int) : Int
}
