package com.artenc.robotic.drivers.mock

import com.artenc.robotic.drivers.SwitchController

class SwitchControllerMock extends SwitchController
{
    private var currentState = false

    override protected def init() {}

    override def on() {
        this.currentState = true
    }
    override def off() {
        this.currentState = false
    }
    override def toggle() : Boolean = {
        this.currentState = !this.currentState
        return this.currentState
    }
    override def getCurrentState() : Boolean = {
        this.currentState
    }
}
