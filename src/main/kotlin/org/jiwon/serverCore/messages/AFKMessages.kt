package org.jiwon.serverCore.messages

import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object AFKMessages {

    const val ADDED_POINT = "${PREFIX}${GRAY}1분동안 잠수하여 ${WHITE_GREEN}잠수 코인${GRAY}이 지급되었습니다."
    const val STARTED_AFK = "${PREFIX}${GRAY}잠수가 시작되었습니다."
    const val END_AFK = "${PREFIX}${GRAY}잠수장을 벗어나 잠수가 종료되었습니다."

}