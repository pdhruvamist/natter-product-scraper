package com.natter.product_scraper.service;

import com.natter.product_scraper.config.ApplicationConfiguration;
import com.natter.product_scraper.model.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@TestPropertySource(properties = {"seedUrl=https://develop.dkaylp6d3ryw1.amplifyapp.com/"})
@ExtendWith({MockitoExtension.class, SpringExtension.class})
class WebScrapperServiceTest {

    @Mock
    private ApplicationConfiguration applicationConfigs;

    @InjectMocks
    private WebScrapperService webScrapperService;

    @Test
    void shouldReturnProductResponseFetchedFromConfiguredSeedUrl() {
        //given
        //when
        ProductResponse actualProductResponse = webScrapperService.fetchAllProductsFromThirdPartyWebService();
        //then
        assertThat(actualProductResponse).isNotNull();
        assertThat(actualProductResponse.productDetails()).isNotEmpty();
    }

    //TODO: extend this class with unhappy scenario around URLMAlformed & IOException etc
}