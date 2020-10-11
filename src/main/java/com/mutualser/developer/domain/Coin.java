package com.mutualser.developer.domain;

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
public class Coin implements Serializable {
  @Convert(converter = CoinTypeConverter.class)
  private CoinType coinType;

  private AuditField auditField;
}
