package com.mutualser.developer.application.assembler;

import com.mutualser.developer.domain.AuditField;
import com.mutualser.developer.dto.AuditFieldDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import static java.util.Objects.isNull;

public class AssemblerAuditField {

  public static AuditField dtoToEntity(AuditFieldDTO dto) {
    if (dto == null) dto = AuditFieldDTO.builder().build();
    if (isNull(dto.getLastModifiedDate()) && !isNull(dto.getCreatedDate())) {
      dto.setLastModifiedDate(LocalDate.now());
    }
    if (isNull(dto.getLastModifiedDate())) {
      dto.setCreatedDate(LocalDate.now());
    }
    AuditField auditField = AuditField.builder().build();
    BeanUtils.copyProperties(dto, auditField);
    return auditField;
  }

  public static AuditFieldDTO entityToDto(AuditField entity) {
    if (entity == null) entity = AuditField.builder().build();
    AuditFieldDTO auditFieldDTO = AuditFieldDTO.builder().build();
    BeanUtils.copyProperties(entity, auditFieldDTO);
    return auditFieldDTO;
  }
}
