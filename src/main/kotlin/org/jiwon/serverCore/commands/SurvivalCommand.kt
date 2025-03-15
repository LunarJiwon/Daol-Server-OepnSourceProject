package org.jiwon.serverCore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.commands.survival.SurvivalMenu
import org.jiwon.serverCore.manager.SurvivalManager
import org.jiwon.serverCore.messages.SurvivalMessage.SET_WORLD
import org.jiwon.serverCore.utils.Components.mm

class SurvivalCommand:CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()) {
            if (sender.hasPermission("server.manage")) {
                if (args[0] == "설정") {
                    SurvivalManager.setSurvivalWorld((sender as Player).world)
                    sender.sendMessage(mm(SET_WORLD))
                    return true
                }
            }
        }else{
            SurvivalMenu(sender as Player)
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        if(sender.hasPermission("server.manage")){
            if(args.size == 1){
                return mutableListOf("<설정>")
            }
        }
        return null
    }
}