package org.jiwon.serverCore.events.land

import org.bukkit.Sound
import org.geysermc.floodgate.api.player.FloodgatePlayer
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_1
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_2
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_3
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_4
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_TO_PRICE
import org.jiwon.serverCore.events.land.process.manage.LandManageBedrockEvent
import org.jiwon.serverCore.events.land.process.purchase.LandPurchaseProcess
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayer
import org.jiwon.serverCore.messages.EconomyTexts
import org.jiwon.serverCore.messages.LandTexts.LIMITED_LAND_AMOUNT
import org.jiwon.serverCore.utils.Components.mm

class BedrockMainMenuClick(private val floodgatePlayer: FloodgatePlayer,private val isPurchase:Boolean) {
    private val player = getPlayer(floodgatePlayer.correctUniqueId)!!

    init {
        if(isPurchase) purchaseMenu()
        else manageMenu()
    }

    private fun purchaseMenu(){
        floodgatePlayer.sendForm(
            LandManager.landPurchaseBedrockMenu(){
                val landOption = defaultConfig.getJsonObject(LAND_SIZE_TO_PRICE)
                if(LandManager.getPlayerLandSize(player) >= 7){
                    player.sendMessage(mm(LIMITED_LAND_AMOUNT))
                    return@landPurchaseBedrockMenu
                }
                when(it.clickedButtonId()){
                    0->{
                        if(BalanceManager.getPlayerBalance(player) >= landOption.getAsJsonObject(LAND_SIZE_1).get("price").asInt) {
                            LandPurchaseProcess(
                                player,
                                (
                                        landOption.getAsJsonObject(LAND_SIZE_1)
                                            .get("size").asString.split("x")[0].toInt()
                                                *
                                                landOption.getAsJsonObject(LAND_SIZE_1)
                                                    .get("size").asString.split("x")[1].toInt()
                                        ),
                                landOption.getAsJsonObject(LAND_SIZE_1).get("price").asInt
                            )
                        }else{
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            player.sendMessage(mm(EconomyTexts.LACK_BALANCE))
                        }
                    }
                    1->{
                        if(BalanceManager.getPlayerBalance(player) >= landOption.getAsJsonObject(LAND_SIZE_2).get("price").asInt) {
                            LandPurchaseProcess(
                                player,
                                (
                                        landOption.getAsJsonObject(LAND_SIZE_2)
                                            .get("size").asString.split("x")[0].toInt()
                                                *
                                                landOption.getAsJsonObject(LAND_SIZE_2)
                                                    .get("size").asString.split("x")[1].toInt()
                                        ),
                                landOption.getAsJsonObject(LAND_SIZE_2).get("price").asInt
                            )
                        }else{
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            player.sendMessage(mm(EconomyTexts.LACK_BALANCE))
                        }
                    }
                    2->{
                        if(BalanceManager.getPlayerBalance(player) >= landOption.getAsJsonObject(LAND_SIZE_3).get("price").asInt) {
                            LandPurchaseProcess(
                                player,
                                (
                                        landOption.getAsJsonObject(LAND_SIZE_3)
                                            .get("size").asString.split("x")[0].toInt()
                                                *
                                                landOption.getAsJsonObject(LAND_SIZE_3)
                                                    .get("size").asString.split("x")[1].toInt()
                                        ),
                                landOption.getAsJsonObject(LAND_SIZE_3).get("price").asInt
                            )
                        }else{
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            player.sendMessage(mm(EconomyTexts.LACK_BALANCE))
                        }
                    }
                    3->{
                        if(BalanceManager.getPlayerBalance(player) >= landOption.getAsJsonObject(LAND_SIZE_4).get("price").asInt) {
                            LandPurchaseProcess(
                                player,
                                (
                                        landOption.getAsJsonObject(LAND_SIZE_4)
                                            .get("size").asString.split("x")[0].toInt()
                                                *
                                                landOption.getAsJsonObject(LAND_SIZE_4)
                                                    .get("size").asString.split("x")[1].toInt()
                                        ),
                                landOption.getAsJsonObject(LAND_SIZE_4).get("price").asInt
                            )
                        }else{
                            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
                            player.sendMessage(mm(EconomyTexts.LACK_BALANCE))
                        }
                    }
                }
            }
        )
    }

    private fun manageMenu(){
        floodgatePlayer.sendForm(LandManager.bedrockLandListMenu(player,null){
            val landName = LandManager.getLandNameFromBedrockInterface(it.clickedButton().text())
            floodgatePlayer.sendForm(LandManager.bedrockLandMenu(LandManager.getPlayerLand(player,landName)!!){ response ->
                LandManageBedrockEvent(floodgatePlayer,landName,response)
            })
            // LandListMenuBedrockEvent()
        })
    }


}