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
public class FundDetailRepDTO {

    private Integer resultCode;

    private FundRepDataDTO data;

    @Data
    @Accessors(chain = true)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class FundRepDataDTO {

        private FundPositionDTO fundPosition;
    }

    @Data
    @Accessors(chain = true)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class FundPositionDTO {

        /**
         * 备注
         */
        private String sourceMark;

        /**
         * 重仓持有的股票
         */
        private List<FundStockDTO> stockList;
    }


    @Data
    @Accessors(chain = true)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class FundStockDTO {

        /**
         * 股票名
         */
        private String name;

        /**
         * 股票编码
         */
        private String code;

        /**
         * 比例
         */
        private String percent;

    }

}
