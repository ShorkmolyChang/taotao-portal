package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.PortalTbItem;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_BASE_INFO}")
	private String ITEM_BASE_INFO;
	@Value("${ITEM_DESC_INFO}")
	private String ITEM_DESC_INFO;
	@Value("${ITEM_PARAM_INFO}")
	private String ITEM_PARAM_INFO;
	
	
	@Override
	public TbItem getItemById(Long id) {
		// TODO Auto-generated method stub
		String json=HttpClientUtil.doGet(REST_BASE_URL+ITEM_BASE_INFO+"/"+id);
		TaotaoResult result=TaotaoResult.formatToPojo(json, PortalTbItem.class);
//		TaotaoResult result=TaotaoResult.formatToPojo(json, TbItem.class);
		
		return (TbItem) result.getData();
	}


	@Override
	public TbItemDesc getItemDescById(Long id) {
		// TODO Auto-generated method stub
		String json=HttpClientUtil.doGet(REST_BASE_URL+ITEM_DESC_INFO+"/"+id);
		if(json.equals("{}"))
			return null;
		TaotaoResult result=TaotaoResult.formatToPojo(json, TbItemDesc.class);
		return (TbItemDesc) result.getData();
	}


//	需要将getParamData数据转化成html字符串返回给页面
	@Override
	public String getItemParamByItemId(Long id) {
		// TODO Auto-generated method stub
		String json=HttpClientUtil.doGet(REST_BASE_URL+ITEM_PARAM_INFO+"/"+id);
		TaotaoResult result=TaotaoResult.formatToPojo(json,TbItemParamItem.class);
		if(result.getData()==null)
			return null;
		TbItemParamItem itemParamItem= (TbItemParamItem) result.getData();
		String data=itemParamItem.getParamData();
//		取规格参数并拼接成html
		List<Map> mapList=JsonUtils.jsonToList(data, Map.class);
//		遍历list生成html
		StringBuffer sb=new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for(Map map:mapList){
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			// 取规格项
			List<Map> mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
			sb.append("	</tbody>\n");
			sb.append("</table>");
		}
		return sb.toString();
		
	}
	
	

}
