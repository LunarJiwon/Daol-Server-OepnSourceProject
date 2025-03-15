package org.jiwon.serverCore.commands.balance

import org.bukkit.entity.Player
import org.geysermc.floodgate.api.player.FloodgatePlayer
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.alternative.ReplaceTexts.AMOUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.BALANCE
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_AMOUNT_PLACE
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_INCORRECT_AMOUNT
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_MIN_BALANCE
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_MISSING_PLAYER
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_SHORTAGE_BALANCE
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_SUCCESS
import org.jiwon.serverCore.messages.BalanceMessages.SEND_BALANCE_SUCCESS_TARGET
import org.jiwon.serverCore.subcommands.BalanceSubCommand
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class SendBalance(player:Player,vararg args:String) : BalanceSubCommand(player, *args) {

    // 첫 번째 인자가 송금 -> 제대로된 인자를 얻기 위해서는 두 번째 인자부터 호출하면 됨

    override fun javaRun(player: Player, vararg args: String) {
        if(args.size > 1){
            if(player.server.getPlayer(args[1].lowercase()) != null){
                if(args.size > 2){
                    if(NumberUtils.isNumeric(args[2])){
                        val targetPlayer = player.server.getPlayer(args[1].lowercase())!!
                        val amount = args[2].toInt()
                        if(amount > 0) {
                            if (BalanceManager.sendPlayerToPlayerBalance(player, targetPlayer, amount)) {
                                // 송금자 메시지 전송
                                player.sendMessage(
                                    mm(
                                        SEND_BALANCE_SUCCESS
                                            .replace(TARGET_PLAYER_NAME, targetPlayer.name)
                                            .replace(AMOUNT, NumberUtils.format(amount))
                                            .replace(BALANCE, NumberUtils.format(NumberUtils.calTax(amount)))
                                    )
                                )

                                // 수신자 메시지 전송
                                targetPlayer.sendMessage(
                                    mm(
                                        SEND_BALANCE_SUCCESS_TARGET
                                            .replace(PLAYER_NAME, player.name)
                                            .replace(AMOUNT, NumberUtils.format(amount))
                                            .replace(BALANCE, NumberUtils.format(NumberUtils.calTax(amount)))
                                    )
                                )
                                return
                            } else {
                                player.sendMessage(
                                    mm(
                                        SEND_BALANCE_SHORTAGE_BALANCE.replace(
                                            BALANCE,
                                            NumberUtils.format(amount - BalanceManager.getPlayerBalance(player))
                                        )
                                    )
                                )
                                return
                            }
                        }
                        player.sendMessage(mm(SEND_BALANCE_MIN_BALANCE))
                        return
                    }
                    player.sendMessage(mm(SEND_BALANCE_INCORRECT_AMOUNT))
                    return
                }
                player.sendMessage(mm(SEND_BALANCE_AMOUNT_PLACE))
                return
            }
            player.sendMessage(mm(SEND_BALANCE_NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,args[1])))
            return
        }
        player.sendMessage(mm(SEND_BALANCE_MISSING_PLAYER))
        return

    }

    override fun bedrockRun(player: FloodgatePlayer, vararg args: String) {
        val originPlayer = instance.server.getPlayer(player.correctUniqueId)!!
        val players = ArrayList<String>()
        instance.server.onlinePlayers.forEach { p ->
            if (p.name != originPlayer.name){
                players.add(p.name)
            }
        }
        player.sendForm(BalanceBedrockMenu.sendBalance(players) { res ->

            val targetPlayerCache = players[res.next<Int>()!!]
            val amountCache = res.next<String>()!!


            if (instance.server.getPlayer(targetPlayerCache) != null) {
                if (NumberUtils.isNumeric(amountCache) ) {
                    val targetPlayer = instance.server.getPlayer(targetPlayerCache)!!
                    if(amountCache != "") {
                        val amount = amountCache.toInt()
                        if(amount > 0) {
                            if (BalanceManager.sendPlayerToPlayerBalance(originPlayer, targetPlayer, amount)) {
                                // 송금자 메시지 전송
                                originPlayer.sendMessage(
                                    mm(
                                        SEND_BALANCE_SUCCESS
                                            .replace(TARGET_PLAYER_NAME, targetPlayer.name)
                                            .replace(AMOUNT, NumberUtils.format(amount))
                                            .replace(BALANCE, NumberUtils.format(NumberUtils.calTax(amount)))
                                    )
                                )

                                // 수신자 메시지 전송
                                targetPlayer.sendMessage(
                                    mm(
                                        SEND_BALANCE_SUCCESS_TARGET
                                            .replace(PLAYER_NAME, originPlayer.name)
                                            .replace(AMOUNT, NumberUtils.format(amount))
                                            .replace(BALANCE, NumberUtils.format(NumberUtils.calTax(amount)))
                                    )
                                )

                            } else {
                                originPlayer.sendMessage(
                                    mm(
                                        SEND_BALANCE_SHORTAGE_BALANCE.replace(
                                            BALANCE,
                                            NumberUtils.format(amount - BalanceManager.getPlayerBalance(targetPlayer))
                                        )
                                    )
                                )

                            }
                        }else originPlayer.sendMessage(
                            mm(SEND_BALANCE_MIN_BALANCE)
                        )
                    }else{
                        originPlayer.sendMessage(
                            mm(SEND_BALANCE_MIN_BALANCE)
                        )
                    }
                } else originPlayer.sendMessage(mm(SEND_BALANCE_INCORRECT_AMOUNT))
            } else originPlayer.sendMessage(
                mm(
                    SEND_BALANCE_NOT_FOUND_PLAYER.replace(
                        TARGET_PLAYER_NAME,
                        targetPlayerCache.toString()
                    )
                )
            )
        })
    }
}