package org.jiwon.serverCore.utils

import net.kyori.adventure.text.Component
import org.jiwon.serverCore.ServerCore.Companion.instance

class Logger {
    companion object{
        fun info(message:Component){
            instance.componentLogger.info(message)
        }
    }
}