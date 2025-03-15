package org.jiwon.serverCore.commands.balance

import org.bukkit.entity.Player
import org.geysermc.floodgate.api.player.FloodgatePlayer
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.alternative.ReplaceTexts.AMOUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.messages.BalanceMessages.INCORRECT_AMOUNT
import org.jiwon.serverCore.messages.BalanceMessages.MISSING_AMOUNT
import org.jiwon.serverCore.messages.BalanceMessages.MISSING_ARGS_PLAYER
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.BalanceMessages.SUCCESS_ADD_BALANCE
import org.jiwon.serverCore.messages.BalanceMessages.SUCCESS_REMOVE_BALANCE
import org.jiwon.serverCore.messages.BalanceMessages.SUCCESS_SET_BALANCE
import org.jiwon.serverCore.subcommands.BalanceSubCommand
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class AddBalance(player: Player,vararg args:String):BalanceSubCommand(player, *args) {
    override fun javaRun(player: Player, vararg args: String) {
        if(args.size > 1){
            if(player.server.getPlayer(args[1]) != null){
                if(args.size > 2){
                    if(NumberUtils.isNumeric(args[2])){
                        val targetPlayer = player.server.getPlayer(args[1])!!
                        val amount = args[2].toInt()

                        BalanceManager.addPlayerBalance(targetPlayer,amount)

                        player.sendMessage(mm(
                            SUCCESS_ADD_BALANCE.replace(TARGET_PLAYER_NAME,targetPlayer.name).replace(
                            AMOUNT, NumberUtils.format(amount))))
                        return
                    }
                    player.sendMessage(mm(INCORRECT_AMOUNT))
                    return
                }
                player.sendMessage(mm(MISSING_AMOUNT))
                return
            }
            player.sendMessage(mm(NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,args[1])))
            return
        }
        player.sendMessage(mm(MISSING_ARGS_PLAYER))
    }

    override fun bedrockRun(player: FloodgatePlayer, vararg args: String) {
        javaRun(ServerCore.instance.server.getPlayer(player.correctUniqueId)!!,*args)
    }
}