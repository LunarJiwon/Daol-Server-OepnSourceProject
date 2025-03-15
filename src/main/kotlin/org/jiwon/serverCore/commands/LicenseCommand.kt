package org.jiwon.serverCore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.jiwon.serverCore.messages.SystemMessages.COPYRIGHT_MESSAGE
import org.jiwon.serverCore.utils.Components.mm


/**
 * 본 클래스 파일은 절대로 삭제 / 변경을 금지합니다.
 */

class LicenseCommand:CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        p0.sendMessage(mm(COPYRIGHT_MESSAGE))
        return true
    }


}