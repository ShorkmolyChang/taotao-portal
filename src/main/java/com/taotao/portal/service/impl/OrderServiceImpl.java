package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_ORDER_URL}")
	private String ORDER_CREATE_ORDER_URL;

	@Override
	public String createOrder(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
//		将orderInfo转化为json对象，利用HttpClientUtil向taotao-order发送get请求
		String json=JsonUtils.objectToJson(orderInfo);
		String result=HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_ORDER_URL, json);
//		从taotaoResult中取到orderId，并返回
		TaotaoResult result2=TaotaoResult.format(result);
		String orderId=result2.getData().toString();
		return orderId;
	}

}
