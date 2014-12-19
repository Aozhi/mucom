package Composition.Hongyu.Essential;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * ͨ�õĺ�����
 */
public class Common {
	
	/**
	 * �������
	 */
	private static Random random = new Random(new Date().getTime());
	
	/**
	 * ����һ�����ڵ���minimum��С��maximum���������
	 * @param minimum ��Сֵ������
	 * @param maximum ���ֵ��������
	 * @return �����
	 */
	public static int getRandomInteger(int minimum, int maximum) {
		return minimum + random.nextInt(maximum - minimum);
	}
	
	/**
	 * ����һ�����ڵ���minimum��С��maximum�����С��
	 * @param minimun ��Сֵ������
	 * @param maximum ���ֵ��������
	 * @return �����
	 */
	public static double getRandomDouble(double minimun, double maximum) {
		return minimun + (maximum - minimun) * random.nextDouble();
	}
	
	/**
	 * �趨��ָ����С
	 * @param count ָ����С
	 * @param arrayList ���趨������������
	 * @param elementClass Ԫ������
	 */
	public static <E> void setSize(int count, ArrayList<E> arrayList, Class<E> elementClass) {
		try {
			while (arrayList.size() < count) {
				arrayList.add(elementClass.newInstance());
			}
			while (arrayList.size() > count) {
				arrayList.remove(arrayList.size() - 1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ���������±꣬�ڱ�Ҫ��ʱ����������������Ĵ�С
	 * @param index �����±�
	 * @param arrayList ����չ������������
	 * @param elementClass Ԫ������
	 */
	public static <E> void expandIfNecessary(int index, ArrayList<E> arrayList, Class<E> elementClass) {
		if (index >= arrayList.size()) {
			setSize(index + 1, arrayList, elementClass);
		}
	}
	
	/**
	 * ��ȫ�Ļ������������ָ�������±������
	 * @param index �����±�
	 * @param arrayList ����������
	 * @param elementClass Ԫ������
	 * @return ��ȡ��Ԫ��
	 */
	public static <E> E getElementSafe(int index, ArrayList<E> arrayList, Class<E> elementClass) {
		expandIfNecessary(index, arrayList, elementClass);
		return arrayList.get(index);
	}
	
	/**
	 * �ж������Ƿ����ָ��Ԫ��
	 * @param array ����
	 * @param element Ԫ��
	 * @return �жϽ��
	 */
	public static <E> boolean arrayContains(E[] array, E element) {
		if (array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (element == array[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��������������һ��Ԫ��
	 * @param array ����
	 * @return ���ص�Ԫ��
	 */
	public static <E> E getRandomElement(E[] array) {
		int index = Common.getRandomInteger(0, array.length);
		return array[index];
	}
	
	/**
	 * ����a%b�ķǸ���ֵ
	 * @param a ����1
	 * @param b ����2
	 * @return ������
	 */
	public static int positiveMod(int a, int b) {
		int result = 0;
		if (a >= 0) {
			result = a % b;
		} else {
			result = (b + a % b) % b;
		}
		return result;
	}
	
	/**
	 * ��ȡ�������ϵ����С����ֵʱΪ���ԣ�����Ϊƽ��
	 * @param value ����
	 * @param min ��Сֵ
	 * @param max ���ֵ
	 * @return ���ϵ��
	 */
	public static double getCoefficient(int value, int min, int max) {
		double position = (double)(value - min) / (double)(max - min);
		if (position <= 0)
			return 0;
		if (position <= 0.5)
			return position * 2;
		return (position * position) * 4;
	}
}
