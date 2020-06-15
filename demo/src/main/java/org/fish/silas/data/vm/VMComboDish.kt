package org.fish.silas.data.vm

class VMComboDish(id: String, name: String, price: Long, icon: String, val combo: List<VMComboDish>) : VMSingleDish(id, name, price, icon)