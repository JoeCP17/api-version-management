package com.example.apiversionmanagement.config

import com.example.apiversionmanagement.mimetype.VersionInterceptorHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig : WebMvcConfigurationSupport() {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(VersionInterceptorHandler())
            .addPathPatterns("/version/**")
    }
}
