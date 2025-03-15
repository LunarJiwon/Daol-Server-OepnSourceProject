package org.jiwon.serverCore.commands.balance

import org.bukkit.entity.Player
import org.geysermc.cumulus.form.CustomForm
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.response.CustomFormResponse
import org.geysermc.cumulus.response.SimpleFormResponse
import org.jiwon.serverCore.ServerCore
import java.util.function.Consumer

class BalanceBedrockMenu {
    companion object {
        fun mainMenu(balance: String, callback: Consumer<SimpleFormResponse>): SimpleForm =
            SimpleForm.builder().title("통장 메뉴")
                .content("통장 잔액 : ${balance}원")
                .button("송금")
                .validResultHandler(callback)
                .build()

        fun sendBalance(players:ArrayList<String>, callback: Consumer<CustomFormResponse>):CustomForm {

            return CustomForm.builder().title("통장 송금")
                .dropdown("송금 대상 선택", players)
                .input("금액 입력","송금할 금액을 입력해주세요")
                .validResultHandler(callback)
                .build()
        }
    }
}