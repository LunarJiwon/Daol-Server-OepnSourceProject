package org.jiwon.serverCore.commands

import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.alternative.ConfigNames
import org.jiwon.serverCore.events.land.BedrockMainMenuClick
import org.jiwon.serverCore.events.land.LandMainMenuClickEvent
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.LAND_MENU_PURCHASE_BUTTON_BEDROCK
import org.jiwon.serverCore.messages.LandTexts.NOT_ALLOW_WORLD
import org.jiwon.serverCore.utils.Components.mm

class LandCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        val player = sender as Player
        val allowWorlds = defaultConfig.getJsonArray(ConfigNames.ALLOW_LAND_COMMAND_WORLD)
        if(allowWorlds.find { worldName -> worldName.asString == player.world.name } != null || player.hasPermission("server.manage")) {
            PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_HARP)
            if (!floodgateInstance.isFloodgatePlayer(player.uniqueId)) {
                if (args.isNotEmpty() && player.hasPermission("server.manage")) {
                    // 특정 플레이어 부동산 관리
                    return true
                }
                registerEvent(LandMainMenuClickEvent(player))
                player.openInventory(LandManager.landMainMenu())
            } else {
                floodgateInstance.getPlayer(player.uniqueId).sendForm(LandManager.bedrockLandMainMenu() { response ->
                    BedrockMainMenuClick(
                        floodgateInstance.getPlayer(player.uniqueId),
                        response.clickedButton().text() == LAND_MENU_PURCHASE_BUTTON_BEDROCK
                    )
                })
            }
            return true
        }
        PlayerManager.playerSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
        player.sendMessage(mm(NOT_ALLOW_WORLD))
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? = null

}