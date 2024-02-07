package com.palmer.module.system.serializer.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime序列化规则
 * <p>
 * 会将LocalDateTime序列化为毫秒级时间戳
 */
public class MyLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public static final MyLocalDateTimeSerializer INSTANCE = new MyLocalDateTimeSerializer();

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeNumber(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        gen.writeString(value.format(dtf));
    }


}
