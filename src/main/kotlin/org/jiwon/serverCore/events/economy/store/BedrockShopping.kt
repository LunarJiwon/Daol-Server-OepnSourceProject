package org.jiwon.serverCore.events.economy.store

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.geysermc.cumulus.component.SliderComponent
import org.geysermc.floodgate.api.player.FloodgatePlayer
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.alternative.ReplaceTexts.AMOUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.ITEM_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PURCHASE_PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.SALE_PRICE
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.EconomyManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayer
import org.jiwon.serverCore.messages.EconomyTexts.INVENTORY_FULL
import org.jiwon.serverCore.messages.EconomyTexts.LACK_BALANCE
import org.jiwon.serverCore.messages.EconomyTexts.LACK_ITEM
import org.jiwon.serverCore.messages.EconomyTexts.PURCHASE_ITEM
import org.jiwon.serverCore.messages.EconomyTexts.SALE_ITEM
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.EconomyData
import org.jiwon.serverCore.utils.NumberUtils

class BedrockShopping(private val floodgatePlayer:FloodgatePlayer,private val economyData: EconomyData,private val isPurchase:Boolean) {

    private val player:Player = getPlayer(floodgatePlayer.correctUniqueId)!!

    init {
        if(isPurchase) itemPurchase()
        else itemSale()
    }

    private fun itemPurchase(){
        floodgatePlayer.sendForm(
            EconomyManager.openBedrockPurchaseMenu(economyData) { result ->
                val amount = result.next<Float>()!!
                val purchaseResult = StoreProcess(player, economyData).itemPurchase(amount.toInt() != 1)
                /**
                 * 반환값이 null : 공간 부족 / false : 자금 부족 / true : 구매 완료
                 */
                if (purchaseResult != null) {
                    if(purchaseResult){
                        player.sendMessage(
                            mm(
                                PURCHASE_ITEM.replace(ITEM_NAME, economyData.item.name).replace(
                                    PURCHASE_PRICE, NumberUtils.format(economyData.purchasePrice*(if (amount.toInt() == 1) 1 else economyData.item.maxStackSize))
                                ).replace(AMOUNT, (if (amount.toInt() == 1) 1 else economyData.item.maxStackSize).toString())
                            )
                        )
                        PlayerManager.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP)
                    }else{
                        player.sendMessage(mm(LACK_BALANCE))
                        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                    }
                } else {
                    player.sendMessage(mm(INVENTORY_FULL))
                    PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                }
            }
        )

        
    }

    private fun itemSale(){
        floodgatePlayer.sendForm(EconomyManager.openBedrockSaleMenu(economyData){
            result->
            val isAll = result.next<Boolean>()!!
            val saleResult = StoreProcess(player, economyData).itemSale(isAll)
            if(saleResult != -1){
                player.sendMessage(mm(
                    SALE_ITEM
                    .replace(ITEM_NAME,economyData.item.name)
                    .replace(SALE_PRICE,NumberUtils.format(if(!isAll)economyData.salePrice else economyData.salePrice * saleResult))
                    .replace(AMOUNT,if(!isAll)"1" else "$saleResult")))
                PlayerManager.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP)
            }else{
                player.sendMessage(mm(LACK_ITEM))
                PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
            }
        })
    }

}