package org.jiwon.serverCore.utils

import java.util.*

data class LocationData (
    val worldUUID: UUID,
    val x:Double,
    val y:Double,
    val z:Double,
    val yaw : Float,
    val pitch:Float
)