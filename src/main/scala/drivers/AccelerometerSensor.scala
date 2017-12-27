package com.artenc.robotic.drivers

import scala.math.atan2
import scala.math.sqrt

abstract class AccelerometerSensor extends DriverBase with SensorTrait
{
    private val MG_PER_LSB : Float = 0.001f;
    private val GRAVITY_EARTH : Float = 9.80665f;
    private val PI : Float = 3.14159265f;

    protected def getRawX() : Float
    protected def getRawY() : Float
    protected def getRawZ() : Float

    def getX() : Float = {
        getRawX() * MG_PER_LSB * GRAVITY_EARTH
    }
    def getY() : Float = {
        getRawY() * MG_PER_LSB * GRAVITY_EARTH
    }
    def getZ() : Float = {
        getRawZ() * MG_PER_LSB * GRAVITY_EARTH
    }

    def getOrientation() : Orientation = {
        getOrientation(getX(), getY(), getZ())
    }

    private def getOrientation(accelX: Float, accelY: Float, accelZ: Float) : Orientation = {
        val signOfZ : Float = if (accelZ >= 0) 1.0f else -1.0f

        val t_roll : Float = accelX * accelX + accelZ * accelZ
        val t_pitch : Float = accelY * accelY + accelZ * accelZ

        Orientation(
            (atan2(accelY, sqrt(t_roll)) * 180 / PI).toFloat,
            (atan2(accelX, signOfZ * sqrt(t_pitch)) * 180 / PI).toFloat
        )
    }

    case class Orientation(roll: Float, pitch: Float)
}
