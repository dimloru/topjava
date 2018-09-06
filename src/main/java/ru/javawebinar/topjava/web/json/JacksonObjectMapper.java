package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * <p>
 * Handling Hibernate lazy-loading
 *
 * @link https://github.com/FasterXML/jackson
 * @link https://github.com/FasterXML/jackson-datatype-hibernate
 * @link http://wiki.fasterxml.com/JacksonHowToCustomSerializers
 */
public class JacksonObjectMapper extends ObjectMapper {

    private static final ObjectMapper MAPPER = new JacksonObjectMapper();

    private JacksonObjectMapper() {
        //customizing json serialization (the case of lazily initialized collections
        registerModule(new Hibernate5Module()); //this module does NOT serialize lazy collections

        registerModule(new JavaTimeModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}