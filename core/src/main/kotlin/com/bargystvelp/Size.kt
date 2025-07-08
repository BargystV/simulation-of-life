package com.bargystvelp

data class Size(
    val width: Int,
    val height: Int
)

fun Size.div(size: Size): Size {
    return Size(width = width / size.width, height = height / size.height)
}
