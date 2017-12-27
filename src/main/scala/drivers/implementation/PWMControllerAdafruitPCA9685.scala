package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.PWMController

class PWMControllerAdafruitPCA9685(channel: Short, executor: PWMAdafruitPCA9685) extends PWMController
{
    override protected def init() {
        this.executor.initDriver()
    }

    override protected def setPWM(on: Int, off: Int) {
        this.executor.setChannelPWM(this.channel, on, off)
    }

    override def getPWMFrequency() : Int = this.executor.getPWMFreq()
    override def getAbsolutePulseMin() : Int = 0
    override def getAbsolutePulseMax() : Int = 4096
}
