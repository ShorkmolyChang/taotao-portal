package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${PORTAL_BASE_URL}")
	private String PORTAL_BASE_URL;
	
	

	// false拦截，true放行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 用户登录拦截器
		TbUser user = userService.getUserByToken(request, response);
		if (user == null) {
			// 没有获取到user，需要重定向至登陆页面
			// forward:url不变 or redirect：url发生改变
			response.sendRedirect(SSO_BASE_URL+SSO_LOGIN_URL + "?redirectURL="+PORTAL_BASE_URL + request.getRequestURI());
			return false;
		}
//		把用户对象放入request中,后继的order/**被拦截的request中都可以取到用户信息
		request.setAttribute("user", user);
//		如果用户登录信息没有过期，则放行
		return true;
	}

	// handler执行完成，在返回ModelAndView之前
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	// 返回ModelAndView之后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
