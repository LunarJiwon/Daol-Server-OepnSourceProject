package org.jiwon.serverCore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.alternative.ConfigNames.AFK_WORLD
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.NOT_FOUND_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.SET_AFK_WORLD
import org.jiwon.serverCore.utils.Components.mm

class AFKCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()){
            if(args[0] == "설정"){
                ServerCore.defaultConfig.set(AFK_WORLD,(sender as Player).world.uid)
                sender.sendMessage(mm(SET_AFK_WORLD))
            }else{
                sender.sendMessage(mm(NOT_FOUND_COMMAND_ARGS))
            }
        }else{
            sender.sendMessage(mm(MISSING_COMMAND_ARGS))
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        return if(p3.size == 1){
            mutableListOf("설정")
        }else null
    }
}