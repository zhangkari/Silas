package org.fish.silas.data.entity

data class ProductEntity(val id: String, val combo: List<ProductEntity>?, val name: String, val price: Long, val iconUrl: String)