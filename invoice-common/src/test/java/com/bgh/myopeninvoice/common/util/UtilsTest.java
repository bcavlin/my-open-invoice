package com.bgh.myopeninvoice.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class UtilsTest {

  @Test
  public void testFromDateAdjusted() {
    int weekStart = 6;

    LocalDate from = LocalDate.parse("2019-02-01");
    LocalDate adjusted = Utils.getFromDateAdjusted(weekStart, from);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-01-26")));

    from = LocalDate.parse("2019-03-01");
    adjusted = Utils.getFromDateAdjusted(weekStart, from);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-02-23")));

    from = LocalDate.parse("2019-04-01");
    adjusted = Utils.getFromDateAdjusted(weekStart, from);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-03-30")));

    from = LocalDate.parse("2019-05-01");
    adjusted = Utils.getFromDateAdjusted(weekStart, from);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-04-27")));

    from = LocalDate.parse("2019-11-01");
    adjusted = Utils.getFromDateAdjusted(weekStart, from);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-10-26")));
  }

  @Test
  public void testToDateAdjusted() {
    int weekStart = 6;

    LocalDate to = LocalDate.parse("2019-02-28");
    LocalDate adjusted = Utils.getToDateAdjusted(weekStart, to);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-03-01")));

    to = LocalDate.parse("2019-03-31");
    adjusted = Utils.getToDateAdjusted(weekStart, to);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-04-05")));

    to = LocalDate.parse("2019-06-30");
    adjusted = Utils.getToDateAdjusted(weekStart, to);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-07-05")));

    to = LocalDate.parse("2019-11-30");
    adjusted = Utils.getToDateAdjusted(weekStart, to);
    Assert.assertTrue(adjusted.isEqual(LocalDate.parse("2019-12-06")));
  }

}
