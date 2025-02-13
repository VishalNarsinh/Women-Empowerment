package com.we.service.impl;

import com.we.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
public class AiServiceImpl implements AiService {
    private ChatClient client;
    public AiServiceImpl(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @Override
    public Flux<String> askAi(String prompt) {
        return client.prompt(prompt).stream().content();

    }
}
