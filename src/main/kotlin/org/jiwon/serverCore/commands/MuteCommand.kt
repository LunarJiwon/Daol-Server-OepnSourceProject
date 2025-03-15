package org.jiwon.serverCore.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts
import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.manager.WarningManager
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.NOT_FOUND_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_TARGET_PLAYER
import org.jiwon.serverCore.messages.SystemMessages.TARGET_PLAYER_WARNING_COUNT
import org.jiwon.serverCore.messages.SystemMessages.TARGET_WARNING_MESSAGE
import org.jiwon.serverCore.messages.SystemMessages.TARGET_WARNING_REMOVE
import org.jiwon.serverCore.messages.SystemMessages.WARNING_ADDED
import org.jiwon.serverCore.messages.SystemMessages.WARNING_REMOVED
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils


@Deprecated("다른 플러그인으로 대체")
class MuteCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) {
            sender.sendMessage(mm(MISSING_COMMAND_ARGS))
            return true
        }
        when(args[0]){
            "활성화"->{
                if(args.size >= 2){
                    if(Bukkit.getOnlinePlayers().any { p -> p.name.lowercase() == args[1].lowercase() }){
                        val targetPlayer = Bukkit.getOnlinePlayers().find { p -> p.name.lowercase() == args[1].lowercase() }!!
                        val reason = StringBuilder()
                        if(args.size >= 3){
                            args.forEachIndexed { index, s ->
                                if(index >= 2){
                                    reason.append(s)
                                }
                                if(index != args.size -1){
                                    reason.append(" ")
                                }
                            }
                        }


                    }else{
                        sender.sendMessage(mm(NOT_FOUND_PLAYER.replace(ReplaceTexts.TARGET_PLAYER_NAME,args[1])))
                    }
                }else{
                    sender.sendMessage(mm(REQUIRE_TARGET_PLAYER))
                }
            }
            "해제"->{
                if(args.size >= 2){
                    if(Bukkit.getOnlinePlayers().any { p -> p.name.lowercase() == args[1].lowercase() }){
                        val targetPlayer = Bukkit.getOnlinePlayers().find { p -> p.name.lowercase() == args[1].lowercase() }!!


                    }else{
                        sender.sendMessage(mm(NOT_FOUND_PLAYER.replace(ReplaceTexts.TARGET_PLAYER_NAME,args[1])))
                    }
                }else{
                    sender.sendMessage(mm(REQUIRE_TARGET_PLAYER))
                }
            }
            else->{
                sender.sendMessage(mm(NOT_FOUND_COMMAND_ARGS))
            }
        }
        return true

    }

    override fun onTabComplete(
        sender: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        if(args.size == 1) return mutableListOf("활성화","해제")
        if(args.size == 3 && args[0] == "활성화") return mutableListOf("<시간:초>")
        return null

    }

}