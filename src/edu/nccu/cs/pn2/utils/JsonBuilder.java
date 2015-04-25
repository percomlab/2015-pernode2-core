package edu.nccu.cs.pn2.utils;

import java.util.Map;

/**
 * 提供一個標準、一致的方法建構 json 字串。
 *
 * @author qrtt1
 */
public interface JsonBuilder {

	/**
	 * 將指定的屬性與內容放入欲建構的 json 字串
	 *
	 * @param property
	 * @param value
	 * @return JsonBuilder
	 */
	public JsonBuilder add(String property, String value);

	/**
	 * 將 Map 內容加入欲建構的 json 字串
	 * @param map
	 * @return JsonBuilder
	 */
	@SuppressWarnings("unchecked")
	public JsonBuilder add(Map map);

	/**
	 * 將給定的屬性與值輸出為 json 字串
	 * @param property
	 * @param value
	 * @return json 字串
	 */
	public String toJson(String property, String value);

	/**
	 * 將 Map 內容輸出為 json 字串
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toJson(Map map);

	/**
	 * 將 JsonBuilder 的內容輸出為 json 字串
	 * @return json 字串
	 */
	public String toJson();
	
	public void reset();

}
