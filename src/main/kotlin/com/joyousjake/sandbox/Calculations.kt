package com.joyousjake.sandbox

/**
 * Created by jacob on 5/12/17.
 */

fun finalVelocities(e1:Double, e2: Double, m1: Double, m2: Double, v1: Double, v2: Double): Pair<Double, Double> {
    val e = maxOf(e1, e2)
    val v1f = (m1*v1+m2*v2-m2*e*(v1-v2))/(m1+m2)
    val v2f = (m1*v1+m2*v2-m1*v1f)/m2
    return Pair(v1f.abs, v2f.abs)
}

val Double.abs
    get() = Math.abs(this)
