package com.dmoffat.website.util.time;

import java.time.LocalDateTime;

/**
 * Interface used to abstract the concept of time in the application.
 *
 * By providing an implementation of this class which returns fixed LocalDateTime objects, we can test time-based things
 * without needing to update tests as time goes on.
 *
 * @author dan
 */
public interface TimeProvider {
    LocalDateTime now();
    LocalDateTime tomorrow();
    LocalDateTime yesterday();
    LocalDateTime lastMonth();
}
