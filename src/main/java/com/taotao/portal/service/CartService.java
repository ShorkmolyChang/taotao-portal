package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.portal.pojo.CartItem;

public interface CartService {

	boolean addCart(Long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	List<CartItem> showCart(HttpServletRequest request);

	boolean updateCartItem(Long itemId, int num, HttpServletRequest request,HttpServletResponse response);
	
	boolean deleteCartItem(Long itemId,HttpServletRequest request,HttpServletResponse response);

}
