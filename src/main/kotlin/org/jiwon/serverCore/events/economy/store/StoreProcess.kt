package org.jiwon.serverCore.events.economy.store

import org.bukkit.entity.Player
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.ItemManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.utils.EconomyData

class StoreProcess(private val player: Player, private val economyData: EconomyData) {

    fun itemPurchase(isSet:Boolean):Boolean?{
        if(!PlayerManager.isPlayerInventoryFull(player,economyData.item,!isSet,economyData.isRestrict)) {
            val finalPrice = if (isSet) economyData.purchasePrice * economyData.item.maxStackSize else economyData.purchasePrice
            if (BalanceManager.getPlayerBalance(player) >= finalPrice) {
                BalanceManager.removePlayerBalance(player,finalPrice)
                PlayerManager.addToPlayerInventoryItem(player,ItemManager.createItemFromStoreData(economyData,if(isSet)  economyData.item.maxStackSize else 1))
                return true
            }else{
                return false
            }
        }
        return null
    }

    fun itemSale(isAll:Boolean):Int {
        if (PlayerManager.getAllPlayerItemAmount(player,economyData.item) != 0) {
            val itemAmount = PlayerManager.getAllPlayerItemAmount(
                player,
                economyData.item
            )
            val finalPrice = if (isAll) economyData.salePrice * itemAmount else economyData.salePrice
            BalanceManager.addPlayerBalance(player, finalPrice)
            if(isAll){
                PlayerManager.removeToPlayerInventoryAllItem(player,economyData.item)
                return itemAmount
            }
            PlayerManager.removeToPlayerInventoryOneItem(
                player,economyData.item
            )
            return itemAmount
        }
        return -1

    }
}