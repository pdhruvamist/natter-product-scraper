package com.natter.product_scraper.controller;

import com.natter.product_scraper.model.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldRespondWithProductResponseWhenGetAllProductsRequested() {
        ResponseEntity<ProductResponse> response = this.testRestTemplate
                .exchange("/v1/products/all", GET, null, ProductResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

}