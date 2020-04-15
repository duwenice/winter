package com.dw.winter.annotation.validator;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.dw.winter.annotation.InEnum;
import com.google.common.collect.Sets;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author duwen@shein.com
 * @date 2020/3/4
 */
public class InEnumValidator implements ConstraintValidator<InEnum, Serializable> {

    private Set<Serializable> key = new HashSet<>(16);

    private Set<String> ignore;

    @Override
    public void initialize(InEnum constraintAnnotation) {
        IEnum[] enumConstants = constraintAnnotation.value().getEnumConstants();
        ignore = Sets.newHashSet(constraintAnnotation.ignore());
        if (enumConstants.length > 0) {
            key = Arrays.stream(enumConstants)
                    .filter(e -> !ignore.contains(e.toString().trim()))
                    .map(e -> e.getValue()).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(Serializable value, ConstraintValidatorContext context) {
        if (key.contains(value)) {
            return true;
        }
        return false;
    }
}
