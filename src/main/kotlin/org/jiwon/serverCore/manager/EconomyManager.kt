package org.jiwon.serverCore.manager

import com.google.gson.JsonArray
import eu.decentsoftware.holograms.api.DHAPI
import net.md_5.bungee.api.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.Sign
import org.bukkit.block.data.type.WallSign
import org.bukkit.block.sign.Side
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.geysermc.cumulus.form.CustomForm
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.response.CustomFormResponse
import org.geysermc.cumulus.response.SimpleFormResponse
import org.jiwon.serverCore.ServerCore.Companion.economyDataFile
import org.jiwon.serverCore.ServerCore.Companion.gson
import org.jiwon.serverCore.alternative.ConfigNames.STORE_LIST
import org.jiwon.serverCore.alternative.ReplaceTexts.AMOUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.ITEM_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PURCHASE_PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.SALE_PRICE
import org.jiwon.serverCore.messages.EconomyTexts
import org.jiwon.serverCore.messages.EconomyTexts.BEDROCK_ITEM_DESCRIPTION
import org.jiwon.serverCore.messages.EconomyTexts.ITEM_NAME_OPTION_MENU_1
import org.jiwon.serverCore.messages.EconomyTexts.ITEM_NAME_OPTION_MENU_2
import org.jiwon.serverCore.messages.EconomyTexts.ITEM_NAME_OPTION_MENU_3
import org.jiwon.serverCore.messages.EconomyTexts.ITEM_NAME_PURCHASE_MENU_1
import org.jiwon.serverCore.messages.EconomyTexts.ITEM_NAME_PURCHASE_MENU_2
import org.jiwon.serverCore.messages.EconomyTexts.ITEM_NAME_SALE_MENU_1
import org.jiwon.serverCore.messages.EconomyTexts.ITEM_NAME_SALE_MENU_2
import org.jiwon.serverCore.messages.EconomyTexts.STORE_INVENTORY_TEXT
import org.jiwon.serverCore.messages.MessageColors.BLANK_TEXT
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.EconomyData
import org.jiwon.serverCore.manager.LocationManager.getLocation
import org.jiwon.serverCore.utils.NumberUtils
import java.util.function.Consumer

object EconomyManager {

    fun openBedrockStore(economyData: EconomyData,result:Consumer<SimpleFormResponse>):SimpleForm{
        return SimpleForm.builder().title("상점").content(BEDROCK_ITEM_DESCRIPTION.replace(ITEM_NAME,"${ChatColor.GREEN}${economyData.item.name}${ChatColor.WHITE}"))
            .button("구매")
            .button("판매")
            .validResultHandler(result)
            .build()
    }

    fun openBedrockPurchaseMenu(economyData: EconomyData, result:Consumer<CustomFormResponse>): CustomForm {
        return CustomForm.builder().title("${economyData.item.name} 구매하기")
            .label("${ChatColor.WHITE}아이템 ${ChatColor.GREEN}${economyData.item.name}${ChatColor.WHITE}을(를) 구매하시겠습니까?")
            .slider("구매 수량", 1.0F,economyData.item.maxStackSize.toFloat(),economyData.item.maxStackSize.toFloat(),1.0F)
            .validResultHandler(result)
            .build()
    }

    fun openBedrockSaleMenu(economyData: EconomyData, result:Consumer<CustomFormResponse>): CustomForm {
        return CustomForm.builder().title("${economyData.item.name} 판매하기")
            .label("${ChatColor.WHITE}아이템 ${ChatColor.GREEN}${economyData.item.name}${ChatColor.WHITE}을(를) 판매하시겠습니까?")
            .toggle("전체판매")
            .validResultHandler(result)
            .build()
    }


    fun openStore(economyData: EconomyData):Inventory{
        val itemList = ArrayList<ItemStack?>()
        for (i in 0..8){
            if(i == 4){
                itemList.add(ItemManager.createItem(economyData.item,null, mutableListOf(
                    mm(ITEM_NAME_OPTION_MENU_1.replace(PURCHASE_PRICE,if(economyData.purchasePrice != -1) "${NumberUtils.format(economyData.purchasePrice)}원" else "${WHITE_RED}<b>구매불가</b>")),
                    mm(ITEM_NAME_OPTION_MENU_2.replace(SALE_PRICE,if(economyData.salePrice != -1) "${NumberUtils.format(economyData.salePrice)}원" else "${WHITE_RED}<b>판매불가</b>")),
                    mm(BLANK_TEXT),
                    mm(ITEM_NAME_OPTION_MENU_3)
                ),false))
                continue
            }
            itemList.add(null)
        }
        val inventoryManager = InventoryManager(9,mm(STORE_INVENTORY_TEXT),itemList)
        return inventoryManager.inventory
    }

    fun openSaleMenu(economyData: EconomyData,itemAmount:Int):Inventory{
        val itemList = ArrayList<ItemStack?>()
        for (i in 0..8){
            if(i == 3){
                itemList.add(ItemManager.createItem(economyData.item,null, mutableListOf(
                    mm(ITEM_NAME_SALE_MENU_1
                        .replace(AMOUNT,"1개")
                        .replace(PURCHASE_PRICE,"${NumberUtils.format(economyData.salePrice)}원")),
                    mm(BLANK_TEXT),
                    mm(ITEM_NAME_SALE_MENU_2
                        .replace(AMOUNT,"1개"))
                ),false))
                continue
            }else if(i==5){
                itemList.add(ItemManager.createItem(economyData.item,null, mutableListOf(
                    mm(ITEM_NAME_SALE_MENU_1
                        .replace(AMOUNT,"전체 판매")
                        .replace(PURCHASE_PRICE,"${NumberUtils.format(economyData.salePrice*itemAmount)}원")),
                    mm(BLANK_TEXT),
                    mm(ITEM_NAME_SALE_MENU_2
                        .replace(AMOUNT,"전체"))
                ),false))
                continue
            }
            itemList.add(null)
        }
        val inventoryManager = InventoryManager(9,mm(STORE_INVENTORY_TEXT),itemList)
        return inventoryManager.inventory
    }

