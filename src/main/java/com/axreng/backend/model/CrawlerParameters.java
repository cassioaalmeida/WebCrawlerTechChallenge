package com.axreng.backend.model;

public class CrawlerParameters {
    private final String baseUrl;
    private final String keyword;
    private Integer maxResults;

    public CrawlerParameters(String baseUrl, String keyword, String maxResults) {
        //this.baseUrl = baseUrl != null ? baseUrl : "http://hiring.axreng.com/";
        //this.keyword = keyword != null ? baseUrl : "four";
        System.out.println(baseUrl);
        this.baseUrl = baseUrl;
        this.keyword = keyword;

        try {
            this.maxResults = Integer.parseInt(maxResults);
        } catch (NumberFormatException e) {
            this.maxResults = -1;
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    @Override
    public String toString() {
        return "CrawlParameters{" +
                "baseUrl='" + baseUrl + '\'' +
                ", keyword='" + keyword + '\'' +
                ", maxResults=" + maxResults +
                '}';
    }
}
