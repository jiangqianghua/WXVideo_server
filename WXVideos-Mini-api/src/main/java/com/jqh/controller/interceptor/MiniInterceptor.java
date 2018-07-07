package com.jqh.controller.interceptor;

import com.jqh.config.BaseConfig;
import com.jqh.utils.JSONResult;
import com.jqh.utils.JsonUtils;
import com.jqh.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class MiniInterceptor implements HandlerInterceptor {

    @Autowired
    public RedisOperator redis ;

    public static final String USER_REDIS_SESSION = "user-redis-session";
    /**
     * 拦截请求，在controller之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        String userId = request.getHeader("userId");
        String userToken = request.getHeader("userToken");
        if(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(userToken)){
            String uniqueToken = redis.get(USER_REDIS_SESSION+":"+userId);
            if(StringUtils.isEmpty(uniqueToken)){
                System.out.println("需要登陆...");
                returnErrorResponse(response,new JSONResult().errorTokenMsg("请登陆"));
                return false;
            }else {
                if(!userToken.equals(uniqueToken)){
                    System.out.println("账号在别的手机端登陆...");
                    returnErrorResponse(response,new JSONResult().errorTokenMsg("账号在别的手机端登陆..."));
                    return false;
                }else{
                    return true ;
                }
            }
        }
        else{
            System.out.println("需要登陆...");
            returnErrorResponse(response,new JSONResult().errorTokenMsg("需要登陆..."));
            return false;
        }

        /**
         * return false 请求被拦截了，
         * return true 请求ok，可以走到controller
         */
    }

    public void returnErrorResponse(HttpServletResponse response, JSONResult result)
            throws IOException,UnsupportedEncodingException {
        OutputStream out = null ;
        try
        {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        }finally {
            if(out != null){
                out.close();
            }
        }
    }

    /**
     * 请求controller之后，渲染视图之前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 视图渲染后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
