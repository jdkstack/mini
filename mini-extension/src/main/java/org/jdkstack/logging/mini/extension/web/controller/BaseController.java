package org.jdkstack.logging.mini.extension.web.controller;

import org.jdkstack.logging.mini.extension.web.annotation.GetMapping;
import org.jdkstack.logging.mini.extension.web.annotation.RequestMapping;
import org.jdkstack.logging.mini.extension.web.annotation.RequestParam;
import org.jdkstack.logging.mini.extension.web.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本控制器 内置的Rest服务
 *
 * @author admin
 */
@RestController(singleton = true)
@RequestMapping("/base")
public class BaseController {

    @GetMapping("/get")
    public Object listUser(
            @RequestParam("recipient") final String recipient,
            @RequestParam("x") final String x,
            @RequestParam("y") final String y) {
        final Map<String, Object> data = new HashMap<>();
        data.put("id", 100);
        data.put("name", "name" + recipient + x + y);
        return data;
    }
}
