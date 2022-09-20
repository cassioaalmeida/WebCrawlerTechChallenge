package com.axreng.backend.service.impl;

import com.axreng.backend.model.Crawler;
import com.axreng.backend.model.CrawlerParameters;
import com.axreng.backend.service.CrawlService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlServiceImpl implements CrawlService {
    String[] keyWordsToIgnore = new String[]{
            "mailto",
            "ftp",
            "css",
            "png",
            "tiff",
            "jpg",
            "gif",
            "js"
    };

    @Override
    public String CrawlTroughUrl(CrawlerParameters parameters) throws MalformedURLException {
        ValidateParameters(parameters);
        Crawler crawler = new Crawler();
        List<String> result = new ArrayList<>();
        String urlPattern = "href=\\\"(?!(https|www|http))(.*?)\\\"";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.DOTALL);
        String printString = "Search starting with base URL '" + parameters.getBaseUrl() + "' and keyword '" + parameters.getKeyword() + "'";
        System.out.println(printString);
        result.add(printString);

        crawler.getUrlQueue().add(parameters.getBaseUrl());

        while (!crawler.getUrlQueue().isEmpty()) {
            InputStream stream = null;
            BufferedReader buffer;
            String line;
            String urlString = crawler.getUrlQueue().remove();
            try {
                URL url = new URL(urlString);
                stream = url.openStream();
                buffer = new BufferedReader(new InputStreamReader(stream));
                StringBuilder rawHtml = new StringBuilder();

                while ((line = buffer.readLine()) != null) {
                    rawHtml.append(line);
                }
                Matcher matcher = pattern.matcher(rawHtml.toString());

                List<String> matches = GetUniqueMatchesFromString(matcher);

                for (String item : matches) {
                    item = item.replace("href=\"", "").replace("../", "").replace("\"", "");
                    String finalItem = parameters.getBaseUrl() + item;
                    if (!crawler.getVisitedURLs().contains(finalItem) && Arrays.stream(keyWordsToIgnore).noneMatch(finalItem::contains)) {
                        crawler.getVisitedURLs().add(finalItem);
                        crawler.getUrlQueue().add(finalItem);
                    }
                }
                if (rawHtml.toString().toLowerCase().contains(parameters.getKeyword().toLowerCase())) {
                    printString = "Result found: " + urlString;
                    System.out.println(printString);
                    result.add(printString);

                    if (parameters.getMaxResults() > 0 && (result.size() - 1) == parameters.getMaxResults()) {
                        break;
                    }
                }
            } catch (Exception exception) {
               // System.out.println(exception);
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (Exception exception) {
                    return exception.toString();
                }
            }
        }
        printString = "Search finished with " + (result.size() - 1) + " results found";
        System.out.println(printString);
        result.add(printString);
        return result.toString();
    }

    private List<String> GetUniqueMatchesFromString(Matcher matcher) {
        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            String actualURL = matcher.group();
            matches.add(actualURL);
        }

        return new ArrayList<>(new HashSet<>(matches));
    }

    private void ValidateParameters(CrawlerParameters parameters) throws MalformedURLException {
        try {
            URL url = new URL(parameters.getBaseUrl());

            if (parameters.getKeyword().length() < 4 || parameters.getKeyword().length() > 32) {
                throw new Exception("Keyword must have between 4 and 32 characters");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Base URL");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
