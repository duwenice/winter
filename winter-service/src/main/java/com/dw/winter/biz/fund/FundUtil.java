package com.dw.winter.biz.fund;

import cn.hutool.http.HttpUtil;
import com.dw.winter.utils.JsonUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 查询蛋卷网基金工具
 *
 * @author duwen
 * @date 2020/11/11 17:53:00
 */
@Slf4j
public class FundUtil {

    public static void stat() {
        Map<String, List<String>> stockFundMap = new HashMap<>(16);
        String url = "https://danjuanapp.com/djapi/v3/filter/fund?type=5&order_by=1m&size=50&page=1";
        String result = HttpUtil.get(url, StandardCharsets.UTF_8);
        System.out.println(result);
        FundRepDTO fundRepDTO = JsonUtils.toObject(result, FundRepDTO.class);
        assert fundRepDTO != null;
        fundRepDTO.getData().getItems().forEach(item -> {
            String detailUrl = "https://danjuanapp.com/djapi/fund/detail/" + item.getFdCode();
            String detailResult = HttpUtil.get(detailUrl, StandardCharsets.UTF_8);
            FundDetailRepDTO fundDetailRepDTO = JsonUtils.toObject(detailResult, FundDetailRepDTO.class);
            assert fundDetailRepDTO != null;
            List<FundDetailRepDTO.FundStockDTO> stockList = fundDetailRepDTO.getData().getFundPosition().getStockList();
            if (CollectionUtils.isNotEmpty(stockList)) {
                stockList.forEach(
                        stock -> {
                            List<String> fundList = stockFundMap.get(stock.getName());
                            if (Objects.nonNull(fundList)) {
                                fundList.add(item.getFdName());
                            } else {
                                stockFundMap.put(stock.getName(), Lists.newArrayList(item.getFdName()));
                            }

                        }
                );
            }
        });
        stockFundMap.forEach((k, v) -> {
            if (v.size() > 5) {
                System.out.println(k + ":" + v);
            }
        });
    }

    public static void main(String[] args) {
        FundUtil.stat();
    }
}
