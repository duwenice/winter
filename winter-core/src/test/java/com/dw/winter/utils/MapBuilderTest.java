package com.dw.winter.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author duwen
 * @date 2020/4/14
 */
class MapBuilderTest {

    @Test
    void newInstance() {
        Map<String, Object> build = MapBuilder.<String, Object>newInstance().put("Key", "Value").build();
        System.out.println(build);
    }

}