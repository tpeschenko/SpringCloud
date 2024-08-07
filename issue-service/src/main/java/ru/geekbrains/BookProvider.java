package ru.geekbrains;

import com.netflix.discovery.EurekaClient;
import org.bouncycastle.pqc.crypto.util.PQCOtherInfoGenerator;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class BookProvider {

    private final WebClient webClient;

    public BookProvider(EurekaClient eurekaClient,
                        ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

    public UUID getRandomBookId() {
        BookDto randomBook = webClient.get()
                .uri("http://book-service/book/random")
                .retrieve()
                .bodyToMono(BookDto.class)
                .block();
        return randomBook.getId();
    }

    public BookDto getRandomBookDto() {
        return webClient.get()
                .uri("http://book-service/book/random")
                .retrieve()
                .bodyToMono(BookDto.class)
                .block();
    }
}

