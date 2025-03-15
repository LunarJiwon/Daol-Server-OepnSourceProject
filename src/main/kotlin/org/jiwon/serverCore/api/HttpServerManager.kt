package org.jiwon.serverCore.api

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

class HttpServerManager(host:String, port:Int) {
    private var defaultBacklog = 0
    private lateinit var server : HttpServer

    init{
        createServer(host,port)
    }

    private fun createServer(host: String,port: Int){
        server = HttpServer.create(InetSocketAddress(host,port),defaultBacklog)
        server.createContext("/info/whitelist",Info())
    }

    fun start(){
        server.start()
    }

    fun stop(delay:Int){
        server.stop(delay)
    }
}