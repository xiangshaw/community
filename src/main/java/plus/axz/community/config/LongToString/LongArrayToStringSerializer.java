package plus.axz.community.config.LongToString;
 
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
 
import java.io.IOException;
 
/**
 * 解决long类型精度丢失问题
 * @description long[]型数组格式化为String数组
 */
@JacksonStdImpl
public class LongArrayToStringSerializer extends JsonSerializer<long[]> {
    @Override
    public void serialize(long[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (long l : value) {
            gen.writeString(String.valueOf(l));
        }
        gen.writeEndArray();
    }
}