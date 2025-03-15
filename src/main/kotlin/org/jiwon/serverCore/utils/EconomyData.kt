package org.jiwon.serverCore.utils


import org.bukkit.Material

data class EconomyData(
    var name:String?,
    var sign:LocationData?,
    var item:Material,
    var purchasePrice:Int,
    var salePrice:Int,
    val isRestrict:Boolean

)

