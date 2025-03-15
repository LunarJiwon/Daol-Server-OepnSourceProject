package org.jiwon.serverCore.messages

import org.jiwon.serverCore.alternative.ReplaceTexts.AMOUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.BALANCE
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.WHITE
import org.jiwon.serverCore.messages.MessageColors.WHITE_BLUE
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object BalanceMessages {
        const val COMMAND_HELPER = "$PREFIX${GRAY}명령어 사용법 ${WHITE}: ${WHITE_BLUE}/통장 <송금> <대상 플레이어> <금액>"
        const val SHOW_BALANCE = "$PREFIX${GRAY}통장 잔액 ${WHITE}: ${BALANCE}원"

        /**
         * 관리자 명령어 반환 메시지
         */
        const val SPECIFIC_PLAYER_BALANCE = "$PREFIX${LIGHT_YELLOW}${TARGET_PLAYER_NAME}${GRAY}의 통장 잔고 ${WHITE}: ${BALANCE}원"
        const val MISSING_ARGS_PLAYER = "$PREFIX${WHITE_RED}대상 플레이어${GRAY}를 입력해주세요."
        const val NOT_FOUND_PLAYER = "$PREFIX${GRAY}대상 플레이어 ${WHITE_RED}${TARGET_PLAYER_NAME}${GRAY}(을)를 찾을 수 없습니다."
        const val MISSING_AMOUNT = "$PREFIX${GRAY}설정(혹은 추가 및 삭제) 할 ${WHITE_RED}금액${GRAY}을 입력해주세요."
        const val INCORRECT_AMOUNT = "$PREFIX${GRAY}올바른 ${WHITE_RED}금액${GRAY}을 입력해주세요."
        const val SUCCESS_SET_BALANCE = "$PREFIX${GRAY}플레이어 ${WHITE}${TARGET_PLAYER_NAME}${GRAY}의 통잔 잔고를 ${WHITE}${AMOUNT}원${GRAY}으로 변경하였습니다."
        const val SUCCESS_ADD_BALANCE = "$PREFIX${GRAY}플레이어 ${WHITE}${TARGET_PLAYER_NAME}${GRAY}의 통잔 잔고를 ${WHITE}${AMOUNT}원${GRAY}만큼 추가하였습니다."
        const val SUCCESS_REMOVE_BALANCE = "$PREFIX${GRAY}플레이어 ${WHITE}${TARGET_PLAYER_NAME}${GRAY}의 통잔 잔고를 ${WHITE}${AMOUNT}원${GRAY}만큼 삭제하였습니다."

        /**
         * 송금
         */
        const val SEND_BALANCE_MISSING_PLAYER = "$PREFIX${GRAY}송금 ${WHITE_RED}대상 플레이어${GRAY}를 입력해주세요. ${WHITE}(/통장 <송금> <대상 플레이어> <금액>)"
        const val SEND_BALANCE_AMOUNT_PLACE = "$PREFIX${GRAY}송금 ${WHITE_RED}금액${GRAY}을 입력해주세요. ${WHITE}(/통장 <송금> <대상 플레이어> <금액>)"
        const val SEND_BALANCE_INCORRECT_AMOUNT = "$PREFIX${GRAY}올바른 송금 ${WHITE_RED}금액${GRAY}을 입력해주세요. ${WHITE}(/통장 <송금> <대상 플레이어> <금액>)"
        const val SEND_BALANCE_NOT_FOUND_PLAYER = "$PREFIX${GRAY}대상 플레이어 ${WHITE_RED}${TARGET_PLAYER_NAME}${GRAY}(을)를 찾을 수 없습니다."
        const val SEND_BALANCE_SHORTAGE_BALANCE = "$PREFIX${GRAY}송금하기 위한 ${WHITE_RED}${BALANCE}원${GRAY} 만큼의 돈이 부족합니다."
        const val SEND_BALANCE_MIN_BALANCE = "$PREFIX${GRAY}송금하기 위해서 ${WHITE_RED}1원 이상${GRAY}의 금액을 입력해주세요."
        const val SEND_BALANCE_SUCCESS = "$PREFIX${GRAY}플레이어 ${WHITE}${TARGET_PLAYER_NAME}${GRAY}에게 ${WHITE}${AMOUNT}원${GRAY}을 송금하였습니다. ${LIGHT_YELLOW}(${GRAY}수수료 포함 : ${WHITE}${BALANCE}원${LIGHT_YELLOW})"
        const val SEND_BALANCE_SUCCESS_TARGET = "$PREFIX${GRAY}플레이어 ${WHITE}${PLAYER_NAME}${GRAY}님이 ${WHITE}${AMOUNT}원${GRAY}을 송금하였습니다. ${LIGHT_YELLOW}(${GRAY}수수료 포함 : ${WHITE}${BALANCE}원${LIGHT_YELLOW})"


}