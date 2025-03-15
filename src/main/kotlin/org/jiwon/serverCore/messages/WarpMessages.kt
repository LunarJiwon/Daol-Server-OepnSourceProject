package org.jiwon.serverCore.messages

import org.jiwon.serverCore.alternative.ReplaceTexts.WARP_NAME
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object WarpMessages {

    const val REQUIRE_WARP_NAME = "${PREFIX}${WHITE_RED}워프 이름이 필요합니다."
    const val ALREADY_WARP_NAME = "${PREFIX}${WHITE_RED}존재하는 워프 이름입니다."
    const val NOT_FOUND_WARP = "${PREFIX}${WHITE_RED}해당 이름은 존재하지 않습니다."
    const val CREATED_WARP = "${PREFIX}${GRAY}워프 ${WHITE_GREEN}${WARP_NAME}${GRAY}(을)를 생성하였습니다."
    const val REMOVED_WARP = "${PREFIX}${GRAY}워프 ${WHITE_RED}${WARP_NAME}${GRAY}(을)를 삭제하였습니다."
    const val TELEPORTED_WARP = "${PREFIX}${GRAY}워프 ${WHITE_GREEN}${WARP_NAME}${GRAY}(으)로 이동하였습니다."
}