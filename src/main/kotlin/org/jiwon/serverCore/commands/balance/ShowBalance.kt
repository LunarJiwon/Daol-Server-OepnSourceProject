package org.jiwon.serverCore.commands.balance

import org.bukkit.entity.Player
import org.geysermc.floodgate.api.player.FloodgatePlayer
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.alternative.ReplaceTexts.BALANCE
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.subcommands.BalanceSubCommand
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayer
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.BalanceMessages.SHOW_BALANCE
import org.jiwon.serverCore.messages.BalanceMessages.SPECIFIC_PLAYER_BALANCE
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class ShowBalance(player: Player,vararg args:String): BalanceSubCommand(player,*args) {

    override fun javaRun(player: Player, vararg args: String) {
        if(args.isNotEmpty()) {
            if (player.hasPermission("server.manager")) {
                    if (getPlayer(args[0]) != null) {
                        player.sendMessage(
                            mm(
                                SPECIFIC_PLAYER_BALANCE.replace(
                                    TARGET_PLAYER_NAME,
                                    getPlayer(args[0])!!.name
                                ).replace(BALANCE,NumberUtils.format(BalanceManager.getPlayerBalance(getPlayer(args[0])!!)))
                            )
                        )

                    return
                }else player.sendMessage(mm(NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,args[0])))
            }
        }else player.sendMessage(mm(SHOW_BALANCE.replace(BALANCE,NumberUtils.format(BalanceManager.getPlayerBalance(player)))))
    }

    override fun bedrockRun(player: FloodgatePlayer, vararg args: String) {
        player.sendForm(BalanceBedrockMenu.mainMenu(NumberUtils.format(BalanceManager.getPlayerBalance(instance.server.getPlayer(player.correctUniqueId)!!)),{
            res ->
            if (res.clickedButton().text() == "송금"){
                instance.server.getPlayer(player.correctUniqueId)!!.performCommand("balance 송금")
            }
        }))
    }


}