package com.yukibuwana.webfluxstockquoteservice.service;

import com.yukibuwana.webfluxstockquoteservice.model.Quote;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class QuoteGeneratorServiceImplTest {
    QuoteGeneratorServiceImpl quoteGeneratorService = new QuoteGeneratorServiceImpl();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void fetchQuoteStream() throws Exception {

        // get quoteFlux of quotes
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        quoteFlux.take(10)
                .subscribe(System.out::println);
    }

    @Test
    public void fetchQuoteStreamCountDown() throws Exception {

        // get quoteFlux of quotes
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        // subscriber lambda
        Consumer<Quote> println = System.out::println;

        // error handle
        Consumer<Throwable> errorHandle = e -> System.out.println("Some Error Occurred");

        // set Countdown latch to 1
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // runnable called upon complate, count down latch
        Runnable allDone = () -> countDownLatch.countDown();

        quoteFlux.take(5)
                .subscribe(println, errorHandle, allDone);

        countDownLatch.await();
    }
}
