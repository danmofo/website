package com.dmoffat.website.util.time;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author dan
 */
@Component
public class TimeProviderImpl implements TimeProvider {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDateTime tomorrow() {
        return now().plusDays(1);
    }

    @Override
    public LocalDateTime yesterday() {
        return now().minusDays(1);
    }
}
