package com.lms.controller;

import com.lms.service.AiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {

    private static final Logger log = LoggerFactory.getLogger(AIController.class);
    private final AiService aiService;

    public AIController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping(value = "/prompt", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> askAi(@RequestParam(name = "query", defaultValue = "How are you, How can you help me ?", required = false ) String prompt) {
        return aiService.askAi(prompt);
    }
}
