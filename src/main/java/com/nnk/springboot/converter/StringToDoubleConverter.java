package com.nnk.springboot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {
  @Override
  public Double convert(String source) {
    if (source == null) {
      return null;
    }
    try {
      return Double.parseDouble(source.replace("\"", "").trim());
    } catch (NumberFormatException e) {
      return null;  // Optionally log this exception or handle it as needed
    }
  }
}