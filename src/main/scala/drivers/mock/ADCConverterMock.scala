package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.ADCConverter

class ADCConverterMock extends ADCConverter
{
    override protected def init() {}

    override def getVoltage() : Float = {
        0
    }
}
