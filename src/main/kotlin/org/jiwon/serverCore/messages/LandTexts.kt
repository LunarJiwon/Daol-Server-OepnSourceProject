package org.jiwon.serverCore.messages

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.jiwon.serverCore.alternative.ReplaceTexts
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.CURRENT_SETTING
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME_AFTER
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_SIZE
import org.jiwon.serverCore.alternative.ReplaceTexts.PLAYER_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.TARGET_PLAYER_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.TAX
import org.jiwon.serverCore.messages.MessageColors.BLACK
import org.jiwon.serverCore.messages.MessageColors.GOLD
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.UN_ITALIC
import org.jiwon.serverCore.messages.MessageColors.WHITE
import org.jiwon.serverCore.messages.MessageColors.WHITE_BLUE
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object LandTexts {

    const val LAND_MENU_TITLE = "${LIGHT_YELLOW}부동산"

    const val LAND_MENU_ITEM_PURCHASE = "${WHITE_BLUE}부동산 구매"
    const val LAND_MENU_ITEM_PURCHASE_DESCRIPTION_1 = "${UN_ITALIC}${WHITE}클릭하여 ${GOLD}부동산 구매하기"

    const val LAND_MENU_ITEM_MANAGE = "${WHITE_GREEN}부동산 관리"
    const val LAND_MENU_ITEM_MANAGE_DESCRIPTION_1 = "${UN_ITALIC}${WHITE}클릭하여 ${GOLD}소유 부동산 관리하기"

    val LAND_MENU_TITLE_BEDROCK = "${ChatColor.GOLD}부동산"
    val LAND_MENU_PURCHASE_BUTTON_BEDROCK = "부동산 ${ChatColor.BOLD}${ChatColor.GREEN}구매"
    val LAND_MENU_MANAGE_BUTTON_BEDROCK = "부동산 ${ChatColor.BOLD}${ChatColor.GOLD}관리"

    const val LAND_SIZE_TEXT = "${LIGHT_YELLOW}${ReplaceTexts.LAND_SIZE}${WHITE} 부동산"
    val LAND_SIZE_DESCRIPTION = arrayOf(
        "${UN_ITALIC}${WHITE}가격 : ${GOLD}${PRICE}원",
        "${UN_ITALIC}${WHITE}총 블록 수 : ${WHITE_GREEN}${BLOCK_COUNT}${WHITE}블록",
        UN_ITALIC,
        "${UN_ITALIC}${GOLD}클릭하여 부동산 구매하기"
    )

    val LAND_PURCHASE_MENU_TITLE_BEDROCK = "${ChatColor.GOLD}${ChatColor.BOLD}부동산 구매"
    val LAND_SIZE_DESCRIPTION_BEDROCK = arrayOf(
        "${ChatColor.DARK_GREEN}${ChatColor.BOLD}${LAND_SIZE} ${ChatColor.RESET}${ChatColor.BLACK}/ ${ChatColor.GOLD}${ChatColor.BOLD}${BLOCK_COUNT}${ChatColor.BLACK}블록",
        "${ChatColor.BLACK}가격 : ${ChatColor.GOLD}${ChatColor.BOLD}${PRICE}원"
    )

    const val LAND_LIST_MENU_TITLE = "${LIGHT_YELLOW}부동산 관리"
    const val LAND_LIST_MENU_TITLE_MERGE = "${LIGHT_YELLOW}부동산 병합"
    const val LAND_LIST_ITEM_NAME = "${LIGHT_YELLOW}${LAND_NAME}"
    const val LAND_LIST_ITEM_DESCRIPTION = "${WHITE_GREEN}${UN_ITALIC}클릭하여 부동산 관리하기"
    const val LAND_LIST_ITEM_DESCRIPTION_MERGE = "${WHITE_GREEN}${UN_ITALIC}클릭하여 해당 부동산과 병합하기"

    const val LAND_MANAGE_MENU_TITLE = "${LIGHT_YELLOW}${LAND_NAME} ${BLACK}관리"
    val LAND_MANAGE_MENU = arrayOf(
        mutableListOf(
            Material.FIREWORK_ROCKET,
            "${LIGHT_YELLOW}영역 이동",
            "${UN_ITALIC}${WHITE}영역 설정에 저장된 위치로 이동합니다."
        ),
        mutableListOf(
          Material.GLASS_PANE,
            "${LIGHT_YELLOW}영역 표시",
            "${UN_ITALIC}${WHITE}영역 범위를 파티클로 표현합니다."
        ),
        mutableListOf(
            Material.NAME_TAG,
            "${LIGHT_YELLOW}영역 이름 변경",
            "${UN_ITALIC}${WHITE}현재 이름 : ${WHITE_GREEN}${LAND_NAME}"
        ),
        mutableListOf(
            Material.DIAMOND_HOE,
            "${LIGHT_YELLOW}영역 재지정",
            "${UN_ITALIC}${WHITE}현재 지정된 영역을 초기화하고 새롭게 지정합니다"
        ),
        mutableListOf(
            Material.DIAMOND_SWORD,
            "${LIGHT_YELLOW}영역 남은블록 지정",
            "${UN_ITALIC}${WHITE}남은 블록을 지정합니다 ${LIGHT_YELLOW}(남은 블록 수 : ${WHITE_BLUE}${BLOCK_COUNT}개)"
        ),
        mutableListOf(
            Material.BUCKET,
            "${LIGHT_YELLOW}영역 병합",
            "${UN_ITALIC}${WHITE}다른 한개의 영역과 해당 영역을 병합합니다"
        ),
        mutableListOf(
            Material.COMPARATOR,
            "${LIGHT_YELLOW}영역 설정",
            "${UN_ITALIC}${WHITE}영역의 각종 설정을 수정 및 확인합니다"
        ),
        mutableListOf(
            Material.GREEN_WOOL,
            "${LIGHT_YELLOW}건축 허용 플레이어 설정",
            "${UN_ITALIC}${WHITE}건축 허용 플레이어를 추가 및 삭제합니다"
        ),
        mutableListOf(
            Material.BARRIER,
            "${LIGHT_YELLOW}준비중",
            "${UN_ITALIC}${WHITE}메뉴 준비중입니다"
        ),
        mutableListOf(
            Material.MAP,
            "${LIGHT_YELLOW}영역 소유권 이전",
            "${UN_ITALIC}${WHITE}해당 영역을 타 플레이어에게 소유권을 이전합니다 (이전 수수료 : ${WHITE_RED}${PRICE}${WHITE}원)"
        ),
        mutableListOf(
            Material.LAVA_BUCKET,
            "${LIGHT_YELLOW}영역 판매",
            "${UN_ITALIC}${WHITE}해당 영역을 판매합니다 ${LIGHT_YELLOW}(판매 환급금 : ${WHITE_RED}${TAX}${WHITE}원)"
        )

    )



    const val LAND_SETTINGS_TITLE = "${WHITE_GREEN}부동산 세부 설정"

    /**
     * 플레이어 영역 설정 메뉴 아이템
     */
    val LAND_SETTINGS_ITEM = arrayOf(
        mutableListOf(
            Material.NETHER_STAR,
            "${LIGHT_YELLOW}영역 이동 위치 설정",
            "${UN_ITALIC}${WHITE}영역 이동 시 이동 될 위치를 설정합니다",
            "${UN_ITALIC}${WHITE}현재 설정 : $CURRENT_SETTING"
        ),
        mutableListOf(
            Material.BIRCH_SIGN,
            "${LIGHT_YELLOW}인사말 설정",
            "${UN_ITALIC}${WHITE}영역 진입 시 플레이어에게 표시 할 메시지를 설정합니다",
            "${UN_ITALIC}${WHITE}현재 설정 : $CURRENT_SETTING"
        ),
        mutableListOf(
            Material.MANGROVE_SIGN,
            "${LIGHT_YELLOW}퇴장말 설정",
            "${UN_ITALIC}${WHITE}영역 퇴장 시 플레이어에게 표시 할 메시지를 설정합니다",
            "${UN_ITALIC}${WHITE}현재 설정 : $CURRENT_SETTING"
        ),
        mutableListOf(
            Material.TNT,
            "${LIGHT_YELLOW}폭발 방지 설정",
            "${UN_ITALIC}${WHITE}영역 내 폭발 보호 여부를 설정합니다",
            "${UN_ITALIC}${WHITE}현재 설정 : $CURRENT_SETTING"
        )
    )

    const val BUILD_ALLOW_PLAYER_MENU_TITLE = "${BLACK}건축 허용 플레이어 관리"
    const val BUILD_ALLOW_PLAYER_MENU_TITLE_BEDROCK = "${BLACK}건축 허용 플레이어 추가"
    const val BUILD_ALLOW_PLAYER_ITEM_DESCRIPTION = "${UN_ITALIC}${WHITE}클릭하여 ${WHITE_RED}삭제${WHITE}하기"
    const val BUILD_ALLOW_PLAYER_NONE_ITEM = "${WHITE}없음"
    const val BUILD_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION = "${UN_ITALIC}${WHITE_GREEN}클릭하여 건축 허용 플레이어 추가하기"

    const val INTERACT_ALLOW_PLAYER_MENU_TITLE = "${BLACK}상호작용 허용 플레이어 관리"
    const val INTERACT_ALLOW_PLAYER_MENU_TITLE_BEDROCK = "${BLACK}상호작용 허용 플레이어 추가"
    const val INTERACT_ALLOW_PLAYER_ITEM_DESCRIPTION = "${UN_ITALIC}${WHITE}클릭하여 ${WHITE_RED}삭제${WHITE}하기"
    const val INTERACT_ALLOW_PLAYER_NONE_ITEM = "${WHITE}없음"
    const val INTERACT_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION = "${UN_ITALIC}${WHITE_GREEN}클릭하여 상호작용 허용 플레이어 추가하기"

    /**
     * 메시지
     */
    const val NOT_ALLOW_WORLD = "${PREFIX}${WHITE_RED}해당 월드에서는 부동산을 사용할 수 없습니다!"
//    const val DONT_SELECT_AIR = "${PREFIX}${WHITE_RED}공기를 통해 영역을 지정할 수 없습니다."
    const val START_LOCATION_SELECT = "${PREFIX}${GRAY}구매 할 부동산 영역을 ${LIGHT_YELLOW}지정${GRAY}해주세요."
    const val ALREADY_USING_LAND_NAME = "${PREFIX}${WHITE_RED}이미 사용중인 이름입니다, ${WHITE_BLUE}다른 이름을 입력해주세요"
    const val OVERLAP_AREA = "${PREFIX}${WHITE_RED}해당 지역은 ${WHITE_BLUE}타인의${WHITE_RED} 땅이 포함되어 있습니다, ${LIGHT_YELLOW}영역을 다시 지정해주세요."
    const val OVER_BLOCK_COUNT = "${PREFIX}${WHITE_RED}블록 ${WHITE_BLUE}${BLOCK_COUNT}${WHITE_RED}개 보다 적게 지정해주세요."
    const val SELECT_FIRST_LOCATION = "${PREFIX}${GRAY}첫 번째 지점이 설정되었습니다, ${WHITE_GREEN}다음 지점을 설정해주세요."
    const val CONFIRM_SELECT_BLOCK = "${PREFIX}${GRAY}총 ${WHITE_GREEN}${BLOCK_COUNT}${WHITE}개${GRAY}의 블록을 선택하였습니다, ${LIGHT_YELLOW}영역의 이름을 입력해주세요."
    const val CONFIRM_LAND = "${PREFIX}${LIGHT_YELLOW}${LAND_NAME}${GRAY} 이름으로 부동산을 구매하려면 ${WHITE_GREEN}확인${GRAY}을 입력해주세요. (남는 블록 수 : ${BLOCK_COUNT})"
    const val CREATED_LAND = "${PREFIX}${WHITE_BLUE}영역 ${WHITE_GREEN}${LAND_NAME}${WHITE_BLUE}을(를) 구매하였습니다."
    const val LIMITED_LAND_AMOUNT = "${PREFIX}${WHITE_RED}구매할 수 있는 부동산 개수를 초과했습니다."
    const val NOT_FOUND_LAND = "${PREFIX}${WHITE_RED}관리 할 부동산이 없습니다."
    const val CHANGE_LAND_NAME_START = "${PREFIX}${GRAY}변경 할 이름을 입력해주세요."
    const val CHANGED_LAND_NAME = "${PREFIX}${GRAY}영역 ${LIGHT_YELLOW}${LAND_NAME}${GRAY}의 이름을 ${WHITE_GREEN}${LAND_NAME_AFTER}${GRAY}(으)로 변경하였습니다."
    const val RE_SIZE_LAND_START = "${PREFIX}${GRAY}영역 재지정을 시작합니다, ${WHITE_GREEN}첫 번째 지점을 설정해주세요."
    const val RE_SIZE_LAND_CONFIRM = "${PREFIX}${GRAY}영역 재지정 후 ${WHITE_BLUE}${BLOCK_COUNT}${GRAY}개의 블록이 남습니다, ${WHITE_GREEN}계속하려면 확인을 입력해주세요"
    const val RE_SIZE_LAND = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}(이)가 재지정 되었습니다. "
    const val REMAIN_START = "${PREFIX}${GRAY}남은 영역을 새로운 영역으로 지정합니다, ${WHITE_GREEN}첫 번째 지점을 설정해주세요."
    const val REMAIN_CREATED = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}(이)가 생성되었습니다. ${WHITE_BLUE}(남은 블록 수 : ${BLOCK_COUNT}개)"
    const val NOT_FOUND_REMAIN_BLOCK = "${PREFIX}${WHITE_RED}남은 영역이 없습니다."
    const val SHOW_LAND_AREA = "${PREFIX}${GRAY}영역을 파티클로 표현합니다."
    const val LAND_MERGE_START = "${PREFIX}${WHITE_GREEN}${LAND_NAME}${GRAY} 영역과 병합을 진행합니다, ${LIGHT_YELLOW}총 ${WHITE_GREEN}${BLOCK_COUNT}${LIGHT_YELLOW}개의 블록을 지정할 수 있습니다."
    const val LIMITED_LAND_MERGE_SIZE = "${PREFIX}${WHITE_RED}병합 시 지정 가능 블록 수가 10,000 블록이 초과합니다."
    const val LAND_MERGE_NAME = "${PREFIX}${GRAY}총 ${WHITE_GREEN}${BLOCK_COUNT}${GRAY}개의 블록이 남습니다, ${WHITE_GREEN}영역 이름을 입력해주세요."
    const val LAND_MERGE_SUCCESS = "${PREFIX}${GRAY}영역이 ${WHITE_GREEN}${LAND_NAME}${GRAY}(으)로 병합되었습니다, ${WHITE_BLUE}(남은 블록 수 : ${BLOCK_COUNT}개)"
    const val SET_LAND_TELEPORT_LOCATION = "${PREFIX}${WHITE_GREEN}영역의 이동 좌표가 현재 위치로 변경되었습니다."
    const val SET_LAND_TELEPORT_LOCATION_ON_AREA = "${PREFIX}${WHITE_RED}영역의 이동 좌표 설정은 설정하려는 영역 내에서 설정해야 합니다."
    const val TELEPORTED_LAND = "${PREFIX}${WHITE_GREEN}${LAND_NAME}${GRAY}(으)로 이동되었습니다."
    const val START_SET_LAND_WELCOME_MESSAGE = "${PREFIX}${GRAY}영역 환영 메시지를 설정합니다, ${WHITE_GREEN}출력할 메시지를 입력해주세요."
    const val START_SET_LAND_EXIT_MESSAGE = "${PREFIX}${GRAY}영역 퇴장 메시지를 설정합니다, ${WHITE_GREEN}출력할 메시지를 입력해주세요."
    const val SET_LAND_WELCOME_MESSAGE = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 인사말이 설정되었습니다."
    const val SET_LAND_EXIT_MESSAGE = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 퇴장말이 설정되었습니다."
    const val SET_LAND_EXIT_MESSAGE_DISABLE = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 퇴장말이 ${WHITE_RED}비활성화 ${GRAY}되었습니다."
    const val SET_LAND_WELCOME_MESSAGE_DISABLE = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 인사말이 ${WHITE_RED}비활성화 ${GRAY}되었습니다."
    const val SET_EXPLODE_ALLOW = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 폭발 보호 설정이 ${WHITE_GREEN}활성화${GRAY}되었습니다."
    const val SET_EXPLODE_DENY = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 폭발 보호 설정이 ${WHITE_RED}비활성화${GRAY}되었습니다."
    const val SELF_APPEND = "${PREFIX}${WHITE_RED}본인은 추가할 수 없습니다."
    const val BUILD_ALLOW_PLAYER_ADD_START = "${PREFIX}${GRAY}건축을 허용할 플레이어 ${WHITE_GREEN}닉네임${GRAY}을 입력해주세요."
    const val APPEND_BUILD_ALLOW_PLAYER = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${PLAYER_NAME}${GRAY}에게 영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 건축 허용 권한을 ${WHITE_GREEN}부여${GRAY}하였습니다."
    const val REMOVED_BUILD_ALLOW_PLAYER = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${PLAYER_NAME}${GRAY}에게 영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 건축 허용 권한을 ${WHITE_RED}삭제${GRAY}하였습니다."
    const val INTERACT_ALLOW_PLAYER_ADD_START = "${PREFIX}${GRAY}건축을 허용할 플레이어 ${WHITE_GREEN}닉네임${GRAY}을 입력해주세요."
    const val APPEND_INTERACT_ALLOW_PLAYER = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${PLAYER_NAME}${GRAY}에게 영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 건축 허용 권한을 ${WHITE_GREEN}부여${GRAY}하였습니다."
    const val REMOVED_INTERACT_ALLOW_PLAYER = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${PLAYER_NAME}${GRAY}에게 영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 건축 허용 권한을 ${WHITE_RED}삭제${GRAY}하였습니다."
    const val LAND_OWNER_CHANGE_START = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}의 소유권을 이전합니다, ${WHITE_RED}이전 할 대상을 입력해주세요."
    const val LAND_OWNER_CHANGE_TARGET_PLAYER_MAX_LAND = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${LAND_NAME}${GRAY}이(가) 소유할 수 있는 영역 개수를 초과합니다."
    const val LAND_OWNER_CHANGE_CONFIRM = "${PREFIX}${GRAY}영역 ${WHITE_RED}${LAND_NAME}${GRAY}의 소유권을 ${WHITE_GREEN}${TARGET_PLAYER_NAME}${GRAY}에게 이전하려면 ${WHITE_BLUE}확인${GRAY}을 입력해주세요. ${WHITE_RED}(이전 중개료 : ${TAX})"
    const val LAND_OWNER_CHANGE_TARGET_ALREADY_NAME = "${PREFIX}${GRAY}해당 플레이어에게 이전할 영역의 동일한 이름의 영역이 존재합니다."
    const val LAND_OWNER_CHANGED = "${PREFIX}${GRAY}플레이어 ${WHITE_GREEN}${TARGET_PLAYER_NAME}${GRAY}에게 ${WHITE_GREEN}${LAND_NAME}${GRAY} 영역의 소유권을 이전하였습니다. ("
    const val LAND_SELL_CONFIRM = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}을(를) 판매하려면 ${WHITE_RED}확인${GRAY}을(를) 입력해주세요."
    const val SUCCESS_LAND_SELL = "${PREFIX}${GRAY}영역 ${WHITE_GREEN}${LAND_NAME}${GRAY}을(를) 판매하였습니다, ${WHITE_GREEN}환급금 : ${PRICE}원"
    const val PROTECT_MESSAGE = "${WHITE_RED}해당 영역은 ${LIGHT_YELLOW}${PLAYER_NAME}${WHITE_RED}님의 땅 입니다."
}
