package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.TemperatureSensor

class TemperatureSensorMock extends TemperatureSensor
{
    override protected def init() {}
    override protected def process() {}

    override def getCelsius() : Float = {
        0
    }
}
