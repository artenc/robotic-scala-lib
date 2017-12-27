package com.artenc.robotic.drivers

abstract class DriverBase
{
    private var isInitialized = false

    def initDriver() {
        if (!this.isInitialized) {
            this.isInitialized = true
            init()
        }
    }
    protected def init()
}
