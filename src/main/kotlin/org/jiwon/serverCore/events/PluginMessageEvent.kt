package org.jiwon.serverCore.events

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.PluginMessageListener
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.ServerCore.Companion.instance

class PluginMessageEvent:PluginMessageListener {
    override fun onPluginMessageReceived(p0: String, p1: Player, p2: ByteArray) {
//        println(ByteStreams.newDataInput(p2).readUTF())
        if(ByteStreams.newDataInput(p2).readUTF() == "whitelist status"){
            val out = ByteStreams.newDataOutput()
            out.writeUTF("whitelist:${instance.server.hasWhitelist()}")
            instance.server.sendPluginMessage(JavaPlugin.getPlugin(ServerCore::class.java),"maintenance:proxy",out.toByteArray())
        }

    }

}