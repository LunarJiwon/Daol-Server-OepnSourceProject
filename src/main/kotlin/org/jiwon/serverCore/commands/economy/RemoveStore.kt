package org.jiwon.serverCore.commands.economy

import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.events.economy.CreateEconomyEvent
import org.jiwon.serverCore.events.economy.RemoveEconomyEvent
import org.jiwon.serverCore.manager.EconomyManager
import org.jiwon.serverCore.messages.SystemMessages.INCORRECT_ITEM
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.REMOVE_STORE_START
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_NUMBER
import org.jiwon.serverCore.messages.SystemMessages.START_CREATE_ECONOMY
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.EconomyData
import org.jiwon.serverCore.utils.NumberUtils

class RemoveStore(val player: Player) {
    init {
        execute()
    }

    private fun execute(){
        player.sendMessage(mm(REMOVE_STORE_START))
        instance.server.pluginManager.registerEvents(RemoveEconomyEvent(player), instance)
    }
}