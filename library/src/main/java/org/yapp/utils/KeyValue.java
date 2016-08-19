package org.yapp.utils;


/**
 * ClassName: KeyValue <br>
 * Description: 键值对对象. <br>
 * Date: 2015-12-2 下午2:49:12 <br>
 * 
 * @author ysj
 * @version
 * @since JDK 1.7
 */
public class KeyValue {
	public final String key;
	public final Object value;

	public KeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getValueStr() {
		return value == null ? null : value.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		KeyValue keyValue = (KeyValue) o;

		return key == null ? keyValue.key == null : key.equals(keyValue.key);

	}

	@Override
	public int hashCode() {
		return key != null ? key.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "KeyValue{" + "key='" + key + '\'' + ", value=" + value + '}';
	}
}
