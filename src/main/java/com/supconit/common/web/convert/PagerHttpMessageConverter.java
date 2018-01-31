package com.supconit.common.web.convert;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.supconit.honeycomb.mvc.converter.FastJsonHttpMessageConverter;

import hc.base.domains.Pageable;

public class PagerHttpMessageConverter extends FastJsonHttpMessageConverter{

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		OutputStream out = outputMessage.getBody();
	    @SuppressWarnings("rawtypes")
		String text = toJson((Pageable)obj);
	    out.write(text.getBytes(getCharset()));
	    out.flush();
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if(!Pageable.class.isAssignableFrom(clazz)){
			return false;
		}
		return super.canWrite(clazz, mediaType);
	}

	private static SerializeConfig		mapping		= new SerializeConfig();
	private static SerializerFeature[]	features	= new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect };
	static {
		mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		mapping.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		mapping.put(java.sql.Time.class, new SimpleDateFormatSerializer("HH:mm:ss"));
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	
	@SuppressWarnings("rawtypes")
	public String toJson(Pageable pager) {
		StringBuilder builder = new StringBuilder("{");
		builder.append("\"rows\":").append(JSON.toJSONString(pager, mapping, features));
		builder.append(",\"pageSize\":").append(pager.getPageSize());
		builder.append(",\"page\":").append(pager.getPageNo());
		builder.append(",\"pageNo\":").append(pager.getPageNo());
		builder.append(",\"total\":").append(pager.getTotal());
		builder.append("}");
		return builder.toString();
	}
}
