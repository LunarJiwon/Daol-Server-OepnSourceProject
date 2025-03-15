package org.jiwon.serverCore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.commands.balance.*
import org.jiwon.serverCore.messages.BalanceMessages.COMMAND_HELPER
import org.jiwon.serverCore.utils.Components.mm

class BalanceCommand : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isNotEmpty()){
            if(args[0] == "송금"){
                SendBalance(sender as Player, *args) // 송금
                return true
            }else if(sender.hasPermission("server.manager")){
                if(args[0] == "설정"){
                    SetBalance(sender as Player ,*args)
                    return true
                }else if(args[0] == "추가"){
                    AddBalance(sender as Player,*args)
                    return true
                }else if(args[0] == "삭제"){
                    RemoveBalance(sender as Player,*args)
                    return true
                }else{
                    ShowBalance(sender as Player,*args)
                    return true
                }
            }else{
                sender.sendMessage(mm(COMMAND_HELPER))
                return true
            }
        }
        ShowBalance(sender as Player,*args) // 통장 메뉴 혹은 돈 출력
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        return if(args.size == 1){
            if(!sender.hasPermission("server.manager")){
                mutableListOf("송금")
            }else{
                mutableListOf("송금","추가","삭제","설정")
            }
        }else if(args.size == 3){
            if(args[0] == "송금"){
                mutableListOf("<금액>")
            }else return null
        }
        else return null

    }

    /**
     * 기능구현
     */



}