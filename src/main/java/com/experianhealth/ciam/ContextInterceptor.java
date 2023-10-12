package com.experianhealth.ciam;

import com.experianhealth.ciam.forgerock.service.AbstractForgeRockIDMServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@Component
public class ContextInterceptor implements HandlerInterceptor {

    RequestContextProviderManager contextProvider ;
    protected static final Logger LOGGER = Logger.getLogger(ContextInterceptor.class.getName());
    ContextInterceptor(@Autowired RequestContextProviderManager contextProvider){
        this.contextProvider = contextProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        LOGGER.info("Starting Request: " + httpServletRequest.getMethod() + " "+ httpServletRequest.getRequestURI());
        contextProvider.reset();
        RequestContext rc = RequestContext.Builder.create().fromHttpServletRequest(httpServletRequest).build();
        System.out.println(rc);
        contextProvider.setRequestContext(rc);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        contextProvider.reset();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        contextProvider.reset();
        LOGGER.info("Completed Request: " + httpServletRequest.getMethod() + " "+ httpServletRequest.getRequestURI() + ", status=" + httpServletResponse.getStatus());
        LOGGER.info("Completed Response: " + ((o != null ) ? o.toString() : "<empty>"));
    }
}
