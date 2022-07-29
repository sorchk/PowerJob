package tech.powerjob.server.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonComponent
public class JsonSerializerManage {
    public static final String DATE_TEIM_FORMAT  = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TEIM_ALL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String TIME_FORMAT = "HH:mm:ss";
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 忽略value为null 时 key的输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        // 防止Long型转json后丢失精度（后两位丢失变为00的问题），将long 等转为字符串
        module.addSerializer(Long.class, com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance);
        module.addSerializer(BigDecimal.class, com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance);
        module.addSerializer(BigInteger.class, com.fasterxml.jackson.databind.ser.std.ToStringSerializer.instance);

        module.addSerializer(Timestamp.class, DateSerializer.instance.withFormat(false, new SimpleDateFormat(DATE_TEIM_ALL_FORMAT)));
        module.addSerializer(Date.class, DateSerializer.instance.withFormat(false, new SimpleDateFormat(DATE_TEIM_FORMAT)));
        module.addSerializer(java.sql.Date.class, DateSerializer.instance.withFormat(false, new SimpleDateFormat(DATE_TEIM_FORMAT)));
        module.addSerializer(Time.class, DateSerializer.instance.withFormat(false, new SimpleDateFormat(TIME_FORMAT)));
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
