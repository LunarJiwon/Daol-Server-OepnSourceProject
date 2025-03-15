package org.jiwon.serverCore.messages

import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.RESET
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object PrefixMessages {

    const val PREFIX_ITEM_DESCRIPTION = "${MessageColors.UN_ITALIC}${LIGHT_YELLOW}길게 누르거나 클릭하여 칭호 적용하기"

    const val PREFIX_MENU_TITLE = "${LIGHT_YELLOW}현재 칭호 : $RESET"
    const val SET_PREFIX = "${PREFIX}${GRAY}칭호가 ${MESSAGE}${RESET}${GRAY}(으)로 적용되었습니다."
    const val CREATED_PREFIX = "${PREFIX}${GRAY}칭호가 발급되었습니다."
    const val CLEAR_PREFIX = "${PREFIX}${GRAY}칭호 설정이 초기화 되었습니다."
    const val ALREADY_PREFIX = "${PREFIX}${GRAY}해당 칭호는 이미 보유하고 있습니다."

}