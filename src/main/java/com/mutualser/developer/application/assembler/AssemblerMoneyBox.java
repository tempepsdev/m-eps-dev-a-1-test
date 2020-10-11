package com.mutualser.developer.application.assembler;

import com.mutualser.developer.domain.MoneyBox;
import com.mutualser.developer.dto.MoneyBoxDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import static java.util.Objects.isNull;

public class AssemblerMoneyBox {
  public static MoneyBox dtoToEntity(MoneyBoxDTO dto) {
    if (!isNull(dto)) {
      MoneyBox moneyBox = new MoneyBox();
      moneyBox.setCoinList(new ArrayList<>());
      BeanUtils.copyProperties(dto, moneyBox);
      moneyBox.setCoinList(AssemblerCoin.listDtoToEntity(dto.getCoinList()));
      return moneyBox;
    }
    return null;
  }

  public static MoneyBoxDTO entityToDto(MoneyBox entity) {
    if (!isNull(entity)) {
      MoneyBoxDTO moneyBoxDTO = MoneyBoxDTO.builder().build();
      moneyBoxDTO.setCoinList(new ArrayList<>());
      BeanUtils.copyProperties(entity, moneyBoxDTO);
      moneyBoxDTO.setCoinList(AssemblerCoin.listEntityToDto(entity.getCoinList()));
      return moneyBoxDTO;
    }
    return null;
  }
}
