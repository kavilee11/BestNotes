package com.fanshuo.android.bestnotes.app.model;

/**
 * @author fanshuo
 * @date 2012-12-27
 */
public class ProductInfo {

	private String money;
	private String currency;
	private String title;
	private String productId;
	
	/**
	 * @param money 售价
	 * @param currency 币种
	 * @param title 支付项目（付费点，最好能详细到某个功能）
	 * @param productId Google Play上的该项目的ID
	 */
	public ProductInfo(String money, String currency, String title,
			String productId) {
		super();
		this.money = money;
		this.currency = currency;
		this.title = title;
		this.productId = productId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
