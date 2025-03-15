package org.jiwon.serverCore.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.ComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer


object Components {

    private val rainbowColors = arrayOf(
        "§4","§6","§e","§a","§b","§9","§5"
    )

    fun mm(miniMessageString: String): Component {
        return MiniMessage.miniMessage().deserialize(miniMessageString)
    }


    // 직렬화기 직접 제작
    fun mmLegacy(miniMessageString: String): String {
        val serializeString = StringBuilder()
        var isRainbow = false
        var rainbowIndex = 0
        var updateIndex = 0
        miniMessageString.forEachIndexed { index, c ->
            if(miniMessageString.indexOf("<rainbow>") == index){
                isRainbow = true
                updateIndex = index + 9
            }else{
                if(isRainbow){
                    if(c != '<'){
                        serializeString.append(rainbowColors[rainbowIndex])
                        rainbowIndex++
                        if(rainbowIndex > 6){
                            rainbowIndex = 0
                        }
                    }else {
                        isRainbow = false
                        rainbowIndex = 0
                    }
                }
                if(updateIndex == index) updateIndex = 0
                if(updateIndex == 0) serializeString.append(c)
            }
        }

        return serializeString.toString().replace("<black>","§0")
            .replace("<dark_blue>","§1")
            .replace("<dark-green>","§2")
            .replace("<dark_aqua>","§3")
            .replace("<dark_red>","§4")
            .replace("<dark_purple>","§5")
            .replace("<gold>","§6")
            .replace("<gray>","§7")
            .replace("<dark_gray>","§9")
            .replace("<green>","§a")
            .replace("<aqua>","§b")
            .replace("<red>","§c")
            .replace("<light_purple>","§d")
            .replace("<yellow>","§e")
            .replace("<white>","§f")
            .replace("<reset>","§r")
            .replace("<bold>","§l")
            .replace("<italic>","§o")
            .replace("<strikethrough>","§m")
            .replace("<obfuscated>","§k")
            .replace("<underline>","§n")
            .replace("<!italic>","§r") // 따로 없는거 같아서
            .replace("<","§")
            .replace(">","")

    }

    fun getLegacyToComponent(legacyText:String): TextComponent {
        return LegacyComponentSerializer.legacySection().deserialize(legacyText)
    }
}