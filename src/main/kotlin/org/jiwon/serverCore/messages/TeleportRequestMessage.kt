package org.jiwon.serverCore.messages

import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object TeleportRequestMessage {
    const val REQUIRE_ARGS = "${PREFIX}${GRAY}텔레포트를 요청 할 ${WHITE_RED}플레이어${GRAY} 혹은 요청에 대한 ${WHITE_RED}수락, 거절${GRAY}을 입력해주세요."

    const val ALREADY_REQUEST = "${PREFIX}${GRAY}해당 플레이어에게 이미 요청을 전송하였습니다."

    const val SUCCESS_REQUEST_TELEPORT = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${PLAYER_NAME}${GRAY}에게 텔레포트 요청을 하였습니다."
    const val REQUEST_TELEPORT = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${PLAYER_NAME}${GRAY}에게 텔레포트 요청이 왔습니다. ${LIGHT_YELLOW}(수락하려면 /텔포요청 수락)"

    const val ACCEPT_TELEPORT = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${PLAYER_NAME}${GRAY}의 텔레포트 요청을 수락하였습니다."
    const val ACCEPT_TELEPORT_DONE = "${PREFIX}${GRAY}텔레포트 요청이 수락되어 이동됩니다."

    const val NOT_FOUND_REQUEST = "${PREFIX}${GRAY}텔레포트를 요청한 플레이어가 존재하지 않습니다."

    const val DENY_TELEPORT = "${PREFIX}${GRAY}플레이어 ${WHITE_RED}${PLAYER_NAME}${GRAY}의 텔레포트 요청을 거절하였습니다."

}