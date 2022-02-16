package com.liust.myproject.MyFiter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
public class ChannelFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper =null;
        if(request instanceof HttpServletRequest){
            requestWrapper  = new RequestWrapper((HttpServletRequest) request);
        }
        if(request==null){
            chain.doFilter(request, response);
        }else{
            chain.doFilter(requestWrapper ,response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
