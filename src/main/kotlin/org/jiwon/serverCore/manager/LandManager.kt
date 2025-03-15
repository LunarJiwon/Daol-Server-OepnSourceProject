package org.jiwon.serverCore.manager

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.geysermc.cumulus.form.SimpleForm
import org.geysermc.cumulus.response.SimpleFormResponse
import org.jiwon.serverCore.ServerCore.Companion.defaultConfig
import org.jiwon.serverCore.ServerCore.Companion.gson
import org.jiwon.serverCore.ServerCore.Companion.landDataFile
import org.jiwon.serverCore.alternative.ConfigNames
import org.jiwon.serverCore.alternative.ConfigNames.LAND_LIST
import org.jiwon.serverCore.alternative.ConfigNames.LAND_SIZE_TO_PRICE
import org.jiwon.serverCore.alternative.ConfigNames.LAND_TAX
import org.jiwon.serverCore.alternative.LandSettings.ALLOW_EXPLODE
import org.jiwon.serverCore.alternative.LandSettings.EXIT_MESSAGE
import org.jiwon.serverCore.alternative.LandSettings.TELEPORT_LOCATION
import org.jiwon.serverCore.alternative.LandSettings.WELCOME_MESSAGE
import org.jiwon.serverCore.alternative.ReplaceTexts.BLOCK_COUNT
import org.jiwon.serverCore.alternative.ReplaceTexts.CURRENT_SETTING
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_NAME
import org.jiwon.serverCore.alternative.ReplaceTexts.LAND_SIZE
import org.jiwon.serverCore.alternative.ReplaceTexts.PRICE
import org.jiwon.serverCore.alternative.ReplaceTexts.TAX
import org.jiwon.serverCore.manager.PlayerManager.getPlayerName
import org.jiwon.serverCore.messages.LandTexts
import org.jiwon.serverCore.messages.LandTexts.BUILD_ALLOW_PLAYER_ITEM_DESCRIPTION
import org.jiwon.serverCore.messages.LandTexts.BUILD_ALLOW_PLAYER_MENU_TITLE
import org.jiwon.serverCore.messages.LandTexts.BUILD_ALLOW_PLAYER_NONE_ITEM
import org.jiwon.serverCore.messages.LandTexts.BUILD_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION
import org.jiwon.serverCore.messages.LandTexts.INTERACT_ALLOW_PLAYER_ITEM_DESCRIPTION
import org.jiwon.serverCore.messages.LandTexts.INTERACT_ALLOW_PLAYER_MENU_TITLE
import org.jiwon.serverCore.messages.LandTexts.INTERACT_ALLOW_PLAYER_NONE_ITEM
import org.jiwon.serverCore.messages.LandTexts.INTERACT_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION
import org.jiwon.serverCore.messages.LandTexts.LAND_LIST_ITEM_DESCRIPTION
import org.jiwon.serverCore.messages.LandTexts.LAND_LIST_ITEM_DESCRIPTION_MERGE
import org.jiwon.serverCore.messages.LandTexts.LAND_LIST_ITEM_NAME
import org.jiwon.serverCore.messages.LandTexts.LAND_LIST_MENU_TITLE
import org.jiwon.serverCore.messages.LandTexts.LAND_LIST_MENU_TITLE_MERGE
import org.jiwon.serverCore.messages.LandTexts.LAND_MANAGE_MENU
import org.jiwon.serverCore.messages.LandTexts.LAND_MANAGE_MENU_TITLE
import org.jiwon.serverCore.messages.LandTexts.LAND_MENU_MANAGE_BUTTON_BEDROCK
import org.jiwon.serverCore.messages.LandTexts.LAND_MENU_PURCHASE_BUTTON_BEDROCK
import org.jiwon.serverCore.messages.LandTexts.LAND_MENU_TITLE_BEDROCK
import org.jiwon.serverCore.messages.LandTexts.LAND_PURCHASE_MENU_TITLE_BEDROCK
import org.jiwon.serverCore.messages.LandTexts.LAND_SETTINGS_ITEM
import org.jiwon.serverCore.messages.LandTexts.LAND_SETTINGS_TITLE
import org.jiwon.serverCore.messages.LandTexts.LAND_SIZE_DESCRIPTION
import org.jiwon.serverCore.messages.LandTexts.LAND_SIZE_DESCRIPTION_BEDROCK
import org.jiwon.serverCore.messages.LandTexts.LAND_SIZE_TEXT
import org.jiwon.serverCore.messages.MessageColors.BLACK
import org.jiwon.serverCore.messages.MessageColors.WHITE
import org.jiwon.serverCore.messages.MessageColors.WHITE_GREEN
import org.jiwon.serverCore.messages.MessageColors.WHITE_RED
import org.jiwon.serverCore.utils.AreaData
import org.jiwon.serverCore.utils.Components.mm
import org.jiwon.serverCore.utils.Components.mmLegacy
import org.jiwon.serverCore.utils.LandData
import org.jiwon.serverCore.utils.NumberUtils
import java.util.function.Consumer


