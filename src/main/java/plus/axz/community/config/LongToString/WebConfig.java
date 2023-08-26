package plus.axz.community.config.LongToString;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.standard.serializer.IStandardJavaScriptSerializer;
 
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
 
/**
 * @description 解决long类型精度丢失
 */
@Slf4j
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
 
    @Resource
    private TemplateEngine templateEngine;
 
    @PostConstruct
    private void init() {
        log.info("WebConfi init...");
        // 通过templateEngine获取SpringStandardDialect
        SpringStandardDialect springStandardDialect = CollectionUtils.findValueOfType(templateEngine.getDialects(), SpringStandardDialect.class);
        IStandardJavaScriptSerializer standardJavaScriptSerializer = springStandardDialect.getJavaScriptSerializer();
        // 反射获取 IStandardJavaScriptSerializer
        Field delegateField = ReflectionUtils.findField(standardJavaScriptSerializer.getClass(), "delegate");
        if (delegateField == null) {
            log.warn("WebConfi init, failed set jasonck module, delegeteField is null !!!");
            return;
        }
        ReflectionUtils.makeAccessible(delegateField);
        Object delegate = ReflectionUtils.getField(delegateField, standardJavaScriptSerializer);
        if (delegate == null) {
            log.warn("WebConfi init, failed set jasonck module, delegete is null !!!");
            return;
        }
        // 如果代理类是JacksonStandardJavaScriptSerializer,则获取mapper,设置model
        if (Objects.equals("JacksonStandardJavaScriptSerializer", delegate.getClass().getSimpleName())) {
            Field mapperField = ReflectionUtils.findField(delegate.getClass(), "mapper");
            if (mapperField == null) {
                log.warn("WebConfi init, failed set jackson module, mapperField is null !!!");
                return;
            }
            ReflectionUtils.makeAccessible(mapperField);
            ObjectMapper objectMapper = (ObjectMapper) ReflectionUtils.getField(mapperField, delegate);
            if (objectMapper == null) {
                log.warn("WebConf init, filed set jackson module, mapper is null !!!");
                return;
            }
            // 设置序列化Module,修改long型序列化为字符串
            objectMapper.registerModule(simpleModule());
            log.info("WebConf init 设置jackson序列化long为字符串成功!!!");
        }
    }
 
    /**
     * 针对long型特殊处理,long型json序列化为字符串类型
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }
 
    /**
     * 解决Spring Boot 2.X 访问静态资源的时候出现404的问题
     */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }*/
 
    private SimpleModule simpleModule() {
        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long[].class, new LongArrayToStringSerializer());
        simpleModule.addSerializer(Long[].class, new JsonSerializer<Long[]>() {
            @Override
            public void serialize(Long[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartArray();
                for (Long v : value) {
                    if (v == null) {
                        gen.writeNull();
                    } else {
                        gen.writeString(v.toString());
                    }
                }
                gen.writeEndArray();
            }
        });
        simpleModule.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.toString());
                }
            }
        });
        return simpleModule;
    }

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
    }


}