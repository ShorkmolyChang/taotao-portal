package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
//	不能定义为static的，不能从properties中获取出数据
	private String REST_BASE_VALUE;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${REST_CONTENT_AD1_CID}")
	private String REST_CONTENT_AD1_CID;

	// 向rest发送get/post请求获取数据，不需要注入mapper
	// 获取大广告位的内容
	@Override
	public String getAdNodeByCid() {
		// TODO Auto-generated method stub
//		向rest发送http请求，并获取结果
		String url=REST_BASE_VALUE + REST_CONTENT_URL + REST_CONTENT_AD1_CID;
		String json = HttpClientUtil.doGet(url);
		TaotaoResult result = TaotaoResult.formatToList(json, TbContent.class);
		List<TbContent> contents = (List<TbContent>) result.getData();

//		System.out.println("*******************"+contents.size()+"**************");
		
		List<AdNode> list = new ArrayList<AdNode>();
		for (TbContent content : contents) {
			AdNode node = new AdNode();
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(content.getPic());

			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(content.getPic2());

			node.setAlt(content.getSubTitle());
			node.setHref(content.getUrl());

			list.add(node);
		}

		// 将结果列表转化成json字符串
		String jsonString = JsonUtils.objectToJson(list);

		return jsonString;
	}

}
