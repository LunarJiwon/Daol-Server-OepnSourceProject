package org.jiwon.serverCore.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_MESSAGE
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_TARGET_PLAYER
import org.jiwon.serverCore.messages.SystemMessages.WHISPER_MESSAGE
import org.jiwon.serverCore.messages.SystemMessages.WHISPER_MESSAGE_RECEIVE
import org.jiwon.serverCore.utils.Components.mm

class WhisperCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()){
            if(args.size >= 2){
                if(Bukkit.getOnlinePlayers().any { p -> p.name.lowercase() == args[0].lowercase()}){
                    val message = StringBuilder()
                    args.forEachIndexed { index, s ->
                        if(index > 0) {
                            message.append(s).append(" ")
                        }
                    }
                    val targetPlayer = Bukkit.getOnlinePlayers().find { p -> p.name.lowercase() == args[0].lowercase() }!!
                    sender.sendMessage(mm(WHISPER_MESSAGE.replace(TARGET_PLAYER_NAME,targetPlayer.name).replace(MESSAGE,message.toString())))
                    targetPlayer.sendMessage(mm(WHISPER_MESSAGE_RECEIVE.replace(TARGET_PLAYER_NAME,sender.name).replace(MESSAGE,message.toString())))
                }else{
                    sender.sendMessage(mm(NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,args[0])))
                }

            }else{
                sender.sendMessage(mm(REQUIRE_MESSAGE))
            }
        }else{
            sender.sendMessage(mm(REQUIRE_TARGET_PLAYER))
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        return if(args.size == 1) {
            null
        }else if(args.size >= 2) mutableListOf("<귓속말 내용>") else null
    }
}