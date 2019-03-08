package com.bgh.myopeninvoice.api.controller;

import com.bgh.myopeninvoice.api.controller.spec.StatisticsAPI;
import com.bgh.myopeninvoice.api.service.StatisticsService;
import com.bgh.myopeninvoice.common.domain.DefaultResponse;
import com.bgh.myopeninvoice.common.domain.OperationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StatisticsController extends AbstractController implements StatisticsAPI {

  @Autowired private StatisticsService statisticsService;

  @Override
  public ResponseEntity<DefaultResponse<Map>> allStatistics() {
    List<Map> result = new ArrayList<>();
    long count;

    result.add(statisticsService.getTotalForCurrentYear());
    result.add(statisticsService.getTotalForLastYear());
    result.add(statisticsService.getMaxForLast12Mths());
    result.add(statisticsService.getMinForLast12Mths());

    DefaultResponse<Map> defaultResponse = new DefaultResponse<>(Map.class);
    defaultResponse.setDetails(result);
    defaultResponse.setStatus(OperationResponse.OperationResponseStatus.SUCCESS);
    return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
  }
}
