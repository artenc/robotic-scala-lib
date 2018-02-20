package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.AirPressureSensor

class AirPressureSensorAdafruitBMP085(sensor: SensorAdafruitBMP085) extends AirPressureSensor
{
    protected override def init() {
        this.sensor.initDriver()
    }

    protected override def process() {
        this.sensor.processDriver()
    }

    override def getKPA() : Float = {
        this.sensor.getPressureKPA()
    }
}
