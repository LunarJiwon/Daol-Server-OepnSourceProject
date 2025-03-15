package org.jiwon.serverCore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.commands.economy.CreateStore
import org.jiwon.serverCore.commands.economy.RemoveStore
import org.jiwon.serverCore.events.economy.RemoveEconomyEvent
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.NOT_FOUND_COMMAND_ARGS
import org.jiwon.serverCore.utils.Components.mm

class EconomyCommand:CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()){
            if(args[0] == "생성"){
                CreateStore(sender as Player, args)
                return true
            }else if(args[0] == "삭제"){
                RemoveStore(sender as Player)
                return true
            }
            sender.sendMessage(mm(NOT_FOUND_COMMAND_ARGS))
            return true
        }
        sender.sendMessage(mm(MISSING_COMMAND_ARGS))
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? = if(args.size == 1) mutableListOf("생성","삭제") else if(args.size == 2) mutableListOf("<구매가/구매불가>") else if(args.size == 3) mutableListOf("<판매가/판매불가>") else null
}