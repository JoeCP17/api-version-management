package com.example.apiversionmanagement.mimetype

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.mvc.condition.RequestCondition
import java.lang.reflect.Method

class VersionInterceptorHandler : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val version = request.getHeader("Accept") ?: ""

        if (version.isBlank()) {
            throw IllegalArgumentException("Accept Header is required.")
        }

        if (handler is HandlerMethod) {
            val methodCondition = getCustomMethodTypeCondition(handler)
            methodCondition.let {
                println(methodCondition)
                return true
            }
        }

        return false
    }

    private fun getCustomMethodTypeCondition(handlerMethod: HandlerMethod): RequestCondition<*> {
        val methodAnnotation = AnnotationUtils.findAnnotation(handlerMethod.method, VersionResource::class.java)
        return createCondition(methodAnnotation!!)
    }
    private fun getCustomMethodCondition(method: Method): RequestCondition<*> {
        val methodAnnotation = AnnotationUtils.findAnnotation(method, VersionResource::class.java)
        return createCondition(methodAnnotation!!)
    }

    private fun createCondition(versionResource: VersionResource): RequestCondition<*> =
        VersionResourceRequestCondition(
            versionResource.media,
            versionResource.from,
            versionResource.to
        )
}
