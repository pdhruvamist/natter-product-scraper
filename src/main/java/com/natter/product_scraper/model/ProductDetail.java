package com.natter.product_scraper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDetail(String name, String description, Float price, @JsonIgnoreProperties(ignoreUnknown = true) Set<String> colors) {
}