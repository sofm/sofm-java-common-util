package com.github.sofm.common.util.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Slf4j
public class MapperUtil {

  private static MapperUtil ourInstance = new MapperUtil();

  private ModelMapper modelMapper;

  private MapperUtil() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  private static MapperUtil getInstance() {
    return ourInstance;
  }

  public static Map<String, Object> toMap(Object object) {
    return toMap(toJson(object));
  }

  public static String toJson(Object object) {
    if (object == null) {
      return null;
    } else {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

      try {
        return mapper.writeValueAsString(object);
      } catch (IOException ex) {
        log.error("(toJson)ex: {}", ExceptionUtil.getFullStackTrace(ex));
        return object.toString();
      }
    }
  }

  public static Map<String, Object> toMap(String json) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    } catch (IOException ex) {
      log.error("(toMap)ex: {}", ExceptionUtil.getFullStackTrace(ex));
      return null;
    }
  }

  public static ModelMapper getModelMapper() {
    return getInstance().modelMapper;
  }

  public static ObjectMapper getMapper() {
    return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static XmlMapper getXmlMapper() {
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return xmlMapper;
  }
}
