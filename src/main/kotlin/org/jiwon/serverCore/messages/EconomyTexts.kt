package org.jiwon.serverCore.messages

import org.jiwon.serverCore.alternative.ReplaceTexts.AMOUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.ITEM_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.PURCHASE_PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.SALE_PRICE
import org.jiwon.serverCore.messages.MessageColors.GRAY
import org.jiwon.serverCore.messages.MessageColors.LIGHT_YELLOW
import org.jiwon.serverCore.messages.MessageColors.NEW_LINE
import org.jiwon.serverCore.messages.MessageColors.UN_ITALIC
import org.jiwon.serverCore.messages.MessageColors.WHITE
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.messages.SystemMessages.PREFIX

object EconomyTexts {

        const val STORE_INVENTORY_TEXT = "<color:#ffdd45><b>상점</b></color>"
        const val ITEM_NAME_OPTION_MENU_1 = "${UN_ITALIC}<color:#8dff85>구매가격 : ${WHITE}${PURCHASE_PRICE}</color>"
        const val ITEM_NAME_OPTION_MENU_2 = "${UN_ITALIC}<color:#ff9195>판매가격 : ${WHITE}${SALE_PRICE}</color>"
        const val ITEM_NAME_OPTION_MENU_3 = "${UN_ITALIC}<color:#FFFFFF>구매하려면 <color:#b8eaff>좌클릭 </color>/ 판매하려면 <color:#fff2a1>우클릭</color>"

        const val ITEM_NAME_PURCHASE_MENU_1 = "${UN_ITALIC}<color:#8dff85>${AMOUNT}개 가격 : <white>${PURCHASE_PRICE}"
        const val ITEM_NAME_PURCHASE_MENU_2 = "${UN_ITALIC}<color:#ffee8f>클릭하여 ${AMOUNT}개 구매하기"

        const val ITEM_NAME_SALE_MENU_1 = "${UN_ITALIC}<color:#8dff85>${AMOUNT} 가격 : <white>${PURCHASE_PRICE}"
        const val ITEM_NAME_SALE_MENU_2 = "${UN_ITALIC}<color:#ffee8f>클릭하여 $AMOUNT 판매하기"

        const val STORE_TITLE_TEXT = "<b><color:#FFFFFF>[</color></b> <color:#00ff6a>상점</color> <b><color:#FFFFFF>]</color></b>"
        const val ITEM_NAME_TEXT = "<color:#c2fff4>${ITEM_NAME}</color>"
        const val STORE_PURCHASE_PRICE_TEXT = "<b><color:#FFFFFF>구매가</color></b> : <color:#09ff00>${PURCHASE_PRICE}</color>"
        const val STORE_SALE_PRICE_TEXT = "<b><color:#FFFFFF>판매가</color></b> : <color:#09ff00>${SALE_PRICE}</color>"

        const val CANT_PURCHASE_ITEM = "${PREFIX}${WHITE_RED}해당 아이템은 구매가 불가 합니다!"
        const val CANT_SALE_ITEM = "${PREFIX}${WHITE_RED}해당 아이템은 판매가 불가 합니다!"

        const val INVENTORY_FULL = "${PREFIX}${WHITE_RED}인벤토리가 가득 찼습니다!"
        const val LACK_BALANCE = "${PREFIX}${WHITE_RED}돈이 부족합니다!"
        const val LACK_ITEM = "${PREFIX}${WHITE_RED}판매 할 아이템이 부족합니다!"

        const val PURCHASE_ITEM = "${PREFIX}${GRAY}아이템 ${LIGHT_YELLOW}${ITEM_NAME}${GRAY}을(를) ${LIGHT_YELLOW}${PURCHASE_PRICE}원${GRAY}에 ${LIGHT_YELLOW}${AMOUNT}개${GRAY} 구매했습니다."
        const val SALE_ITEM = "${PREFIX}${GRAY}아이템 ${LIGHT_YELLOW}${ITEM_NAME}${GRAY}을(를) ${LIGHT_YELLOW}${SALE_PRICE}원${GRAY}에 ${LIGHT_YELLOW}${AMOUNT}개${GRAY} 판매했습니다."

        const val BEDROCK_ITEM_DESCRIPTION = "아이템 ${ITEM_NAME}을(를) 구매 / 판매 하시겠습니까?"

        const val ALREADY_SHOP = "${PREFIX}${GRAY}해당 표지판에는 존재하는 상점이 있습니다"


}