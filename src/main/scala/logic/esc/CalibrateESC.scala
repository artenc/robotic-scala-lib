package com.artenc.robotic.logic.esc

import com.artenc.robotic.drivers.MotorController
import com.artenc.robotic.drivers.SwitchController

class CalibrateESC private(motorControllers: Array[MotorController], motorSwitch: SwitchController, switchOnDelayMs: Long)
{
    def this(motorControllers: Array[MotorController], motorSwitch: SwitchController) {
        this(motorControllers, motorSwitch, 0)
    }
    def this(motorControllers: Array[MotorController], switchOnDelayMs: Long) {
        this(motorControllers, null, switchOnDelayMs)
    }
    def this(motorController: MotorController, motorSwitch: SwitchController) {
        this(Array(motorController), motorSwitch)
    }
    def this(motorController: MotorController, switchOnDelayMs: Long) {
        this(Array(motorController), switchOnDelayMs)
    }

    private var highReadyCallback : () => Unit = null

    def setHighReadyCallback(highReadyCallback: () => Unit) {
        this.highReadyCallback = highReadyCallback
    }

    def calibrate() {
        val switchInitialState = if (this.motorSwitch != null) this.motorSwitch.getCurrentState() else false

        if (switchInitialState)
            setMotorsSwitch(false)

        setMotorsThrottle(1)

        if (this.highReadyCallback != null)
            this.highReadyCallback()

        if (this.switchOnDelayMs > 0)
            Thread.sleep(this.switchOnDelayMs)

        setMotorsSwitch(true)
        setMotorsThrottle(0)

        if (!switchInitialState && this.motorSwitch != null)
            this.motorSwitch.off()
    }

    private def setMotorsThrottle(throttle: Float) {
        this.motorControllers.foreach(_.sendThrottle(throttle))
        Thread.sleep(1000)
    }

    private def setMotorsSwitch(on: Boolean) {
        if (this.motorSwitch == null)
            return

        if (on)
            this.motorSwitch.on()
        else
            this.motorSwitch.off()

        Thread.sleep(1000)
    }
}
