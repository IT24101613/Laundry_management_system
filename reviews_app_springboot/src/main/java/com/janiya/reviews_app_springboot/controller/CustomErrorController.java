package com.janiya.reviews_app_springboot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        
        StringBuilder errorDetails = new StringBuilder();
        errorDetails.append("<html><body style='font-family: Arial; padding: 20px;'>");
        errorDetails.append("<h2>Error Details</h2>");
        errorDetails.append("<p><strong>Status Code:</strong> ").append(statusCode).append("</p>");
        errorDetails.append("<p><strong>Request URI:</strong> ").append(requestUri).append("</p>");
        errorDetails.append("<p><strong>Message:</strong> ").append(message).append("</p>");
        
        if (exception != null) {
            errorDetails.append("<p><strong>Exception:</strong> ").append(exception.getClass().getName()).append("</p>");
            errorDetails.append("<p><strong>Exception Message:</strong> ").append(exception.getMessage()).append("</p>");
            errorDetails.append("<h3>Stack Trace:</h3><pre>");
            for (StackTraceElement element : exception.getStackTrace()) {
                errorDetails.append(element.toString()).append("\n");
            }
            errorDetails.append("</pre>");
        }
        
        errorDetails.append("<hr><p><a href='/'>Go to Home</a> | <a href='/select-user'>Select User</a></p>");
        errorDetails.append("</body></html>");
        
        return errorDetails.toString();
    }
}
