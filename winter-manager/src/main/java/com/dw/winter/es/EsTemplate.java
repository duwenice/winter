package com.dw.winter.es;

import com.dw.winter.commom.base.IdDTO;
import com.dw.winter.utils.JsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author duwen
 */
@Slf4j
@Component
public class EsTemplate {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 同步新增文档
     *
     * @param t     entity
     * @param index 索引名
     */
    @SneakyThrows
    public <T extends IdDTO> void insert(T t, String index) {
        IndexRequest indexRequest = new IndexRequest(index)
                .id(t.getId())
                .source(JsonUtils.toJsonString(t), XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 异步新增文档
     *
     * @param t     entity
     * @param index 索引名
     */
    public <T extends IdDTO> void insertAsync(T t, String index) {
        IndexRequest indexRequest = new IndexRequest(index)
                .id(t.getId())
                .source(JsonUtils.toJsonString(t), XContentType.JSON);
        restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
            }

            @Override
            public void onFailure(Exception e) {
                log.error("异步新增文档失败", e);
            }
        });
    }

    /**
     * 同步批量新增文档
     *
     * @param list  entity list
     * @param index 索引名
     */
    @SneakyThrows
    public <T extends IdDTO> void insertBatch(List<T> list, String index) {
        BulkRequest bulkRequest = new BulkRequest(index);
        list.forEach(t -> {
            IndexRequest indexRequest = new IndexRequest(index)
                    .id(t.getId())
                    .source(JsonUtils.toJsonString(t), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    /**
     * 根据文档id更新文档
     *
     * @param t     entity
     * @param index 索引名
     */
    @SneakyThrows
    public <T extends IdDTO> void updateByDocId(T t, String index) {
        UpdateRequest updateRequest = new UpdateRequest(index, t.getId())
                .doc(JsonUtils.toJsonString(t), XContentType.JSON);
        restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
    }


    /**
     * 根据查询条件更新文档
     *
     * @param t        entity
     * @param index    索引名
     * @param queryMap 查询条件
     */
    @SneakyThrows
    public <T extends IdDTO> void updateByQuery(T t, String index, Map<String, Object> queryMap) {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();

        restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
    }

    /**
     * 从es中查询
     *
     * @param queryMap 查询参数
     * @param index    索引名
     * @param clazz    查询结果需转换的类型
     * @return 查询结果
     */
    @SneakyThrows
    public List<?> query(Map<String, Object> queryMap, String index, Class<?> clazz) {
        SearchRequest searchRequest = new SearchRequest()
                .indices(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        queryMap.forEach((key, value) ->
                boolQueryBuilder.must(QueryBuilders.termQuery(key, value))
        );
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Arrays.stream(response.getHits().getHits())
                .map(e -> JsonUtils.toObject(e.getSourceAsString(), clazz))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public void pageQuery(Map<String, Object> queryMap, String index, Class<?> clazz, List<String> groupFields) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        queryMap.forEach((key, value) ->
                boolQueryBuilder.must(QueryBuilders.termQuery(key, value))
        );
        AggregationBuilders.terms("groupResult");
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }
}
