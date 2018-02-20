package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.AltitudeSensor

class AltitudeSensorMock extends AltitudeSensor
{
    override protected def init() {}
    override protected def process() {}

    override def getMeters() : Float = {
        0
    }
}
