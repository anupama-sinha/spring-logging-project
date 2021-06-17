package com.anupama.sinha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        prepareMDC(request, handler);
        LOGGER.info("Incoming Request");
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long requestEndTime = System.currentTimeMillis();
        long execTime = requestEndTime - Long.parseLong(MDC.get("startTime"));
        LOGGER.info("Request Completed; execTime={}ms", execTime);
        MDC.clear();
    }

    void prepareMDC(HttpServletRequest request, Object handler) {
        long requestStartTime = System.currentTimeMillis();
        MDC.put("startTime", String.valueOf(requestStartTime));
        if (handler instanceof HandlerMethod) {
            String method = ((HandlerMethod) handler).getMethod().getName();
            MDC.put("ENDPOINT", method);
        }
        MDC.put("QUERYSTRING", request.getQueryString());
        MDC.put("URI", request.getRequestURI());
    }
}