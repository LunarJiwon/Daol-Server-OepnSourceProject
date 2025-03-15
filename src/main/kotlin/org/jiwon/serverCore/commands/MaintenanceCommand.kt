package org.jiwon.serverCore.commands

import com.google.common.io.ByteStreams
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.messages.MessageColors.GOLD
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.SystemMessages
import org.jiwon.serverCore.messages.SystemMessages.MAINTENANCE_START_BROADCAST_MESSAGE
import org.jiwon.serverCore.messages.SystemMessages.MAINTENANCE_START_BROADCAST_SUBTITLE
import org.jiwon.serverCore.messages.SystemMessages.MAINTENANCE_START_BROADCAST_TITLE
import org.jiwon.serverCore.utils.Components.mm
import java.time.Duration

class MaintenanceCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()){
            if (args[0] == "시작"){
                Bukkit.broadcast(mm(MAINTENANCE_START_BROADCAST_MESSAGE))
                Bukkit.getOnlinePlayers().forEach {
                    it.showTitle(
                        Title.title(
                            mm(MAINTENANCE_START_BROADCAST_TITLE),
                            mm(MAINTENANCE_START_BROADCAST_SUBTITLE),
                            Title.Times.times(Duration.ofMillis(5), Duration.ofSeconds(5), Duration.ofMillis(5))
                        )
                    )
                }
                Bukkit.getScheduler().runTaskLater(instance, Runnable {
                    sender.server.onlinePlayers.forEach { player ->
                        if (!player.hasPermission("server.manager")){
                            val out = ByteStreams.newDataOutput()
                            out.writeUTF("Connect")
                            out.writeUTF("maintenance")
                            player.sendPluginMessage(instance,"BungeeCord",out.toByteArray())
                        }
                    }
                    sender.server.setWhitelist(true)
                    sender.server.reloadWhitelist()
                    sender.sendMessage(mm(SystemMessages.SUCCESS_MAINTENANCE_START))
                },100)

                return true
            }else if (args[0] == "종료"){
                sender.server.setWhitelist(false)
                sender.server.reloadWhitelist()
                sender.sendMessage(mm(SystemMessages.SUCCESS_MAINTENANCE_END))
                return true
            }

        }
        sender.sendMessage(mm(SystemMessages.NOT_FOUND_COMMAND_ARGS))
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        args: Array<out String>
    ): MutableList<String>? = if (args.size == 1) mutableListOf("시작","종료") else null



}