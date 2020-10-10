package com.tiago.nossobancodigital.modules.account.utils.generators;

import java.util.Random;

public class AgencyGenerator{
  public static String generate() {
    Random random = new Random();
    Integer limit = (int) Math.pow(10, 4);
    String randomAgency =  String.format("%04d", random.nextInt(limit));
    return randomAgency;
  }
}
