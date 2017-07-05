package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.service.ContentService;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
//	model是视图对象
	@RequestMapping("/index")
	public String showIndex(Model model) {
//		取轮播图内容
		String json=contentService.getAdNodeByCid();
//	将内容传递回页面
		model.addAttribute("ad1",json);
		
		return "index";
	}

}
