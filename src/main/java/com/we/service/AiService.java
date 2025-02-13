package com.we.service;

import reactor.core.publisher.Flux;

public interface AiService {
    Flux<String> askAi(String prompt);
}
