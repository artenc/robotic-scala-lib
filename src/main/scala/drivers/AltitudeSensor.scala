package com.artenc.robotic.drivers

abstract class AltitudeSensor extends DriverBase with SensorTrait
{
    def getMeters() : Float
}
