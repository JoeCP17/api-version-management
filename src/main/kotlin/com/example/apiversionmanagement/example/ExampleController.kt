package com.example.apiversionmanagement.example

import com.example.apiversionmanagement.version.ApiVersion
import com.example.apiversionmanagement.version.VersionResource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {

    @GetMapping("/api/v1/example")
    fun example(): String = "네이밍을 통한 버전관리 API 입니다."

    @GetMapping("/example")
    @VersionResource(from = ApiVersion.V1)
    fun exampleV1(): String = "버전관리 어노테이션을 통한 버전관리 API 입니다."

}
