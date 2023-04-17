package com.example.foodike.presentation.home.util

enum class Filter(val value: String) {
    FASTDELIVERY("Быстрая доставка"),
    DISCOUNT("50% Скидка"),
    GREATDEALS("Выгодные предложения"),
    NONVEG("Для всех")

}

fun getAllTags(): List<Filter> {
    return listOf(
        Filter.FASTDELIVERY, Filter.DISCOUNT, Filter.GREATDEALS,
        Filter.NONVEG
    )
}

fun getTag(value: String): Filter? {
    val map = Filter.values().associateBy(Filter::value)
    return map[value]
}

