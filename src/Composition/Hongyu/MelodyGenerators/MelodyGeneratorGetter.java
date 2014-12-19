package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MelodyGenerator;

/**
 * ������������ȡ��
 */
public class MelodyGeneratorGetter {
	
	/**
	 * ����������ö��
	 */
	public static enum GeneratorsEnum {
		DefaultMelodyGenerator,
		RandomPhrasedMelody,
		WideRandomMelody,
		MarkovMelodyGenerator,
		MarkovMelodyGeneratorVariation,
		ExtremeHighMelodyGenerator,
		ExtremeLowMelodyGenerator,
		ExtremeVariousMelodyGenerator,
		ExtremeMonotonousMelodyGenerator,
		TestMelodyGenerator,
		SimpleMelodyGenerator_Style1,//�����Ը�
		SimpleMelodyGenerator_Style2,//������ܴܺ������ǿ
		SimpleMelodyGenerator_Style3,//���ƹ���
		SimpleMelodyGenerator_Style4,//�����С�����ɽ��滺�����仯��
	}
	
	/**
	 * ������������
	 */
	public static final HashMap<GeneratorsEnum, MelodyGenerator> MELODY_GENERATORS = new HashMap<>();
	
	static {
		MELODY_GENERATORS.put(GeneratorsEnum.DefaultMelodyGenerator, new DefaultMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.RandomPhrasedMelody, new RandomPhrasedMelody());
		MELODY_GENERATORS.put(GeneratorsEnum.WideRandomMelody, new WideRandomMelody());
		MELODY_GENERATORS.put(GeneratorsEnum.MarkovMelodyGenerator, new MarkovMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.MarkovMelodyGeneratorVariation, new MarkovMelodyGeneratorVariation());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeHighMelodyGenerator, new ExtremeHighMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeLowMelodyGenerator, new ExtremeLowMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeVariousMelodyGenerator, new ExtremeVariousMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.ExtremeMonotonousMelodyGenerator, new ExtremeMonotonousMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.TestMelodyGenerator, new TestMelodyGenerator());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style1, new SimpleMelodyGenerator_Style1());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style2, new SimpleMelodyGenerator_Style2());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style3, new SimpleMelodyGenerator_Style3());
		MELODY_GENERATORS.put(GeneratorsEnum.SimpleMelodyGenerator_Style4, new SimpleMelodyGenerator_Style4());
	}
	
	/**
	 * ѡȡ����������
	 * @param ���ֲ���
	 * @return ����������
	 */
	public static MelodyGenerator getMelodyGenerator(HashMap<String, Integer> parameter) {
		int pitch = parameter.get(Constant.PITCH_STRING);
		int variation = parameter.get(Constant.VARIATION_STRING);
		if (variation <= 10) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeMonotonousMelodyGenerator);
		}
		if (variation >= 90) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeVariousMelodyGenerator);
		}
		if (pitch <= 10) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeLowMelodyGenerator);
		}
		if (pitch >= 90) {
			return MELODY_GENERATORS.get(GeneratorsEnum.ExtremeHighMelodyGenerator);
		}
		return MELODY_GENERATORS.get(GeneratorsEnum.MarkovMelodyGenerator);
	}
}
