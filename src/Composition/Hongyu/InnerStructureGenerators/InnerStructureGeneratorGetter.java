package Composition.Hongyu.InnerStructureGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.InnerStructureGenerator;

/**
 * �ڲ��ṹ��������ȡ��
 */
public class InnerStructureGeneratorGetter {
	
	/**
	 * �ڲ��ṹ������ö��
	 */
	public static enum GeneratorsEnum {
		DefaultInnerStructureGenerator,
		ShortInnerStructureGenerator,
	}
	
	/**
	 * �ڲ��ṹ��������
	 */
	public static final HashMap<GeneratorsEnum, InnerStructureGenerator> INNER_STRUCTURE_GENERATORS = new HashMap<>();
	
	static {
		INNER_STRUCTURE_GENERATORS.put(GeneratorsEnum.DefaultInnerStructureGenerator, new DefaultInnerStructureGenerator());
		INNER_STRUCTURE_GENERATORS.put(GeneratorsEnum.ShortInnerStructureGenerator, new ShortInnerStructureGenerator());
	}
	
	/**
	 * ѡȡ�ڲ��ṹ������
	 * @param barsCount С������
	 * @return �ڲ��ṹ������
	 */
	public static InnerStructureGenerator getInnerStructureGenerator(int barsCount) {
		if (barsCount >= 6)
			return INNER_STRUCTURE_GENERATORS.get(GeneratorsEnum.DefaultInnerStructureGenerator);
		if (2 <= barsCount && barsCount < 6)
			return INNER_STRUCTURE_GENERATORS.get(GeneratorsEnum.ShortInnerStructureGenerator);
		throw new IllegalArgumentException();
	}
}
