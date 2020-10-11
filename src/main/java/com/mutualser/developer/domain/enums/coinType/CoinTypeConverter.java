package com.mutualser.developer.domain.enums.coinType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import static java.util.Objects.isNull;

@Converter
public class CoinTypeConverter implements AttributeConverter<CoinType, String> {
  @Override
  public String convertToDatabaseColumn(CoinType coinType) {
    if (!isNull(coinType)) {
      return coinType.getDescription();
    }
    return CoinType.NOT_APPLY.getDescription();
  }

  @Override
  public CoinType convertToEntityAttribute(String s) {
    return CoinType.findByPrimaryKey(s);
  }
}
