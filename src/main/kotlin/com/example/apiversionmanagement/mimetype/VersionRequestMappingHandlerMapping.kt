package com.example.apiversionmanagement.mimetype

import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.servlet.mvc.condition.RequestCondition
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.Method

class VersionRequestMappingHandlerMapping: RequestMappingHandlerMapping() {
    override fun getCustomTypeCondition(handlerType: Class<*>): RequestCondition<*> {
        val typeAnnotation =
            AnnotationUtils.findAnnotation(handlerType, VersionResource::class.java)

        return createCondition(typeAnnotation!!)
    }

    override fun getCustomMethodCondition(method: Method): RequestCondition<*> {
        val methodAnnotation = AnnotationUtils.findAnnotation(method, VersionResource::class.java)
        return createCondition(methodAnnotation!!)
    }

    private fun createCondition(versionMapping: VersionResource): RequestCondition<*> =
        VersionResourceRequestCondition(
            versionMapping.media,
            versionMapping.from,
            versionMapping.to
        )
}
