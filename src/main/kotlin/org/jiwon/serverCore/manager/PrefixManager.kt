package org.jiwon.serverCore.manager

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.kyori.adventure.text.TextComponent
import net.luckperms.api.node.NodeType
import net.luckperms.api.node.types.PrefixNode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.geysermc.cumulus.form.CustomForm
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.response.CustomFormResponse
import org.geysermc.cumulus.response.SimpleFormResponse
import org.geysermc.cumulus.response.result.FormResponseResult
import org.geysermc.cumulus.response.result.ValidFormResponseResult
import org.jiwon.serverCore.ServerCore.Companion.luckperms
import org.jiwon.serverCore.ServerCore.Companion.prefixData
import org.jiwon.serverCore.messages.PrefixMessages.PREFIX_ITEM_DESCRIPTION
import org.jiwon.serverCore.messages.PrefixMessages.PREFIX_MENU_TITLE
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.Components.mmLegacy
import java.util.function.Consumer

object PrefixManager {

    fun createPrefixItem(prefix:String):ItemStack = ItemManager.createItem(Material.NAME_TAG,mm(prefix), mutableListOf(mm(PREFIX_ITEM_DESCRIPTION)),true,"prefix:${prefix}")

    fun addPrefixPlayer(player: Player, prefix: String):Boolean{
        if(prefixData.getRawData(player.uniqueId.toString()) == null){
            val playerDataObject = JsonObject()
            val prefixList = JsonArray()
            prefixList.add(prefix)

            playerDataObject.addProperty("current",prefix)
            playerDataObject.add("prefixes",prefixList)

            prefixData.set(player.uniqueId.toString(),playerDataObject)
        }else{

            if(prefixData.getJsonObject(player.uniqueId.toString()).getAsJsonArray("prefixes").any { p -> p.asString == prefix}){
                return false
            }

            val tempPrefixData = prefixData.getJsonObject(player.uniqueId.toString())
            val prefixList = tempPrefixData.getAsJsonArray("prefixes")
            prefixList.add(prefix)
            tempPrefixData.addProperty("current",prefix)
            prefixData.set(player.uniqueId.toString(),tempPrefixData)
        }
        luckperms.userManager.modifyUser(player.uniqueId) {
            it.data().clear { n -> n.type == NodeType.PREFIX }
            it.data().add(PrefixNode.builder(mmLegacy("$prefix &r&f"), 100).build())
        }
        prefixData.save()
        return true
    }

    fun resetPrefixPlayer(player: Player){
        if(prefixData.getRawData(player.uniqueId.toString()) != null){
            luckperms.userManager.modifyUser(player.uniqueId) {
                it.data().clear { n -> n.type == NodeType.PREFIX }
//                it.nodes.removeIf { n -> n.type == NodeType.PREFIX }
            }
            prefixData.getJsonObject(player.uniqueId.toString()).addProperty("current","-")
            prefixData.save()

        }
    }

    fun setPrefix(player: Player,prefix: String){
        val tempPrefixData = prefixData.getJsonObject(player.uniqueId.toString())
        tempPrefixData.addProperty("current",prefix)
        prefixData.set(player.uniqueId.toString(),tempPrefixData)
        luckperms.userManager.modifyUser(player.uniqueId) {
            it.data().clear { n -> n.type == NodeType.PREFIX }
            it.data().add(PrefixNode.builder(mmLegacy("$prefix &r&f"), 100).build())
        }
        prefixData.save()
    }


    fun prefixMenu(player: Player):Inventory{
        val menuList = ArrayList<ItemStack?>()

        var currentPrefix : String? = null

        if(prefixData.getRawData(player.uniqueId.toString()) != null){
            currentPrefix = prefixData.getJsonObject(player.uniqueId.toString()).get("current").asString
            val prefixList = prefixData.getJsonObject(player.uniqueId.toString()).getAsJsonArray("prefixes")
            prefixList.forEach {
                menuList.add(
                    ItemManager.createItem(Material.NAME_TAG,
                        mm(it.asString),
                        mutableListOf(mm(PREFIX_ITEM_DESCRIPTION)),
                        false,
                        "prefix:${it.asString}"
                    )
                )
            }
        }

        val prefixInventory = InventoryManager(54,mm(PREFIX_MENU_TITLE + if(currentPrefix != null && currentPrefix != "-") currentPrefix else "없음"),menuList)
        return prefixInventory.inventory
    }

    fun prefixBedrockMenu(player: Player,callback:Consumer<SimpleFormResponse>): SimpleForm {
        var currentPrefix :String? = null
        val form = SimpleForm.builder()

        if(prefixData.getRawData(player.uniqueId.toString()) != null){
            currentPrefix = prefixData.getJsonObject(player.uniqueId.toString()).get("current").asString
            prefixData.getJsonObject(player.uniqueId.toString()).getAsJsonArray("prefixes").forEach {
                form.button("${mmLegacy(it.asString)}\n${mmLegacy(PREFIX_ITEM_DESCRIPTION)}\n${it.asString}")
            }
        }


        form.title(mmLegacy(PREFIX_MENU_TITLE + if(currentPrefix != null && currentPrefix != "-") currentPrefix else "없음"))
        form.validResultHandler(callback)
        return form.build()
    }



}