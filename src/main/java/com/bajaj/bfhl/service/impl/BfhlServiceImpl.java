package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.config.AppConfig;
import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class BfhlServiceImpl implements BfhlService {

    private static final Logger log = LoggerFactory.getLogger(BfhlServiceImpl.class);

    private final AppConfig appConfig;

    public BfhlServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public BfhlResponse process(BfhlRequest request) {
        List<String> data = request.getData();
        log.info("Processing {} element(s)", data.size());

        List<String> evenNumbers      = new ArrayList<>();
        List<String> oddNumbers       = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        List<Character> alphaCharPool = new ArrayList<>(); // individual chars for concat_string
        long sumValue = 0;

        for (String item : data) {
            if (isNumeric(item)) {
                long num = Long.parseLong(item);
                sumValue += num;
                if (num % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabetic(item)) {
                alphabets.add(item.toUpperCase());
                for (char c : item.toCharArray()) {
                    alphaCharPool.add(c);
                }
            } else {
                specialChars.add(item);
            }
        }

        String concatString = buildConcatString(alphaCharPool);

        log.info("Processing complete – sum={}, concat={}", sumValue, concatString);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(appConfig.getUserId())
                .email(appConfig.getEmail())
                .rollNumber(appConfig.getRollNumber())
                .evenNumbers(evenNumbers)
                .oddNumbers(oddNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(sumValue))
                .concatString(concatString)
                .build();
    }

   
    private boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

   
    private boolean isAlphabetic(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

  
    private String buildConcatString(List<Character> chars) {
        Collections.reverse(chars);
        StringBuilder sb = new StringBuilder(chars.size());
        for (int i = 0; i < chars.size(); i++) {
            char c = chars.get(i);
            sb.append(i % 2 == 0 ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }
        return sb.toString();
    }
}
