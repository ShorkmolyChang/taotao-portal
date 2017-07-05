package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.PortalTbItem;
import com.taotao.portal.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	// 将商品的基本信息，描述参数，以及规格参数，可以异步加载
	// 首先加载商品的基本信息，商品的规格参数使用ajax延迟1s加载，当用户点击商品规格时，加载商品规格参数
	@RequestMapping("/item/{id}")
	// model 当需要向html页面传递数据时，才需要使用model对象
	public String getItemById(@PathVariable Long id, Model model) {
		TbItem item =  itemService.getItemById(id);
		model.addAttribute("item", item);
		return "item";
	}

	// 需要直接返回html片段，因此需要设置MediaType，默认是以json字符串返回
	@RequestMapping(value = "/item/desc/{id}", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String getItemDescById(@PathVariable Long id) {
		TbItemDesc desc = itemService.getItemDescById(id);
		if (desc != null)
			return desc.getItemDesc();
		return null;
	}

	@RequestMapping(value = "/item/param/{id}", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String getItemParamById(@PathVariable Long id) {
		String result = itemService.getItemParamByItemId(id);

		return result;
	}

}
