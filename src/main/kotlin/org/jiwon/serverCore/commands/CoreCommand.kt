package org.jiwon.serverCore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.ServerCore.Companion.gson
import org.jiwon.serverCore.alternative.ConfigNames
import org.jiwon.serverCore.messages.SystemMessages
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.manager.LocationManager


class CoreCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()){
            if (args[0] == "reload") {
                defaultConfig.reload()
                sender.sendMessage(mm(SystemMessages.SUCCESS_RELOAD))
                return true
            }else if(args[0] == "set"){
                if(args.size > 1){
                    if(args[1] == "tutorial"){
                        defaultConfig.set(ConfigNames.TUTORIAL_LOCATION,gson.toJsonTree(LocationManager.createJsonLocation((sender as Player).location)))
                        sender.sendMessage(mm(SystemMessages.SUCCESS_SET_TUTORIAL))
                        return true
                    }else if(args[1] == "spawn"){
                        defaultConfig.set(ConfigNames.SPAWN_LOCATION,gson.toJsonTree(LocationManager.createJsonLocation((sender as Player).location)))
                        sender.sendMessage(mm(SystemMessages.SUCCESS_SET_SPAWN))
                        return true
                    }
                }
            }
        }
        sender.sendMessage(mm(SystemMessages.NOT_FOUND_COMMAND_ARGS))
        return false
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? = if (args.size == 1) mutableListOf("reload","set") else if(args.size == 2 && args[0] == "set") mutableListOf("tutorial","spawn ") else null



}