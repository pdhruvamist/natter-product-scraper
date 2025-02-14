package com.natter.product_scraper.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.natter.product_scraper.model.ProductDetail;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductResponse(@JsonProperty("results") Set<ProductDetail> productDetails) {
}

