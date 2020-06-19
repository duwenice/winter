package com.dw.winter.commom.demo;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器demo
 *
 * @author duwen
 */
public class BloomFilterDemo {

    public static void main(String[] args) {
        int total = 1000000;
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), total, 0.0002);
        for (int i = 0; i < total; i++) {
            bloomFilter.put(i);
        }
        int matchCount = 0;
        for (int i = 0; i < total + 10000; i++) {
            if (bloomFilter.mightContain(i)) {
                matchCount++;
            }
        }
        System.out.println("匹配到的数量" + matchCount);
    }
}
