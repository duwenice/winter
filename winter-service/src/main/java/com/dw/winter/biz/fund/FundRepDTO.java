package com.dw.winter.biz.fund;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author duwen
 * @date 2020/11/11 18:18:05
 */
@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FundRepDTO {

    private Integer resultCode;

    private FundRepDataDTO data;

    @Data
    @Accessors(chain = true)
    public static class FundRepDataDTO {

        private List<FundRepDataItemDTO> items;
    }

    @Data
    @Accessors(chain = true)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class FundRepDataItemDTO {

        /**
         * 基金编码
         */
        private String fdCode;

        /**
         * 基金名称
         */
        private String fdName;

        /**
         * 基金净值
         */
        private String unitNav;
    }


}
