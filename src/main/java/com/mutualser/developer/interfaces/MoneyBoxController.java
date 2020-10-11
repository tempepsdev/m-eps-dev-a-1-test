package com.mutualser.developer.interfaces;

import com.mutualser.developer.dto.MoneyBoxDTO;
import com.mutualser.developer.dto.queries.DistinctCoinsInMoneyBoxDTO;
import com.mutualser.developer.dto.queries.DistinctCoinsInMoneyBoxResponseDTO;
import com.mutualser.developer.service.MoneyBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/money-box")
@Slf4j
public class MoneyBoxController {
  @Autowired private MoneyBoxService moneyBoxService;

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MoneyBoxDTO> listAllMoneyBox() {
    List<MoneyBoxDTO> moneyBoxDTOS = moneyBoxService.findAllMoneyBox();
    if (!moneyBoxDTOS.isEmpty()) return new ResponseEntity(moneyBoxDTOS, HttpStatus.OK);
    return new ResponseEntity(null, HttpStatus.NO_CONTENT);
  }

  @PostMapping(
      value = "/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MoneyBoxDTO> saveMoneyBox(@RequestBody MoneyBoxDTO moneyBoxDTO) {
    MoneyBoxDTO response = moneyBoxService.saveMoneyBoxDto(moneyBoxDTO);
    if (!isNull(response)) {
      return new ResponseEntity(response, HttpStatus.OK);
    }
    return new ResponseEntity(null, HttpStatus.NO_CONTENT);
  }

  @PostMapping(
      value = "/distinct/coins",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DistinctCoinsInMoneyBoxResponseDTO> findDistinctCoinsInMoneyBoxResponseDTO(
      @RequestBody DistinctCoinsInMoneyBoxDTO distinctCoinsInMoneyBoxDTO) {
    DistinctCoinsInMoneyBoxResponseDTO response =
        moneyBoxService.distinctCoinsInMoneyBox(distinctCoinsInMoneyBoxDTO);
    if (!isNull(response)) {
      return new ResponseEntity(response, HttpStatus.OK);
    }
    return new ResponseEntity(null, HttpStatus.NO_CONTENT);
  }
}
