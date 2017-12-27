package com.artenc.robotic.drivers

abstract class PWMController extends DriverBase
{
    private var pulseMin : Int = getAbsolutePulseMin()
    private var pulseRange : Int = getAbsolutePulseMax() - getAbsolutePulseMin()

    def getPWMFrequency() : Int
    def getAbsolutePulseMin() : Int
    def getAbsolutePulseMax() : Int
    protected def setPWM(on: Int, off: Int)

    def setPWM(percent: Float) {
        if (percent < 0 || percent > 1)
            throw new Exception("Percent can be between 0 and 1")

        setPWM(
            this.pulseMin,
            this.pulseMin + (this.pulseRange * percent).toInt
        )
    }

    def setPulseRange(min: Int, max: Int) {
        if (max > getAbsolutePulseMax())
            throw new Exception("Pulse range Max can't be larger then absolute max")
        if (min < getAbsolutePulseMin())
            throw new Exception("Pulse range Min can't be smaller then absolute min")
        if (min >= max)
            throw new Exception("Min pulse must be smaller then max pulse")

        this.pulseMin = min
        this.pulseRange = max - min
    }

    def setPulseWidthRange(msMin: Float, msMax: Float) {
	    val periodMs : Float = 1000f / getPWMFrequency()
	    val tickWidth : Float = periodMs / getAbsolutePulseMax()

	    setPulseRange((msMin / tickWidth).toInt, (msMax / tickWidth).toInt)
    }
}
