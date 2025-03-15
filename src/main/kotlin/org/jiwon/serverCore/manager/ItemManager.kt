package org.jiwon.serverCore.manager

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.EconomyData
import org.jiwon.serverCore.utils.PersistentDataContainerManager

object ItemManager {
    /**
     * @description 메뉴 아이템을 만드는 함수
     * @param type 메뉴 아이템 재질
     * @param itemName 메뉴 이름
     * @param itemDescription 메뉴 설명
     * @param isGlow 인첸트 여부
     */
    fun createItem(type:Material, itemName:Component?, itemDescription:MutableList<Component>?, isGlow:Boolean):ItemStack{
        val tempItem = ItemStack(type,1)
        val tempItemMeta = tempItem.itemMeta
        if(itemName != null) tempItemMeta.itemName(itemName)
        if(isGlow){
            tempItemMeta.addEnchant(Enchantment.RESPIRATION,1,false)
        }
        tempItemMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        tempItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        tempItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        tempItemMeta.addItemFlags(ItemFlag.HIDE_STORED_ENCHANTS)
        tempItemMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        tempItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        tempItemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON)
        if(itemDescription != null) tempItemMeta.lore(itemDescription)
        tempItem.setItemMeta(tempItemMeta)
        return tempItem
    }

    /**
     * @description 아이템을 생성하는 함수
     * @param type 메뉴 아이템 재질
     * @param itemName 메뉴 이름
     * @param itemDescription 메뉴 설명
     * @param isGlow 인첸트 여부
     * @param customData 커스텀 데이터
     */
    fun createItem(type:Material, itemName:Component?, itemDescription:MutableList<Component>?, isGlow:Boolean, customData:String):ItemStack{
        val tempItem = createItem(type,itemName,itemDescription,isGlow)
        tempItem.setItemMeta(PersistentDataContainerManager.setItemCustomData(tempItem.itemMeta,customData))
        return tempItem
    }

    fun createBlankItem():ItemStack = createItem(Material.GRAY_STAINED_GLASS_PANE,mm(""),null,false)

    /**
     * @description 상점 전용 아이템을 생성하는 함수
     * @param economyData 상점 데이터
     * @param amount 수량
     * @return 일치여부 판단 결과 반환
     */
    fun createItemFromStoreData(economyData: EconomyData,amount:Int): ItemStack {
        val item = ItemStack(economyData.item,amount)
        if(!economyData.isRestrict){
            return item
        }else{
            item.setItemMeta(PersistentDataContainerManager.setItemRestrict(item.itemMeta,true))
            return item
        }
    }




    /**
     * @description 비교 대상의 아이템의 수정된 아이템을 제외하여 같은지 비교하는 함수
     * @param targetItem 검사 할 아이템
     * @param type 일치 여부를 판단할 기준 아이템 재질
     * @return 일치여부 판단 결과 반환
     */
    fun defaultItemCompare(targetItem:ItemStack,type:Material): Boolean {
        return targetItem.type == type && PersistentDataContainerManager.getItemRestrictInfo(targetItem.itemMeta) == null && targetItem.enchantments.isEmpty()
    }

}