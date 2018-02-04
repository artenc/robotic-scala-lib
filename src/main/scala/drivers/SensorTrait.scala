package com.artenc.robotic.drivers

trait SensorTrait
{
    private var isProcessing : Boolean = false

    def processDriver() {
        if (this.isProcessing)
            return

        this.isProcessing = true
        this.process()
        this.isProcessing = false
    }

    protected def process()

    def getIsProcessing() : Boolean = {
        this.isProcessing
    }
}