    fun openPurchaseMenu(economyData: EconomyData):Inventory{
        val itemList = ArrayList<ItemStack?>()
        for (i in 0..8){
            if(i == 3){
                itemList.add(ItemManager.createItem(economyData.item,null, mutableListOf(
                    mm(ITEM_NAME_PURCHASE_MENU_1
                        .replace(AMOUNT,"1")
                        .replace(PURCHASE_PRICE,"${NumberUtils.format(economyData.purchasePrice)}원")),
                    mm(BLANK_TEXT),
                    mm(ITEM_NAME_PURCHASE_MENU_2
                        .replace(AMOUNT,"1"))
                ),false))
                continue
            }else if(i==5){
                itemList.add(ItemManager.createItem(economyData.item,null, mutableListOf(
                    mm(ITEM_NAME_PURCHASE_MENU_1
                        .replace(AMOUNT,economyData.item.maxStackSize.toString())
                        .replace(PURCHASE_PRICE,"${NumberUtils.format(economyData.purchasePrice*economyData.item.maxStackSize)}원")),
                    mm(BLANK_TEXT),
                    mm(ITEM_NAME_PURCHASE_MENU_2
                        .replace(AMOUNT,economyData.item.maxStackSize.toString()))
                ),false))
                continue
            }
            itemList.add(null)
        }
        val inventoryManager = InventoryManager(9,mm(STORE_INVENTORY_TEXT),itemList)
        return inventoryManager.inventory
    }

    fun createStore(economyData: EconomyData) {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        val storeName = (1..10).map { charset.random() }.joinToString("").toString()
        val storeArray: JsonArray
        if (economyDataFile.getRawData(STORE_LIST) != null) {
            storeArray = (economyDataFile.getJsonArray(STORE_LIST))
            if (storeArray.find { store ->
                    (gson.fromJson(
                        store.deepCopy(),
                        EconomyData::class.java
                    )).name == storeName
                } != null) return createStore(economyData)
            economyData.name = storeName
            storeArray.add(gson.toJsonTree(economyData))


        } else {
            economyData.name = storeName
            storeArray = JsonArray()
            storeArray.add(gson.toJsonTree(economyData))
            economyDataFile.set(STORE_LIST, storeArray)

        }
        economyDataFile.save()

        // 표지판 텍스트 변경
        val signBlock = (getLocation(economyData.sign!!).block)
        val sign = (signBlock.state as Sign)
        // 베드락 시인성 향상 목표..
//        sign.getSide(Side.FRONT).isGlowingText = true
        sign.getSide(Side.FRONT).line(0, mm(EconomyTexts.STORE_TITLE_TEXT))
        sign.getSide(Side.FRONT).line(1, mm(EconomyTexts.ITEM_NAME_TEXT.replace(ITEM_NAME, economyData.item.name)))
        sign.getSide(Side.FRONT).line(
            2, mm(
                EconomyTexts.STORE_PURCHASE_PRICE_TEXT.replace(
                    PURCHASE_PRICE,
                    if (economyData.purchasePrice != -1) "${economyData.purchasePrice}원" else "${WHITE_RED}<b>구매불가</b>"
                )
            )
        )
        sign.getSide(Side.FRONT).line(
            3, mm(
                EconomyTexts.STORE_SALE_PRICE_TEXT.replace(
                    SALE_PRICE,
                    if (economyData.salePrice != -1) "${economyData.salePrice}원" else "${WHITE_RED}<b>판매불가</b>"
                )
            )
        )
        sign.update() // 표지판 업데이트
        createHologram(signBlock,storeName,economyData.item)

    }

    fun createHologram(signBlock:Block,storeName:String,item:Material){
        val facing = (signBlock.blockData as WallSign).facing
        val hologramLocation = when (facing) {
            BlockFace.WEST -> signBlock.location.add(1.0, 1.0, 0.0).toCenterLocation().add(0.0, 0.5, 0.0)
            BlockFace.EAST -> signBlock.location.add(-1.0, 1.0, 0.0).toCenterLocation().add(0.0, 0.5, 0.0)
            BlockFace.SOUTH -> signBlock.location.add(0.0, 1.0, -1.0).toCenterLocation().add(0.0, 0.5, 0.0)
            else -> signBlock.location.add(0.0, 1.0, 1.0).toCenterLocation().add(0.0, 0.5, 0.0)
        }

        // 아이템 홀로그램

        DHAPI.addHologramLine(
            DHAPI.createHologram(storeName, hologramLocation, true, mutableListOf("")),
            item
        )

    }

    fun getStoreInSign(location:Location):EconomyData?{
        if(economyDataFile.getRawData(STORE_LIST) != null){
            economyDataFile.getJsonArray(STORE_LIST).forEach { cacheStore->
                val store = gson.fromJson(cacheStore.deepCopy(),EconomyData::class.java)
                if(location == getLocation(store.sign!!)) return store

            }
        }
        return null
    }

    fun removeStore(economyData: EconomyData):Boolean{
        if(economyDataFile.getRawData(STORE_LIST) != null){
            val stores = economyDataFile.getJsonArray(STORE_LIST)
            stores.forEach { cacheStore->
                val store = gson.fromJson(cacheStore.deepCopy(),EconomyData::class.java)
                if(economyData == store) {
                    DHAPI.removeHologram(store.name)
                    stores.remove(cacheStore)
                    economyDataFile.save()
                    return true
                }
            }
        }
        return false
    }






}