package org.jiwon.serverCore.events.land.process.purchase

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.EconomyTexts
import org.jiwon.serverCore.messages.LandTexts.LIMITED_LAND_AMOUNT
import org.jiwon.serverCore.messages.LandTexts.START_LOCATION_SELECT
import org.jiwon.serverCore.utils.Components.mm

class LandPurchaseMenuClickEvent(private val player: Player):Listener {
    private val defaultInventoryEvent = DefaultInventoryEvent(player,this)

    init {
        registerEvent(defaultInventoryEvent)
    }

    @EventHandler
    private fun menuClickEvent(event: InventoryClickEvent) {
        if(player == event.whoClicked){
            event.isCancelled = true
            if(event.clickedInventory?.holder is InventoryManager){
                val landOptions = LandManager.getSaleLands()
                if(event.slot == 2 || event.slot == 3 || event.slot == 5 || event.slot == 6) {
                    if(LandManager.getPlayerLandSize(player) >= 7){
                        player.sendMessage(mm(LIMITED_LAND_AMOUNT))
                        return
                    }
                    when (event.slot) {
                        2 -> { //제일 작은
                            if(BalanceManager.getPlayerBalance(player) >= landOptions[0].get("price").asInt) {
                                LandPurchaseProcess(
                                    player,
                                    landOptions[0].get("size").asString.split("x")[0].toInt() * landOptions[0].get("size").asString.split(
                                        "x"
                                    )[1].toInt(),
                                    landOptions[0].get("price").asInt
                                )
                            }else{
                                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                                player.sendMessage(EconomyTexts.LACK_BALANCE)
                            }
                        }
                        3->{
                            if(BalanceManager.getPlayerBalance(player) >= landOptions[1].get("price").asInt) {
                                LandPurchaseProcess(
                                    player,
                                    landOptions[1].get("size").asString.split("x")[0].toInt() * landOptions[1].get("size").asString.split(
                                        "x"
                                    )[1].toInt(),
                                    landOptions[1].get("price").asInt
                                )
                            }else{
                                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                                player.sendMessage(EconomyTexts.LACK_BALANCE)
                            }
                        }
                        5->{
                                if(BalanceManager.getPlayerBalance(player) >= landOptions[2].get("price").asInt) {

                                    LandPurchaseProcess(
                                        player,
                                        landOptions[2].get("size").asString.split("x")[0].toInt() * landOptions[2].get("size").asString.split(
                                            "x"
                                        )[1].toInt(),
                                        landOptions[2].get("price").asInt
                                    )
                                }else{
                                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                                    player.sendMessage(EconomyTexts.LACK_BALANCE)
                                }
                        }
                        6->{
                                if(BalanceManager.getPlayerBalance(player) >= landOptions[3].get("price").asInt) {

                                    LandPurchaseProcess(
                                        player,
                                        landOptions[3].get("size").asString.split("x")[0].toInt() * landOptions[3].get("size").asString.split(
                                            "x"
                                        )[1].toInt(),
                                        landOptions[3].get("price").asInt
                                    )
                                }else{
                                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                                    player.sendMessage(EconomyTexts.LACK_BALANCE)
                                }
                        }
                    }
                    player.closeInventory()
                    unregisterEvent(defaultInventoryEvent)
                    unregisterEvent(this)
                }
            }
        }
    }
}