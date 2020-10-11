package com.mutualser.developer.application.assembler;

import com.mutualser.developer.domain.AuditField;
import com.mutualser.developer.domain.Coin;
import com.mutualser.developer.dto.AuditFieldDTO;
import com.mutualser.developer.dto.CoinDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.stream.Collectors;

public class AssemblerCoin {
  public static Coin dtoToEntity(CoinDTO coinDTO) {
    if (!isNull(coinDTO)) {
      Coin coin = Coin.builder().auditField(new AuditField()).build();
      BeanUtils.copyProperties(coinDTO, coin);
      coin.setAuditField(AssemblerAuditField.dtoToEntity(coinDTO.getAuditField()));
      return coin;
    }
    return null;
  }

  public static CoinDTO entityToDto(Coin coin) {
    if (!isNull(coin)) {
      CoinDTO coinDTO = CoinDTO.builder().auditField(AuditFieldDTO.builder().build()).build();
      BeanUtils.copyProperties(coin, coinDTO);
      coinDTO.setAuditField(AssemblerAuditField.entityToDto(coin.getAuditField()));
      return coinDTO;
    }
    return null;
  }

  public static List<Coin> listDtoToEntity(List<CoinDTO> coinDTOS) {
    if (!coinDTOS.isEmpty()) {
      return coinDTOS.parallelStream()
          .filter(coinDTO -> !isNull(coinDTO))
          .map(AssemblerCoin::dtoToEntity)
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  public static List<CoinDTO> listEntityToDto(List<Coin> coins) {
    if (!coins.isEmpty()) {
      return coins.parallelStream().map(AssemblerCoin::entityToDto).collect(Collectors.toList());
    }
    return new ArrayList<>();
  }
}
