package com.mutualser.developer.service;

import com.mutualser.developer.domain.enums.coinType.CoinType;
import com.mutualser.developer.dto.AuditFieldDTO;
import com.mutualser.developer.dto.CoinDTO;
import com.mutualser.developer.dto.MoneyBoxDTO;
import com.mutualser.developer.dto.queries.DistinctCoinsInMoneyBoxDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class MoneyBoxServiceTest {
  @Autowired private MoneyBoxService moneyBoxService;
  MoneyBoxDTO moneyBoxDTO;
  CoinDTO coinDTO;
  DistinctCoinsInMoneyBoxDTO distinctCoinsInMoneyBoxDTO;

  public void setUp() {
    moneyBoxDTO = MoneyBoxDTO.builder().coinList(new ArrayList<>()).build();
    coinDTO =
        CoinDTO.builder()
            .auditField(AuditFieldDTO.builder().build())
            .coinType(CoinType.COIN_100)
            .build();
    distinctCoinsInMoneyBoxDTO =
        DistinctCoinsInMoneyBoxDTO.builder().coinType(CoinType.COIN_100).build();
  }

  @Test
  public void saveMoneyBoxDtoTest() {
    this.setUp();
    moneyBoxDTO.getCoinList().add(coinDTO);
    MoneyBoxDTO response = moneyBoxService.saveMoneyBoxDto(moneyBoxDTO);
    Assertions.assertNotNull(response);
  }

  @Test
  public void findAllMoneyBoxTest() {
    this.setUp();
    Assert.assertNotNull(moneyBoxService.findAllMoneyBox());
  }

  @Test
  public void distinctCoinsInMoneyBox() {
    this.setUp();
    moneyBoxDTO.getCoinList().add(coinDTO);
    MoneyBoxDTO response = moneyBoxService.saveMoneyBoxDto(moneyBoxDTO);
    distinctCoinsInMoneyBoxDTO.setMoneyBoxId(response.getId());
    Assert.assertNotNull(moneyBoxService.distinctCoinsInMoneyBox(distinctCoinsInMoneyBoxDTO));
  }
}
