package com.artenc.robotic.drivers.implementation

import com.artenc.robotic.drivers.MotorController
import com.artenc.robotic.drivers.PWMController

class MotorControllerPWM(pwmController: PWMController) extends MotorController
{
    override protected def init() {
        this.pwmController.initDriver()
    }

    override def sendThrottle(percent: Float) {
        this.pwmController.setPWM(percent)
    }
}
