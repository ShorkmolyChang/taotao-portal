package com.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;

	@Override
	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		try {
//		从request中取token
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			// 判断token是否有值
			if (StringUtils.isBlank(token))
				return null;
			// 调用sso提供的服务
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
			System.out.println("**************result********************");
			System.out.println(json);
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
			if(result.getStatus()!=200){
				return null;
			}
//		取用户对象
			TbUser user=(TbUser) result.getData();
			return user;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
