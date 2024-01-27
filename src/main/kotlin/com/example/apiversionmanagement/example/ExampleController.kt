package com.example.apiversionmanagement.example

import com.example.apiversionmanagement.mimetype.ApiVersion
import com.example.apiversionmanagement.mimetype.VersionResource
import com.example.apiversionmanagement.resolver.Version
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {

    /**
     * 구현항목
     * 1. 네이밍을 통한 버전 관리 ( /api/v1 ... )
     * 2. resolver을 통한 버전관리
     * 3. Mime Type을 통한 버전관리
     */
    @GetMapping("/api/v1/example")
    fun example(): String = "네이밍을 통한 버전관리 API 입니다."

    @GetMapping("/example")
    @VersionResource(from = ApiVersion.V1)
    fun exampleV1(): String = "버전관리 어노테이션을 통한 버전관리 API 입니다."

    @GetMapping("/resolver/example", headers = ["X-API-VERSION=2.0"])
    fun exampleResolver(): String = "resolver을 통한 버전관리 API 입니다."
}
