package com.lms.service;

import reactor.core.publisher.Flux;

public interface AiService {
    Flux<String> askAi(String prompt);
}
