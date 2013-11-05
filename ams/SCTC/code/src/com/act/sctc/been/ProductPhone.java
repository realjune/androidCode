package com.act.sctc.been;

import java.util.List;


public class ProductPhone  {
	public   String name				;
	public   String icon				;
	public   String introduce			;
	public   int price				;
	public   String amount				;
	private final static String category="1";
	private String producer;
	private int screenWith;
	private int screenHeight;

	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public int getScreenWith() {
		return screenWith;
	}
	public void setScreenWith(int screenWith) {
		this.screenWith = screenWith;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	private List<PhoneBaseInfo>pictures;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public List<PhoneBaseInfo> getPictures() {
		return pictures;
	}
	public void setPictures(List<PhoneBaseInfo> pictures) {
		this.pictures = pictures;
	}
	public static String getCategory() {
		return category;
	}
	
}
