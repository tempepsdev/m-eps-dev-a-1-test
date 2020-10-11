package com.mutualser.developer.dto.queries;

import com.mutualser.developer.domain.enums.coinType.CoinType;
import com.mutualser.developer.domain.enums.coinType.CoinTypeConverter;
import lombok.*;

import javax.persistence.Convert;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistinctCoinsInMoneyBoxDTO implements Serializable {
  private String moneyBoxId;

  @Convert(converter = CoinTypeConverter.class)
  private CoinType coinType;
}
