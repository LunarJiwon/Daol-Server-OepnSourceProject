package org.jiwon.serverCore.utils

import java.util.UUID

data class LandData(
    val landName:String,
    val area:AreaData,
    val remainArea:Int,
    val settings:HashMap<String,String>,
    val buildAllowPlayers:ArrayList<String>,
    val interactAllowPlayers:ArrayList<String>
)
