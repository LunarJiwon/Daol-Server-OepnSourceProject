package org.jiwon.serverCore.messages

import org.bukkit.Material
import org.jiwon.serverCore.alternative.ReplaceTexts.TAX
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.UN_ITALIC
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object SurvivalMessage {

    const val SET_WORLD = "${PREFIX}${WHITE_GREEN}야생 월드 설정이 완료되었습니다."

    val SURVIVAL_TITLE = "${WHITE_GREEN}야생 메뉴"

    val SURVIVAL_MENU = arrayOf(
        mutableListOf(
            Material.GRASS_BLOCK,
            "${WHITE_GREEN}야생으로 이동하기",
            "${UN_ITALIC}${LIGHT_YELLOW}통행료 : ${WHITE_RED}${TAX}원"
        ),
        mutableListOf(
            Material.NETHERRACK,
            "${WHITE_GREEN}네더로 이동하기",
            "${UN_ITALIC}${LIGHT_YELLOW}통행료 : ${WHITE_RED}${TAX}원"
        ),
        mutableListOf(
            Material.END_STONE,
            "${WHITE_GREEN}엔더로 이동하기",
            "${UN_ITALIC}${LIGHT_YELLOW}통행료 : ${WHITE_RED}${TAX}원"
        )
    )

    const val TELEPORTED_WORLD_SURVIVAL = "${PREFIX}${WHITE_GREEN}야생월드${GRAY}로 이동하였습니다."
    const val TELEPORTED_WORLD_NETHER = "${PREFIX}${WHITE_GREEN}네더월드${GRAY}로 이동하였습니다."
    const val TELEPORTED_WORLD_END = "${PREFIX}${WHITE_GREEN}엔드월드${GRAY}로 이동하였습니다."

    const val WORLD_READY = "${PREFIX}${GRAY}해당 월드는 준비중입니다."

}