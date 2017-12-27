package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.VoltageSensor

class VoltageSensorMock extends VoltageSensor
{
    private val VOLTAGE_CHANGE: Float = -0.005f

    private var currentVoltage: Float = 12.4f

    override protected def init() {}
    override def processDriver() {
        this.currentVoltage += VOLTAGE_CHANGE
    }

    override def getVoltage() : Float = {
        this.currentVoltage
    }
}
