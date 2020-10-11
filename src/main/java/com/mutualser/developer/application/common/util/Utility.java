package com.mutualser.developer.application.common.util;

import java.util.UUID;

public class Utility {
  public static String generateRandomId() {
    return UUID.randomUUID().toString();
  }
}
