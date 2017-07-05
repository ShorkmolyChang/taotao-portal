package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Value("${SEARCH_SEARCH_URL}")
	private String SEARCH_SEARCH_URL;

	@Override
	public SearchResult search(String keyword, int page, int rows) {
		// TODO Auto-generated method stub
		// 使用http请求调用taotao-search实现的服务
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword", keyword);
		param.put("page", page + "");
		param.put("rows", rows + "");
		// 调用服务
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL + SEARCH_SEARCH_URL, param);
		// 将结果转化成对象
		TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
		// 取返回结果
		SearchResult searchResult = (SearchResult) result.getData();

		return searchResult;
	}

}
