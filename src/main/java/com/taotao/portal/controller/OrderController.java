package com.taotao.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	public String createOrder(HttpServletRequest request, OrderInfo orderInfo, Model model) {
		// 拦截器中已经将user对象放到request中，此处可以直接取出
		TbUser user = (TbUser) request.getAttribute("user");
		// 补全orderInfo中的相关信息
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		// 将调用taotao-order中服务，生成订单
		String orderId = orderService.createOrder(orderInfo);
		// 从返回值中取出orderId放到model中
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		return "success";
	}

}
