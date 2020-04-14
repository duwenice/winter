package com.dw.winter.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duwen
 * @date 2020/4/14
 */
public class MapBuilder<K, V> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Integer DEFAULT_CAPACITY = 16;
    private final Map<K, V> map;

    /**
     * 链式Map创建类
     *
     * @param map 要使用的Map实现类
     */
    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    /**
     * 创建Builder，默认HashMap实现
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return MapBuilder
     */
    public static <K, V> MapBuilder<K, V> newInstance() {
        return new MapBuilder(new HashMap<K, V>(DEFAULT_CAPACITY));
    }

    /**
     * 指定容量创建Builder，默认HashMap实现
     *
     * @param capacity 容量
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @return MapBuilder
     */
    public static <K, V> MapBuilder<K, V> newInstanceWithCapacity(int capacity) {
        return new MapBuilder(new HashMap<K, V>(capacity));
    }

    /**
     * 链式Map创建
     *
     * @param k Key类型
     * @param v Value类型
     * @return 当前类
     */
    public MapBuilder<K, V> put(K k, V v) {
        map.put(k, v);
        return this;
    }

    /**
     * 链式Map创建
     *
     * @param map 合并map
     * @return 当前类
     */
    public MapBuilder<K, V> putAll(Map<K, V> map) {
        this.map.putAll(map);
        return this;
    }

    /**
     * 创建后的map
     *
     * @return 创建后的map
     */
    public Map<K, V> build() {
        return this.map;
    }

}
