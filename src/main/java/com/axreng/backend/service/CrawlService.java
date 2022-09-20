package com.axreng.backend.service;

import com.axreng.backend.model.CrawlerParameters;

import java.io.IOException;

public interface CrawlService {
    String CrawlTroughUrl(CrawlerParameters parameters) throws IOException;
}
