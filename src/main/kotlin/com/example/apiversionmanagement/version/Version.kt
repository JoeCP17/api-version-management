package com.example.apiversionmanagement.version

class Version (
    version: String
): Comparable<Version> {

    private var major: Int = 0
    private var minor: Int = 0

    init {
        create(version)
    }

    private fun create(version: String) {
        val tokens = version.split("//.")

        if (tokens.size != 2) {
            throw IllegalArgumentException("version must be in the form of 'major.minor'")
        }

        major = Integer.parseInt(tokens[0])
        minor = Integer.parseInt(tokens[1])
    }

    override fun compareTo(other: Version): Int {
        return when {
            this.major > other.major -> 1
            this.major < other.major -> -1
            this.minor > other.minor -> 1
            this.minor < other.minor -> -1
            else -> 0
        }
    }

    override fun toString(): String = "v$major.$minor"

}
