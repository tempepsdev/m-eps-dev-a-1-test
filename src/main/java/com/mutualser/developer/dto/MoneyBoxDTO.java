package com.mutualser.developer.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyBoxDTO implements Serializable {
  private String id;
  private List<CoinDTO> coinList;
  private int coinsCounter;
  private int totalMoney;
}
