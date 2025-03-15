package org.jiwon.serverCore

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.github.lunarjiwon.DiscordEmbedBuilder
import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.plugin.java.JavaPlugin
import org.geysermc.floodgate.api.FloodgateApi
import org.jiwon.serverCore.alternative.ConfigNames
import org.jiwon.serverCore.alternative.ConfigNames.END_TOLL_PRICE
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_1
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_2
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_3
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_4
import org.jiwon.serverCore.alternative.ConfigNames.NETHER_TOLL_PRICE
import org.jiwon.serverCore.alternative.ConfigNames.SURVIVAL_TOLL_PRICE
import org.jiwon.serverCore.api.HttpServerManager
import org.jiwon.serverCore.commands.*
import org.jiwon.serverCore.discord.DiscordWebhookManager
import org.jiwon.serverCore.discord.DiscordWebhookManager.Companion.serverStatusWebhook
import org.jiwon.serverCore.events.*
import org.jiwon.serverCore.events.land.protect.LandProtectEvent
import org.jiwon.serverCore.manager.ConfigManager
import org.jiwon.serverCore.manager.EventManager.registerEvent
import org.jiwon.serverCore.scoreboard.ScoreboardManager
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.Logger
import org.json.simple.JSONArray
import java.awt.Color

class ServerCore : JavaPlugin() {


    companion object{
        lateinit var defaultConfig : ConfigManager
        lateinit var playerDataFile : ConfigManager
        lateinit var balanceDataFile : ConfigManager
        lateinit var economyDataFile : ConfigManager
        lateinit var landDataFile : ConfigManager
        lateinit var warpDataFile:ConfigManager
        lateinit var survivalDataFile:ConfigManager
//        lateinit var afkData:ConfigManager
        lateinit var prefixData:ConfigManager
        lateinit var warningData:ConfigManager
//        lateinit var muteData:ConfigManager
//        lateinit var jailData:ConfigManager


        lateinit var luckperms : LuckPerms
        lateinit var httpServer : HttpServerManager
        var gson = Gson()

        lateinit var instance : JavaPlugin
        lateinit var floodgateInstance: FloodgateApi


    }


