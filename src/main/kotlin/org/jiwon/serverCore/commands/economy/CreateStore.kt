package org.jiwon.serverCore.commands.economy

import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.instance
import org.jiwon.serverCore.events.economy.CreateEconomyEvent
import org.jiwon.serverCore.manager.EconomyManager
import org.jiwon.serverCore.utils.EconomyData
import org.jiwon.serverCore.messages.SystemMessages.INCORRECT_ITEM
import org.jiwon.serverCore.messages.SystemMessages.MISSING_COMMAND_ARGS
import org.jiwon.serverCore.messages.SystemMessages.REQUIRE_NUMBER
import org.jiwon.serverCore.messages.SystemMessages.START_CREATE_ECONOMY
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.NumberUtils

class CreateStore(val player:Player, val args:Array<out String>) {
    init {
        execute()
    }

    private fun execute(){
        if(args.size > 2){
            if(NumberUtils.isNumeric(args[1]) || args[1] == "구매불가" && NumberUtils.isNumeric(args[2]) || args[2] == "판매불가"){
                if(!player.inventory.itemInMainHand.isEmpty){
                    player.sendMessage(mm(START_CREATE_ECONOMY))
                    instance.server.pluginManager.registerEvents(
                        // 이벤트 클래스 호출
                        CreateEconomyEvent(player, EconomyData(null,null,player.inventory.itemInMainHand.type,
                            if (args[1] != "구매불가") args[1].toInt() else -1, if (args[2] != "판매불가") args[2].toInt() else -1,if(args.size > 3) if(args[3] == "true") true else false else false)
                        ) { economyData -> EconomyManager.createStore(economyData) },
                        // 인스턴스
                        instance
                    )
                    return
                }
                player.sendMessage(mm(INCORRECT_ITEM))

            }
            player.sendMessage(mm(REQUIRE_NUMBER))
            return
        }
        player.sendMessage(mm(MISSING_COMMAND_ARGS))
        return
    }


}