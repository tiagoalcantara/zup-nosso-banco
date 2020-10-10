package com.tiago.nossobancodigital.modules.account.utils.generators;

import java.util.Random;

public class AccountGenerator {
  public static String generate() {
    Random random = new Random();
    Integer limit = (int) Math.pow(10, 8);
    String randomAgency =  String.format("%08d", random.nextInt(limit));
    return randomAgency;
  }
}
