package org.jiwon.serverCore.utils

import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance

interface PlatformTaskProcess {

    fun platformCheck(player: Player){
        if(floodgateInstance.isFloodgatePlayer(player.uniqueId)) bedrockRun()
        else javaRun()
    }

    fun bedrockRun()

    fun javaRun()
}