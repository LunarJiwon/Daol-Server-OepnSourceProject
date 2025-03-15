package org.jiwon.serverCore.manager

import com.google.gson.JsonArray
import org.jiwon.serverCore.ServerCore.Companion.gson
import org.jiwon.serverCore.ServerCore.Companion.warpDataFile
import org.jiwon.serverCore.alternative.ConfigNames.WARP_LIST
import org.jiwon.serverCore.utils.WarpData

object WarpManager {

    fun createWarp(warpData: WarpData){
        if(warpDataFile.getRawData(WARP_LIST) != null){
            warpDataFile.getJsonArray(WARP_LIST).add(
                gson.toJsonTree(warpData)
            )
        }else{
            val warpList = JsonArray()
            warpList.add(gson.toJsonTree(warpData))
            warpDataFile.set(WARP_LIST,warpList)
        }
        warpDataFile.save()
    }

    fun getWarp(warpName:String):WarpData?{
        if(warpDataFile.getRawData(WARP_LIST) != null){
            return gson.fromJson(warpDataFile.getJsonArray(WARP_LIST).find { warp->gson.fromJson(warp,WarpData::class.java).warpName == warpName },WarpData::class.java)
        }
        return null
    }

    fun removeWarp(warpName: String){
        warpDataFile.getJsonArray(WARP_LIST).remove(warpDataFile.getJsonArray(WARP_LIST).find { warp -> gson.fromJson(warp,WarpData::class.java).warpName == warpName })
    }
}