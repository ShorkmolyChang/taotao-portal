package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.portal.service.StaticPageService;

@Controller
public class StaticPageController {
	
	@Autowired
	private StaticPageService staticPageService;
	
	@RequestMapping("/gen/item/{id}")
	@ResponseBody
	public TaotaoResult genItemTemplate(@PathVariable Long id){
		
//		System.out.println("******************inside StaticPageController:genItemTemplate****************************");
		
		TaotaoResult result;
		try {
			result = staticPageService.genItemHtml(id);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
