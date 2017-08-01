package com.dmoffat.website.model;

/**
 * @author danielmoffat
 */
public class Views {
    // This view only displays basic information about an entity and never includes mapped collections.
    public interface Basic {}

    // This view displays slightly more information, but still no mapped collections
    public interface Summary extends Basic {}

    // This returns almost every property from entities.
    public interface Detailed extends Summary {}
}
