package org.jiwon.serverCore.utils

import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.alternative.ConfigNames.ECONOMY_TAX
import java.text.DecimalFormat

object NumberUtils{

    /**
     * @description 화폐 단위 문자열 변환 함수
     * @param decimal 대상 숫자
     */
    fun format(decimal:Int):String{
        return DecimalFormat("###,###").format(decimal)
    }

    /**
     * @description 숫자 확인 함수
     * @param s 검증할 문자열
     */
    fun isNumeric(s: String): Boolean {
        return s.chars().allMatch { Character.isDigit(it) }
    }

    /**
     * @description 세금 계산 함수
     * @param amount 금액
     */
    fun calTax(amount:Int):Int{
        return (amount-(amount* defaultConfig.getFloat(ECONOMY_TAX))).toInt()
    }


}
