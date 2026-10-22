package com.example.galaxyshooter.physics

object Collision {
    init { System.loadLibrary("galaxy") }
    external fun nativeVersion(): String
    external fun circlesOverlap(x1: Float, y1: Float, r1: Float,
                                x2: Float, y2: Float, r2: Float): Boolean
}
