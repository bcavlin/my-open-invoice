package com.bgh.myopeninvoice.api.service;

import com.bgh.myopeninvoice.common.exception.InvalidResultDataException;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class RSSService {

  private String bsnsss =
      "https://www.canada.ca/content/dam/cra-arc/migration/cra-arc/esrvc-srvce/rss/bsnsss-eng.xml";
 private String mdrm =
      "https://www.canada.ca/content/dam/cra-arc/migration/cra-arc/esrvc-srvce/rss/mdrm-eng.xml";

  @Cacheable("bsnsss-feed")
  public SyndFeed getBSNSSSFeed() throws InvalidResultDataException {
      try (CloseableHttpClient client = HttpClients.createMinimal()) {
          HttpUriRequest request = new HttpGet(bsnsss);
          try (CloseableHttpResponse response = client.execute(request);
               InputStream stream = response.getEntity().getContent()) {
              SyndFeedInput input = new SyndFeedInput();
              return input.build(new XmlReader(stream));
          } catch (FeedException e) {
              log.error(e.getMessage(), e);
          }
      } catch (IOException e) {
          log.error(e.getMessage(), e);
      }
      throw new InvalidResultDataException();
  }

    @Cacheable("mdrm-feed")
    public SyndFeed getMDRMFeed() throws InvalidResultDataException {
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(bsnsss);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                return input.build(new XmlReader(stream));
            } catch (FeedException e) {
                log.error(e.getMessage(), e);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        throw new InvalidResultDataException();
    }
}
