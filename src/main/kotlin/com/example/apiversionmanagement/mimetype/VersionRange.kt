package com.example.apiversionmanagement.mimetype

data class VersionRange(
    val from: Version,
    val to: Version
) {
    fun includes(other: String): Boolean {
        val otherVersion = Version(other)
        return otherVersion in from..to
    }

    override fun toString(): String = "range[$from - $to]"
}
