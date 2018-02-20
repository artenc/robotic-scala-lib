package com.artenc.robotic.drivers

abstract class TemperatureSensor extends DriverBase with SensorTrait
{
    def getCelsius() : Float
}
