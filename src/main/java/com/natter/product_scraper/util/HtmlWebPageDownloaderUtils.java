package com.natter.product_scraper.util;

import com.natter.product_scraper.exception.ProductScrapperException;
import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;

@UtilityClass
public final class HtmlWebPageDownloaderUtils {

    public static Document getHtmlDocumentsFrom(String seedUrl) {
        try {
            return Jsoup
                    .connect(seedUrl)
                    .get();
        } catch (MalformedURLException urlException) {
            throw new ProductScrapperException("Supplied invalid seed URL", urlException);
        } catch (IOException e) {
            throw new ProductScrapperException("Unable to receive response from upstream system", e);
        } catch (Exception ex) {
            throw new ProductScrapperException("Something went wrong accessing seed URL", ex);
        }
    }

}
