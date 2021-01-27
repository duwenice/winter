package com.dw.winter.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author duwen
 */
@Getter
@AllArgsConstructor
public enum EsIndexEnum {
    //
    USER("user"),
    ;

    private final String indexName;
}
