package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.PWMController

class PWMControllerMock extends PWMController
{
    override protected def init() {}
    override protected def setPWM(on: Int, off: Int) {}

    override def getPWMFrequency() : Int = 100
    override def getAbsolutePulseMin() : Int = 0
    override def getAbsolutePulseMax() : Int = 10000
}
