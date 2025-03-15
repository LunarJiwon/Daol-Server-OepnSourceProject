package org.jiwon.serverCore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.commands.prefix.PrefixMenu
import org.jiwon.serverCore.manager.PrefixManager
import org.jiwon.serverCore.messages.PrefixMessages.CLEAR_PREFIX
import org.jiwon.serverCore.messages.PrefixMessages.CREATED_PREFIX
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.NOT_FOUND_COMMAND_ARGS
import org.jiwon.serverCore.utils.Components.mm

class PrefixCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("server.manage") && args.isNotEmpty()){
            if (args[0] == "생성") {
                if (args.size > 1) {
                    val prefix = StringBuilder()
                    args.forEachIndexed { index, s ->
                        if (index >= 1) {
                            prefix.append(s)
                            if (index != args.lastIndex) {
                                prefix.append(" ")
                            }
                        }
                    }
                    (sender as Player).inventory.addItem(PrefixManager.createPrefixItem(prefix.toString()))
                    sender.sendMessage(mm(CREATED_PREFIX))
                    // 칭호전송
                    return true
                }
            }
        }
        if(args.isNotEmpty()){
            if(args[0] == "초기화"){
                PrefixManager.resetPrefixPlayer(sender as Player)
                sender.sendMessage(mm(CLEAR_PREFIX))
                return true
            }
        }
        PrefixMenu(sender as Player)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        if(sender.hasPermission("server.manage")){
            if(args.size == 1) return mutableListOf("생성","초기화")
            else if(args.size >= 2) return mutableListOf("<칭호 이름>")
        }else if(args.size == 1) return mutableListOf("초기화")
        return null
    }

}