object LandManager {

    fun landInteractAllowPlayerSettingsBedrockMenu(player: Player,landName: String,response: Consumer<SimpleFormResponse>): SimpleForm {
        val interactAllowPlayers = getPlayerLand(player, landName)!!.interactAllowPlayers
        val form = SimpleForm.builder().title(
            mmLegacy(
                INTERACT_ALLOW_PLAYER_MENU_TITLE
            )
        )
        for(i in 0..8){
            if(interactAllowPlayers.size - 1 >= i){
                form.button(
                    mmLegacy(
                        "${getPlayerName(interactAllowPlayers[i])}\n${INTERACT_ALLOW_PLAYER_ITEM_DESCRIPTION}"
                    )
                )
            }else{
                form.button(
                    mmLegacy(
                        "${INTERACT_ALLOW_PLAYER_NONE_ITEM}\n${INTERACT_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION}"
                    )
                )
            }
        }
        form.validResultHandler(response)
        return form.build()
    }

    fun landInteractAllowPlayersSettingMenu(player: Player,landName: String):Inventory{
        val interactAllowPlayers = getPlayerLand(player, landName)!!.interactAllowPlayers
        val interactAllowPlayerItems = ArrayList<ItemStack?>()

        for(i in 0..8){
            if(interactAllowPlayers.size - 1 >= i){
                interactAllowPlayerItems.add(
                    ItemManager.createItem(
                        Material.PLAYER_HEAD,
                        mm(getPlayerName(interactAllowPlayers[i])!!),
                        mutableListOf(
                            mm(INTERACT_ALLOW_PLAYER_ITEM_DESCRIPTION)
                        ),
                        false,
                        interactAllowPlayers[i]
                    )
                )
            }else{
                interactAllowPlayerItems.add(
                    ItemManager.createItem(
                        Material.SKELETON_SKULL,
                        mm(INTERACT_ALLOW_PLAYER_NONE_ITEM),
                        mutableListOf(
                            mm(INTERACT_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION)
                        ),
                        false
                    )
                )
            }
        }

        val interactAllowPlayersMenu = InventoryManager(9,mm(INTERACT_ALLOW_PLAYER_MENU_TITLE),interactAllowPlayerItems)
        return interactAllowPlayersMenu.inventory
    }

    fun landBuildAllowPlayersSettingBedrockMenu(player: Player,landName: String,response: Consumer<SimpleFormResponse>): SimpleForm {
        val buildAllowPlayers = getPlayerLand(player, landName)!!.buildAllowPlayers
        val form = SimpleForm.builder().title(
            mmLegacy(BUILD_ALLOW_PLAYER_MENU_TITLE)
        )
        for (i in 0..8){
            if(buildAllowPlayers.size - 1 >= i){
                form.button(
                    mmLegacy(
                        "${getPlayerName(buildAllowPlayers[i])}\n${BUILD_ALLOW_PLAYER_ITEM_DESCRIPTION}"
                    )
                )
            }else{
                form.button(
                    mmLegacy(
                        "${BUILD_ALLOW_PLAYER_NONE_ITEM}\n${BUILD_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION}"
                    )
                )
            }
        }
        form.validResultHandler(response)
        return form.build()
    }

