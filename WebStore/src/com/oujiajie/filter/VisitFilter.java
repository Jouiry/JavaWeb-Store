package com.oujiajie.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter2",urlPatterns = "/admin/*")
public class VisitFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String referer = request.getHeader("referer");
       if(referer != null) {
           chain.doFilter(request,response);
       } else {
           request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
       }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
