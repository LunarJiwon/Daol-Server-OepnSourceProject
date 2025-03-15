package org.jiwon.serverCore.events.prefix

import org.bukkit.entity.Player
import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.manager.PrefixManager
import org.jiwon.serverCore.messages.PrefixMessages.SET_PREFIX
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.PersistentDataContainerManager

object PrefixMenuClickBedrock {

    fun menuClick(player: Player,buttonText:String){
        val prefix = buttonText.split("\n")[2]
        PrefixManager.setPrefix(player,prefix)
        player.sendMessage(mm(SET_PREFIX.replace(MESSAGE,prefix)))
    }

}