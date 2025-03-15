package org.jiwon.serverCore.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.alternative.ConfigNames.SPEAK_PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.alternative.ReplaceTexts.PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.manager.BalanceManager
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_SPEAK_ARGS
import org.jiwon.serverCore.messages.SystemMessages.SPEAK
import org.jiwon.serverCore.messages.SystemMessages.SPEAK_LACK_BALANCE
import org.jiwon.serverCore.messages.SystemMessages.SUCCESS_SPEAK
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class SpeakCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()){
            val price = ServerCore.defaultConfig.getInt(SPEAK_PRICE)
            if(BalanceManager.getPlayerBalance(sender as Player) >= price){
                val message = StringBuilder()
                args.forEach {
                    message.append(it).append(" ")
                }
                Bukkit.broadcast(mm(
                    SPEAK.replace(TARGET_PLAYER_NAME,sender.name).replace(MESSAGE,message.toString())
                ))
                BalanceManager.removePlayerBalance(sender,price)
                sender.sendMessage(mm(SUCCESS_SPEAK.replace(PRICE,NumberUtils.format(price))))
            }else{
                sender.sendMessage(mm(
                    SPEAK_LACK_BALANCE.replace(PRICE,NumberUtils.format(price))
                ))
            }

        }else{
            sender.sendMessage(mm(REQUIRE_SPEAK_ARGS))
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        return if(args.isNotEmpty()){
            mutableListOf("확성기 내용")
        }else null
    }

}