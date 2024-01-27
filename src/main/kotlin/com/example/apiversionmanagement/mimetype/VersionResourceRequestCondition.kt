package com.example.apiversionmanagement.mimetype

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition
import java.util.regex.Matcher
import java.util.regex.Pattern

class VersionResourceRequestCondition(
    private val acceptedMediaType: String,
    versions: Collection<VersionRange>
) : AbstractRequestCondition<VersionResourceRequestCondition>() {

    private val versions: Set<VersionRange> = versions.toSet()

    constructor(acceptedMediaType: String, from: String, to: String) : this(
        acceptedMediaType,
        setOf(VersionRange(Version(from), Version(to)))
    )

    override fun combine(other: VersionResourceRequestCondition): VersionResourceRequestCondition {
        val newVersions = LinkedHashSet(this.versions)
        newVersions.addAll(other.versions)

        val newMediaType = when {
            acceptedMediaType.isNotBlank() && other.acceptedMediaType.isNotBlank() && acceptedMediaType != other.acceptedMediaType ->
                throw IllegalArgumentException("Both conditions should have the same media type. $acceptedMediaType =!= ${other.acceptedMediaType}")

            acceptedMediaType.isNotBlank() -> acceptedMediaType
            else -> other.acceptedMediaType
        }

        return VersionResourceRequestCondition(newMediaType, newVersions)
    }

    private fun isVersionMatched(version: String): Boolean {
        return versions.any { it.includes(version) }
    }

    private fun isAcceptMatched(matcher: Matcher): Boolean {
        if (matcher.matches()) {
            val actualMediaType = matcher.group(1)
            val version = matcher.group(2)
            if (acceptedMediaType.startsWith(actualMediaType)) {
                return isVersionMatched(version)
            }
        }
        return false
    }

    override fun getMatchingCondition(request: HttpServletRequest): VersionResourceRequestCondition? {
        val accept = request.getHeader("Accept") ?: ""
        val regexPattern = Pattern.compile("(.*)-(\\d+\\.\\d+).*")
        val matcher = regexPattern.matcher(accept)

        if (isAcceptMatched(matcher)) {
            return this
        } else {
            val params = request.parameterMap
            if (params.any { it.key == "version" }) {
                val paramVersion = params["version"]!![0]
                if (isVersionMatched(paramVersion)) return this
            }
        }

        return null
    }

    override fun compareTo(
        other: VersionResourceRequestCondition,
        request: HttpServletRequest
    ): Int {
        return 0
    }

    override fun getContent(): Collection<*> {
        return versions
    }

    override fun toString(): String {
        return buildString {
            append("version={")
            append("media=").append(acceptedMediaType).append(",")
            versions.joinTo(this, separator = ",")
            append("}")
        }
    }

    override fun getToStringInfix(): String {
        return " && "
    }
}
