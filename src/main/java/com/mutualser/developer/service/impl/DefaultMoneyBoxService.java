package com.mutualser.developer.service.impl;

import com.mutualser.developer.application.assembler.AssemblerMoneyBox;
import com.mutualser.developer.domain.MoneyBox;
import com.mutualser.developer.domain.enums.coinType.CoinType;
import com.mutualser.developer.dto.CoinDTO;
import com.mutualser.developer.dto.MoneyBoxDTO;
import com.mutualser.developer.dto.queries.DistinctCoinsInMoneyBoxDTO;
import com.mutualser.developer.dto.queries.DistinctCoinsInMoneyBoxResponseDTO;
import com.mutualser.developer.exceptions.moneybox.MoneyBoxConflictException;
import com.mutualser.developer.infrastructure.persistence.MoneyBoxRepository;
import com.mutualser.developer.service.MoneyBoxService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultMoneyBoxService implements MoneyBoxService {

  private final MoneyBoxRepository moneyBoxRepository;
  private final MessageSource messageSource;

  @Override
  public List<MoneyBoxDTO> findAllMoneyBox() {
    List<MoneyBox> moneyBoxList = moneyBoxRepository.findAll();
    if (!moneyBoxList.isEmpty()) {
      return moneyBoxList.parallelStream()
          .filter(moneyBox -> !isNull(moneyBox))
          .map(AssemblerMoneyBox::entityToDto)
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  @Override
  @Transactional(readOnly = true)
  public MoneyBoxDTO saveMoneyBoxDto(MoneyBoxDTO moneyBoxDTO) {
    if (!isNull(moneyBoxDTO)) {
      try {
        if (isNull(moneyBoxDTO.getCoinList())) {
          moneyBoxDTO.setCoinList(new ArrayList<>());
        }
        moneyBoxDTO.setCoinsCounter(validateNumberOfCoins(moneyBoxDTO.getCoinList()));
        moneyBoxDTO.setTotalMoney(validateTotalMoney(moneyBoxDTO.getCoinList()));
        MoneyBox moneyBox = moneyBoxRepository.save(AssemblerMoneyBox.dtoToEntity(moneyBoxDTO));
        return AssemblerMoneyBox.entityToDto(moneyBox);
      } catch (Exception exception) {
        log.error(exception.getMessage().toUpperCase());
        throw new MoneyBoxConflictException(
            messageSource.getMessage(
                "money-box.message.error.could.not.save", null, Locale.getDefault()));
      }
    }
    return null;
  }

  @Override
  public DistinctCoinsInMoneyBoxResponseDTO distinctCoinsInMoneyBox(
      DistinctCoinsInMoneyBoxDTO distinctCoinsInMoneyBoxDTO) {
    if (!isNull(distinctCoinsInMoneyBoxDTO)
        && !StringUtils.isEmpty(distinctCoinsInMoneyBoxDTO.getMoneyBoxId())) {
      Optional<Optional<MoneyBox>> moneyBox =
          Optional.of(moneyBoxRepository.findById(distinctCoinsInMoneyBoxDTO.getMoneyBoxId()));
      if (moneyBox.get().isPresent()) {
        return validateDistinctCoinsInMoneyBox(
            AssemblerMoneyBox.entityToDto(moneyBox.get().get()),
            distinctCoinsInMoneyBoxDTO.getCoinType());
      } else {
        throw new MoneyBoxConflictException(
            String.format(
                messageSource.getMessage(
                    "money-box.message.error.could.not.find", null, Locale.getDefault()),
                distinctCoinsInMoneyBoxDTO.getMoneyBoxId()));
      }
    }
    return null;
  }

  private DistinctCoinsInMoneyBoxResponseDTO validateDistinctCoinsInMoneyBox(
      final MoneyBoxDTO moneyBoxDTO, final CoinType coinType) {
    if (!isNull(moneyBoxDTO) && !moneyBoxDTO.getCoinList().isEmpty() && !isNull(coinType)) {
      return DistinctCoinsInMoneyBoxResponseDTO.builder()
          .coinType(coinType)
          .coinTypeCounter((int) giveMeCoinTypeCounter(coinType, moneyBoxDTO.getCoinList()))
          .totalMoney(giveMeTotalMoneyByCoinType(coinType, moneyBoxDTO.getCoinList()))
          .build();
    }
    return null;
  }

  private int giveMeTotalMoneyByCoinType(CoinType coinType, List<CoinDTO> coinList) {
    AtomicInteger totalMoney = new AtomicInteger(0);
    if (!isNull(coinList) && !coinList.isEmpty()) {
      coinList.stream()
          .filter(coinDTO -> !isNull(coinDTO))
          .filter(coinDTO -> !coinDTO.getCoinType().equals(CoinType.NOT_APPLY))
          .filter(coinDTO -> coinDTO.getCoinType().equals(coinType))
          .forEach(
              coinDTO ->
                  totalMoney.set(
                      Integer.parseInt(coinDTO.getCoinType().getDescription()) + totalMoney.get()));
    }
    return totalMoney.get();
  }

  private long giveMeCoinTypeCounter(CoinType coinType, List<CoinDTO> coinList) {
    return coinList.stream()
        .filter(coinDTO -> !isNull(coinDTO))
        .filter(coinDTO -> coinDTO.getCoinType().equals(coinType))
        .count();
  }

  private int validateTotalMoney(final List<CoinDTO> coinList) {
    AtomicInteger totalMoney = new AtomicInteger(0);
    if (!isNull(coinList) && !coinList.isEmpty()) {
      coinList.parallelStream()
          .filter(coinDTO -> !isNull(coinDTO))
          .filter(coinDTO -> !coinDTO.getCoinType().getId().equals(CoinType.NOT_APPLY_CODE))
          .forEach(
              coinDTO ->
                  totalMoney.set(
                      Integer.parseInt(coinDTO.getCoinType().getDescription()) + totalMoney.get()));
    }
    return totalMoney.get();
  }

  private int validateNumberOfCoins(final List<CoinDTO> coinList) {
    if (!isNull(coinList) && !coinList.isEmpty()) {
      return coinList.size();
    }
    return 0;
  }
}
