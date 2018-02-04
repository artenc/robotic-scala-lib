package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.GyroscopeSensor

class GyroscopeSensorMock extends GyroscopeSensor
{
    override protected def init() {}
    override protected def process() {}

    override def getDpsX() : Float = {
        0
    }
    override def getDpsY() : Float = {
        0
    }
    override def getDpsZ() : Float = {
        0
    }
}
