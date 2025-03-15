package org.jiwon.serverCore.manager

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.World.Environment.*
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.response.SimpleFormResponse
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.ServerCore.Companion.survivalDataFile
import org.jiwon.serverCore.alternative.ConfigNames.END_TOLL_PRICE
import org.jiwon.serverCore.alternative.ConfigNames.END_WORLD
import org.jiwon.serverCore.alternative.ConfigNames.NETHER_TOLL_PRICE
import org.jiwon.serverCore.alternative.ConfigNames.NETHER_WORLD
import org.jiwon.serverCore.alternative.ConfigNames.SURVIVAL_TOLL
import org.jiwon.serverCore.alternative.ConfigNames.SURVIVAL_TOLL_PRICE
import org.jiwon.serverCore.alternative.ConfigNames.SURVIVAL_WORLD
import org.jiwon.serverCore.alternative.ReplaceTexts.TAX
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.messages.SurvivalMessage.SURVIVAL_MENU
import org.jiwon.serverCore.messages.SurvivalMessage.SURVIVAL_TITLE
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.Components.mmLegacy
import org.jiwon.serverCore.utils.NumberUtils
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList


object SurvivalManager {
    enum class SurvivalOptions {
        Survival,
        Nether,
        End,
    }
    fun setSurvivalWorld(world: World){
        when (world.environment) {
            NORMAL -> {
                survivalDataFile.set(SURVIVAL_WORLD,world.uid)
            }
            NETHER -> {
                survivalDataFile.set(NETHER_WORLD,world.uid)
            }
            THE_END -> {
                survivalDataFile.set(END_WORLD,world.uid)
            }

            else->{

            }
        }
        survivalDataFile.save()
    }

    private fun getSurvivalWorld(survivalOptions: SurvivalOptions): String? {
        return when(survivalOptions){
            SurvivalOptions.Survival ->{
                survivalDataFile.getRawData(SURVIVAL_WORLD)
            }

            SurvivalOptions.Nether ->{
                survivalDataFile.getRawData(NETHER_WORLD)
            }

            else ->{
                survivalDataFile.getRawData(END_WORLD)
            }
        }?.asString
    }

    fun survivalMenu(): Inventory {
        val menuItems = ArrayList<ItemStack?>()
        var temp = 0
        for(i in 0..8){
            if(i == 2 || i == 4 || i == 6){
                menuItems.add(ItemManager.createItem(
                    (SURVIVAL_MENU[temp][0] as Material),
                    mm(SURVIVAL_MENU[temp][1] as String),
                    mutableListOf(
                        mm((SURVIVAL_MENU[temp][2] as String).replace(TAX,
                            NumberUtils.format(
                                defaultConfig.getJsonObject(SURVIVAL_TOLL).get(
                                    if(temp == 0) SURVIVAL_TOLL_PRICE else if(temp == 1) NETHER_TOLL_PRICE else END_TOLL_PRICE
                                ).asInt
                            )))
                    ),
                    false,
                    temp.toString()
                ))
                temp++
            }else{
                menuItems.add(null)
            }
        }
        val inventory = InventoryManager(9,mm(SURVIVAL_TITLE),menuItems)

        return inventory.inventory

    }

    fun survivalBedrockMenu(callback:Consumer<SimpleFormResponse>): SimpleForm {
        val form = SimpleForm.builder().title(mmLegacy(SURVIVAL_TITLE))
        SURVIVAL_MENU.forEachIndexed { index, it ->
            form.button("${mmLegacy(it[1] as String)}\n${mmLegacy((it[2] as String).replace(
                TAX, NumberUtils.format(
                    defaultConfig.getJsonObject(SURVIVAL_TOLL).get(
                        if(index == 0) SURVIVAL_TOLL_PRICE else if(index == 1) NETHER_TOLL_PRICE else END_TOLL_PRICE
                    ).asInt
                )
            ))}")
        }
        form.validResultHandler(callback)
        return form.build()
    }

    fun getTollPrice(survivalOptions: SurvivalOptions): Int {
        return defaultConfig.getJsonObject(SURVIVAL_TOLL).get(
            if(survivalOptions == SurvivalOptions.Survival) SURVIVAL_TOLL_PRICE else if(survivalOptions == SurvivalOptions.Nether) NETHER_TOLL_PRICE else END_TOLL_PRICE
        ).asInt

    }

    fun removePass(player: Player,survivalOptions: SurvivalOptions):Boolean{
        return (PlayerManager.removePlayerCustomDataItem(player,Material.PAPER,survivalOptions.name))
    }

    fun addPass(player: Player,survivalOptions: SurvivalOptions):ItemStack{
        return ItemManager.createItem(
            Material.PAPER,
            mm(
                if(survivalOptions == SurvivalOptions.Nether) "${WHITE_RED}네더 통행권" else "${WHITE_RED}엔드 통행권"
            ),
            mutableListOf(
                mm(
                    "${LIGHT_YELLOW}월드간 통행시 통행료을 지불하여 이동할 수 있습니다."
                )
            ),
            true,
            survivalOptions.name

        )
    }

    fun getTeleportLocation(survivalOptions:SurvivalOptions): Location? {
        if(getSurvivalWorld(survivalOptions) == null) return null
        val world = Bukkit.getWorld(UUID.fromString(getSurvivalWorld(survivalOptions)))
        var location = Location(world,(-8000..8000).random().toDouble(),120.0,(-8000..8000).random().toDouble())
        while (true) {
            location = location.add(0.0,-1.0,0.0)
            if(location.block.type == Material.LAVA){
                location = Location(world,(-8000..8000).random().toDouble(),120.0,(-8000..8000).random().toDouble())
            }
            if(location.block.type != Material.VOID_AIR && location.block.type != Material.AIR && location.block.type != Material.CAVE_AIR && !location.block.isEmpty){
                break
            }
        }
        return location.add(0.0,1.0,0.0)
    }


}