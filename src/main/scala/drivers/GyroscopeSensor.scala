package com.artenc.robotic.drivers

abstract class GyroscopeSensor extends DriverBase with SensorTrait
{
    def getDpsX() : Float
    def getDpsY() : Float
    def getDpsZ() : Float
}
