package com.project.learnifyapp.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtils {
    public static HttpServletRequest getCurrentRequest() { //hàm này viết ra mục đích là tái sử dụng HttpServletRequest
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest(); // -> trả về cho chúng ta request hiện tại
    }
}
