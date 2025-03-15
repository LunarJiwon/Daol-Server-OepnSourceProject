package org.jiwon.serverCore.events.land.process.manage.settings

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.LandSettings
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.SET_EXPLODE_ALLOW
import org.jiwon.serverCore.messages.LandTexts.SET_EXPLODE_DENY
import org.jiwon.serverCore.messages.LandTexts.SET_LAND_EXIT_MESSAGE
import org.jiwon.serverCore.messages.LandTexts.SET_LAND_EXIT_MESSAGE_DISABLE
import org.jiwon.serverCore.messages.LandTexts.SET_LAND_WELCOME_MESSAGE_DISABLE
import org.jiwon.serverCore.messages.LandTexts.SET_LAND_TELEPORT_LOCATION
import org.jiwon.serverCore.messages.LandTexts.SET_LAND_TELEPORT_LOCATION_ON_AREA
import org.jiwon.serverCore.messages.LandTexts.SET_LAND_WELCOME_MESSAGE
import org.jiwon.serverCore.messages.LandTexts.START_SET_LAND_EXIT_MESSAGE
import org.jiwon.serverCore.messages.LandTexts.START_SET_LAND_WELCOME_MESSAGE
import org.jiwon.serverCore.utils.Components.mm

class LandSetSettings(private val player:Player, private val landName:String, private val settingsName:String) {

    init{
        when(settingsName){
            LandSettings.TELEPORT_LOCATION->{
                if(LandManager.isOverlapLocation(player.location).filter { land -> land.key == player.uniqueId.toString() && land.value.landName == landName }.isNotEmpty()){
                    LandManager.setLandSetting(player,landName, LandSettings.TELEPORT_LOCATION, LocationManager.createStringToLocationData(player.location))
                    PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                    player.sendMessage(mm(SET_LAND_TELEPORT_LOCATION))
                }else{
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                    player.sendMessage(mm(SET_LAND_TELEPORT_LOCATION_ON_AREA))
                }
            }
            LandSettings.WELCOME_MESSAGE->{
                player.sendMessage(mm(START_SET_LAND_WELCOME_MESSAGE))
                registerEvent(LandSettingPlayerInputEvent(player){
                    if(it == "삭제"){
                        player.sendMessage(mm(SET_LAND_WELCOME_MESSAGE_DISABLE.replace(LAND_NAME,landName)))
                        PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        LandManager.setLandSetting(player,landName,LandSettings.WELCOME_MESSAGE,it)
                    }else{
                        player.sendMessage(mm(SET_LAND_WELCOME_MESSAGE.replace(LAND_NAME,landName)))
                        PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        LandManager.setLandSetting(player,landName,LandSettings.WELCOME_MESSAGE,it)
                    }

                })
            }
            LandSettings.EXIT_MESSAGE->{
                player.sendMessage(mm(START_SET_LAND_EXIT_MESSAGE))
                registerEvent(LandSettingPlayerInputEvent(player){
                    if(it == "삭제"){
                        player.sendMessage(mm(SET_LAND_EXIT_MESSAGE_DISABLE.replace(LAND_NAME,landName)))
                        PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        LandManager.setLandSetting(player,landName,LandSettings.EXIT_MESSAGE,it)
                    }else{
                        player.sendMessage(mm(SET_LAND_EXIT_MESSAGE.replace(LAND_NAME,landName)))
                        PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        LandManager.setLandSetting(player,landName,LandSettings.EXIT_MESSAGE,it)
                    }

                })

            }
            LandSettings.ALLOW_EXPLODE->{
                val currentSetting = LandManager.getLandSetting(player,landName, LandSettings.ALLOW_EXPLODE)
                if(currentSetting == "true"){
                    player.sendMessage(mm(SET_EXPLODE_DENY.replace(LAND_NAME,landName)))
                    PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                    LandManager.setLandSetting(player, landName,LandSettings.ALLOW_EXPLODE,"false")
                }else{
                    player.sendMessage(mm(SET_EXPLODE_ALLOW.replace(LAND_NAME,landName)))
                    PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                    LandManager.setLandSetting(player, landName,LandSettings.ALLOW_EXPLODE,"true")
                }
                LandSettingsProcess(player, landName)
            }
        }
    }

}