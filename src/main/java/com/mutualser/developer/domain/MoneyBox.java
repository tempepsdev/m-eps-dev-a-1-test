package com.mutualser.developer.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyBox implements Serializable {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private List<Coin> coinList;

  private int coinsCounter;

  private int totalMoney;
}
