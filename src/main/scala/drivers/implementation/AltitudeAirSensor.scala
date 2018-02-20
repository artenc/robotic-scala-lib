package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.AltitudeSensor
import com.artenc.robotic.drivers.AirPressureSensor
import com.artenc.robotic.drivers.TemperatureSensor

import scala.math.pow

class AltitudeAirSensor(pressureSensor: AirPressureSensor, temperatureSensor: TemperatureSensor)
        extends AltitudeSensor
{
    private val SEA_LEVEL_HPA : Float = 1013.25f

    protected override def init() {
        this.pressureSensor.initDriver()
        this.temperatureSensor.initDriver()
    }

    protected override def process() {
        this.pressureSensor.processDriver()
        this.temperatureSensor.processDriver()
    }

    override def getMeters() : Float = {
    	return (
            (pow((SEA_LEVEL_HPA / this.pressureSensor.getKPA()), 0.190223F).toFloat - 1.0f) *
            (this.temperatureSensor.getCelsius() + 273.15F)
        ) / 0.0065f
    }
}
