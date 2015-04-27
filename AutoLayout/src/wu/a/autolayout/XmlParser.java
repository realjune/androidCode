package wu.a.autolayout;

import java.io.InputStream;


public interface XmlParser<T> {
	/**
	 * 解析输入�? 得到Book对象集合
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public T parse(InputStream is) throws Exception;

	/**
	 * 序列化Book对象集合 得到XML形式的字符串
	 * 
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public String serialize(T books) throws Exception;
}
