package com.pintoo.ems.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pintoo.ems.Response.Cursor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class CursorUtil {

    public static String getEncoded(Cursor cursor)  {
        if (cursor == null ){
            return null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        ObjectMapper mapper =  new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(cursor);
            return encoder.encode(s.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("error", e);
        }
       return null;

    }

    public static Cursor getCursor(String cursor) {
        if (cursor == null || cursor.isEmpty()){
            return null;
        }
        byte[] decoded = Base64.getDecoder().decode(cursor);
        String decodedStr = new String(decoded, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(decodedStr, new TypeReference<Cursor>() {});
        } catch (Exception e) {
            log.error("Errro cursor, ",e);
        }

        return null;
    }
}
