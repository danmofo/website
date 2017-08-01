package com.dmoffat.website.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author danielmoffat
 */
@JsonIgnoreProperties("permalink")
public class BasicPost extends Post {
}
