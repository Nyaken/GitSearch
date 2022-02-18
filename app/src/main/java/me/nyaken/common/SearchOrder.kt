package me.nyaken.common

enum class SearchOrder(
    private val key: String,
    private val value: String
) {

    ASC("asc", "오름차순"),
    DESC("desc", "내림차순");

    override fun toString(): String {
        return value
    }

    fun toKey(): String {
        return key
    }

}