package com.labluetech.weather.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import static org.springframework.util.Assert.notNull;

public abstract class WeatherMetric {
    @Id
    protected String id;
    protected WeatherMetricType type;
    protected Double value;
    protected Date publishedAt;
    protected Date timestamp;
    protected String deviceId;
    @Indexed(expireAfterSeconds=0)
    @Field("ttl")
    protected Date ttl;

    protected WeatherMetric(WeatherMetric.Builder<?, ?> builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.value=builder.value;
        this.timestamp = builder.timestamp;
        this.deviceId = builder.deviceId;
        this.ttl = builder.ttl;
        publishedAt = new Date();
    }

    public WeatherMetric() {
    }


    public String getId() {
        return id;
    }

    public WeatherMetricType getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Date getTtl() {
        return ttl;
    }

    protected void checkWeatherMetric(Date timestamp, String deviceId) {
        checkTimestamp(timestamp);
        checkDeviceId(deviceId);
    }
    private void checkTimestamp(Date timestamp) {
        notNull(timestamp, "timestamp cannot be null");
    }

    private void checkDeviceId(String deviceId) {
        notNull(deviceId, "deviceId cannot be null");
    }
    public static abstract class Builder<T extends WeatherMetric, B extends WeatherMetric.Builder<T, B>> {
        private B thisObj;
        private String id;
        protected WeatherMetricType type;
        protected Double value;
        private Date timestamp;
        private String deviceId;
        private Date ttl;


        protected Builder() {
            thisObj = getThis();
        }

        public B id(String id) {
            this.id = id;
            return thisObj;
        }

        public B name(WeatherMetricType type) {
            this.type = type;
            return thisObj;
        }

        public B value(Double value) {
            this.value = value;
            return thisObj;
        }

        public B timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return thisObj;
        }

        public B deviceId(String deviceId) {
            this.deviceId = deviceId;
            return thisObj;
        }




        public B ttl(Date ttl) {
            this.ttl = ttl;
            return thisObj;
        }


        protected abstract B getThis();
        public abstract T build();
    }

}
