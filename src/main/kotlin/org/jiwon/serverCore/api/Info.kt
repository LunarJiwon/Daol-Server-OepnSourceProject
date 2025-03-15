package org.jiwon.serverCore.api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import org.jiwon.serverCore.ServerCore.Companion.instance
import java.io.IOException


class Info : HttpHandler {
    override fun handle(exchange: HttpExchange?) {
        // 화이트리스트 상태 반환
        if(exchange?.requestURI?.toString()?.startsWith("/info/whitelist") == true){
            sendContent(exchange, instance.server.hasWhitelist().toString())
        }
    }

    @Throws(IOException::class)
    private fun sendContent(exchange: HttpExchange, content: String) {
        val bytes = content.toByteArray()

        exchange.sendResponseHeaders(200, bytes.size.toLong())

        val outputStream = exchange.responseBody
        outputStream.write(bytes)
        outputStream.flush()
    }
}