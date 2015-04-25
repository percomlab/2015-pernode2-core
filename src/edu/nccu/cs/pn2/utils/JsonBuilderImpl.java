package edu.nccu.cs.pn2.utils;

import java.util.Map;

import org.json.simple.JSONObject;

/**
 * JsonBuilderImpl 物件實體可由 <b>JsonUtils.createBuilder()</b> 靜態方法獲得。 每次呼叫 JsonUtils.createBuilder() 方法都會建立新的 JsonBuilder
 * 物件。 以下列 json 字串為例： <br />
 * {"XXX":"xxx", "YYY":"yyy", "ZZZ":"zzz"} <br/>
 * <br/>
 * 我們可以利用 JsonBuilder 建構它。 <h4>以單一屬性建構 json 字串：</h4>
 * 
 * <pre>
 * JsonUtils.createBuilder().add(&quot;XXX&quot;, &quot;xxx&quot;).add(&quot;YYY&quot;, &quot;yyy&quot;).add(&quot;ZZZ&quot;, &quot;zzz&quot;).toJson();
 * </pre>
 * 
 * <h4>以 Map 建構 json 字串：</h4>
 * 
 * <pre>
 * Map map = new HashMap();
 * map.put(&quot;XXX&quot;, &quot;xxx&quot;);
 * map.put(&quot;YYY&quot;, &quot;yyy&quot;);
 * map.put(&quot;ZZZ&quot;, &quot;zzz&quot;);
 * 
 * JsonUtils.createBuilder().add(map).toJson();
 * JsonUtils.createBuilder().toJson(map);
 * </pre>
 * 
 * <h4>以 Map 混合單一屬性建構 json 字串：</h4>
 * 
 * <pre>
 * Map map = new HashMap();
 * map.put(&quot;XXX&quot;, &quot;xxx&quot;);
 * map.put(&quot;ZZZ&quot;, &quot;zzz&quot;);
 * 
 * JsonUtils.createBuilder().add(map).add(&quot;YYY&quot;, &quot;yyy&quot;).toJson();
 * </pre>
 * 
 * @author qrtt1
 */
public class JsonBuilderImpl implements JsonBuilder
{

    JSONObject json = new JSONObject();

    @SuppressWarnings("unchecked")
    public JsonBuilder add(String property, String value)
    {
        json.put(property, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public JsonBuilder add(Map map)
    {
        json.putAll(map);
        return this;
    }

    public String toJson()
    {
        return json.toString();
    }

    public String toJson(String property, String value)
    {
        return add(property, value).toJson();
    }

    @SuppressWarnings("unchecked")
    public String toJson(Map map)
    {
        return add(map).toJson();
    }

    @Override
    public String toString()
    {
        return toJson();
    }

    @Override
    public void reset()
    {
        json.clear();
    }

}
