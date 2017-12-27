package com.artenc.robotic.drivers

abstract class SwitchController extends DriverBase
{
    def on()
    def off()
    def toggle() : Boolean
    def getCurrentState() : Boolean
}
