package org.jiwon.serverCore.manager

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.jiwon.serverCore.ServerCore.Companion.instance

class InventoryManager(menuSize:Int, title:Component, data:ArrayList<ItemStack?>):InventoryHolder {

    private var inventory:Inventory

    init{
        val tempInventory = Bukkit.createInventory(this,menuSize,title)
        data.forEachIndexed { index,menuData ->
            tempInventory.setItem(index,
                menuData ?: ItemManager.createBlankItem()
            )
        }
        inventory = tempInventory
    }

    override fun getInventory(): Inventory = inventory
}