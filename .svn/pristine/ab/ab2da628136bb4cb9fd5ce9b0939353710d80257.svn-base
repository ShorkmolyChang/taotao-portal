package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping("/search")
	// 需要返回一个jsp页面
	public String search(@RequestParam("q") String keyword, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "60") int rows, Model modle) {

		// get字符集处理
		try {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
			keyword = "";
			e.printStackTrace();
		}
		// 返回结果
		SearchResult searchResult = searchService.search(keyword, page, rows);
		// 传递参数给页面
		modle.addAttribute("query", keyword);
		modle.addAttribute("totalPages", searchResult.getPageCount());
		modle.addAttribute("itemList", searchResult.getItemList());
		modle.addAttribute("page", searchResult.getCurlPage());
		// 返回逻辑页面
		return "search";
	}

}
