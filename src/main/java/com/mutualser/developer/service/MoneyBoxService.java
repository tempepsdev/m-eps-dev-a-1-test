package com.mutualser.developer.service;

import com.mutualser.developer.dto.MoneyBoxDTO;
import com.mutualser.developer.dto.queries.DistinctCoinsInMoneyBoxDTO;
import com.mutualser.developer.dto.queries.DistinctCoinsInMoneyBoxResponseDTO;

import java.util.List;

public interface MoneyBoxService {
  List<MoneyBoxDTO> findAllMoneyBox();

  MoneyBoxDTO saveMoneyBoxDto(MoneyBoxDTO moneyBoxDTO);

  DistinctCoinsInMoneyBoxResponseDTO distinctCoinsInMoneyBox(DistinctCoinsInMoneyBoxDTO distinctCoinsInMoneyBoxDTO);
}
