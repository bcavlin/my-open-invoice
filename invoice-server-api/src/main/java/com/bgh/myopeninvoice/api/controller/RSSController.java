package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.RSSAPI;
import com.bgh.myopeninvoice.api.service.RSSService;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class RSSController extends AbstractController implements RSSAPI {

  @Autowired private RSSService rssService;

  @Value("${rss.links}")
  private String[] urls;


  @Override
  public ResponseEntity<DefaultResponse<SyndFeed>> getRSSFeed() {
    List<SyndFeed> result = new ArrayList<>();

    for (String s : urls) {
      result.add(rssService.getRSSFeed(s));
    }

    DefaultResponse<SyndFeed> defaultResponse = new DefaultResponse<>(SyndFeed.class);
    defaultResponse.setCount((long) result.size());
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