    private fun settingInit(){
        Logger.info(mm("<#44fc54>데이터 저장 파일 설정"))

        if(!dataFolder.exists()) dataFolder.mkdir()

        /**
         * 기본 설정 파일
         */
        defaultConfig = ConfigManager("settings")
        // 입장 메시지
        if(defaultConfig.getRawData(ConfigNames.PLAYER_JOIN) == null) defaultConfig.set(ConfigNames.PLAYER_JOIN,"")
        // 퇴장 메시지
        if(defaultConfig.getRawData(ConfigNames.PLAYER_EXIT) == null) defaultConfig.set(ConfigNames.PLAYER_EXIT,"")
        // 첫 입장 메시지
        if(defaultConfig.getRawData(ConfigNames.PLAYER_FIRST_JOIN_MESSAGE) == null) defaultConfig.set(ConfigNames.PLAYER_FIRST_JOIN_MESSAGE,"")
        // 송금 수수료
        if(defaultConfig.getRawData(ConfigNames.ECONOMY_TAX) == null) defaultConfig.set(ConfigNames.ECONOMY_TAX,0.0)
        // 판매 수수료
        if(defaultConfig.getRawData(ConfigNames.LAND_TAX) == null) defaultConfig.set(ConfigNames.LAND_TAX,0.0)
        // 튜토리얼 위치
        if(defaultConfig.getRawData(ConfigNames.TUTORIAL_LOCATION) == null) defaultConfig.set(ConfigNames.TUTORIAL_LOCATION,"none")
        // 부동산 허용 월드
        if(defaultConfig.getRawData(ConfigNames.ALLOW_LAND_COMMAND_WORLD) == null) defaultConfig.set(ConfigNames.ALLOW_LAND_COMMAND_WORLD,JSONArray())
        // 부동산 크기별 가격
        if(defaultConfig.getRawData(ConfigNames.LAND_SIZE_TO_PRICE) == null) {
            val landSize1 = JsonObject()
            landSize1.addProperty("size", "5x5")
            landSize1.addProperty("price", 100)

            val landSize2 = JsonObject()
            landSize2.addProperty("size", "25x25")
            landSize2.addProperty("price", 200)

            val landSize3 = JsonObject()
            landSize3.addProperty("size", "50x50")
            landSize3.addProperty("price", 300)

            val landSize4 = JsonObject()
            landSize4.addProperty("size", "80x80")
            landSize4.addProperty("price", 400)
            val landOption = JsonObject()
            landOption.add(LAND_SIZE_1, landSize1)
            landOption.add(LAND_SIZE_2, landSize2)
            landOption.add(LAND_SIZE_3, landSize3)
            landOption.add(LAND_SIZE_4, landSize4)

            defaultConfig.set(ConfigNames.LAND_SIZE_TO_PRICE,landOption)
        }
        if(defaultConfig.getRawData(ConfigNames.SURVIVAL_TOLL) == null) {
            val options = JsonObject()

            options.addProperty(SURVIVAL_TOLL_PRICE,0)
            options.addProperty(NETHER_TOLL_PRICE,0)
            options.addProperty(END_TOLL_PRICE,0)
            defaultConfig.set(ConfigNames.SURVIVAL_TOLL,options)
        }
        if(defaultConfig.getRawData(ConfigNames.SPEAK_PRICE) == null) defaultConfig.set(ConfigNames.SPEAK_PRICE,5000)


        defaultConfig.save()

        /**
         * 사용자 접속 파일
         */
        playerDataFile = ConfigManager("playerData")


        /**
         * 경제 기록 파일
         */
        balanceDataFile = ConfigManager("balance")

        /**
         * 상점 기록 파일
         */
        economyDataFile = ConfigManager("economyData")

        /**
         * 부동산 기록 파일
         */
        landDataFile = ConfigManager("landData")

        /**
         * 워프 기록 파일
         */
        warpDataFile = ConfigManager("warpData")

        /**
         * 야생 기록 파일
         */
        survivalDataFile = ConfigManager("survivalData")

//        /**
//         * 잠수 포인트
//         */
//        afkData = ConfigManager("sleepData")

        /**
         * 칭호 데이터
         */
        prefixData = ConfigManager("prefixData")

        /**
         * 경고 데이터
         */
        warningData = ConfigManager("warningData")

        /**
         * 경고 데이터
         */
//        muteData = ConfigManager("muteData")

        /**
         * 경고 데이터
         */
//        jailData = ConfigManager("jailData")

        Logger.info(mm("<#44fc54>데이터 저장 파일 설정 완료"))


    }

    private fun registerEvents(){
        Logger.info(mm("<#fce744>이벤트 등록"))

        registerEvent(PlayerJoinOrExitEvent())
        registerEvent(PlayerInteractionEvent())
        registerEvent(PlayerPerCommandEvent())
        registerEvent(LandProtectEvent())
        registerEvent(ChatEvent())
        registerEvent(PlayerRespawnEvent())
        registerEvent(DeathEvent())
        registerEvent( PortalEvent())
        registerEvent(PlayerChangeWorldEvent())

        Logger.info(mm("<#44fc54>이벤트 등록 완료"))
    }

