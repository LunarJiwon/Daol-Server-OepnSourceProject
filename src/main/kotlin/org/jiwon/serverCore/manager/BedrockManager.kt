package org.jiwon.serverCore.manager

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.response.SimpleFormResponse
import org.jiwon.serverCore.utils.Components
import java.util.function.Consumer

object BedrockManager {

    fun playerSelectMenu(title:String,player:Player,response:Consumer<SimpleFormResponse>): SimpleForm {
        val form = SimpleForm.builder().title(Components.mmLegacy(title))
        Bukkit.getOnlinePlayers().forEach {
            if(it.player != player){
                form.button(it.name)
            }
        }
        form.validResultHandler(response)
        return form.build()
    }

}