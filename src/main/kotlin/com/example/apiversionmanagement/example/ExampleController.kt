package com.example.apiversionmanagement.example

import com.example.apiversionmanagement.mimetype.ApiVersion
import com.example.apiversionmanagement.mimetype.VersionResource
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
    fun exampleUri(): String = "URI를 통한 버전관리 API 입니다."

    @GetMapping("/version/example")
    @VersionResource(from = ApiVersion.V1)
    fun exampleMimeType(): String = "Mime Type Version Type API"

    @GetMapping("/resolver/example", headers = ["X-API-VERSION=2.0"])
    fun exampleHeader(): String = "Header을 통한 버전관리 API 입니다."

}
