package com.mutualser.developer.domain.enums.coinType;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;

@Getter
public enum CoinType {
  COIN_50(CoinType.COIN_50_CODE, "50"),
  COIN_100(CoinType.COIN_100_CODE, "100"),
  COIN_200(CoinType.COIN_200_CODE, "200"),
  COIN_500(CoinType.COIN_500_CODE, "500"),
  COIN_1000(CoinType.COIN_1000_CODE, "1000"),
  NOT_APPLY(CoinType.NOT_APPLY_CODE, "NO APLICA");

  public static final String COIN_50_CODE = "50";
  public static final String COIN_100_CODE = "100";
  public static final String COIN_200_CODE = "200";
  public static final String COIN_500_CODE = "500";
  public static final String COIN_1000_CODE = "1000";
  public static final String NOT_APPLY_CODE = "99";

  private static final HashMap<String, CoinType> ENUM_MAP_BY_CODE = new HashMap<>();

  @JsonValue private final String id;
  private final String description;

  static {
    ENUM_MAP_BY_CODE.put(COIN_50_CODE, COIN_50);
    ENUM_MAP_BY_CODE.put(COIN_100_CODE, COIN_100);
    ENUM_MAP_BY_CODE.put(COIN_200_CODE, COIN_200);
    ENUM_MAP_BY_CODE.put(COIN_500_CODE, COIN_500);
    ENUM_MAP_BY_CODE.put(COIN_1000_CODE, COIN_1000);
    ENUM_MAP_BY_CODE.put(NOT_APPLY_CODE, NOT_APPLY);
  }

  CoinType(String id, String description) {
    this.id = id;
    this.description = description;
  }

  public static CoinType findByPrimaryKey(String id) {
    if (id != null && ENUM_MAP_BY_CODE.containsKey(id)) {
      return ENUM_MAP_BY_CODE.get(id);
    } else {
      return NOT_APPLY;
    }
  }

  public static Collection<CoinType> getList() {
    return ENUM_MAP_BY_CODE.values();
  }
}
