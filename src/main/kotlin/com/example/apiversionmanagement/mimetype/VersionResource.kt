package com.example.apiversionmanagement.mimetype


@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class VersionResource(
    val media: String = "application/vnd.example",
    val from: String = "",
    val to: String = MAX_VERSION
) {
    companion object {
        const val MAX_VERSION = "99.99"
    }
}
