package org.jiwon.serverCore.events.land.process.manage.settings

import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.alternative.LandSettings
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager

class LandSettingsProcess(private val player:Player,private val landName:String) {

    init{
        if(floodgateInstance.isFloodgatePlayer(player.uniqueId)) bedrockProcess()
        else javaProcess()
    }

    private fun javaProcess(){
        registerEvent(LandSettingsMenuClickEvent(player,landName))
        player.openInventory(LandManager.landSettingsMenu(
            player, landName
        ))
    }

    private fun bedrockProcess(){
        floodgateInstance.getPlayer(player.uniqueId).sendForm(LandManager.landSettingsMenuBedrock(player,landName){
            when(it.clickedButtonId()){
                0->{
                    LandSetSettings(player,landName, LandSettings.TELEPORT_LOCATION)
                }
                1->{
                    LandSetSettings(player,landName, LandSettings.WELCOME_MESSAGE)
                }
                2->{
                    LandSetSettings(player,landName, LandSettings.EXIT_MESSAGE)
                }
                3->{
                    LandSetSettings(player,landName, LandSettings.ALLOW_EXPLODE)
                }
            }
        })
    }


}