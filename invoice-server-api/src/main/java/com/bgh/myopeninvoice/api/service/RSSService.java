package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Service
@CacheConfig(cacheNames = {"rss-feed"})
public class RSSService {

    @Cacheable()
    public SyndFeed getRSSFeed(String url) throws InvalidResultDataException {
        LocalDate d = LocalDate.now().minusMonths(6);

        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed build = input.build(new XmlReader(stream));
                build
                        .getEntries()
                        .removeIf(
                                e ->
                                        e.getUpdatedDate() != null
                                                && e.getUpdatedDate()
                                                .toInstant()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                                .isBefore(d));
                return build;
            }
        } catch (Exception e) {
            throw new InvalidResultDataException(e.getMessage(), e);
        }
    }
}
