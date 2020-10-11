package com.mutualser.developer.exceptions.data;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ErrorResponse implements Serializable {
  private String id;
  private String message;
}
