package com.taotao.portal.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class StaticPageServiceImpl implements StaticPageService {

	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${STATIC_PAGE_PATH}")
	private String STATIC_PAGE_PATH;

	@Override
	public TaotaoResult genItemHtml(Long id) throws Exception {
		// TODO Auto-generated method stub
//		商品基本信息
		TbItem item=itemService.getItemById(id);
//		商品描述
		TbItemDesc itemDesc=itemService.getItemDescById(id);
//		商品规格参数
		String itemParamItem=itemService.getItemParamByItemId(id);
//		生成静态页面
		Configuration configuration=freeMarkerConfigurer.getConfiguration();
		Template template=configuration.getTemplate("item.ftl");
//		创建一条数据集
		Map root=new HashMap<>();
//		向数据集中添加属性
		root.put("item", item);
		root.put("itemDesc", itemDesc==null?" ":itemDesc.getItemDesc());
		root.put("itemParam", itemParamItem==null?" ":itemParamItem);
//		创建一个writer对象
		Writer out=new FileWriter(new File(STATIC_PAGE_PATH+id+".html"));
//		生成静态文件
		template.process(root, out);
		out.flush();
		out.close();
		return TaotaoResult.ok();
		
	}
}
