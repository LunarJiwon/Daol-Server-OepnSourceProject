package org.jiwon.serverCore.manager

import com.google.gson.*
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import org.jiwon.serverCore.ServerCore
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.charset.StandardCharsets

class ConfigManager(fileName: String) {

    private val file: File = File(JavaPlugin.getPlugin(ServerCore::class.java).dataFolder, "${fileName}.json")

    private var temp: JsonObject = JsonObject()

    val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    init {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.writeText("{}", StandardCharsets.UTF_8)
        }
        reload()
    }

    fun reload() {
        val reader = FileReader(file)
        temp = gson.fromJson(reader, JsonObject::class.java) ?: JsonObject()
        reader.close()
    }

    fun getRawData(key: String): JsonElement? = temp[key]

    fun getJsonObject(key:String):JsonObject = temp.getAsJsonObject(key)

    fun getString(key: String) = temp[key]?.asString ?: ""

    fun getInt(key: String) = temp[key]?.asInt ?: 0

    fun getFloat(key: String) = temp[key]?.asFloat ?: 0.0f



    fun getJsonArray(key: String): JsonArray {
        return temp[key]?.asJsonArray ?: JsonArray()
    }

    fun getKeyFromValue(value:Any):String?{
        return temp.keySet().find { key -> temp[key] == value }
    }

    fun set(key: String, value: Any) {
        if (temp.has(key)) {
            temp.remove(key)
        }
        temp.add(key, gson.toJsonTree(value))
    }

    fun save() {
        val writer = FileWriter(file, StandardCharsets.UTF_8)
        try {
            writer.write(gson.toJson(temp))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            writer.flush()
            writer.close()
        }
    }
}
