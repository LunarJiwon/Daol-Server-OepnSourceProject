package org.jiwon.serverCore.events.land.protect

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.LandSettings.ALLOW_EXPLODE
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.manager.LandManager
import org.jiwon.serverCore.manager.PlayerManager
import org.jiwon.serverCore.messages.LandTexts.PROTECT_MESSAGE
import org.jiwon.serverCore.utils.Components.mm
import java.util.UUID

object LandProtectProcess {

    private fun sendProtectMessage(player: Player?,playerUUID:String){
        player?.sendActionBar(
            mm(PROTECT_MESSAGE.replace(PLAYER_NAME,PlayerManager.getPlayerName(playerUUID)!!))
        )
        player?.let { PlayerManager.playerSound(it, Sound.BLOCK_NOTE_BLOCK_BASS) }
    }

    fun protectAll(location: Location,player:Player?):Boolean{
        val area = LandManager.isOverlapLocation(location)
        if(area.isNotEmpty()){
            if(area.keys.first() != player?.uniqueId.toString()){
                sendProtectMessage(player,area.keys.first())
            }

        }
        return area.isNotEmpty()
    }

    /**
     * 건축허용 여부 파악
     */
    fun protectBuild(location: Location,player: Player):Boolean{
        val area = LandManager.isOverlapLocation(location)
        if(area.isNotEmpty()){
            if(area.keys.first() != player.uniqueId.toString()) {
                if (area.values.first().buildAllowPlayers.any { p -> p == player.uniqueId.toString() }) {
                    return false
                } else {
                    sendProtectMessage(player, area.keys.first())
                    return true
                }
            }
        }
        return false
    }

    /**
     * 상호작용허용 여부 파악
     * @deprecated
     */
    @Deprecated("상호작용 메뉴 삭제")
    fun protectInteract(location: Location,player: Player):Boolean{
        val area = LandManager.isOverlapLocation(location)
        if(area.isNotEmpty()){
            if(area.values.first().interactAllowPlayers.any{ p -> p == player.uniqueId.toString()}){
                return false
            }else{
                sendProtectMessage(player,area.keys.first())
                return true
            }

        }
        return false
    }

    /**
     * @description 폭발방지
     * @param location 검증 위치
     */
    fun protectExplode(location: Location):Boolean{
        val area = LandManager.isOverlapLocation(location)
        if(area.isNotEmpty()){
            return (area.values.first().settings[ALLOW_EXPLODE] == "true")
        }
        return false
    }


    /**
     * @description 흐르는 블록 방지
     * @param location 검증 위치
     * @param toLocation 검증 위치2
     */
    fun protectFlowBlock(location: Location, toLocation: Location):Boolean{
        val area = LandManager.isOverlapLocation(location)
        if(area.isEmpty()){
            val compareArea = LandManager.isOverlapLocation(toLocation)
            return (compareArea.isNotEmpty())
        }
        return false
    }

    fun pistonProtect(blocks:List<Block>):Boolean{
        blocks.forEach {
            val area = LandManager.isOverlapLocation(it.location)
            if(area.isNotEmpty()){
                return true
            }
        }
        return false
    }

}