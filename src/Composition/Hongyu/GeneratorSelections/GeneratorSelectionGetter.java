package Composition.Hongyu.GeneratorSelections;

import java.util.HashMap;

import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.GeneratorSelection;

public class GeneratorSelectionGetter {

	public static GeneratorSelection getGeneratorSelection(HashMap<String, Integer> parameter) {
		int variation = parameter.get(Constant.VARIATION_STRING);
		//����ϴ�����
		if (inRange(variation, 80, Integer.MAX_VALUE)) {
			return new VariousSelection(parameter);
		}
		return new CustomSelection(parameter);
	}
	
	/**
	 * ���value����ֵ�Ƿ���ڵ���minimum��С��maximum
	 * @param value ������ֵ
	 * @param minimum ��Сֵ��������
	 * @param maximum ���ֵ����������
	 * @return �Ƿ�������������
	 */
	public static boolean inRange(int value, int minimum, int maximum) {
		return (value >= minimum && value < maximum);
	}
	
}
