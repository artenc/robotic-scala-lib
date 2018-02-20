package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.TemperatureSensor

class TemperatureSensorAdafruitBMP085(sensor: SensorAdafruitBMP085) extends TemperatureSensor
{
    protected override def init() {
        this.sensor.initDriver()
    }

    protected override def process() {
        this.sensor.processDriver()
    }

    override def getCelsius() : Float = {
        this.sensor.getTemperatureCelsius()
    }
}