    fun landBuildAllowPlayersSettingMenu(player: Player,landName: String):Inventory{
        val buildAllowPlayers = getPlayerLand(player, landName)!!.buildAllowPlayers
        val buildAllowPlayerItems = ArrayList<ItemStack?>()

        for(i in 0..8){
            if(buildAllowPlayers.size - 1 >= i){
                buildAllowPlayerItems.add(
                    ItemManager.createItem(
                        Material.PLAYER_HEAD,
                        mm(getPlayerName(buildAllowPlayers[i])!!),
                        mutableListOf(
                            mm(BUILD_ALLOW_PLAYER_ITEM_DESCRIPTION)
                        ),
                        false,
                        buildAllowPlayers[i]
                    )
                )
            }else{
                buildAllowPlayerItems.add(
                    ItemManager.createItem(
                        Material.SKELETON_SKULL,
                        mm(BUILD_ALLOW_PLAYER_NONE_ITEM),
                        mutableListOf(
                            mm(BUILD_ALLOW_PLAYER_NONE_ITEM_DESCRIPTION)
                        ),
                        false
                    )
                )
            }
        }

        val buildAllowPlayersMenu = InventoryManager(9,mm(BUILD_ALLOW_PLAYER_MENU_TITLE),buildAllowPlayerItems)
        return buildAllowPlayersMenu.inventory
    }

    fun landSettingsMenuBedrock(player: Player,landName: String,response:Consumer<SimpleFormResponse>): SimpleForm {
        val exitMessage = getLandSetting(player,landName,EXIT_MESSAGE)
        val joinMessage = getLandSetting(player,landName, WELCOME_MESSAGE)
        val isExplode = getLandSetting(player,landName, ALLOW_EXPLODE)
        val teleportLocation = getLandSetting(player,landName, TELEPORT_LOCATION)
        val form = SimpleForm.builder().title(mmLegacy(LAND_SETTINGS_TITLE))

        LAND_SETTINGS_ITEM.forEachIndexed { index, it ->
            when(index){
                0->{
                    form.button(mmLegacy(
                        "${it[1]}\n${it[3].toString().replace(CURRENT_SETTING,if(teleportLocation == null) "${WHITE_RED}미설정" else "${WHITE_GREEN}설정됨")}"
                    ))
                }
                1->{
                    form.button(mmLegacy(
                        "${it[1]}\n${it[3].toString().replace(CURRENT_SETTING,if(joinMessage.isNullOrEmpty()) "${WHITE_RED}미설정" else "${WHITE_GREEN}${joinMessage}")}"
                    ))
                }
                2->{
                    form.button(mmLegacy(
                        "${it[1]}\n${it[3].toString().replace(CURRENT_SETTING,if(exitMessage.isNullOrEmpty()) "${WHITE_RED}미설정" else "${WHITE_GREEN}${joinMessage}")}"
                    ))
                }
                3->{
                    form.button(mmLegacy(
                        "${it[1]}\n${it[3].toString().replace(CURRENT_SETTING,if(isExplode == "true") "${WHITE_GREEN}설정됨" else "${WHITE_RED}미설정")}"
                    ))
                }
            }
        }
        form.validResultHandler(response)
        return form.build()
    }

    fun landSettingsMenu(player: Player,landName: String):Inventory{

        val exitMessage = getLandSetting(player,landName,EXIT_MESSAGE)
        val joinMessage = getLandSetting(player,landName, WELCOME_MESSAGE)
        val isExplode = getLandSetting(player,landName, ALLOW_EXPLODE)
        val teleportLocation = getLandSetting(player,landName, TELEPORT_LOCATION)

        val settingsItems = ArrayList<ItemStack?>()
        for(i in 0..8){
            var currentItem : ItemStack? = null
            when (i){
                0->{
                    currentItem = ItemManager.createItem(
                        (LAND_SETTINGS_ITEM[0][0] as Material),
                        mm(LAND_SETTINGS_ITEM[0][1] as String),
                        mutableListOf(
                            mm(LAND_SETTINGS_ITEM[0][2] as String),
                            mm((LAND_SETTINGS_ITEM[0][3] as String).replace(CURRENT_SETTING
                            ,if(teleportLocation == null) "${WHITE_RED}미설정" else "${WHITE_GREEN}설정됨"))
                        ),
                        false,
                        TELEPORT_LOCATION
                    )
                }
                1->{
                    currentItem = ItemManager.createItem(
                        (LAND_SETTINGS_ITEM[1][0] as Material),
                        mm(LAND_SETTINGS_ITEM[1][1] as String),
                        mutableListOf(
                            mm(LAND_SETTINGS_ITEM[1][2] as String),
                            mm((LAND_SETTINGS_ITEM[1][3] as String).replace(CURRENT_SETTING
                                ,if(joinMessage.isNullOrEmpty()) "${WHITE_RED}미설정" else "${WHITE_GREEN}${joinMessage}"))
                        ),
                        false,
                        WELCOME_MESSAGE
                    )
                }
                2->{
                    currentItem = ItemManager.createItem(
                        (LAND_SETTINGS_ITEM[2][0] as Material),
                        mm(LAND_SETTINGS_ITEM[2][1] as String),
                        mutableListOf(
                            mm(LAND_SETTINGS_ITEM[2][2] as String),
                            mm((LAND_SETTINGS_ITEM[2][3] as String).replace(CURRENT_SETTING
                                ,if(exitMessage.isNullOrEmpty()) "${WHITE_RED}미설정" else "${WHITE_GREEN}${joinMessage}"))
                        ),
                        false,
                        EXIT_MESSAGE
                    )
                }
                3->{
                    currentItem = ItemManager.createItem(
                        (LAND_SETTINGS_ITEM[3][0] as Material),
                        mm(LAND_SETTINGS_ITEM[3][1] as String),
                        mutableListOf(
                            mm(LAND_SETTINGS_ITEM[3][2] as String),
                            mm((LAND_SETTINGS_ITEM[3][3] as String).replace(CURRENT_SETTING
                                ,if(isExplode == "true") "${WHITE_GREEN}설정됨" else "${WHITE_RED}미설정"))
                        ),
                        false,
                        ALLOW_EXPLODE
                    )
                }
                else->currentItem = null
            }
            settingsItems.add(currentItem)
        }
        val settingsInventory = InventoryManager(9,mm(LAND_SETTINGS_TITLE),settingsItems)
        return settingsInventory.inventory
    }

