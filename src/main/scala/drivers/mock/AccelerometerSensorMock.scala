package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.AccelerometerSensor

class AccelerometerSensorMock extends AccelerometerSensor
{
    override protected def init() {}
    override def processDriver() {}

    override def getRawX() : Float = {
        0
    }
    override def getRawY() : Float = {
        0
    }
    override def getRawZ() : Float = {
        0
    }
}
