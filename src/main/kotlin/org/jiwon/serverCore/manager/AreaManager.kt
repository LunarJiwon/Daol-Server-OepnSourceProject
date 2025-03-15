package org.jiwon.serverCore.manager

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.LocationData
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object AreaManager {

    fun spawnParticlesAtBorder(area: AreaData,player:Player){

        val world = LocationManager.getLocation(area.firstLocation).world
        val minX = minOf(area.firstLocation.x, area.secondLocation.x)
        val maxX = maxOf(area.firstLocation.x, area.secondLocation.x)
        val minY = player.location.y
        val maxY = player.location.add(0.0,10.0,0.0).y
        val minZ = minOf(area.firstLocation.z, area.secondLocation.z)
        val maxZ = maxOf(area.firstLocation.z, area.secondLocation.z)

        for (x in minX.toInt()..maxX.toInt()) {
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, x.toDouble(), minY, minZ - 0.5), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, x.toDouble(), minY, maxZ + 0.5), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, x.toDouble(), maxY, minZ - 0.5), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, x.toDouble(), maxY, maxZ + 0.5), 1)
        }

        // Z축 방향 테두리 (X 고정)
        for (z in minZ.toInt()..maxZ.toInt()) {
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, minX - 0.5, minY, z.toDouble()), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, maxX + 0.5, minY, z.toDouble()), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, minX - 0.5, maxY, z.toDouble()), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, maxX + 0.5, maxY, z.toDouble()), 1)
        }

        // Y축 방향 테두리 (높이 방향)
        for (y in minY.toInt()..maxY.toInt()) {
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, minX - 0.5, y.toDouble(), minZ - 0.5), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, maxX + 0.5, y.toDouble(), minZ - 0.5), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, minX - 0.5, y.toDouble(), maxZ + 0.5), 1)
            player.spawnParticle(Particle.HAPPY_VILLAGER, Location(world, maxX + 0.5, y.toDouble(), maxZ + 0.5), 1)
        }
    }

    fun getBlockCountFromArea(area:AreaData):Int{
        val maxX = max(area.firstLocation.x.toInt(),area.secondLocation.x.toInt())
        val minX = min(area.firstLocation.x.toInt(),area.secondLocation.x.toInt())

        val maxZ = max(area.firstLocation.z.toInt(),area.secondLocation.z.toInt())
        val minZ = min(area.firstLocation.z.toInt(),area.secondLocation.z.toInt())
        return (abs(maxX - minX) + 1) * (abs(maxZ - minZ) + 1)
    }

    fun isOverlapArea(firstArea:AreaData,secondArea: AreaData):Boolean{
        if(firstArea.firstLocation.worldUUID == secondArea.firstLocation.worldUUID) {
            val firstMinX = minOf(firstArea.firstLocation.x, firstArea.secondLocation.x)
            val firstMaxX = maxOf(firstArea.firstLocation.x, firstArea.secondLocation.x)
            val firstMinZ = minOf(firstArea.firstLocation.z, firstArea.secondLocation.z)
            val firstMaxZ = maxOf(firstArea.firstLocation.z, firstArea.secondLocation.z)

            val secondMinX = minOf(secondArea.firstLocation.x, secondArea.secondLocation.x)
            val secondMaxX = maxOf(secondArea.firstLocation.x, secondArea.secondLocation.x)
            val secondMinZ = minOf(secondArea.firstLocation.z, secondArea.secondLocation.z)
            val secondMaxZ = maxOf(secondArea.firstLocation.z, secondArea.secondLocation.z)

            val isXOverlap = firstMaxX >= secondMinX && secondMaxX >= firstMinX
            val isYOverlap = firstMaxZ >= secondMinZ && secondMaxZ >= firstMinZ

            return isXOverlap && isYOverlap
        }
        return false
    }

    fun isOverlapLocation(area: AreaData, location: LocationData): Boolean {
        if(area.firstLocation.worldUUID == location.worldUUID) {
            val minX = minOf(area.firstLocation.x, area.secondLocation.x)
            val maxX = maxOf(area.firstLocation.x, area.secondLocation.x)
            val minZ = minOf(area.firstLocation.z, area.secondLocation.z)
            val maxZ = maxOf(area.firstLocation.z, area.secondLocation.z)

            return location.x in minX..maxX && location.z in minZ..maxZ
        }
        return false
    }


}