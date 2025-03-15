package org.jiwon.serverCore.manager

import org.bukkit.entity.Player
import org.jiwon.serverCore.ServerCore.Companion.balanceDataFile
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.Logger
import org.jiwon.serverCore.utils.NumberUtils

object BalanceManager {

        /**
         * @description 플레이어 통장 존재 여부 반환 함수
         * @param player 대상 플레이어
         */
        fun isExistsPlayerBalance(player:Player):Boolean = balanceDataFile.getRawData(player.uniqueId.toString()) != null

        /**
         * @description 통장 잔액 가져오는 함수
         * @param player 대상 플레이어
         */
        fun getPlayerBalance(player: Player) : Int = Integer.parseInt((balanceDataFile.getInt(player.uniqueId.toString())).toString())


        /**
         * @description 플레이어 통장 개설 함수
         * @param player 대상 플레이어
         */
        fun createPlayerBalance(player:Player) : Boolean{
            balanceDataFile.set(player.uniqueId.toString(), 10000)
            balanceDataFile.save()
            Logger.info(mm("<#aeff70>${player.name}의 통장 개설"))
            return true
        }

        /**
         * @description 플레이어 통장 잔액 설정
         * @param player 대상 플레이어
         * @param amount 금액
         */
        fun setPlayerBalance(player: Player, amount:Int){
            balanceDataFile.set(player.uniqueId.toString(), amount)
        }

        /**
         * @description 플레이어 통장 잔액 추가
         * @param player 대상 플레이어
         * @param amount 금액
         */
        fun addPlayerBalance(player: Player, amount:Int){
            balanceDataFile.set(player.uniqueId.toString(), getPlayerBalance(player)+amount)
        }

        /**
         * @description 플레이어 통장 잔액 삭감
         * @param player 대상 플레이어
         * @param amount 금액
         */
        fun removePlayerBalance(player: Player, amount:Int){
            balanceDataFile.set(player.uniqueId.toString(), getPlayerBalance(player)-amount)
        }

        /**
         * @description 플레이어 - 플레이어 송금 함수
         * @param originPlayer 발송인
         * @param targetPlayer 수취인
         * @param amount 금액
         */
        fun sendPlayerToPlayerBalance(originPlayer:Player, targetPlayer:Player, amount:Int):Boolean{
            if(getPlayerBalance(originPlayer) >= amount){
                setPlayerBalance(originPlayer, getPlayerBalance(originPlayer) - amount)
                setPlayerBalance(targetPlayer, getPlayerBalance(targetPlayer) + NumberUtils.calTax(amount))
                return true
            }
            return false
        }


}