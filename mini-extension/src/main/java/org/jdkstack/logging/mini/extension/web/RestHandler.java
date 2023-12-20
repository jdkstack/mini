package org.jdkstack.logging.mini.extension.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdkstack.logging.mini.extension.web.context.Context;
import org.jdkstack.logging.mini.extension.web.data.ControllerMetaData;
import org.jdkstack.logging.mini.extension.web.data.MethodMetaData;
import org.jdkstack.logging.mini.extension.web.data.ParameterMetaData;

public class RestHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange he) throws IOException {
    String protocol = he.getProtocol();
    if ("HTTP/1.1".equals(protocol)) {
      //只支持1.1.
      URI requestURI = he.getRequestURI();
      String path = requestURI.getPath();
      // 静态资源。
      if (path.contains(".")) {
        // 静态资源(支持什么HTTP METHOD?).
        path = URLDecoder.decode(path, "UTF-8");
        Path normalize = FileSystems.getDefault().getPath("apps").toAbsolutePath();
        File file = Paths.get(normalize.toString(), path).toFile();
        int code = 200;
        int fileLength = 0;
        byte[] bytes = null;
        if (!file.exists()) {
          code = 200;
          bytes = "不存在".getBytes(StandardCharsets.UTF_8);
          fileLength = bytes.length;
        } else if (file.isFile() && (file.isHidden() || !file.exists())) {
          code = 200;
          bytes = "没有找到资源".getBytes(StandardCharsets.UTF_8);
          fileLength = bytes.length;
        } else if (file.isDirectory()) {
          String sb = sendListing(file, path);
          bytes = sb.getBytes(StandardCharsets.UTF_8);
          fileLength = bytes.length;
        } else {
          RandomAccessFile raf = new RandomAccessFile(file, "r");
          fileLength = (int) raf.length();
          bytes = new byte[fileLength];
          raf.read(bytes);
        }
        he.sendResponseHeaders(code, fileLength);
        Headers responseHeaders = he.getResponseHeaders();
        responseHeaders.add("Content-Type", "text/html;charset=utf-8");
        OutputStream responseBody = he.getResponseBody();
        responseBody.write(bytes);
        // 关闭输出流
        responseBody.close();
      } else {
        //动态资源.
        String requestMethod = he.getRequestMethod();
        if ("GET".equals(requestMethod)) {
          String query = requestURI.getQuery();
          Context context = StartApplication.context();
          MethodMetaData methodMetaData = context.getFullyPaths().get(path);
          List<ParameterMetaData> parameterMetaDatas = context.getFullyPathsParams().get(path);
          Map<String, Object> parameters = new HashMap<>(16);
          String[] split = query.split("&");
          for (String s : split) {
            String[] split1 = s.split("=");
            parameters.put(split1[0], split1[1]);
          }
          List<Object> paramsValues = new ArrayList<>();
          for (ParameterMetaData param : parameterMetaDatas) {
            paramsValues.add(parameters.get(param.getParameterName()));
          }
          ControllerMetaData controllerMetaData = context.getControllerMetaDataMap().get(methodMetaData.getBasePath());
          Object obj;
          //替换掉{}后,在进行一次比较,如果不匹配,不存在URL,如果匹配,则进行参数的替换操作
          boolean singleton = controllerMetaData.isSingleton();
          obj = controllerMetaData.getObj();
          if (obj == null && !singleton) {
            Class<?> aClass = context.getFullyQualifiedClasses().get(path);
            Constructor<?> constructor = null;
            try {
              constructor = aClass.getConstructor();
            } catch (NoSuchMethodException e) {
              throw new RuntimeException(e);
            }
            try {
              obj = constructor.newInstance();
            } catch (InstantiationException e) {
              throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
              throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
              throw new RuntimeException(e);
            }
          }
          try {
            Object invoke = methodMetaData.getMethod().invoke(obj, paramsValues.toArray());
            StringBuilder sb = new StringBuilder();
            List<Map<String, Object>> list = (ArrayList) invoke;
            for (Map<String, Object> map : list) {
              sb.append("{" + map.get("i") + map.get("id") + map.get("name") + "}");
            }
            byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
            he.sendResponseHeaders(200, bytes.length);
            Headers responseHeaders = he.getResponseHeaders();
            responseHeaders.add("Content-Type", "application/json;charset=utf-8");
            OutputStream responseBody = he.getResponseBody();
            responseBody.write(bytes);
            // 关闭输出流
            responseBody.close();
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
          }
        } else {

        }
      }
    }
  }

  private String sendListing(File dir, String dirPath) {
    StringBuilder buf = new StringBuilder().append("<!DOCTYPE html>\r\n").append("<html><head><meta charset='utf-8' /><title>").append("Listing of: ").append(dirPath).append("</title></head><body>\r\n")

        .append("<h3>Listing of: ").append(dirPath).append("</h3>\r\n")

        .append("<ul>").append("<li><a href=\"../\">..</a></li>\r\n");

    File[] files = dir.listFiles();
    if (files != null) {
      for (File f : files) {
        if (f.isHidden() || !f.canRead()) {
          continue;
        }
        String name = f.getName();
        buf.append("<li><a href=\"").append(name).append("\">").append(name).append("</a></li>\r\n");
      }
    }
    buf.append("</ul></body></html>\r\n");
    return buf.toString();
  }
}
