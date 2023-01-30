package com.example.benchmark;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class JsonSerialBenchmarkTest {

  @Rule
  public BenchmarkRule benchmarkRule = new BenchmarkRule();

  interface JsonLibrary {
    void init(Map<String, Object> map);
    String serialize() throws Exception;
  }

  void checkOutput(String json) throws Exception {
    System.out.println(json);
  }

  private final Map<String, Object> jsonMap;

  {
    final com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
        .setObjectToNumberStrategy(com.google.gson.ToNumberPolicy.LONG_OR_DOUBLE)
        .create();

    jsonMap = gson.fromJson(TEST_JSON, Map.class);
  }

  @Test
  public void orgjsonbuiltin() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.JSONObject obj;

      @Override
      public void init(Map<String, Object> map) {
        obj = new org.json.JSONObject(map);
      }

      @Override
      public String serialize() throws Exception {
        return obj.toString();
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void latestaospjson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.aosp_latest.JSONObject obj;

      @Override
      public void init(Map<String, Object> map) {
        obj = new org.json.aosp_latest.JSONObject(map);
      }

      @Override
      public String serialize() throws Exception {
        return obj.toString();
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void optimizedaospjson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.aosp_optimized.JSONObject obj;

      @Override
      public void init(Map<String, Object> map) {
        obj = new org.json.aosp_optimized.JSONObject(map);
      }

      @Override
      public String serialize() throws Exception {
        return obj.toString();
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }


  @Test
  public void orgjsonoriginal() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.orig.JSONObject obj;

      @Override
      public void init(Map<String, Object> map) {
        obj = new org.json.orig.JSONObject(map);
      }

      @Override
      public String serialize() throws Exception {
        return obj.toString();
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void underscore() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      Map<String, Object> map;

      @Override
      public void init(Map<String, Object> map) {
        this.map = map;
      }

      @Override
      public String serialize() throws Exception {
        return underscore.Json.toJsonJavaString(map);
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void gson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final Gson gson = new GsonBuilder()
          .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
          .create();

      Map<String, Object> map;

      @Override
      public void init(Map<String, Object> map) {
        this.map = map;
      }

      @Override
      public String serialize() throws Exception {
        return gson.toJson(map);
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void fastjson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      Map<String, Object> map;

      @Override
      public void init(Map<String, Object> map) {
        this.map = map;
      }


      @Override
      public String serialize() throws Exception {
        return com.alibaba.fastjson.JSON.toJSONString(map);
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void jackson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final com.fasterxml.jackson.databind.ObjectMapper jacksonMapper
          = new com.fasterxml.jackson.databind.ObjectMapper();
      Map<String, Object> map;

      @Override
      public void init(Map<String, Object> map) {
        this.map = map;
      }

      @Override
      public String serialize() throws Exception {
        return jacksonMapper.writeValueAsString(map);
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void nanojson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      Map<String, Object> map;

      @Override
      public void init(Map<String, Object> map) {
        this.map = map;
      }


      @Override
      public String serialize() throws Exception {
        return com.grack.nanojson.JsonWriter.string(map);
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  @Test
  public void jacksonjr() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      Map<String, Object> map;

      @Override
      public void init(Map<String, Object> map) {
        this.map = map;
      }


      @Override
      public String serialize() throws Exception {
        return com.fasterxml.jackson.jr.ob.JSON.std.asString(map);
      }
    };
    lib.init(jsonMap);

    String result = null;
    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      result = lib.serialize();
    }

    checkOutput(result);
  }

  private static final String TEST_JSON = SampleJsonData.TEST_MERCH_PROPS_JSON;

}
