package org.jiwon.serverCore.discord

import io.github.lunarjiwon.DiscordWebhook
import io.github.lunarjiwon.DiscordEmbedBuilder
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.Logger
import java.awt.Color
import java.net.URI
import javax.net.ssl.HttpsURLConnection


class DiscordWebhookManager {

    companion object {
        // 서버 상태 메시지 송출 디스코드 웹훅
        val serverStatusWebhook = DiscordWebhook(
            ""
        )
        // 인게임 이벤트 송출 디스코드 웹훅
        val inGameWebhook = DiscordWebhook(
            ""
        )
        // 관리 관련 디스코드 웹훅
        val manageWebhook = DiscordWebhook(
            ""
        )
        // 경고 관련 디스코드 웹훅
        val warningWebhook = DiscordWebhook(
            ""
        )

    }

    init {
        val embed = DiscordEmbedBuilder().setTitle("서버 상태 안내").setDescription("서버 상태가 온라인입니다").setColor(Color(73, 242, 112)).build()
        serverStatusWebhook.setEmbed(embed)
        serverStatusWebhook.execute()
        Logger.info(mm("<#44fc54>디스코드 연동 시스템 가동"))
    }

}