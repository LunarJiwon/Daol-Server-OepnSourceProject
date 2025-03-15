package org.jiwon.serverCore.events.land.process

import org.bukkit.Location
import org.bukkit.entity.Player
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.manager.LocationManager
import org.jiwon.serverCore.messages.LandTexts.SELECT_FIRST_LOCATION
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.LandData

abstract class LandSelectTask(private val player: Player,private val alreadyLandName:String?,private val secondLandName:String?) {

    private var tempLocation : Location? = null
    private var areaData : AreaData? = null

    init{
        firstTask()
    }

    fun firstTask(){
        areaData = null
        registerEvent(LandSelectEvent(player,alreadyLandName,secondLandName) { loc -> secondTask(loc) })
    }

    private fun secondTask(location:Location){
        player.sendMessage(mm(SELECT_FIRST_LOCATION))
        tempLocation = location
        registerEvent(LandSelectEvent(player,alreadyLandName,secondLandName){loc -> lastTask(loc)})
    }

    private fun lastTask(location: Location){
        areaData = AreaData(
            LocationManager.createJsonLocation(tempLocation!!),
            LocationManager.createJsonLocation(location)
        )
        result(areaData!!)
    }

    abstract fun result(areaData: AreaData)

}