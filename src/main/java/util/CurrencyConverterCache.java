package util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterCache {
    private static CurrencyConverterCache instance;
    private Map<String, CacheItem> cache;
    private Duration expirationTime;

    private CurrencyConverterCache() {
        this.cache = new HashMap<>();
        this.expirationTime = Duration.ofMinutes(5);
    }

    public static CurrencyConverterCache getInstance() {
        if (instance == null) {
            instance = new CurrencyConverterCache();
        }
        return instance;
    }

    public void addToCache(String key, Double value) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plus(expirationTime);

        cache.put(key, new CacheItem(value, expiration));
    }

    public Double getFromCache(String key) {
        CacheItem item = cache.get(key);
        if (item != null && item.hasNotExpired()) {
            return item.getValue();
        }
        
        cache.remove(key);
        return null;
    }

    private static class CacheItem {
        private final Double value;
        private final LocalDateTime expiration;

        public CacheItem(Double value, LocalDateTime expiration) {
            this.value = value;
            this.expiration = expiration;
        }

        public Double getValue() {
            return value;
        }

        public boolean hasNotExpired() {
            return LocalDateTime.now().isBefore(expiration);
        }
    }
}