    fun landManageMenu(landData: LandData):Inventory{
        val menuItems = ArrayList<ItemStack?>()
        var menuIndex = 0
        val blockCount = AreaManager.getBlockCountFromArea(landData.area) + landData.remainArea
        val price = (blockCount * 100) + (blockCount * defaultConfig.getFloat(
            LAND_TAX
        )).toInt()
        val sellPrice = (blockCount * 50)
        for(i in 0..44){
            if(i == 12 || i == 14 || i in 19..25 || i == 30 || i == 32){
                menuItems.add(
                    ItemManager.createItem(
                        LAND_MANAGE_MENU[menuIndex][0] as Material,
                        mm(LAND_MANAGE_MENU[menuIndex][1] as String),
                        mutableListOf(mm(
                            (LAND_MANAGE_MENU[menuIndex][2] as String).replace(LAND_NAME,landData.landName).replace(TAX,
                                NumberUtils.format(sellPrice)).replace(BLOCK_COUNT,NumberUtils.format(landData.remainArea)).replace(
                                PRICE,NumberUtils.format(price))
                        )),
                        false
                    )
                )
                menuIndex++
            }else menuItems.add(null)
        }
        val menuInventory = InventoryManager(45,mm(LAND_MANAGE_MENU_TITLE.replace(LAND_NAME,landData.landName)),menuItems)
        return menuInventory.inventory
    }

    fun getLandNameFromBedrockInterface(buttonText:String):String{
        return buttonText.split("e")[1].split("\n")[0]
    }

