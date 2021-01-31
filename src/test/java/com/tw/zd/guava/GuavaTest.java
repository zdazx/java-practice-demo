package com.tw.zd.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuavaTest {
    @Test
    void should_limit_execution_times_when_using_RateLimiter() {
        RateLimiter rateLimiter = RateLimiter.create(100);

        int startTime = ZonedDateTime.now().getSecond();
        IntStream.range(1, 1000).forEach(i -> {
            rateLimiter.acquire();
            System.out.print(i);
        });
        int elapsedTime = ZonedDateTime.now().getSecond() - startTime;

        assertTrue(elapsedTime >= 10);
    }

    @Test
    void should_get_rate_limiter_resource_without_blocking() {
        RateLimiter rateLimiter = RateLimiter.create(1);

        rateLimiter.acquire();
        boolean result = rateLimiter.tryAcquire(2, 10, TimeUnit.MILLISECONDS);

        assertFalse(result);


    }
}
