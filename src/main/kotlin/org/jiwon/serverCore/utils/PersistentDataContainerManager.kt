package org.jiwon.serverCore.utils

import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import org.jiwon.serverCore.ServerCore.Companion.instance

object PersistentDataContainerManager {
    private val dataKey = NamespacedKey(instance,"data")
    private val storeKey = NamespacedKey(instance,"restrict")
    fun setItemRestrict(itemMeta:ItemMeta,isRestrict:Boolean): ItemMeta {
        itemMeta.persistentDataContainer.set(storeKey, PersistentDataType.BOOLEAN,isRestrict)
        return itemMeta
    }

    fun getItemRestrictInfo(itemMeta: ItemMeta):Boolean?{
        return itemMeta.persistentDataContainer.get(storeKey, PersistentDataType.BOOLEAN)
    }

    fun setItemCustomData(itemMeta: ItemMeta,data:String):ItemMeta{
        itemMeta.persistentDataContainer.set(dataKey,PersistentDataType.STRING,data)
        return itemMeta
    }

    fun getItemCustomData(itemMeta: ItemMeta):String?{
        return itemMeta.persistentDataContainer.get(dataKey,PersistentDataType.STRING)
    }
}