package com.dw.winter.common.config;

import com.google.common.base.Strings;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author duwen
 * @date 2020/7/26 17:12:25
 */
public class OrderTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<LocalDateTime> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<LocalDateTime> preciseShardingValue) {
        return preciseShardingValue.getLogicTableName() + "_" + Strings.padStart(String.valueOf(preciseShardingValue.getValue().getMonth().getValue()), 2, '0');
    }
}
