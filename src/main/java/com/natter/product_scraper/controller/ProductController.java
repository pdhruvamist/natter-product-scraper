package com.natter.product_scraper.controller;

import com.natter.product_scraper.model.response.ProductResponse;
import com.natter.product_scraper.service.WebScrapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/v1/products")
public class ProductController {

    private final WebScrapperService webScrapperService;

    @Autowired
    public ProductController(WebScrapperService webScrapperService) {
        this.webScrapperService = webScrapperService;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getAllProductsFromThirdpartyWebService() {
        ProductResponse productResponse = webScrapperService.fetchAllProductsFromThirdPartyWebService();

        return ResponseEntity.ok(productResponse);
    }
}
