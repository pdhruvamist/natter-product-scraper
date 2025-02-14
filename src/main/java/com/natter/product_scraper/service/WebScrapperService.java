package com.natter.product_scraper.service;

import com.natter.product_scraper.config.ApplicationConfiguration;
import com.natter.product_scraper.model.ProductDetail;
import com.natter.product_scraper.model.response.ProductResponse;
import com.natter.product_scraper.util.HtmlExtractorUtils;
import com.natter.product_scraper.util.HtmlWebPageDownloaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WebScrapperService {

    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public WebScrapperService(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }


    public ProductResponse fetchAllProductsFromThirdPartyWebService() {
        String seedUrl = applicationConfiguration.getSeedUrl();
        Document mainWebPage = HtmlWebPageDownloaderUtils.getHtmlDocumentsFrom(seedUrl);
        Set<String> productUrns = HtmlExtractorUtils.extractProductUrlsFrom(mainWebPage);
        Set<Element> productContents = getProductContentsFrom(productUrns, seedUrl);
        Set<ProductDetail> productDetails = prepareProductDetailsFrom(productContents);
        log.debug("Fetched all product details");

        return new ProductResponse(productDetails);
    }

    private Set<ProductDetail> prepareProductDetailsFrom(Set<Element> productContents) {
        return productContents.parallelStream()
                .map(transformProductDetailsFromProductContent)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private Set<Element> getProductContentsFrom(Set<String> productUrns, String seedUrl) {
        return productUrns.parallelStream()
                .map(productUrl -> HtmlWebPageDownloaderUtils.getHtmlDocumentsFrom(seedUrl + productUrl))
                .map(HtmlExtractorUtils::getProductContentsFrom)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private final Function<Element, Set<ProductDetail>> transformProductDetailsFromProductContent = (productElement) -> {
        String title = HtmlExtractorUtils.extractProductTitleFrom(productElement);
        Float defaultPrice = HtmlExtractorUtils.extractProductDefaultPriceFrom(productElement);
        String description = HtmlExtractorUtils.extractProductDescriptionFrom(productElement);
        Set<String> colors = HtmlExtractorUtils.extractProductColorsFrom(productElement);
        Map<String, Float> priceByMemory = HtmlExtractorUtils.extractProductPriceByMemoryFrom(productElement);

        if (!priceByMemory.isEmpty()) {

            return priceByMemory.entrySet().parallelStream()
                    .map(e -> {
                        String name = String.join(" ", title, e.getKey(), "GB");
                        return new ProductDetail(name, description, e.getValue(), null);
                    })
                    .collect(Collectors.toSet());
        }

        return Set.of(new ProductDetail(title, description, defaultPrice, colors));
    };
}
