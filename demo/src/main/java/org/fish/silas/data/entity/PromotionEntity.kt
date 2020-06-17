package org.fish.silas.data.entity

data class PromotionEntity(val id: String,
                           val name: String,
                           val type: Int, // 0 折扣券，    1 折后立减券
                           val value: Int /* 85 折扣85折， 5 折后立减5元 */)