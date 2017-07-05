package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class PortalTbItem extends TbItem {
	
	public String[] getImages() {
		// TODO Auto-generated method stub
		String[] strings=this.getImage().split(",");
		return strings;
	}

}
