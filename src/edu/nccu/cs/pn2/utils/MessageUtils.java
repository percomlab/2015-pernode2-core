package edu.nccu.cs.pn2.utils;

import java.io.StringReader;
import java.util.Map;
import java.util.WeakHashMap;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MessageUtils 是一套重新包裝的 library，以 <b>Jackson JSON Processor</b> 處理 json 字串解析； 以 <b>Simple Java toolkit for JSON</b> 處理
 * json 字串建構。 包裝成一個 JsonUtils 目的在提供共通的MessageUtils，讓 INSIGHT SDK 不直接與其他 json library 產生相依關係。 <br />
 * <br />
 * 對於解析 json 字串，JsonUtils 提供 get 靜態方法，它被多載成三種型式：
 * <ul>
 * <li>public static java.lang.String get(java.lang.String json, java.lang.String property)
 * <li>public static java.util.Map<java.lang.String,java.lang.String> get(java.lang.String json, java.lang.String...
 * properties)
 * <li>public static java.util.Map<java.lang.String,java.lang.String> get(java.lang.String json)
 * </ul>
 * 以下列 json 字串為例： <br />
 * {"XXX":"xxx", "YYY":"yyy", "ZZZ":"zzz"} <br/>
 * 
 * <pre>
 * // 結果為 xxx
 * 	MessageUtils.get(jsonRawString, &quot;XXX&quot;)
 * 
 * 	// 結果為一個 Map&lt;String, String&gt;,
 * 	// 	含 XXX 為 key, xxx 為 value 的內容；YYY 為 key, yyy 為 value 的內容
 * 	MessageUtils.get(jsonRawString, &quot;XXX&quot;, &quot;YYY&quot;)
 * 
 * 	// 結果為一個 Map&lt;String, String&gt;,
 * 	//	含 XXX 為 key, xxx 為 value 的內容；YYY 為 key, yyy 為 value 的內容；ZZZ 為 key, zzz 為 value 的內容
 * 	MessageUtils.get(jsonRawString)
 * </pre>
 * 
 * @author qrtt1
 */
public class MessageUtils
{
    final static JsonFactory _factory = new JsonFactory();

    static Logger log = LoggerFactory.getLogger(MessageUtils.class);

    protected static JsonParser execute(String json, String property)
    {

        if (json == null || property == null)
        {
            log.warn("json or property is null. [json: " + json + ", property: " + property + "]");
            return null;
        }

        /*
         * parser will read many token which follow the sequence FIELD, VALUE, FIELD, VALUE ...
         */
        try
        {
            JsonParser jp = _factory.createJsonParser(new StringReader(json));
            JsonToken t = null;
            while ((t = jp.nextToken()) != null)
            {

                // not found the matched field which has the name: property
                if (!JsonToken.FIELD_NAME.equals(t) || !jp.getCurrentName().equals(property))
                {
                    continue;
                }

                // return matching field's next token (value token)
                jp.nextToken();
                return jp;
            }
        }
        catch (Exception e)
        {
            log.error("json string is: " + json, e);
        }

        log.info("no match property: " + property);
        return null;
    }

    /**
     * 在 json 字串內，取得單一屬性的值。
     * 
     * @param json
     *            json字串
     * @param property
     *            json字串內的屬性名稱
     * @return 屬性名稱對性的內容，如果沒有這個屬性就會是空字串
     */
    public static String get(String json, String property)
    {
        JsonParser p = execute(json, property);
        try
        {
            return p == null ? "" : p.getText();
        }
        catch (Exception e)
        {
            log.error("", e);
        }
        return "";
    }

    /**
     * 在 json 字串內，取得指定屬性的值。
     * 
     * @param json
     *            json字串
     * @param properties
     *            json字串內的屬性名稱，您可以輸入多個屬性名稱以「,」分隔
     * @return 回傳一個 Map，如果沒有任何屬性名稱出現在裡面，這將是個空的 Map
     */
    public static Map<String, String> get(String json, String... properties)
    {
        Map<String, String> map = new WeakHashMap<String, String>();
        for (String prop : properties)
        {
            String v = get(json, prop);
            if (v != null && !"".equals(v))
            {
                map.put(prop, v);
            }
        }
        return map;
    }

    /**
     * 在 json 字串內，取得所有屬性的值。
     * 
     * @param json
     *            json字串
     * @return 回傳一個 Map，如果 json 字串是 null、空字串或不合法的 json 字串，這將是個空的 Map。
     */
    public static Map<String, String> get(String json)
    {
        Map<String, String> map = new WeakHashMap<String, String>();
        if (json == null || "".equals(json.trim()))
        {
            return map;
        }

        try
        {
            JsonParser jp = _factory.createJsonParser(new StringReader(json));
            JsonToken t = null;
            while ((t = jp.nextToken()) != null)
            {
                if (!JsonToken.FIELD_NAME.equals(t))
                {
                    continue;
                }

                String key = jp.getCurrentName();
                jp.nextToken();
                String value = jp.getText();
                map.put(key, value);
            }
        }
        catch (Exception e)
        {
        }

        return map;
    }

    /**
     * 取得 JsonBuilder，它可用來建構 json 字串。
     * 
     * @return JsonBuilder
     */
    public static JsonBuilder jsonBuilder()
    {
        return new JsonBuilderImpl();
    }
    
    public static MessagePropertiesBuilder propertiesBuilder()
    {
        return new MessagePropertiesBuilder();
    }

}
