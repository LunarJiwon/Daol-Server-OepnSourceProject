package org.jiwon.serverCore.events.economy

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.events.economy.store.BedrockShopping
import org.jiwon.serverCore.events.economy.store.ShoppingEvent
import org.jiwon.serverCore.manager.EconomyManager
import org.jiwon.serverCore.manager.EconomyManager.openStore
import org.jiwon.serverCore.manager.EconomyManager.openBedrockStore
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.EconomyTexts.CANT_PURCHASE_ITEM
import org.jiwon.serverCore.messages.EconomyTexts.CANT_SALE_ITEM
import org.jiwon.serverCore.utils.Components.mm

class PlayerStoreSignClickEvent(event:PlayerInteractEvent){

    companion object{
        var ignorePlayer = ArrayList<Player>()
    }

    init{
        if(ignorePlayer.find { p -> event.player == p } == null) {
            if (event.clickedBlock != null) {
                if (event.clickedBlock!!.type == Material.OAK_WALL_SIGN) {
                    val store = EconomyManager.getStoreInSign(event.clickedBlock!!.location)
                    if (store != null) {
                        event.isCancelled = true
                        val player = event.player
                        if (!floodgateInstance.isFloodgatePlayer(player.uniqueId)) {
                            player.openInventory(openStore(store))
                            registerEvent(ShoppingEvent(player, store, null))
                        } else {
                            val floodgatePlayer = floodgateInstance.getPlayer(player.uniqueId)
                            floodgatePlayer.sendForm(
                                openBedrockStore(store) { res ->
                                    val isPurchase = res.clickedButton().text() == "구매"
                                    if(isPurchase){
                                        if(store.purchasePrice == -1){
                                            player.sendMessage(mm(CANT_PURCHASE_ITEM))
                                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                                            return@openBedrockStore
                                        }
                                    }else{
                                        if(store.salePrice == -1){
                                            player.sendMessage(mm(CANT_SALE_ITEM))
                                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                                            return@openBedrockStore
                                        }
                                    }
                                    BedrockShopping(floodgatePlayer,store, isPurchase = isPurchase)

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}