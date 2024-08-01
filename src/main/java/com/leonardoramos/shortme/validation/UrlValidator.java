package com.leonardoramos.shortme.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    private static final String URL_PATTERN = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
    private Pattern pattern = Pattern.compile(URL_PATTERN);

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null) {
            return false;
        }
        return pattern.matcher(url).matches();
    }
}
