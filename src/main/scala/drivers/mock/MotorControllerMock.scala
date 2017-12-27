package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.MotorController

class MotorControllerMock extends MotorController
{
    override protected def init() {}
    override def sendThrottle(percent: Float) {}
}
