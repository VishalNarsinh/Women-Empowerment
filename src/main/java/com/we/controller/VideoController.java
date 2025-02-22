package com.we.controller;

import com.we.dto.VideoDto;
import com.we.model.Video;
import com.we.service.VideoService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "lessonId",defaultValue = "0",required = false) long lessonId
            ) {

        VideoDto videoDto = videoService.saveVideo(file, lessonId);
        
        return ResponseEntity.ok(videoDto);
    }

    @GetMapping("/stream/{videoId}")
    public ResponseEntity<?> getVideo(@PathVariable("videoId") long videoId) {
        VideoDto videoDto = videoService.getVideoByVideoId(videoId);
        String videoUrl = videoDto.getVideoUrl();
        String contentType = videoDto.getContentType();
        if(contentType==null || contentType.isEmpty())
            contentType = "application/octet-stream";
        Resource resource = new FileSystemResource(videoUrl);
        return ResponseEntity.
                ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    }

    @GetMapping("/stream/range/{videoId}")
    public ResponseEntity<?> getVideoRange(@PathVariable("videoId") long videoId,
                                           @RequestHeader(name = "range", required = false) String range) throws IOException {
        if(range==null || range.isEmpty()){
            return getVideo(videoId);
        }
        VideoDto videoDto = videoService.getVideoByVideoId(videoId);
        Path path = Paths.get(videoDto.getVideoUrl());
        Resource resource = new FileSystemResource(videoDto.getVideoUrl());
        String contentType = videoDto.getContentType();
        if(contentType==null || contentType.isEmpty()){
            contentType = "application/octet-stream";
        }

        long fileLength = path.toFile().length();
        String[] ranges = range.replace("bytes=", "").split("-");
        long rangeStart = Long.parseLong(ranges[0]);
        long rangeEnd = (ranges.length > 1) ? Long.parseLong(ranges[1]) : fileLength - 1;

        if (rangeEnd > fileLength - 1) {
            rangeEnd = fileLength - 1;
        }
        long contentLength = rangeEnd - rangeStart + 1;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Range","bytes="+rangeStart+"-"+rangeEnd+"/"+fileLength);
        httpHeaders.add("Cache-Control","no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma","no-cache");
        httpHeaders.add("Expires","0");
        httpHeaders.add("X-Content-Type-Options","nosniff");
        httpHeaders.setContentLength(contentLength);
        InputStream inputStream = Files.newInputStream(path);
        inputStream.skip(rangeStart);
        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType(contentType))
                .body(new InputStreamResource(inputStream));
    }
}