    private fun registerCommands(){
        // Paper Command API 정식 출시 후 명령어 실행 방식 변경

        Logger.info(mm("<#fce744>명령어 등록"))

        /**
         * 아래 내용은 절대로 삭제를 금지합니다.
         */
        val licenseCommand = LicenseCommand()
        getCommand("license")!!.setExecutor(licenseCommand)

        /**
         * 시스템 명령어
         */
        val coreCommand = CoreCommand()
        getCommand("server-core")!!.setExecutor(coreCommand)
        getCommand("server-core")!!.tabCompleter = coreCommand

        /**
         * 유지보수(점검) 명령어
         */
        val maintenance = MaintenanceCommand()
        getCommand("maintenance")!!.setExecutor(maintenance)
        getCommand("maintenance")!!.tabCompleter = maintenance

        /**
         * 경제 명령어
         */
        val balanceCommand = BalanceCommand()
        getCommand("balance")!!.setExecutor(balanceCommand)
        getCommand("balance")!!.tabCompleter = balanceCommand

        /**
         * 상점 명령어
         */
        val economyCommand = EconomyCommand()
        getCommand("economy")!!.setExecutor(economyCommand)
        getCommand("economy")!!.tabCompleter = economyCommand

        /**
         * 부동산 명령어
         */
        val landCommand = LandCommand()
        getCommand("land")!!.setExecutor(landCommand)
        getCommand("land")!!.tabCompleter = landCommand

        /**
         * 워프 명령어
         */
        val warpCommand = WarpCommand()
        getCommand("warp")!!.setExecutor(warpCommand)
        getCommand("warp")!!.tabCompleter = warpCommand

        /**
         * 야생 명령어
         */
        val survivalCommand = SurvivalCommand()
        getCommand("survival")!!.setExecutor(survivalCommand)
        getCommand("survival")!!.tabCompleter = survivalCommand

        /**
         * 텔포요청 명령어
         */
        val teleportRequestCommand = TeleportRequestCommand()
        getCommand("tpa")!!.setExecutor(teleportRequestCommand)
        getCommand("tpa")!!.tabCompleter = teleportRequestCommand

        /**
         * 공지 명령어
         */
        val announceCommand = AnnounceCommand()
        getCommand("announce")!!.setExecutor(announceCommand)

        /**
         * 귓속말 명령어
         */
        val whisperCommand = WhisperCommand()
        getCommand("whisper")!!.setExecutor(whisperCommand)
        getCommand("whisper")!!.tabCompleter = whisperCommand

        /**
         * 잠수 설정 명령어
         */
        val afkCommand = AFKCommand()
        getCommand("afk")!!.setExecutor(afkCommand)
        getCommand("afk")!!.tabCompleter = afkCommand

        /**
         * 확성기 명령어
         */
        val speakCommand = SpeakCommand()
        getCommand("speak")!!.setExecutor(speakCommand)
        getCommand("speak")!!.tabCompleter = speakCommand

        /**
         * 칭호 명령어
         */
        val prefixCommand = PrefixCommand()
        getCommand("prefix")!!.setExecutor(prefixCommand)
        getCommand("prefix")!!.tabCompleter = prefixCommand

        /**
         * 경고 명령어
         */
        val warningCommand = WarningCommand()
        getCommand("warn")!!.setExecutor(warningCommand)
        getCommand("warn")!!.tabCompleter = warningCommand

//        /**
//         * 뮤트 명령어
//         */
//        val muteCommand = MuteCommand()
//        getCommand("mute")!!.setExecutor(muteCommand)
//        getCommand("mute")!!.tabCompleter = muteCommand


        Logger.info(mm("<#44fc54>명령어 등록 완료"))

    }

    private fun restServerStart(){
        Logger.info(mm("<#70e0ff> REST API 서비스 시작"))
        httpServer = HttpServerManager("0.0.0.0",14722)
        httpServer.start()

    }

    private fun startSchedules(){
//        AFKSchedule()
    }

    override fun onEnable() {
        instance = getPlugin(ServerCore::class.java) // 플러그인 인스턴스
        floodgateInstance = FloodgateApi.getInstance() // 플로드 게이트 인스턴스
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord");
        luckperms = LuckPermsProvider.get()

        Logger.info(mm("<#70e0ff> ServerCore 시스템 시작"))

        // 데이터 파일 설정
        settingInit()

        // 이벤트 등록
        registerEvents()

        // 명령어 등록
        registerCommands()

        // 디스코드 연동 시스템 가동
        DiscordWebhookManager()

        // 스코어보드
        ScoreboardManager()

        // REST API 서버
        restServerStart()

        // 스케쥴러 시작
        startSchedules()
//        economyDataFile.getJsonArray("STORE_LIST").forEach {
//            val store = gson.fromJson(it,EconomyData::class.java)
//            EconomyManager.createHologram(LocationManager.getLocation(store.sign!!).block,store.name!!,store.item)
//        }

    }

    override fun onDisable() {
        defaultConfig.save()
        balanceDataFile.save()
        playerDataFile.save()
        economyDataFile.save()
        landDataFile.save()
        warpDataFile.save()
        survivalDataFile.save()
//        afkData.save()
        prefixData.save()




        httpServer.stop(0)
        val embed = DiscordEmbedBuilder().setTitle("서버 상태 안내").setDescription("서버 상태가 오프라인입니다").setColor(Color(245, 66, 66)).build()
        serverStatusWebhook.setEmbed(embed)
        serverStatusWebhook.execute()
    }
}
