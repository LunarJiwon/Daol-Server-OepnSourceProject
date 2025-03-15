package org.jiwon.serverCore.events.economy.store

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.alternative.ReplaceTexts.AMOUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.ITEM_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PURCHASE_PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.SALE_PRICE
import org.jiwon.serverCore.events.DefaultInventoryEvent
import org.jiwon.serverCore.manager.EconomyManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.EventManager.unregisterEvent
import org.jiwon.serverCore.manager.InventoryManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.EconomyTexts.CANT_PURCHASE_ITEM
import org.jiwon.serverCore.messages.EconomyTexts.CANT_SALE_ITEM
import org.jiwon.serverCore.messages.EconomyTexts.INVENTORY_FULL
import org.jiwon.serverCore.messages.EconomyTexts.LACK_BALANCE
import org.jiwon.serverCore.messages.EconomyTexts.LACK_ITEM
import org.jiwon.serverCore.messages.EconomyTexts.PURCHASE_ITEM
import org.jiwon.serverCore.messages.EconomyTexts.SALE_ITEM
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.EconomyData
import org.jiwon.serverCore.utils.NumberUtils

class ShoppingEvent(val player: Player, private val economyData:EconomyData, private val isPurchase:Boolean?):Listener {

    private val defaultEvent = DefaultInventoryEvent(player,this)

    init{
        registerEvent(defaultEvent)
    }

    @EventHandler
    private fun inventoryEvent(event:InventoryClickEvent){
        if((event.whoClicked as Player) == player){
            event.isCancelled = true
            if(event.currentItem == null) return
            if(event.clickedInventory!!.holder !is InventoryManager) return
            if(event.currentItem!!.type == Material.GRAY_STAINED_GLASS_PANE) return
            if(isPurchase == null){
                if(event.isLeftClick){
                    if(economyData.purchasePrice != -1) {
                        player.openInventory(EconomyManager.openPurchaseMenu(economyData))
                        instance.server.pluginManager.registerEvents(ShoppingEvent(player, economyData, true), instance)

                        PlayerManager.playerSound(player,Sound.BLOCK_NOTE_BLOCK_HARP)

                        HandlerList.unregisterAll(defaultEvent)
                        HandlerList.unregisterAll(this)
                    }else{
                        PlayerManager.playerSound(player,Sound.BLOCK_NOTE_BLOCK_BASS)
                        player.sendMessage(mm(CANT_PURCHASE_ITEM))
                        //구매불가 메시지 출력
                    }
                }else if(event.isRightClick){
                    if(economyData.salePrice != -1) {
                        player.openInventory(
                            EconomyManager.openSaleMenu(
                                economyData,
                                PlayerManager.getAllPlayerItemAmount(player, economyData.item)
                            )
                        )
                        instance.server.pluginManager.registerEvents(
                            ShoppingEvent(player, economyData, false),
                            instance
                        )

                        PlayerManager.playerSound(player,Sound.BLOCK_NOTE_BLOCK_HARP)

                    }else{
                        PlayerManager.playerSound(player,Sound.BLOCK_NOTE_BLOCK_BASS)
                        player.sendMessage(mm(CANT_SALE_ITEM))
                    }
                }
            }
            else if(isPurchase) {
                val purchaseResult = StoreProcess(player, economyData).itemPurchase(event.slot != 3)
                if (purchaseResult != null) {
                    if (purchaseResult) {
                        player.sendMessage(
                            mm(
                                PURCHASE_ITEM.replace(ITEM_NAME, economyData.item.name).replace(
                                    PURCHASE_PRICE, NumberUtils.format(economyData.purchasePrice*(if (event.slot == 3) 1 else economyData.item.maxStackSize))
                                ).replace(AMOUNT, (if (event.slot == 3) 1 else economyData.item.maxStackSize).toString())
                            )
                        )
                        PlayerManager.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP)
                    } else {
                        player.sendMessage(mm(LACK_BALANCE))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                    }
                } else {
                    player.sendMessage(mm(INVENTORY_FULL))
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                }
            }else{
                val saleResult = StoreProcess(player, economyData).itemSale(event.slot != 3)
                if(saleResult != -1){
                    player.sendMessage(mm(SALE_ITEM
                        .replace(ITEM_NAME,economyData.item.name)
                        .replace(SALE_PRICE,NumberUtils.format(if(event.slot == 3)economyData.salePrice else economyData.salePrice * saleResult))
                        .replace(AMOUNT,if(event.slot == 3)"1" else "$saleResult")))
                    PlayerManager.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP)
                }else{
                    player.sendMessage(mm(LACK_ITEM))
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                }


            }
        }
    }
}