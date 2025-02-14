package com.natter.product_scraper.util;

import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public final class HtmlExtractorUtils {

    public static Set<String> extractProductUrlsFrom(Document mainWebPage) {
        return mainWebPage.select("a[href*=./product]").parallelStream()
                .map(element -> element.attr("href"))
                .map(relativeUri -> relativeUri.substring(2))
                .collect(Collectors.toSet());

    }

    public String extractProductDescriptionFrom(Element productElement) {
        return productElement.select("p[class=\"description card-text\"]").text();
    }

    public Set<String> extractProductColorsFrom(Element productElement) {
        return productElement.select("option[class=\"dropdown-item\"]").parallelStream()
                .map(Element::val)
                .filter(val -> !val.isBlank())
                .collect(Collectors.toSet());
    }

    public static Set<Element> getProductContentsFrom(Document productWebPage) {
        Elements captionDivs = productWebPage.select("div[class=\"caption\"]");

        return new HashSet<>(captionDivs);
    }

    public Map<String, Float> extractProductPriceByMemoryFrom(Element productElement) {
        Element scriptElement = productElement.ownerDocument().selectFirst("script");
        if (!Objects.nonNull(scriptElement)) {

            return Map.of();
        }
        String scriptBlock = scriptElement.data();
        Pattern pattern = Pattern.compile("\\{value: \"([0-9]+)\", price: ([0-9]+.[0-9]+)\\}");
        Matcher matcher = pattern.matcher(scriptBlock);
        Map<String, Float> priceByMemory = new HashMap<>();
        while (matcher.find()) {
            priceByMemory.put(matcher.group(1), Float.valueOf(matcher.group(2)));
        }

        return priceByMemory;
    }

    public static String extractProductTitleFrom(Element productElement) {
        return productElement.select("h4[class=\"title card-title\"]").text();
    }

    public static Float extractProductDefaultPriceFrom(Element productElement) {
        String defaultPriceText = productElement.select("h4[class=\"price float-end pull-right\"]").text()
                .substring(1);

        return Float.valueOf(defaultPriceText);
    }

}
