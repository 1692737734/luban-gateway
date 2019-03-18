package com.luban.http;

import com.luban.container.RequestContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LuServlet extends HttpServlet {
    private LuRunner luRunner = new LuRunner();

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(111);
        luRunner.init(req,resp);
        try {
            luRunner.preRoute();
            luRunner.route();
            luRunner.postRoute();
        }catch (Throwable e){
            RequestContext.getCurrentContext().getResponse().sendError(HttpServletResponse.SC_FOUND,e.getMessage());
        }finally {
            RequestContext.getCurrentContext().unset();
        }
    }
}
