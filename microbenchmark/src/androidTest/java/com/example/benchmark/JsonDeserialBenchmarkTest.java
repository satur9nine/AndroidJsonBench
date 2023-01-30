package com.example.benchmark;

import androidx.benchmark.BenchmarkState;
import androidx.benchmark.junit4.BenchmarkRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.AssertionFailedError;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@RunWith(AndroidJUnit4.class)
public class JsonDeserialBenchmarkTest {

  @Rule
  public BenchmarkRule benchmarkRule = new BenchmarkRule();

  interface JsonLibrary {
    void parse(String json) throws Exception;
    Iterator<String> keyIter();
    Object get(String key) throws Exception;
  }

  // Many use cases don't actually get all fields
  static final boolean GET_HALF = true;

  void parseAndGet(JsonLibrary jl, String json) throws Exception {
    jl.parse(json);

    boolean skipGet = false;
    for (Iterator<String> it = jl.keyIter(); it.hasNext(); ) {
      String key = it.next();
      if (!GET_HALF || !skipGet) {
        jl.get(key);
      }
      skipGet = !skipGet;
    }
  }

  void checkOutput(JsonLibrary lib) throws Exception {
    Class<?> numClass;
    if (TEST_JSON.equals(SampleJsonData.TEST_MERCH_PROPS_JSON)) {
      numClass = lib.get("pinLength").getClass();
    } else if (TEST_JSON.equals(SampleJsonData.TEST_ORDER_JSON)) {
      numClass = lib.get("total").getClass();
    } else {
      throw new UnsupportedOperationException("Need a decimal key");
    }

    if (!Long.class.equals(numClass) && !Integer.class.equals(numClass)) {
      throw new AssertionFailedError("Decimal number not int or long: " + numClass);
    }
  }

  @Test
  public void genson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final com.owlike.genson.Genson gensonInstance = new com.owlike.genson.Genson();
      Map<String, Object> parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = gensonInstance.deserialize(json, Map.class);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void orgjsonbuiltin() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.JSONObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = new org.json.JSONObject(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keys();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void latestaospjson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.aosp_latest.JSONObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = new org.json.aosp_latest.JSONObject(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keys();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void optimizedaospjson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.aosp_optimized.JSONObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = new org.json.aosp_optimized.JSONObject(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keys();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void orgjsonoriginal() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.json.orig.JSONObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = new org.json.orig.JSONObject(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keys();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void underscore() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      Map<String, Object> parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = (Map<String, Object>) underscore.Json.fromJson(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void gson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
          .setObjectToNumberStrategy(com.google.gson.ToNumberPolicy.LONG_OR_DOUBLE)
          .create();
      Map<String, Object> parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = gson.fromJson(json, Map.class);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void tapestry() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      org.apache.tapestry5.json.JSONObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = new org.apache.tapestry5.json.JSONObject(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void fastjson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      com.alibaba.fastjson.JSONObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = com.alibaba.fastjson.JSON.parseObject(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void jackson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final com.fasterxml.jackson.databind.ObjectMapper jacksonMapper
          = new com.fasterxml.jackson.databind.ObjectMapper();
      Map<String, Object> parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = jacksonMapper.readValue(json, HashMap.class);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void jsonsmart() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final net.minidev.json.parser.JSONParser parser
          = new net.minidev.json.parser.JSONParser(net.minidev.json.parser.JSONParser.MODE_JSON_SIMPLE);
      net.minidev.json.JSONObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = (net.minidev.json.JSONObject) parser.parse(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

//  @Ignore("Slow and strictly forces old junit with class conflicts")
//  public void jsonsimple() throws Exception {
//    JsonLibrary lib = new JsonLibrary() {
//      final org.json.simple.parser.JSONParser parser
//          = new org.json.simple.parser.JSONParser();
//      org.json.simple.JSONObject parsed;
//
//      @Override
//      public void parse(String json) throws Exception {
//        parsed = (org.json.simple.JSONObject) parser.parse(json);
//      }
//
//      @Override
//      public Iterator<String> keyIter() {
//        return parsed.keySet().iterator();
//      }
//
//      @Override
//      public Object get(String key) throws Exception {
//        return parsed.get(key);
//      }
//    };
//
//    final BenchmarkState state = benchmarkRule.getState();
//    while (state.keepRunning()) {
//      parseAndGet(lib, TEST_JSON);
//    }
//
//    checkOutput(lib);
//  }

//  @Ignore("Moshi only creates doubles....")
//  public void moshi() throws Exception {
//    JsonLibrary lib = new JsonLibrary() {
//      final com.squareup.moshi.JsonAdapter<Map> adapter
//          = new com.squareup.moshi.Moshi.Builder().build().adapter(Map.class);
//      Map<String, Object> parsed;
//
//      @Override
//      public void parse(String json) throws Exception {
//        parsed = adapter.fromJson(json);
//      }
//
//      @Override
//      public Iterator<String> keyIter() {
//        return parsed.keySet().iterator();
//      }
//
//      @Override
//      public Object get(String key) throws Exception {
//        return parsed.get(key);
//      }
//    };
//
//    final BenchmarkState state = benchmarkRule.getState();
//    while (state.keepRunning()) {
//      parseAndGet(lib, TEST_JSON);
//    }
//
//    checkOutput(lib);
//  }

  @Test
  public void jsoniter() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      Map<String, Object> parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = com.jsoniter.JsonIterator.deserialize(json, Map.class);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

//  @Ignore("Keeps numbers as strings until get")
//  public void minimaljson() throws Exception {
//    JsonLibrary lib = new JsonLibrary() {
//      com.eclipsesource.json.JsonObject parsed;
//
//      @Override
//      public void parse(String json) throws Exception {
//        parsed = com.eclipsesource.json.Json.parse(json).asObject();
//      }
//
//      @Override
//      public Iterator<String> keyIter() {
//        return parsed.names().iterator();
//      }
//
//      @Override
//      public Object get(String key) throws Exception {
//        return parsed.get(key);
//      }
//    };
//
//    final BenchmarkState state = benchmarkRule.getState();
//    while (state.keepRunning()) {
//      parseAndGet(lib, TEST_JSON);
//    }
//
//    checkOutput(lib);
//  }

  @Test
  public void jodd() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final jodd.json.JsonParser jsonParser = new jodd.json.JsonParser();
      Map<String, Object> parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = jsonParser.parse(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void nanojson() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      final com.grack.nanojson.JsonParser.JsonParserContext<com.grack.nanojson.JsonObject> jsonParser
          = com.grack.nanojson.JsonParser.object();
      com.grack.nanojson.JsonObject parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = jsonParser.from(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  @Test
  public void jacksonjr() throws Exception {
    JsonLibrary lib = new JsonLibrary() {
      Map<String, Object> parsed;

      @Override
      public void parse(String json) throws Exception {
        parsed = com.fasterxml.jackson.jr.ob.JSON.std.mapFrom(json);
      }

      @Override
      public Iterator<String> keyIter() {
        return parsed.keySet().iterator();
      }

      @Override
      public Object get(String key) throws Exception {
        return parsed.get(key);
      }
    };

    final BenchmarkState state = benchmarkRule.getState();
    while (state.keepRunning()) {
      parseAndGet(lib, TEST_JSON);
    }

    checkOutput(lib);
  }

  private static final String TEST_JSON = SampleJsonData.TEST_MERCH_PROPS_JSON;

}