    fun bedrockLandMenu(landData: LandData,result: Consumer<SimpleFormResponse>): SimpleForm {
        val form = SimpleForm.builder().title(
            mmLegacy(LAND_MANAGE_MENU_TITLE.replace(LAND_NAME,landData.landName))
        )
        val blockCount = AreaManager.getBlockCountFromArea(landData.area) + landData.remainArea
        val price = (blockCount * 100) + (blockCount * defaultConfig.getFloat(
            LAND_TAX
        )).toInt()
        val sellPrice = (blockCount * 50)
        LAND_MANAGE_MENU.forEach {
            form.button(mmLegacy("${it[1]}\n${it[2].toString().replace(WHITE,BLACK).replace(LAND_NAME,landData.landName).replace(TAX,
                NumberUtils.format(sellPrice)).replace(BLOCK_COUNT,NumberUtils.format(landData.remainArea)).replace(
                PRICE,NumberUtils.format(price))}"
            ))
        }
        form.validResultHandler(result)
        return form.build()
    }


    fun bedrockLandListMenu(player:Player,excludeLandName:String?,result: Consumer<SimpleFormResponse>): SimpleForm {
        val form = SimpleForm.builder().title(
            mmLegacy(if(excludeLandName == null) LAND_LIST_MENU_TITLE else LAND_LIST_MENU_TITLE_MERGE),
        )
        val playerLandList = getPlayerLandList(player)!!
        playerLandList.forEach {
           form.button(
               "${mmLegacy(LAND_LIST_ITEM_NAME.replace(LAND_NAME, it.landName))}\n" +
                       mmLegacy(if (excludeLandName == null) LAND_LIST_ITEM_DESCRIPTION else LAND_LIST_ITEM_DESCRIPTION_MERGE)
           )
        }

        form.validResultHandler(result)

        return form.build()


    }

    /**
     * @description 플레이어 부동산 목록 표시 함수(자바 에디션)
     *
     */
    fun landListMenu(player: Player,excludeLandName:String?):Inventory{
        val landItems = ArrayList<ItemStack?>()
        val playerLandList = getPlayerLandList(player)!!
        landItems.add(null)
        for(i in 0..6){
            if(playerLandList.size - 1 >= i) {
                if(excludeLandName != playerLandList[i].landName) {
                    landItems.add(
                        ItemManager.createItem(
                            Material.GRASS_BLOCK,
                            mm(LAND_LIST_ITEM_NAME.replace(LAND_NAME, playerLandList[i].landName)),
                            mutableListOf(
                                mm(if (excludeLandName == null) LAND_LIST_ITEM_DESCRIPTION else LAND_LIST_ITEM_DESCRIPTION_MERGE)
                            ),
                            false,
                            playerLandList[i].landName
                        )
                    )
                }
            }
            else landItems.add(null)
        }
        landItems.add(null)
        val menuInventory = InventoryManager(
            9,
            title = mm(if(excludeLandName == null) LAND_LIST_MENU_TITLE else LAND_LIST_MENU_TITLE_MERGE),
            data = landItems
        )
        return menuInventory.inventory

    }


    fun landPurchaseBedrockMenu(result:Consumer<SimpleFormResponse>):SimpleForm{
        val landOption = getSaleLands()
        val form = SimpleForm.builder()
            .title(LAND_PURCHASE_MENU_TITLE_BEDROCK)
        for(i in 0..3){
            form.button(LAND_SIZE_DESCRIPTION_BEDROCK[0].replace(
                LAND_SIZE, landOption[i].get("size").asString
            ).replace(
                BLOCK_COUNT, NumberUtils.format(landOption[i].get("size").asString.split("x")[0].toInt() * landOption[i].get("size").asString.split("x")[1].toInt())
            )+"\n"+ LAND_SIZE_DESCRIPTION_BEDROCK[1].replace(PRICE,NumberUtils.format(landOption[i].get("price").asInt)))
        }
        form.validResultHandler(result)
        return form.build()

    }

    /**
     * @description 부동산 구매 메뉴 생성 함수
     */
    fun landPurchaseMenu():Inventory{
        val landOptions = getSaleLands()

        var j = 0
        val menuItems = ArrayList<ItemStack?>()
        for(i in 0..8){
            if(i == 2 || i == 3 || i == 5 || i == 6){
                menuItems.add(ItemManager.createItem(
                    if(i == 2) Material.DIRT else if(i == 3) Material.COARSE_DIRT else if(i == 5) Material.DIRT_PATH else Material.GRASS_BLOCK,
                    mm(LAND_SIZE_TEXT.replace(LAND_SIZE,landOptions[j].get("size").asString)),
                    mutableListOf(
                        mm(LAND_SIZE_DESCRIPTION[0].replace(PRICE,NumberUtils.format(landOptions[j].get("price").asInt))),
                        mm(LAND_SIZE_DESCRIPTION[1].replace(
                            BLOCK_COUNT,NumberUtils.format(
                                landOptions[j].get("size").asString.split("x")[0].toInt()*landOptions[j].get("size").asString.split("x")[1].toInt()
                            ))),
                        mm(LAND_SIZE_DESCRIPTION[2]),
                        mm(LAND_SIZE_DESCRIPTION[3]),
                    ),
                    false
                ))
                j++
            }else menuItems.add(null)
        }
        val menuInventory = InventoryManager(9,mm(LandTexts.LAND_MENU_TITLE),menuItems)
        return menuInventory.inventory
    }


    /**
     * @description 베드락 메인 메뉴 생성 함수
     */
    fun bedrockLandMainMenu(response:Consumer<SimpleFormResponse>): SimpleForm {

        return SimpleForm.builder()
            .title(LAND_MENU_TITLE_BEDROCK)
            .button(LAND_MENU_PURCHASE_BUTTON_BEDROCK)
            .button(LAND_MENU_MANAGE_BUTTON_BEDROCK)
            .validResultHandler(response)
            .build()
    }

    /**
     * @description 메인메뉴 생성 함수
     */
    fun landMainMenu():Inventory{
        val menuItems = ArrayList<ItemStack?>()
        for(i in 0..8){
            when (i) {
                3 -> {
                    menuItems.add(ItemManager.createItem(
                        type = Material.GOLD_INGOT,
                        itemName = mm(LandTexts.LAND_MENU_ITEM_PURCHASE),
                        itemDescription = mutableListOf(
                            mm(LandTexts.LAND_MENU_ITEM_PURCHASE_DESCRIPTION_1)
                        ),
                        isGlow = true
                    ))
                }
                5 -> {
                    menuItems.add(ItemManager.createItem(
                        type = Material.GRASS_BLOCK,
                        itemName = mm(LandTexts.LAND_MENU_ITEM_MANAGE),
                        itemDescription = mutableListOf(
                            mm(LandTexts.LAND_MENU_ITEM_MANAGE_DESCRIPTION_1)
                        ),
                        isGlow = true
                    ))
                }
                else -> menuItems.add(null)
            }
        }

        val menuInventory = InventoryManager(9,mm(LandTexts.LAND_MENU_TITLE),menuItems)
        return menuInventory.inventory
    }

    /**
     * @description 부동산 구매 옵션 가져오는 함수
     *
     */
    fun getSaleLands(): MutableList<JsonObject> {
        val landOption = defaultConfig.getJsonObject(LAND_SIZE_TO_PRICE)
        return mutableListOf(
            landOption.getAsJsonObject(ConfigNames.LAND_SIZE_1),
            landOption.getAsJsonObject(ConfigNames.LAND_SIZE_2),
            landOption.getAsJsonObject(ConfigNames.LAND_SIZE_3),
            landOption.getAsJsonObject(ConfigNames.LAND_SIZE_4)
        )

    }

    /**
     * @description 플레이어 부동산 추가 함수
     * {
     *  "land_list":{
     *      "player_uuid":[
     *          {
     *              landData
     *          }
     *      ]
     *   }
     * }
     */
    fun createPlayerLand(player:Player ,land:LandData):Boolean{
        val landList : JsonObject
        val playerObject : JsonObject
        val playerLandList : JsonArray
        if(landDataFile.getRawData(LAND_LIST) != null){
            landList = landDataFile.getJsonObject(LAND_LIST)
            if(landList.has(player.uniqueId.toString())){
                playerLandList = landList.get(player.uniqueId.toString()).asJsonArray
                playerLandList.add(gson.toJsonTree(land))
            }else{
                playerLandList = JsonArray()
                playerLandList.add(gson.toJsonTree(land))
                landList.add(player.uniqueId.toString(),playerLandList)
            }
        }else{
            playerObject = JsonObject()
            playerLandList = JsonArray()
            playerLandList.add(gson.toJsonTree(land))

            playerObject.add(player.uniqueId.toString(),playerLandList)
            landDataFile.set(LAND_LIST,playerObject)
        }
        landDataFile.save()
        return true
    }

    /**
     * 영역이 겹치는지 판단하는 함수
     */
    fun isOverlapLocation(standardLocation:Location): HashMap<String,LandData> {
        val owner: HashMap<String,LandData> = HashMap<String,LandData>()
        if(landDataFile.getRawData(LAND_LIST) != null) {
            val landList = landDataFile.getJsonObject(LAND_LIST)
            run breaker@{
                landList.keySet().forEach {
                    landList.get(it).asJsonArray.forEach { landObject ->
                        val land = gson.fromJson(landObject.asJsonObject, LandData::class.java)
                        if (AreaManager.isOverlapLocation(
                                land.area,
                                LocationManager.createJsonLocation(standardLocation)
                            )
                        ) {
                            owner[it] = land

                            return@breaker
                        }

                    }
                }
            }
        }
        return owner
    }

    /**
     * @description 두 영역이 겹치는지 확인하는 함수
     */
    fun isOverlapArea(standardArea:AreaData): HashMap<String,LandData> {
        val owner = HashMap<String,LandData>()
        if(landDataFile.getRawData(LAND_LIST) != null){
            val landList = landDataFile.getJsonObject(LAND_LIST)
            run breaker@{
                landList.keySet().forEach {
                    landList.get(it).asJsonArray.forEach { landObject ->
                        val land = gson.fromJson(landObject.asJsonObject, LandData::class.java)
                        if (AreaManager.isOverlapArea(
                                land.area,
                                standardArea
                            )
                        ) {
                            owner[it] = land
                            return@breaker
                        }

                    }
                }
            }
        }
        return owner
    }


    fun setPlayerLand(player: Player,land: LandData){
        removePlayerLandFromLandName(player,land.landName)
        createPlayerLand(player,land)
    }

    fun getPlayerLand(player:Player,landName:String): LandData? {
        if(landDataFile.getJsonObject(LAND_LIST).getAsJsonArray(player.uniqueId.toString()) != null) {
            landDataFile.getJsonObject(LAND_LIST).getAsJsonArray(player.uniqueId.toString()).forEach {
                val land = gson.fromJson(it.asJsonObject, LandData::class.java)
                if (land.landName == landName) {
                    return land
                }
            }
        }
        return null
    }

    fun getPlayerLandList(player:Player):ArrayList<LandData>?{
        var landList:ArrayList<LandData>? = null
        if(landDataFile.getRawData(LAND_LIST) != null){
            if(landDataFile.getJsonObject(LAND_LIST).getAsJsonArray(player.uniqueId.toString()) != null){
                landList = ArrayList()
                landDataFile.getJsonObject(LAND_LIST).getAsJsonArray(player.uniqueId.toString()).forEach {
                    landList.add(gson.fromJson(it,LandData::class.java))
                }
            }
        }
        return landList
    }

    fun getPlayerLandSize(player:Player):Int{
        getPlayerLandList(player)?.let {
            return it.size
        }
        return 0
    }

    fun removePlayerLandFromLandName(player: Player,landName:String){
        val playerLandList = landDataFile.getJsonObject(LAND_LIST).getAsJsonArray(player.uniqueId.toString())
        playerLandList.remove(playerLandList.find { land -> gson.fromJson(land,LandData::class.java).landName == landName })
    }

    fun getPlayerLandFromLandName(player: Player,landName:String): LandData? {
        return getPlayerLandList(player)?.find {
            landData ->  landData.landName == landName
        }
    }

    fun changePlayerLandName(player: Player,beforeLandName:String,afterLandName:String){
        val beforeLand = getPlayerLandFromLandName(player,beforeLandName)!!.copy(
            landName = afterLandName
        )
        createPlayerLand(player,beforeLand)
        removePlayerLandFromLandName(player,beforeLandName)
    }

    fun setLandSetting(player: Player,landName: String,settingsName:String,value:String){
        val settings = getPlayerLandJsonObject(player, landName)!!.getAsJsonObject("settings")
        settings.remove(settingsName)
        settings.add(settingsName,gson.toJsonTree(value))

    }

    fun getLandSetting(player: Player,landName: String,settingsName: String):String?{
        return getPlayerLand(player,landName)!!.settings[settingsName]
    }

    fun addBuildAllowPlayer(player: Player,landName: String,targetPlayer: String){
        getPlayerLandJsonObject(player, landName)!!.getAsJsonArray("buildAllowPlayers").add(
            targetPlayer
        )

    }

    fun removeBuildAllowPlayer(player: Player,landName: String,targetPlayer: String){
        getPlayerLandJsonObject(player, landName)!!.getAsJsonArray("buildAllowPlayers").remove(gson.toJsonTree(targetPlayer))
    }

    fun addInteractAllowPlayer(player: Player,landName: String,targetPlayer: String){
        getPlayerLandJsonObject(player, landName)!!.getAsJsonArray("interactAllowPlayers").add(
            targetPlayer
        )

    }

    fun removeInteractAllowPlayer(player: Player,landName: String,targetPlayer: String){
        getPlayerLandJsonObject(player, landName)!!.getAsJsonArray("interactAllowPlayers").remove(gson.toJsonTree(targetPlayer))
    }


    private fun getPlayerLandJsonObject(player: Player, landName: String): JsonObject? =
        landDataFile.getJsonObject(LAND_LIST).getAsJsonArray(player.uniqueId.toString()).find {
            gson.fromJson(it, LandData::class.java).landName == landName
        }!!.asJsonObject


}

