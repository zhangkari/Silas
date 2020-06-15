package org.fish.silas.data.event

import androidx.annotation.IntDef

const val ADD_CLICK = 0
const val SUBTRACT_CLICK = 1

@IntDef(ADD_CLICK, SUBTRACT_CLICK)
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
annotation class ClickAction

data class DishClickEvent(val action: @ClickAction Int, val id: String)