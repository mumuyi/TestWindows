package com.nuaa.ai;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyObjectUtils {
	// ͨ�����ػ�ȡObject �ľ���ֵ;
		public static List<String> getObjectValue(Object object,List<String> list) throws Exception {
			if (object != null) {
				// �õ�����
				Class<?> clz = object.getClass();
				// ��ȡʵ������������ԣ�����Field����
				Field[] fields = clz.getDeclaredFields();

				for (Field field : fields) {
					// System.out.println(field.getGenericType());// ��ӡ�����������������
					
					// ���������String
					if (field.getGenericType().toString().equals("class java.lang.String")) { // ���type�������ͣ���ǰ�����"class
																								// "�����������
						// �õ������Ե�gettet����
						/**
						 * ������Ҫ˵��һ�£����Ǹ���ƴ�յ��ַ�������д��getter������
						 * ��Booleanֵ��ʱ����isXXX��Ĭ��ʹ��ide����getter�Ķ���isXXX��
						 * �������NoSuchMethod�쳣 ��˵�����Ҳ����Ǹ�gettet���� ��Ҫ�����淶
						 */
						Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));

						String val = (String) m.invoke(object);// ����getter������ȡ����ֵ
						if (val != null) {
							// System.out.println("String type:" + val);
							list.add(val);
						} else {
							list.add("");
						}

					}

					// ���������Integer
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

					// ���������Double
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

					// ���������Boolean �Ƿ�װ��
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

					// ���������boolean �����������Ͳ�һ�� �����е�˵������������� isXXX�� �Ǿ�ȫ����isXXX��
					// �����Ҳ���getter�ľ�����
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
					// ���������Date
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
					// ���������Short
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
					// �������Ҫ�������������Լ�����չ
				}
			} else {
				System.err.println("object is null");
			}
			return list;
		}

		public static List<String>getAttributeName(Object object){
			List <String> names=new ArrayList<String>();
			if (object != null) {
				// �õ�����
				Class<?> clz = object.getClass();
				// ��ȡʵ������������ԣ�����Field����
				Field[] fields = clz.getDeclaredFields();
				// ��ȡ����;
				for (Field field : fields) {
					names.add(field.getName());
				}
			}
			return names;
		}
		
		
		// ��һ���ַ����ĵ�һ����ĸ��д;
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
