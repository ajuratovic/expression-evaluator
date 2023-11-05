package org.ajuratovic.expression_evaluator.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "LoggingInterceptor", urlPatterns = "/*")
@Slf4j
public class LoggingInterceptor extends OncePerRequestFilter {

    public static final String LOG_RESPONSE_FORMAT = "HTTP status '{}', Response '{}'";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestBody = new String(requestWrapper.getContentAsByteArray());
        log.info("Endpoint '{}', Method '{}', Request '{}'", request.getPathInfo(), request.getMethod(), requestBody);

        logResponse(responseWrapper);
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) throws IOException {
        HttpStatus httpStatus = HttpStatus.resolve(responseWrapper.getStatus());
        String responseBody = new String(responseWrapper.getContentAsByteArray());

        switch (httpStatus) {
            case INTERNAL_SERVER_ERROR:
                log.error(LOG_RESPONSE_FORMAT, httpStatus, responseBody);
                break;
            case BAD_REQUEST, METHOD_NOT_ALLOWED, NOT_FOUND:
                log.warn(LOG_RESPONSE_FORMAT, httpStatus, responseBody);
                break;
            default:
                log.info(LOG_RESPONSE_FORMAT, httpStatus, responseBody);
        }

        responseWrapper.copyBodyToResponse();
    }
}
