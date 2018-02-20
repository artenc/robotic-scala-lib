package com.artenc.robotic.drivers

abstract class AirPressureSensor extends DriverBase with SensorTrait
{
    def getKPA() : Float
}
