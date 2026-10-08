package com.nbbang.matching.controller

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.core.io.support.ResourceRegion
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

/**
 * [기본 주석] 오디오(BGM) 및 비디오(먹방 쇼츠) 미디어 파일을 실시간 스트리밍하기 위한 REST 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/streaming")
class StreamingController {

    /**
     * [기능 주석] 동적 비디오 스트리밍 API.
     * 클라이언트가 요청한 파일명(filename)에 맞는 영상을 찾아 Byte-Range 분할 스트리밍으로 전송합니다.
     */
    @GetMapping("/video/{filename}")
    fun streamVideo(
        @PathVariable filename: String,
        @RequestHeader headers: HttpHeaders
    ): ResponseEntity<ResourceRegion> {
        val video = ClassPathResource("static/video/$filename")

        // 요청한 파일이 존재하지 않을 경우 404 Not Found 처리
        if (!video.exists()) {
            return ResponseEntity.notFound().build()
        }

        // 최대 1MB 단위로 쪼개진 청크(Chunk) 데이터를 생성하여 반환 (HTTP 206 Partial Content)
        val region = getResourceRegion(video, headers)
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
            .body(region)
    }

    /**
     * [기능 주석] 오디오(BGM) 스트리밍 API.
     * bgm.mp3 파일을 비디오와 동일한 Partial Content 방식으로 스트리밍합니다.
     */
    @GetMapping("/audio")
    fun streamAudio(@RequestHeader headers: HttpHeaders): ResponseEntity<ResourceRegion> {
        val audio = ClassPathResource("static/music/bgm.mp3")
        val region = getResourceRegion(audio, headers)
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaTypeFactory.getMediaType(audio).orElse(MediaType.APPLICATION_OCTET_STREAM))
            .body(region)
    }

    /**
     * [기능 주석] 미디어 리소스(Resource)를 HTTP Range 요청에 맞게 일정 크기(최대 1MB)로 분할(Chunk)하는 내부 유틸리티 로직입니다.
     */
    private fun getResourceRegion(resource: Resource, headers: HttpHeaders): ResourceRegion {
        val contentLength = resource.contentLength()
        val range = headers.range.firstOrNull()
        return if (range != null) {
            val start = range.getRangeStart(contentLength)
            val end = range.getRangeEnd(contentLength)
            val rangeLength = minOf(1024 * 1024L, end - start + 1) // 1MB 제한
            ResourceRegion(resource, start, rangeLength)
        } else {
            val rangeLength = minOf(1024 * 1024L, contentLength)
            ResourceRegion(resource, 0, rangeLength)
        }
    }
}