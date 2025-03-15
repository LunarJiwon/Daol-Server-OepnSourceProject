package org.jiwon.serverCore.events.land.process.manage

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.alternative.LandSettings.TELEPORT_LOCATION
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.events.land.process.manage.buildAllow.BuildAllowPlayerManageProcess
import org.jiwon.serverCore.events.land.process.manage.interactAllow.InteractAllowPlayerManageProcess
import org.jiwon.serverCore.events.land.process.manage.merge.LandMergeProcess
import org.jiwon.serverCore.events.land.process.manage.nameChanage.LandNameConfirmEvent
import org.jiwon.serverCore.events.land.process.manage.owner.OwnerChangeProcess
import org.jiwon.serverCore.events.land.process.manage.reSize.ReSelectLandProcess
import org.jiwon.serverCore.events.land.process.manage.remain.RemainProcess
import org.jiwon.serverCore.events.land.process.manage.sell.LandSellProcess
import org.jiwon.serverCore.events.land.process.manage.settings.LandSettingsProcess
import org.jiwon.serverCore.manager.*
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.messages.LandTexts.CHANGE_LAND_NAME_START
import org.jiwon.serverCore.messages.LandTexts.LIMITED_LAND_AMOUNT
import org.jiwon.serverCore.messages.LandTexts.NOT_FOUND_REMAIN_BLOCK
import org.jiwon.serverCore.messages.LandTexts.REMAIN_START
import org.jiwon.serverCore.messages.LandTexts.RE_SIZE_LAND_START
import org.jiwon.serverCore.messages.LandTexts.SHOW_LAND_AREA
import org.jiwon.serverCore.messages.LandTexts.TELEPORTED_LAND
import org.jiwon.serverCore.utils.Components.mm

class LandManageMenuClickEvent(private val player: Player, private val landName: String):Listener {
    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init{
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun menuClickEvent(event: InventoryClickEvent){
        if(event.whoClicked == player){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager){
                if(event.currentItem?.type != Material.GRAY_STAINED_GLASS_PANE) PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
                when(event.slot){
                    12->{
                        player.closeInventory()
                        if(LandManager.getLandSetting(player,landName,TELEPORT_LOCATION) == null){
                            player.teleport(
                                LocationManager.getLocation(LandManager.getPlayerLand(player, landName)!!.area.firstLocation).add(0.0,1.0,0.0)
                            )
                            player.sendMessage(mm(TELEPORTED_LAND.replace(LAND_NAME,landName)))
                            PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        }else{
                            player.teleport(
                                LocationManager.getLocationFromString(LandManager.getLandSetting(player, landName,
                                    TELEPORT_LOCATION)!!)
                            )
                            player.sendMessage(mm(TELEPORTED_LAND.replace(LAND_NAME,landName)))
                            PlayerManager.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        }

                    }
                    14->{// 영역표시
                        player.closeInventory()
                        AreaManager.spawnParticlesAtBorder(LandManager.getPlayerLand(player,landName)!!.area,player)
                        player.sendMessage(mm(SHOW_LAND_AREA))
                    }
                    19->{ // 이름변경
                        player.closeInventory()
                        registerEvent(LandNameConfirmEvent(player,landName))


                    }
                    20->{ // 재지정
                        player.closeInventory()
                        ReSelectLandProcess(player,landName)
                    }
                    21->{ // 남는블록 새 영역 지정
                        if(LandManager.getPlayerLandSize(player) >= 7){
                            player.sendMessage(LIMITED_LAND_AMOUNT)
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            return
                        }
                        val land = LandManager.getPlayerLand(player,landName)!!
                        if(land.remainArea == 0){
                            player.sendMessage(mm(NOT_FOUND_REMAIN_BLOCK))
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            return
                        }
                        player.closeInventory()
                        RemainProcess(player,land)
                    }
                    22->{ // 영역 병합
                        player.closeInventory()
                        LandMergeProcess(player, landName)
                    }
                    23->{//영역 설정
                        player.closeInventory()
                        LandSettingsProcess(player, landName)
                    }
                    24->{ // 건축허용 플레이어
                        player.closeInventory()
                        BuildAllowPlayerManageProcess(player, landName)

                    }
                    25->{
//                        player.closeInventory()
//                        InteractAllowPlayerManageProcess(player,landName)
                    }
                    30->{
                        player.closeInventory()
                        OwnerChangeProcess(player,landName)
                    }
                    32->{
                        player.closeInventory()
                        LandSellProcess(player, landName)
                    }
                }
            }
        }
    }
}