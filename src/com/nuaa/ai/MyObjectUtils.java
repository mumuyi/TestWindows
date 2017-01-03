package com.nuaa.ai;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyObjectUtils {
	// 通过返回获取Object 的具体值;
		public static List<String> getObjectValue(Object object,List<String> list) throws Exception {
			if (object != null) {
				// 拿到该类
				Class<?> clz = object.getClass();
				// 获取实体类的所有属性，返回Field数组
				Field[] fields = clz.getDeclaredFields();

				for (Field field : fields) {
					// System.out.println(field.getGenericType());// 打印该类的所有属性类型
					
					// 如果类型是String
					if (field.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
																								// "，后面跟类名
						// 拿到该属性的gettet方法
						/**
						 * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
						 * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
						 * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
						 */
						Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));

						String val = (String) m.invoke(object);// 调用getter方法获取属性值
						if (val != null) {
							// System.out.println("String type:" + val);
							list.add(val);
						} else {
							list.add("");
						}

					}

					// 如果类型是Integer
					if (field.getGenericType().toString().equals("class java.lang.Integer")) {
						Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						Integer val = (Integer) m.invoke(object);
						if (val != null) {
							// System.out.println("Integer type:" + val);
							list.add(val.toString());
						} else {
							list.add("");
						}

					}

					// 如果类型是Double
					if (field.getGenericType().toString().equals("class java.lang.Double")) {
						Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						Double val = (Double) m.invoke(object);
						if (val != null) {
							// System.out.println("Double type:" + val);
							list.add(val.toString());
						} else {
							list.add("");
						}

					}

					// 如果类型是Boolean 是封装类
					if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
						Method m = (Method) object.getClass().getMethod(field.getName());
						Boolean val = (Boolean) m.invoke(object);
						if (val != null) {
							// System.out.println("Boolean type:" + val);
							list.add(val.toString());
						} else {
							list.add("");
						}

					}

					// 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
					// 反射找不到getter的具体名
					if (field.getGenericType().toString().equals("boolean")) {
						Method m = (Method) object.getClass().getMethod(field.getName());
						Boolean val = (Boolean) m.invoke(object);
						if (val != null) {
							// System.out.println("boolean type:" + val);
							list.add(val.toString());
						} else {
							list.add("");
						}

					}
					// 如果类型是Date
					if (field.getGenericType().toString().equals("class java.util.Date")) {
						Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						Date val = (Date) m.invoke(object);
						if (val != null) {
							// System.out.println("Date type:" + val);
							list.add(val.toString());
						} else {
							list.add("");
						}

					}
					// 如果类型是Short
					if (field.getGenericType().toString().equals("class java.lang.Short")) {
						Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						Short val = (Short) m.invoke(object);
						if (val != null) {
							// System.out.println("Short type:" + val);
							list.add(val.toString());
						} else {
							list.add("");
						}
					}
					// 如果还需要其他的类型请自己做扩展
				}
			} else {
				System.err.println("object is null");
			}
			return list;
		}

		public static List<String>getAttributeName(Object object){
			List <String> names=new ArrayList<String>();
			if (object != null) {
				// 拿到该类
				Class<?> clz = object.getClass();
				// 获取实体类的所有属性，返回Field数组
				Field[] fields = clz.getDeclaredFields();
				// 获取名字;
				for (Field field : fields) {
					names.add(field.getName());
				}
			}
			return names;
		}
		
		
		// 把一个字符串的第一个字母大写;
		private static String getMethodName(String fildeName) throws Exception {
			if (fildeName.charAt(0) >= 'a' && fildeName.charAt(0) <= 'z') {
				byte[] items = fildeName.getBytes();
				items[0] = (byte) ((char) items[0] - 'a' + 'A');
				return new String(items);
			} else {
				return fildeName;
			}
		}
}
