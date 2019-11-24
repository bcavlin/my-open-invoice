package com.bgh.myopeninvoice.db.domain;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountDataEntityTest {

  @Test
  public void comparisonHash() {

    AccountDataEntity entity = new AccountDataEntity();
    entity.setAccountId(1);
    entity.setItemDate(LocalDate.now());
    entity.setDescription("test2");
    entity.setCredit(BigDecimal.valueOf(123));
    entity.setDebit(BigDecimal.valueOf(456));

    AccountDataEntity entity2 = new AccountDataEntity();
    entity2.setAccountId(1);
    entity2.setItemDate(LocalDate.now());
    entity2.setDescription("test2");
    entity2.setCredit(BigDecimal.valueOf(123));
    entity2.setDebit(BigDecimal.valueOf(456));

    Assert.assertEquals(entity.hash(), entity2.hash());
  }

}