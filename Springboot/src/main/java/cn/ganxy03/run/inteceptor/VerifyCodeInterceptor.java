package cn.ganxy03.run.inteceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

    @Component
    public class VerifyCodeInterceptor implements HandlerInterceptor {
        private static final Logger logger = LoggerFactory.getLogger(VerifyCodeInterceptor.class);

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String requestURI = request.getRequestURI();
            String ipAddress = request.getRemoteAddr();

            if (requestURI.contains("/v3/api-docs")) {
                return true;
            } else if (requestURI.contains("/swagger-ui.html")) {
                return true;
            } else if (requestURI.contains("/webjars/springfox-swagger-ui/springfox.css")) {
                return true;
            } else if (requestURI.contains("/webjars/springfox-swagger-ui/swagger-ui.css")) {
                return true;
            } else if (requestURI.contains("/swagger-resources/configuration/ui")) {
                return true;
            } else if (requestURI.contains(" /null/swagger-resources/configuration/ui")) {
                return true;
            } else if (requestURI.contains("/swagger-resources/configuration/security")) {
                return true;
            } else if (requestURI.contains("/null/swagger-resources/configuration/security")) {
                return true;
            } else if (requestURI.contains("/error")) {
                return true;
            } else if (requestURI.contains("/swagger-resources")) {
                return true;
            } else if (requestURI.contains("/webjars/springfox-swagger-ui/swagger-ui-standalone-preset.js")) {
                return true;
            } else if (requestURI.contains("/webjars/springfox-swagger-ui/springfox.js")) {
                return true;
            } else if (requestURI.contains("/webjars/springfox-swagger-ui/swagger-ui-bundle.js")) {
                return true;
            } else if (requestURI.contains("/webjars/springfox-swagger-ui/favicon-32x32.png")) {
                return true;
            } else if (requestURI.contains("/webjars/springfox-swagger-ui/favicon-16x16.png")) {
                return true;
            }

            if (!checkVerifyCode(request, response)) {
                logger.warn("\nAccess Denied for request: {}\nIP Address: {}", requestURI, ipAddress);
                return false;
            } else {
                // 如果请求成功，打印成功信息
                logger.info("\nAccess Denied for request: {}\nIP Address: {}", requestURI, ipAddress);
                return true;
            }
        }


        private boolean checkVerifyCode(HttpServletRequest request, HttpServletResponse response) {
            String verifyCode = request.getHeader("verifyCode");
            System.out.println("verifyCode:" + verifyCode);
            if (!"VerifyCode".equals(verifyCode)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 返回403拒绝访问
                try {
                    response.getWriter().write("Invalid verifyCode. Access Denied.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            } else {
                // 如果请求成功，打印成功信息
                return true;
            }
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            // Do nothing here
        }
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            // Do nothing here
        }

    }
