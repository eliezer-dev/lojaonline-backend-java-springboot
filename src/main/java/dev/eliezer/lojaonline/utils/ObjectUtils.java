package dev.eliezer.lojaonline.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class ObjectUtils {

    private final ObjectMapper objectMapper;

    @Autowired
    public ObjectUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper; // Injeta o ObjectMapper configurado no JacksonConfig
    }



    //A ideia dessa função é ser algo parecido com Oject.assign(objetoAtual, atualizacao) do Javascript
    public Object objectUpdate (Object actual, Object update) {
        Map<String, Object> updatedValues = objectMapper.convertValue(update, Map.class);

        for (Map.Entry<String, Object> entry : updatedValues.entrySet()) {
            if (entry.getValue() != null) {
                try {
                    Field field = actual.getClass().getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(actual, entry.getValue());
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return actual;
    }

    public <T> T parseObject (Class<T> targetClass, String json) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
