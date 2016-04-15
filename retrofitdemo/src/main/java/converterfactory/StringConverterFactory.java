package converterfactory;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import retrofit.Converter;


/**
 * Created by shitian on 2015/11/24.
 */
public class StringConverterFactory extends Converter.Factory{
    public static StringConverterFactory create() {
        return create(new String());
    }

    /**
     * Create an instance using {@code string} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static StringConverterFactory create(String string) {
        return new StringConverterFactory(string);
    }

    private final String string;

    private StringConverterFactory(String string) {
        if (string == null) throw new NullPointerException("string == null");
        this.string = string;
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        // 由于Java没有标准的获取Type方法，借用GSON里的TypeToken来获取
        Type token = new TypeToken<String>(){}.getType();
        if (type == token) {
            // 目标类型为String类型则进行转换
            return new StringConverter();
        }
        return super.fromResponseBody(type, annotations);
    }

    @Override public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return super.toRequestBody(type, annotations);
    }

    private class StringConverter implements Converter{

        @Override
        public Object convert(Object value) throws IOException {
            if (value != null) {
                if (value instanceof ResponseBody) {
                    ResponseBody rb = (ResponseBody) value;
                    return rb.string();
                }
            }
            return null;
        }
    }
}

