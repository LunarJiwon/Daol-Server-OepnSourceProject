package org.jiwon.serverCore.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.TeleportRequestManager
import org.jiwon.serverCore.messages.BalanceMessages.NOT_FOUND_PLAYER
import org.jiwon.serverCore.messages.TeleportRequestMessage
import org.jiwon.serverCore.messages.TeleportRequestMessage.ACCEPT_TELEPORT
import org.jiwon.serverCore.messages.TeleportRequestMessage.ACCEPT_TELEPORT_DONE
import org.jiwon.serverCore.messages.TeleportRequestMessage.ALREADY_REQUEST
import org.jiwon.serverCore.messages.TeleportRequestMessage.DENY_TELEPORT
import org.jiwon.serverCore.messages.TeleportRequestMessage.NOT_FOUND_REQUEST
import org.jiwon.serverCore.messages.TeleportRequestMessage.REQUEST_TELEPORT
import org.jiwon.serverCore.messages.TeleportRequestMessage.SUCCESS_REQUEST_TELEPORT
import org.jiwon.serverCore.utils.Components.mm

class TeleportRequestCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()){
            if(args[0] == "수락" || args[0] == "거절"){
                if(args[0] == "수락"){
                    val acceptPlayer = TeleportRequestManager.acceptPlayer(sender as Player)
                    if(acceptPlayer != null){
                        sender.sendMessage(mm(ACCEPT_TELEPORT.replace(PLAYER_NAME,acceptPlayer)))
                        Bukkit.getOnlinePlayers().find { p -> p.name == acceptPlayer }!!.sendMessage(mm(
                            ACCEPT_TELEPORT_DONE))
                    }else{
                        sender.sendMessage(mm(NOT_FOUND_REQUEST))
                    }

                }else{
                    val denyPlayer = TeleportRequestManager.denyPlayer(sender as Player)
                    if(denyPlayer != null){
                        sender.sendMessage(mm(DENY_TELEPORT.replace(PLAYER_NAME,denyPlayer)))
                    }else{
                        sender.sendMessage(mm(NOT_FOUND_REQUEST))
                    }
                }
            }else{
                val targetPlayer = Bukkit.getOnlinePlayers().find { p -> p.name.lowercase() == args[0].lowercase() }
                if(targetPlayer != null){
                    if(TeleportRequestManager.requestPlayer(sender as Player,targetPlayer)){
                        sender.sendMessage(mm(SUCCESS_REQUEST_TELEPORT.replace(PLAYER_NAME,targetPlayer.name)))
                        targetPlayer.sendMessage(mm(REQUEST_TELEPORT.replace(PLAYER_NAME,sender.name)))
                    }else{
                        sender.sendMessage(mm(ALREADY_REQUEST))
                    }

                }else{
                    sender.sendMessage(mm(NOT_FOUND_PLAYER.replace(TARGET_PLAYER_NAME,args[0])))

                }
            }
        }else{
            sender.sendMessage(mm(TeleportRequestMessage.REQUIRE_ARGS))
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        return if(args.size == 1){
            val tab = ArrayList<String>()
            Bukkit.getOnlinePlayers().forEach {
                if(it.name != sender.name){
                    tab.add(it.name)
                }
            }
            tab.add("수락")
            tab.add("거절")
            tab.toMutableList()
        }else null
    }

}