package org.jdkstack.logging.mini.extension.web.controller;

import org.jdkstack.logging.mini.extension.web.annotation.GetMapping;
import org.jdkstack.logging.mini.extension.web.annotation.PathVariable;
import org.jdkstack.logging.mini.extension.web.annotation.PostMapping;
import org.jdkstack.logging.mini.extension.web.annotation.RequestBody;
import org.jdkstack.logging.mini.extension.web.annotation.RequestMapping;
import org.jdkstack.logging.mini.extension.web.annotation.RequestParam;
import org.jdkstack.logging.mini.extension.web.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理请求的接口(相当于Servlet) .
 * <pre>
 * 1: 无参数(任意类型的Http请求)需要参考HTTP规范 .
 * 2: 一个参数(POST PUT @RequestBody) .
 * 3: 任意参数(GET @RequestParam @PathVariable).
 * </pre>
 *
 * @author admin
 */
@RestController(singleton = true)
@RequestMapping("/user")
public class UserController {

    @GetMapping("/users")
    public List<Map<String, Object>> users(
            @RequestParam("recipient") final String recipient,
            @RequestParam("x") final String x,
            @RequestParam("y") final String y) {
        //LOG.info("接受到请求参数是:recipient={},x={},y={}", recipient, x, y);
        final List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Map<String, Object> data = new HashMap<>();
            data.put("i", i);
            data.put("id", 100);
            data.put("name", "name" + recipient + x + y);
            list.add(data);
        }
        return list;
    }

    @GetMapping("/user/{name}")
    public Object userName(@PathVariable("name") final String name) {
        final Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        return data;
    }

    @GetMapping("/user/5")
    public Object user5() {
        final Map<String, Object> data = new HashMap<>();
        data.put("FullHttpRequest2", "FullHttpRequest2");
        data.put("response2", "response2");
        data.put("ctx", "ctx");
        return data;
    }

    @PostMapping("/user/1")
    public Object user1(@RequestBody("user1") final Map<String, Object> data) {
        final Map<String, Object> data1 = new HashMap<>();
        data1.put("id", data.get("id"));
        data1.put("name", data.get("name"));
        return data1;
    }
}
