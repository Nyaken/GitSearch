package me.nyaken.common

enum class SearchSort(
    private val key: String,
    private val value: String
) {

    BEST_MATCH("", "Best Match"),
    STARS("stars", "Stars"),
    FORKS("forks", "Forks"),
    HELP_WANTED_ISSUE("help-wanted-issues", "Help Wanted Issues"),
    UPDATED("updated", "Updated");

    override fun toString(): String {
        return value
    }

    fun toKey(): String {
        return key
    }
}