package Composition.Hongyu.StructureGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.StructureGenerator;

/**
 * �ṹ��������ȡ��
 */
public class StructureGeneratorGetter {

	/**
	 * �ṹ������ö��
	 */
	public static enum GeneratorsEnum {
		DefaultStructureGenerator,
		StructureGeneratorConsiderTime,
	}

	/**
	 * �ṹ��������
	 */
	public static final HashMap<GeneratorsEnum, StructureGenerator> MELODY_GENERATORS = new HashMap<>();
	
	static {
		MELODY_GENERATORS.put(GeneratorsEnum.DefaultStructureGenerator, new DefaultStructureGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.StructureGeneratorConsiderTime, new StructureGeneratorConsiderTime());
	}
	
	/**
	 * ѡȡ�ṹ������
	 * @param ���ֲ���
	 * @return �ṹ������
	 */
	public static StructureGenerator getStructureGenerator(HashMap<String, Integer> parameter) {
		return MELODY_GENERATORS.get(GeneratorsEnum.StructureGeneratorConsiderTime);
	}
}
