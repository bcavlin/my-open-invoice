package com.bgh.myopeninvoice.api.controller.spec;

import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.rometools.rome.feed.synd.SyndFeed;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "Statistics Controller")
@RequestMapping(value = "/api/v1")
public interface RSSAPI {

    @GetMapping(value = "/rss", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<DefaultResponse<SyndFeed>> getRSSFeed();

}
