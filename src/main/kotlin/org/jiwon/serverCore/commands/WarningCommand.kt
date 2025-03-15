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
import org.jiwon.serverCore.messages.SystemMessages.NOT_FOUND_WARNING
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_TARGET_PLAYER
import org.jiwon.serverCore.messages.SystemMessages.TARGET_PLAYER_WARNING_COUNT
import org.jiwon.serverCore.messages.SystemMessages.TARGET_WARNING_MESSAGE
import org.jiwon.serverCore.messages.SystemMessages.TARGET_WARNING_REMOVE
import org.jiwon.serverCore.messages.SystemMessages.WARNING_ADDED
import org.jiwon.serverCore.messages.SystemMessages.WARNING_REMOVED
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class WarningCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) {
            sender.sendMessage(mm(MISSING_COMMAND_ARGS))
            return true
        }
        when(args[0]){
            "부여"->{
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
                        WarningManager.addPlayerWarning(sender as Player,targetPlayer,reason.toString())
                        sender.sendMessage(mm(WARNING_ADDED.replace(ReplaceTexts.TARGET_PLAYER_NAME,targetPlayer.name).replace(MESSAGE,reason.toString())))
                        targetPlayer.sendMessage(mm(TARGET_WARNING_MESSAGE.replace(MESSAGE,reason.toString())))
                    }else{
                        sender.sendMessage(mm(NOT_FOUND_PLAYER.replace(ReplaceTexts.TARGET_PLAYER_NAME,args[1])))
                    }
                }else{
                    sender.sendMessage(mm(REQUIRE_TARGET_PLAYER))
                }
            }
            "회수"->{
                if(args.size >= 2){
                    if(Bukkit.getOnlinePlayers().any { p -> p.name.lowercase() == args[1].lowercase() }){
                        val targetPlayer = Bukkit.getOnlinePlayers().find { p -> p.name.lowercase() == args[1].lowercase() }!!

                        if(WarningManager.removePlayerWarning(sender as Player,targetPlayer)){
                            sender.sendMessage(mm(WARNING_REMOVED.replace(ReplaceTexts.TARGET_PLAYER_NAME,targetPlayer.name)))
                            targetPlayer.sendMessage(mm(TARGET_WARNING_REMOVE))
                        }else{
                            sender.sendMessage(mm(NOT_FOUND_WARNING))
                        }

                    }else{
                        sender.sendMessage(mm(NOT_FOUND_PLAYER.replace(ReplaceTexts.TARGET_PLAYER_NAME,args[1])))
                    }
                }else{
                    sender.sendMessage(mm(REQUIRE_TARGET_PLAYER))
                }
            }
            "확인"->{
                if(args.size >= 2){
                    if(Bukkit.getOnlinePlayers().any { p -> p.name.lowercase() == args[1].lowercase() }){
                        val targetPlayer = Bukkit.getOnlinePlayers().find { p -> p.name.lowercase() == args[1].lowercase() }!!

                        sender.sendMessage(mm(TARGET_PLAYER_WARNING_COUNT.replace(TARGET_PLAYER_NAME,targetPlayer.name).replace(
                            MESSAGE,NumberUtils.format(WarningManager.getPlayerWarning(targetPlayer)))))
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
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        if(p3.size == 1) return mutableListOf("부여","회수","확인")
        if(p3.size >= 3 && p3[0] == "부여") return mutableListOf("<경고 사유>")
        return null
    }
}