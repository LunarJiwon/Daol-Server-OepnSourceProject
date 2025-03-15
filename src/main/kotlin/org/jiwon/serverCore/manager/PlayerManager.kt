package org.jiwon.serverCore.manager

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.ServerCore.Companion.playerDataFile
import org.jiwon.serverCore.utils.PersistentDataContainerManager
import java.util.*

object PlayerManager {
        fun getPlayer(uuid: UUID):Player? {
            return if (instance.server.getPlayer(uuid) != null) {
                instance.server.getPlayer(uuid)!!
            } else if (instance.server.getOfflinePlayer(uuid).player != null) {
                instance.server.getOfflinePlayer(uuid).player
            } else null
        }

    fun getPlayerName(uuid:String):String?{
        return if(playerDataFile.getRawData(uuid) != null) {
            playerDataFile.getString(uuid)
        }else null
    }

        fun getPlayer(name:String):Player? {
            return if (instance.server.getPlayer(name) != null) {
                instance.server.getPlayer(name)
            } else if (playerDataFile.getRawData(name) != null) instance.server.getPlayer(UUID.fromString(playerDataFile.getString(name)))
            else null
        }

    fun getAllPlayerItemAmount(player:Player,type:Material):Int{
        var count = 0
        player.inventory.storageContents.forEach { item ->
            if(item != null) {
                if(ItemManager.defaultItemCompare(item,type)){
                    count += item.amount
                }
            }
        }
        return count
    }

    /**
     * @description 플레이어의 인벤토리가 가득 찼거나 동일 아이템의 수량이 남아있는 경우를 판단하는 함수
     * @param player 인벤토리를 확인할 플레이어
     * @param type 기준 아이템 재질
     * @param isMax 아이템 최대 수량 보유 확인 여부
     * @param isRestrict 아이템 특정 키 포함 여부 확인
     */
    fun isPlayerInventoryFull(player:Player,type:Material,isMax:Boolean,isRestrict:Boolean): Boolean {
        var isFull = true
        player.inventory.storageContents.forEach {
            item ->
            if(item == null) isFull = false
            else if(item.type == Material.AIR) isFull = false
            else if(item.type == type && item.amount < type.maxStackSize && !isMax && if(isRestrict) PersistentDataContainerManager.getItemRestrictInfo(item.itemMeta) != null else true) isFull = false
        }
        return isFull
    }


    /**
     * 플레이어 인벤토리 관련 업데이트 필요 시 player.updateInventory() 함수 필수 호출
     */

    /**
     * @description 플레이어 인벤토리에서 커스텀 아이템을 찾아 삭제하는 함수
     * @param player 대상 플레이어
     * @param material 대상 재질
     * @param customData 커스텀 데이터
     */
    fun removePlayerCustomDataItem(player: Player,material: Material,customData:String): Boolean {
        var result = false
        player.inventory.storageContents.forEach {
                item ->
            if(item == null) return@forEach
            else if(item.type != material) return@forEach
            if(PersistentDataContainerManager.getItemCustomData(item.itemMeta) == customData){
                player.inventory.removeItem(item)
                player.updateInventory()
                result = true
            }
        }
        return result

    }

    /**
     * @description 플레이어 인벤토리에 아이템을 추가하는 함수
     * @param player 대상 플레이어
     * @param item 추가 할 아이템
     */
    fun addToPlayerInventoryItem(player:Player,item:ItemStack){
        player.inventory.addItem(item)
        player.updateInventory()
    }

    /**
     * @description 플레이어 인벤토리에 일치하는 아이템을 삭제하는 함수
     * @param player 대상 플레이어
     * @param type 기준 아이템
     */
    fun removeToPlayerInventoryOneItem(player:Player, type:Material){
        run remove@{
            player.inventory.storageContents.forEach { item ->
                if (item != null) {
                    if (ItemManager.defaultItemCompare(targetItem = item,type=type)) {
                        item.amount -= 1
                        return@remove
                    }
                }
            }
        }
        player.updateInventory()
    }

    /**
     * @description 플레이어 인벤토리에 일치하는 아이템 모두 삭제하는 함수
     * @param player 대상 플레이어
     * @param type 기준 아이템 재질
     */
    fun removeToPlayerInventoryAllItem(player:Player, type:Material){
        player.inventory.storageContents.forEach { item ->
            if(item != null){
                if(ItemManager.defaultItemCompare(targetItem = item,type=type)){
                    item.amount -= item.amount
                }
            }
        }
        player.updateInventory()
    }

    /**
     * @description 플레이어에게 소리를 재생하는 함수
     * @param player 대상 플레이어
     * @param sound 재생 할 소리 열거값
     */
    fun playerSound(player:Player,sound:Sound){
        player.playSound(player as Entity,sound,1f,1f)
    }
}