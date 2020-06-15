package org.fish.silas.model

interface Adapter<Src, Dst> {
    fun adapt(src: Src): Dst
}