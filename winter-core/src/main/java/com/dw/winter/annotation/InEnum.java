package com.dw.winter.annotation;


import com.baomidou.mybatisplus.core.enums.IEnum;
import com.dw.winter.annotation.validator.InEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验前端传入的Integer是否在指定的Enum中,ignore可以指定忽略校验的枚举名
 *
 * @author duwen
 * @date 2020/3/4
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InEnumValidator.class)
public @interface InEnum {

    String message() default "value should be included in enum";

    Class<? extends IEnum> value();

    String[] ignore() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
