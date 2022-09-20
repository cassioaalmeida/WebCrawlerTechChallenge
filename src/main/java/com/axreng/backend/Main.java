package com.axreng.backend;

import com.axreng.backend.model.CrawlerParameters;
import com.axreng.backend.service.CrawlService;
import com.axreng.backend.service.impl.CrawlServiceImpl;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        try {
            CrawlService crawlService = new CrawlServiceImpl();
            crawlService.CrawlTroughUrl(new CrawlerParameters(System.getenv("BASE_URL"),
                    System.getenv("KEYWORD"),
                    System.getenv("MAX_RESULTS")));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        /*get("/crawl", (req, res) -> {
            CrawlService crawlService = new CrawlServiceImpl();
            return crawlService.CrawlTroughUrl(new CrawlerParameters(System.getProperty("BASE_URL"),
                    System.getProperty("KEYWORD"),
                    System.getProperty("MAX_RESULTS")));
        });*/
    }
}
