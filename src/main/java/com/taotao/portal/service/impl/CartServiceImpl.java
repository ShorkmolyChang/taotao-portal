package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ItemService itemService;
	@Value("${CART_COOKIE_EXPIRE}")
	private int CART_COOKIE_EXPIRE;

	// 该方法将购物车存入cookie中，不安全，推荐cookie+数据库持久化相结合
	@Override
	public boolean addCart(Long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// 从cookie中取购物车列表
		List<CartItem> list = getCartFromCookie(request);
		// 查询cookie中是否有该商品，有则数量增加
		boolean flag = false;
		for (int i = 0; list != null && i < list.size(); i++) {
			// 包装数据类型，不能直接用==进行相比。两个对象直接==比较的是对象地址的值
			if (list.get(i).getId().longValue() == itemId.longValue()) {
				list.get(i).setNum(list.get(i).getNum() + num);
				flag = true;
				break;
			}
		}
		// 购物车列表中没有改商品，是一个新商品，需要获取商品信息。
		if (!flag) {
			TbItem item = itemService.getItemById(itemId);
			if (item == null)
				return false;
			CartItem cartItem = new CartItem();
			cartItem.setId(item.getId());
			cartItem.setNum(num);
			cartItem.setPrice(item.getPrice());
			cartItem.setTitle(item.getTitle());
			// 只保存一张图片信息
			if (!StringUtils.isBlank(item.getImage())) {
				String[] image = item.getImage().split(",");
				cartItem.setImage(image);
			}
			// 将新商品信息添加到cookie中
			list.add(cartItem);
		}
		// 将list重新写入cookie中,COOKIE有效期5天
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), CART_COOKIE_EXPIRE, true);
		return true;
	}

	public List<CartItem> getCartFromCookie(HttpServletRequest request) {
		// 从cookie中取数据
		String json = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (StringUtils.isBlank(json))
			return new ArrayList<CartItem>();
		// 将json转化成java对象
		List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
		return list;
	}

	@Override
	public List<CartItem> showCart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return getCartFromCookie(request);
	}

	@Override
	public boolean updateCartItem(Long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// 从cookie中取出商品列表
		List<CartItem> list = getCartFromCookie(request);
		// 更新该商品的数量
		for (CartItem item : list) {
			if (item.getId().longValue() == itemId) {
				item.setNum(num);
				break;
			}
		}
		// 写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), CART_COOKIE_EXPIRE, true);
		return true;
	}

	@Override
	public boolean deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// 从cookie中取出购物车列表
		List<CartItem> list = getCartFromCookie(request);
		// 遍历列表，删除指定id的cartItem
		for (CartItem item : list) {
			if (item.getId().longValue() == itemId) {
				list.remove(item);
				break;
			}
		}
		// 重新将购物车列表写入cookie中
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), CART_COOKIE_EXPIRE, true);
		return true;
	}

}
