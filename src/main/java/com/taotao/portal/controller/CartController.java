package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		boolean result = cartService.addCart(itemId, num, request, response);
		if (result)
			return "cartSuccess";
		else
			return "cartFailed";
	}

	@RequestMapping("/cart/cart")
	// 返回json或者直接相应客户端时需要responseBody,此处直接返回客户端
	public String showCart(HttpServletRequest request, Model model) {
		List<CartItem> list = cartService.showCart(request);
		// 将商品列表传递给jsp
		model.addAttribute("cartList", list);
		return "cart";
	}

	@RequestMapping("/cart/update/num/{itemId}/{num}.action")
	@ResponseBody
	public TaotaoResult updateCart(@PathVariable Long itemId, @PathVariable int num, HttpServletRequest request,
			HttpServletResponse response) {
		boolean result = cartService.updateCartItem(itemId, num, request, response);
		return TaotaoResult.ok(result);
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		boolean result = cartService.deleteCartItem(itemId, request, response);
		if(result)
			return "redirect:/cart/cart.html";
		else
			return "cartFailed";
	}
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> list = cartService.showCart(request);
		// 将商品列表传递给jsp
		model.addAttribute("cartList", list);
		return "order-cart";
	}

}
