package com.dreamerproject.model.validators;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //to do: regex to validate email
        return true;
    }
}
