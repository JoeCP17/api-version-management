package com.example.apiversionmanagement.config

import com.example.apiversionmanagement.mimetype.VersionRequestMappingHandlerMapping
import org.springframework.context.annotation.Configuration
import org.springframework.format.support.FormattingConversionService
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.accept.ContentNegotiationManager
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.WebContentInterceptor
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import org.springframework.web.servlet.resource.ResourceUrlProvider

@Configuration
class WebConfig: WebMvcConfigurer {

    @Configuration
    class ApiAccessConfig(
        private val webInterceptors: List<WebContentInterceptor>,
        private val converters: List<HttpMessageConverter<*>>
    ) : WebMvcConfigurationSupport() {
        override fun requestMappingHandlerMapping(
            contentNegotiationManager: ContentNegotiationManager,
            conversionService: FormattingConversionService,
            resourceUrlProvider: ResourceUrlProvider
        ): RequestMappingHandlerMapping {
            VersionRequestMappingHandlerMapping().apply {
                order = 0
                setInterceptors(getInterceptors(conversionService, resourceUrlProvider))

                if (webInterceptors.isNotEmpty()) {
                    setInterceptors(webInterceptors.toTypedArray())
                }

                return this
            }
        }

        override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
            converters.addAll(this.converters)
        }

        override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
            configurer.defaultContentType(MediaType.APPLICATION_JSON)
        }
    }

}
