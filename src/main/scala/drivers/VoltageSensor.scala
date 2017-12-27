package com.artenc.robotic.drivers

abstract class VoltageSensor extends DriverBase with SensorTrait
{
    def getVoltage() : Float
}
