package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Constant;

/**
 * �Ų�����ȡ��
 */
public class ArrangerGetter {
		
	/**
	 * �Ų���ö��
	 */
	public static enum GeneratorsEnum {
		SimpleArranger,//�򵥵��Ų�
		DefaultArranger,//�ϼ򵥵��Ų�
		NormalArranger,//��ͨ���Ų�
		ComplexArranger,//�ϸ��ӵ��Ų�
		PianoAdvancedClassical,//���ӷḻ���Ų�
		DefaultArrangerOrnament,//ԭTestArranger
		TestMelody,//��������
		TestHarmony,//���Ժ���
	}
	
	/**
	 * �Ų�����
	 */
	public static final HashMap<GeneratorsEnum, Arranger> ARRANGERS = new HashMap<>();
	
	static {
		ARRANGERS.put(GeneratorsEnum.SimpleArranger, new SimpleArranger());
		ARRANGERS.put(GeneratorsEnum.DefaultArranger, new DefaultArranger());
		ARRANGERS.put(GeneratorsEnum.NormalArranger, new NormalArranger());
		ARRANGERS.put(GeneratorsEnum.ComplexArranger, new ComplexArranger());
		ARRANGERS.put(GeneratorsEnum.PianoAdvancedClassical, new PianoAdvancedClassical());
		ARRANGERS.put(GeneratorsEnum.DefaultArrangerOrnament, new DefaultArrangerOrnament());
		ARRANGERS.put(GeneratorsEnum.TestMelody, new TestMelody());
		ARRANGERS.put(GeneratorsEnum.TestHarmony, new TestHarmony());
	}
	
	/**
	 * ѡȡ�Ų���
	 * @param ���ֲ���
	 * @return �Ų���
	 */
	public static Arranger getArranger(HashMap<String, Integer> parameter) {
		int speed = parameter.get(Constant.SPEED_STRING);
		if (speed <= 20)
			return ARRANGERS.get(GeneratorsEnum.SimpleArranger);
		if (speed <= 40)
			return ARRANGERS.get(GeneratorsEnum.DefaultArranger);
		if (speed <= 60)
			return ARRANGERS.get(GeneratorsEnum.NormalArranger);
		if (speed <= 80)
			return ARRANGERS.get(GeneratorsEnum.ComplexArranger);
		return ARRANGERS.get(GeneratorsEnum.PianoAdvancedClassical);
	}
	
}
