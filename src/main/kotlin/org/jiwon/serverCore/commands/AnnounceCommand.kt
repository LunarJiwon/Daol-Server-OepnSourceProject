package org.jiwon.serverCore.commands

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.messages.SystemMessages.ANNOUNCE_MESSAGE
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.utils.Components.mm

class AnnounceCommand:CommandExecutor,TabCompleter {

    companion object {
        var task = 0
    }

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(args.isEmpty()){
            sender.sendMessage(mm(MISSING_COMMAND_ARGS))
        }else{
            val message = StringBuilder()
            args.forEach {
                message.append(it).append(" ")
            }
            val bossBar = Bukkit.createBossBar(message.toString(),BarColor.RED,BarStyle.SEGMENTED_20)
            bossBar.progress = 1.0
            Bukkit.broadcast(mm(ANNOUNCE_MESSAGE.replace(MESSAGE,message.toString())))
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, Runnable {
                Bukkit.getOnlinePlayers().forEach {
                    if(bossBar.players.none { p -> p == it }) bossBar.addPlayer(it)
                }
                if(bossBar.progress <= 0.05){
                    bossBar.removeAll()

                    Bukkit.getScheduler().cancelTask(task)
                }else{
                    bossBar.progress -= 0.05
                }



            },20L,20L)
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        return null
    }

}