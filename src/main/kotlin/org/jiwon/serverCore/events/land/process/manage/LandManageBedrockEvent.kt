package org.jiwon.serverCore.events.land.process.manage

import org.bukkit.Sound
import org.geysermc.cumulus.response.SimpleFormResponse
import org.geysermc.floodgate.api.player.FloodgatePlayer
import org.jiwon.serverCore.alternative.LandSettings.TELEPORT_LOCATION
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.events.land.process.manage.buildAllow.BuildAllowPlayerManageProcess
import org.jiwon.serverCore.events.land.process.manage.interactAllow.InteractAllowPlayerManageProcess
import org.jiwon.serverCore.events.land.process.manage.merge.LandMergeProcess
import org.jiwon.serverCore.events.land.process.manage.nameChanage.LandNameConfirmEvent
import org.jiwon.serverCore.events.land.process.manage.owner.OwnerChangeProcess
import org.jiwon.serverCore.events.land.process.manage.reSize.ReSelectLandProcess
import org.jiwon.serverCore.events.land.process.manage.remain.RemainProcess
import org.jiwon.serverCore.events.land.process.manage.sell.LandSellProcess
import org.jiwon.serverCore.events.land.process.manage.settings.LandSettingsProcess
import org.jiwon.serverCore.manager.AreaManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayer
import org.jiwon.serverCore.messages.LandTexts.LIMITED_LAND_AMOUNT
import org.jiwon.serverCore.messages.LandTexts.NOT_FOUND_REMAIN_BLOCK
import org.jiwon.serverCore.messages.LandTexts.SHOW_LAND_AREA
import org.jiwon.serverCore.messages.LandTexts.TELEPORTED_LAND
import org.jiwon.serverCore.utils.Components.mm

class LandManageBedrockEvent(floodgatePlayer: FloodgatePlayer,landName:String,response: SimpleFormResponse) {

    private val player = getPlayer(floodgatePlayer.correctUniqueId)!!

    init{
        when(response.clickedButtonId()){
            0->{
                if(LandManager.getLandSetting(player,landName, TELEPORT_LOCATION) == null){
                    player.teleport(
                        LocationManager.getLocation(LandManager.getPlayerLand(player, landName)!!.area.firstLocation).add(0.0,1.0,0.0)
                    )
                    player.sendMessage(mm(TELEPORTED_LAND.replace(LAND_NAME,landName)))
                    PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                }else{
                    player.teleport(
                        LocationManager.getLocationFromString(
                            LandManager.getLandSetting(player, landName,
                            TELEPORT_LOCATION
                            )!!)
                    )
                    player.sendMessage(mm(TELEPORTED_LAND.replace(LAND_NAME,landName)))
                    PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                }
            }
            1->{
                AreaManager.spawnParticlesAtBorder(LandManager.getPlayerLand(player,landName)!!.area,player)
                player.sendMessage(mm(SHOW_LAND_AREA))
            }
            2->{
                registerEvent(LandNameConfirmEvent(player,landName))
            }
            3->{
                ReSelectLandProcess(player,landName)
            }
            4->{
                if(LandManager.getPlayerLandSize(player) >= 7){
                    player.sendMessage(LIMITED_LAND_AMOUNT)
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                }else{
                    val land = LandManager.getPlayerLand(player,landName)!!
                    if(land.remainArea == 0){
                        player.sendMessage(mm(NOT_FOUND_REMAIN_BLOCK))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                    }else{
                        RemainProcess(player,land)
                    }
                }
            }
            5->{
                LandMergeProcess(player, landName)
            }
            6->{
                LandSettingsProcess(player, landName)
            }
            7->{
                BuildAllowPlayerManageProcess(player, landName)
            }
            8->{
//                InteractAllowPlayerManageProcess(player,landName)
            }
            9->{
                OwnerChangeProcess(player,landName)
            }
            10->{
                LandSellProcess(player, landName)
            }
        }
    }




}