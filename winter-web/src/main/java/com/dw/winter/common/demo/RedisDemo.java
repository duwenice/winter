package com.dw.winter.common.demo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author duwen
 */
@RestController
public class RedisDemo {

    @Configuration
    public static class RedisConfig {

        @Bean
        @ConditionalOnMissingBean(name = "redisTemplate")
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(connectionFactory);
            redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
            redisTemplate.afterPropertiesSet();
            return redisTemplate;
        }
    }


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redisPush")
    public void redisPush() {
        redisTemplate.opsForList().leftPush("listKey", "1");
        redisTemplate.opsForList().leftPush("listKey", "2");
        redisTemplate.opsForList().leftPush("listKey", "3");
        redisTemplate.opsForList().leftPush("listKey", "4");
    }

    @GetMapping("/redisConsumer")
    public void redisConsumer() {
        System.out.println(redisTemplate.opsForList().rightPop("listKey"));
        System.out.println(redisTemplate.opsForList().rightPop("listKey"));
        System.out.println(redisTemplate.opsForList().rightPop("listKey"));
        System.out.println(redisTemplate.opsForList().rightPop("listKey"));
    }

    /**
     * lua脚本获取锁
     */
    @GetMapping("/getLock")
    public void getLock() {
        String key = "redisLockKey";
        String value = "redisLockValue";
        Boolean lock = redisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.set(key.getBytes(StandardCharsets.UTF_8),
                        value.getBytes(StandardCharsets.UTF_8),
                        Expiration.from(60, TimeUnit.SECONDS),
                        RedisStringCommands.SetOption.SET_IF_ABSENT));
        System.out.println(lock);
    }

    /**
     * lua脚本释放锁
     */
    @GetMapping("/releaseLock")
    public void releaseLock() {
        String key = "redisLockKey";
        String value = "redisLockValue";
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Boolean unLockStat = redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.eval(script.getBytes(), ReturnType.BOOLEAN, 1,
                key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8)));
        System.out.println(unLockStat);
    }
}
