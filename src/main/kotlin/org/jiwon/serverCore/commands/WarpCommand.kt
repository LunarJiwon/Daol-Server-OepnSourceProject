package org.jiwon.serverCore.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts.WARP_NAME
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.manager.WarpManager
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.NOT_FOUND_COMMAND_ARGS
import org.jiwon.serverCore.messages.WarpMessages.ALREADY_WARP_NAME
import org.jiwon.serverCore.messages.WarpMessages.CREATED_WARP
import org.jiwon.serverCore.messages.WarpMessages.NOT_FOUND_WARP
import org.jiwon.serverCore.messages.WarpMessages.REMOVED_WARP
import org.jiwon.serverCore.messages.WarpMessages.REQUIRE_WARP_NAME
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.WarpData

class WarpCommand:CommandExecutor,TabCompleter {

//    init{
//        Bukkit.getPluginCommand()
//    }

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isEmpty()){
            sender.sendMessage(mm(MISSING_COMMAND_ARGS))
            return true
        }
        if(args[0] == "생성"){
            if(args.size < 2){
                sender.sendMessage(mm(REQUIRE_WARP_NAME))
                return true
            }
            if(WarpManager.getWarp(args[1]) != null){
                sender.sendMessage(mm(ALREADY_WARP_NAME))
                return true
            }
            WarpManager.createWarp(
                WarpData(args[1],LocationManager.createJsonLocation((sender as Player).location),if(args.size >= 3) if(args[2] == "false") false else true else true)
            )
            sender.sendMessage(mm(CREATED_WARP.replace(WARP_NAME,args[1])))
        }else if(args[0] == "삭제"){
            if(args.size < 2){
                sender.sendMessage(mm(REQUIRE_WARP_NAME))
                return true
            }
            if(WarpManager.getWarp(args[1]) == null){
                sender.sendMessage(mm(NOT_FOUND_WARP))
                return true
            }
            WarpManager.removeWarp(args[1])
            sender.sendMessage(mm(REMOVED_WARP.replace(WARP_NAME,args[1])))
        }else{
            sender.sendMessage(mm(NOT_FOUND_COMMAND_ARGS))
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? {
        if(args.size == 1){
            return mutableListOf("<생성/삭제>")
        }else{
            if(args.size == 2){
                return mutableListOf("<워프이름>")
            }else if(args.size > 2){
                if(args[0] == "생성"){
                    return mutableListOf("<메시지 출력 여부:true/false>")
                }
            }
            return null
        }
    }
}