package com.dw.winter.biz.es;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author duwen
 */
@Data
@Accessors(chain = true)
public class EsUserDO {

    private String userName;

    private String city;

    private String age;

    private String salary;
}
