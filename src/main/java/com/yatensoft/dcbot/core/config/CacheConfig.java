/** By YamiY Yaten */
package com.yatensoft.dcbot.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of local cache
 */
@Configuration
@EnableCaching
public class CacheConfig {
    public static final String DISCORD_CHANNEL_CACHE_NAME = "discord-channel";
    public static final String DISCORD_SERVER_CACHE_NAME = "discord-server";

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder().maximumSize(100));
        cacheManager.setCacheNames(List.of(DISCORD_CHANNEL_CACHE_NAME, DISCORD_SERVER_CACHE_NAME));
        return cacheManager;
    }
}
