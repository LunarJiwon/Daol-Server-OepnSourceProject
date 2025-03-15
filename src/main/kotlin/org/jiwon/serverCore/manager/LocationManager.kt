package org.jiwon.serverCore.manager

import org.bukkit.Bukkit
import org.bukkit.Location
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.utils.LocationData
import java.util.UUID


object LocationManager{

        fun createJsonLocation(location: Location) = LocationData(location.world.uid,location.x,location.y,location.z,location.yaw,location.pitch)
        fun getLocation(location: LocationData) = Location(instance.server.getWorld(location.worldUUID),location.x,location.y,location.z,location.yaw,location.pitch)
        fun getLocationFromString(location:String): Location {
                val splitLocation = location.split(":")
                return Location(Bukkit.getWorld(UUID.fromString(splitLocation[0])),splitLocation[1].toDouble(),splitLocation[2].toDouble(),splitLocation[3].toDouble(),splitLocation[4].toFloat(),splitLocation[5].toFloat())
        }
        fun createStringToLocationData(location: Location):String{
                return "${location.world.uid}:${location.x}:${location.y}:${location.z}:${location.yaw}:${location.pitch}"
        }

}