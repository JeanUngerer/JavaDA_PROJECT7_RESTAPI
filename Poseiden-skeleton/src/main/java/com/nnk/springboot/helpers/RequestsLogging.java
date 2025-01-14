package com.nnk.springboot.helpers;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RequestsLogging implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) servletRequest;
            log.info(
                    "Starting a transaction for req : {}",
                    req.getRequestURI());

            filterChain.doFilter(servletRequest, servletResponse);
            log.info(
                    "Committing a transaction for req : {}",
                    req.getRequestURI());
    }
}
