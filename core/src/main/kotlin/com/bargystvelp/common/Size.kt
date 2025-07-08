package com.bargystvelp.common

data class Size(
    val width: Int,
    val height: Int
)

fun Size.div(size: Size): Size {
    return Size(width = width.div(size.width), height = height.div(size.height))
}

fun Size.times(size: Size): Size {
    return Size(width = width.times(size.width), height = height.times(size.height))
}
