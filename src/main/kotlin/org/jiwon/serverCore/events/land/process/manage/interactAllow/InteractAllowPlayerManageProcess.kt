package org.jiwon.serverCore.events.land.process.manage.interactAllow

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.floodgateInstance
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.events.land.process.manage.buildAllow.BuildAllowPlayerClickEvent
import org.jiwon.serverCore.manager.BedrockManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.manager.PlayerManager.getPlayerName
import org.jiwon.serverCore.messages.LandTexts
import org.jiwon.serverCore.messages.LandTexts.APPEND_BUILD_ALLOW_PLAYER
import org.jiwon.serverCore.messages.LandTexts.APPEND_INTERACT_ALLOW_PLAYER
import org.jiwon.serverCore.messages.LandTexts.BUILD_ALLOW_PLAYER_MENU_TITLE_BEDROCK
import org.jiwon.serverCore.messages.LandTexts.INTERACT_ALLOW_PLAYER_MENU_TITLE_BEDROCK
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.PlatformTaskProcess

class InteractAllowPlayerManageProcess(private val player: Player,private val landName:String):PlatformTaskProcess {

    init {
        platformCheck(player)
    }

    override fun bedrockRun() {
        floodgateInstance.getPlayer(player.uniqueId).sendForm(
            LandManager.landInteractAllowPlayerSettingsBedrockMenu(player,landName){
                val buttonText = it.clickedButton().text().split("\n")[0]
                if(buttonText.indexOf("§") == -1){
                    val selectPlayer = PlayerManager.getPlayer(buttonText)?.player
                    // 예외처리 테스트
                    LandManager.removeInteractAllowPlayer(player,landName,selectPlayer?.uniqueId.toString())
                    player.sendMessage(
                        mm(
                            LandTexts.REMOVED_INTERACT_ALLOW_PLAYER.replace(LAND_NAME,landName).replace(
                                PLAYER_NAME, selectPlayer?.name!!))
                    )
                }else{
                    floodgateInstance.getPlayer(player.uniqueId).sendForm(
                        BedrockManager.playerSelectMenu(INTERACT_ALLOW_PLAYER_MENU_TITLE_BEDROCK,player){
                                it2 ->
                            val selectPlayer = Bukkit.getOnlinePlayers().find { pl -> pl.name == it2.clickedButton().text() }!!
                            LandManager.addInteractAllowPlayer(player,landName, selectPlayer.uniqueId.toString())
                            player.sendMessage(mm(
                                APPEND_INTERACT_ALLOW_PLAYER.replace(PLAYER_NAME,selectPlayer.name).replace(
                                    LAND_NAME,landName)))
                        }
                    )
                }
            }
        )
    }

    override fun javaRun() {
        player.openInventory(LandManager.landInteractAllowPlayersSettingMenu(player, landName))
        registerEvent(InteractAllowPlayerClickEvent(player,landName))
    }
}