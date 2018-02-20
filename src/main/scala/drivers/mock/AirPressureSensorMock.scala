package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.AirPressureSensor

class AirPressureSensorMock extends AirPressureSensor
{
    override protected def init() {}
    override protected def process() {}

    override def getKPA() : Float = {
        0
    }
}
