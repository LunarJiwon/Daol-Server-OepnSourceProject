package org.jiwon.serverCore.messages

import org.jiwon.serverCore.alternative.ReplaceTexts.MESSAGE
import org.jiwon.serverCore.alternative.ReplaceTexts.PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.messages.MessageColors.BOLD
import org.jiwon.serverCore.messages.MessageColors.GOLD
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.NEW_LINE
import org.jiwon.serverCore.messages.MessageColors.RESET
import org.jiwon.serverCore.messages.MessageColors.WHITE
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED

object SystemMessages {
        const val PREFIX = " <#fcba03>》 "
        const val SUCCESS_RELOAD = "${PREFIX}<#ababab>설정 파일을 새로 불러왔습니다."
        const val SUCCESS_SET_TUTORIAL = "${PREFIX}<#ababab>튜토리얼 위치를 현재 서있는 위치로 설정하였습니다."
        const val SUCCESS_SET_SPAWN = "${PREFIX}<#ababab>리스폰 위치를 현재 서있는 위치로 설정하였습니다."

        const val MAINTENANCE_START_BROADCAST_TITLE = "${GOLD}잠시 후 점검이 시작됩니다."
        const val MAINTENANCE_START_BROADCAST_SUBTITLE = "${GRAY}점검 완료 후 자동으로 재접속 됩니다."
        const val MAINTENANCE_START_BROADCAST_MESSAGE = "${GRAY}===================================${NEW_LINE}${NEW_LINE}${GOLD}\t잠시 후 점검이 시작됩니다.${NEW_LINE}\t${LIGHT_YELLOW}점검 상세 내용은 디스코드를 확인하세요.${NEW_LINE}${NEW_LINE}${GRAY}==================================="

        const val ANNOUNCE_MESSAGE = "${WHITE}${BOLD}[ ${GOLD}공지 ${WHITE}${BOLD}] ${RESET}${WHITE}${MESSAGE}"

        const val SET_AFK_WORLD = "${PREFIX}${WHITE_GREEN}잠수장 월드를 설정하였습니다."

        /**
         * 관리자용 응답 메시지 (간결, 파악하기 어렵게)
         */
        const val NOT_FOUND_COMMAND_ARGS = "${PREFIX}<#ababab>존재하지 않는 인자를 호출했습니다."
        const val MISSING_COMMAND_ARGS = "${PREFIX}<#ababab>인자를 입력해주세요."
        const val REQUIRE_NUMBER = "${PREFIX}<#ababab>올바른 숫자를 입력해주세요."
        const val SUCCESS_MAINTENANCE_START = "${PREFIX}<#ababab>서버 점검이 <#68ff57>시작<#ababab>되었습니다."
        const val SUCCESS_MAINTENANCE_END = "${PREFIX}<#ababab>서버 점검이 <#ff5757>종료<#ababab>되었습니다."


        /**
         * 귓속말 메시지
         */
        const val REQUIRE_TARGET_PLAYER = "${PREFIX}${GRAY}대상 플레이어를 입력해주세요."
        const val REQUIRE_MESSAGE = "${PREFIX}${GRAY}전송 할 메시지를 입력해주세요."
        const val WHISPER_MESSAGE = "${PREFIX}${GRAY}${TARGET_PLAYER_NAME}에게 ${WHITE}${MESSAGE}"
        const val WHISPER_MESSAGE_RECEIVE = "${PREFIX}${GRAY}${TARGET_PLAYER_NAME}(이)가 ${WHITE}${MESSAGE}"

        /**
        * 상점 메시지
         */
        const val START_CREATE_ECONOMY = "${PREFIX}<#ababab>상점 생성 시작 - ${LIGHT_YELLOW}표지판을 바라보고 우클릭해주세요."
        const val REQUIRE_SIGN = "${PREFIX}<#ababab>표지판을 바라보고 우클릭해주세요."
        const val REMOVE_STORE_START = "${PREFIX}<#ababab>삭제할 상점의 표지판을 파괴해주세요."
        const val INCORRECT_ITEM = "${PREFIX}<#ababab>공기는 판매할 수 없습니다."
        const val SUCCESS_CREATE_STORE = "${PREFIX}<#ababab>상점 생성이 완료되었습니다."
        const val CANCEL_TASK = "${PREFIX}<#ababab>작업이 취소되었습니다."
        const val NOT_FOUND_STORE = "${PREFIX}<#ababab>존재하는 상점이 없습니다."
        const val REMOVE_STORE = "${PREFIX}<#ababab>상점이 삭제되었습니다."
        const val REQUEST_TO_CANCEL = "${PREFIX}<#ababab>작업을 취소하려면 채팅에 ${WHITE_RED}취소<#ababab>를 입력하세요."

        /**
         * 확성기
         */
        const val REQUIRE_SPEAK_ARGS = "${PREFIX}${GRAY}확성기 ${WHITE_RED}내용${GRAY}을 입력해주세요."
        const val SPEAK = "${GRAY}========== ${LIGHT_YELLOW}확성기 ${GRAY}==========\n\n${WHITE}${BOLD}[ ${RESET}${LIGHT_YELLOW}${TARGET_PLAYER_NAME} ${WHITE}${BOLD}] $RESET: ${MESSAGE}\n\n${GRAY}=========================="
        const val SPEAK_LACK_BALANCE = "${PREFIX}${GRAY}확성기를 사용하려면 ${WHITE_RED}${PRICE}${GRAY}원이 필요합니다."
        const val SUCCESS_SPEAK = "${PREFIX}${GRAY}${WHITE_GREEN}${PRICE}${GRAY}원으로 확성기를 사용하였습니다."

        /**
         * 경고 메시지
         */
        const val WARNING_ADDED ="${PREFIX}${GRAY}플레이어 ${WHITE_RED}${TARGET_PLAYER_NAME}${GRAY}에게 경고 1회를 부여하였습니다. ${LIGHT_YELLOW}(사유 : ${MESSAGE})"
        const val TARGET_WARNING_MESSAGE = "${PREFIX}${WHITE_RED}경고 1회를 받았습니다. ${GRAY}사유 : $MESSAGE"
        const val WARNING_REMOVED = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${TARGET_PLAYER_NAME}${GRAY}의 경고를 1회 회수하였습니다."
        const val TARGET_WARNING_REMOVE = "${PREFIX}${WHITE_GREEN}경고 1회가 회수되었습니다."
        const val TARGET_PLAYER_WARNING_COUNT = "${PREFIX}${WHITE}${TARGET_PLAYER_NAME}의 경고 횟수 : ${WHITE_RED}${MESSAGE}${
                WHITE}회"
        const val NOT_FOUND_WARNING = "${PREFIX}${WHITE_RED}해당 플레이어에게 회수 할 경고가 없습니다."

        const val COPYRIGHT_MESSAGE = "${PREFIX}${GOLD}본 플러그인 LunarJiwon의 오픈소스 프로젝트 \"다올서버 플러그인 오픈소스\"를 사용하였습니다."
}