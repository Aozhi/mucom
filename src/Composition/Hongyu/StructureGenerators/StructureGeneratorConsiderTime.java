package Composition.Hongyu.StructureGenerators;

import java.util.ArrayList;
import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.GeneratorSelection;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.StructureGenerator;
import Composition.Hongyu.GeneratorSelections.GeneratorSelectionGetter;
import Composition.Hongyu.InnerStructureGenerators.InnerStructureGeneratorGetter;
import Composition.Hongyu.Ornamenters.Appoggiatura;
import Composition.Hongyu.Ornamenters.DefaultOrnamenter;

/**
 * ���ǵ�ʱ������صĽṹ������
 */

public class StructureGeneratorConsiderTime implements StructureGenerator {

	private static final String[] structures = {
		"1",
		"12",
		"121",
		"1211",
		"21211",
		"121311",
		"1213211",
		"21213211",
		"121231211",
	};
	
	@Override
	public void generateStructure(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int meter = 4;
		int time = parameter.get(Constant.TIME_STRING);
		time += (4 - 2);
		int halfPartCount = time / 8;
		int partsCount = halfPartCount / 2;
		int transpose = getTranspose(parameter.get(Constant.PITCH_STRING));
		GeneratorSelection generatorSelection = GeneratorSelectionGetter.getGeneratorSelection(parameter);
		//�Ƿ��а������
		boolean halfPartExisted = (halfPartCount % 2 == 1);
		//�Ƿ�������
		boolean noVoicePartExisted = true;
		//�ṹ����
		String structureString = getStructureString(partsCount);
		//�Ƿ���bridge֮��
		boolean afterbridge = false;
		
		if (!halfPartExisted && partsCount < 8) {
			if (Common.getRandomInteger(0, 2) == 0 || partsCount < 4) {
				noVoicePartExisted = false;
			}
		}
		
		// initialize unique parts
		int uniquePartsCount = getUniquePartsCount(structureString);
		if (halfPartExisted) {
			musicDescription.setUniquePartsCount(uniquePartsCount + 1);
			musicDescription.getUniquePart(0).innerStructureGenerator = InnerStructureGeneratorGetter.getInnerStructureGenerator(4);
		} else {
			musicDescription.setUniquePartsCount(uniquePartsCount);
			musicDescription.getUniquePart(0).innerStructureGenerator = InnerStructureGeneratorGetter.getInnerStructureGenerator(8);
		}
		for (int i = 1; i < musicDescription.getUniquePartsCount(); i++) {
			musicDescription.getUniquePart(i).innerStructureGenerator = InnerStructureGeneratorGetter.getInnerStructureGenerator(8);
		}
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			musicDescription.getUniquePart(i).setMeter(meter);
			musicDescription.getUniquePart(i).rhythmGenerator = generatorSelection.getRhythmGenerator();
			musicDescription.getUniquePart(i).harmonyGenerator = generatorSelection.getHarmonyGenerator();
			musicDescription.getUniquePart(i).melodyGenerator = generatorSelection.getMelodyGenerator();
		}
		
		// initialize parts
		if (halfPartExisted) {
			//���ڰ�����䣬��Ϊ����
			musicDescription.setPartsCount(partsCount + 1);
			musicDescription.getPart(0).setUniquePartIndex(0);
			musicDescription.getPart(0).setArrangeHint(0);
			musicDescription.getPart(0).setTranspose(transpose - 1);
			musicDescription.getPart(0).setScale(getScale('0', parameter));
			for (int i = 1; i < musicDescription.getPartsCount(); i++){
				char ch = structureString.charAt(i - 1);
				musicDescription.getPart(i).setUniquePartIndex(ch - '0');
				musicDescription.getPart(i).setArrangeHint(getArrangeHint(ch, afterbridge));
				musicDescription.getPart(i).setTranspose(getTranspose(ch, transpose));
				musicDescription.getPart(i).setScale(getScale(ch, parameter));
				if (ch == '3') { afterbridge = true; }
			}
		}
		if (noVoicePartExisted && !halfPartExisted) {
			//�����ӵ��ǲ����ڰ������
			musicDescription.setPartsCount(partsCount);
			musicDescription.getPart(0).setUniquePartIndex(0);
			musicDescription.getPart(0).setArrangeHint(0);
			musicDescription.getPart(0).setTranspose(transpose - 1);
			musicDescription.getPart(0).setScale(getScale('0', parameter));
			for (int i = 1; i < musicDescription.getPartsCount(); i++){
				char ch = structureString.charAt(i);
				musicDescription.getPart(i).setUniquePartIndex(ch - '1');
				musicDescription.getPart(i).setArrangeHint(getArrangeHint(ch, afterbridge));
				musicDescription.getPart(i).setTranspose(getTranspose(ch, transpose));
				musicDescription.getPart(i).setScale(getScale(ch, parameter));
				if (ch == '3') { afterbridge = true; }
			}
		}
		if (!noVoicePartExisted && !halfPartExisted) {
			//û������
			musicDescription.setPartsCount(partsCount);
			for (int i = 0; i < musicDescription.getPartsCount(); i++) {
				char ch = structureString.charAt(i);
				musicDescription.getPart(i).setUniquePartIndex(ch - '1');
				musicDescription.getPart(i).setArrangeHint(getArrangeHint(ch, afterbridge));
				musicDescription.getPart(i).setTranspose(getTranspose(ch, transpose));
				musicDescription.getPart(i).setScale(getScale(ch, parameter));
				if (ch == '3') { afterbridge = true; }
			}
		}
		//�����ĳ�ʼ������
		for (int i = 0; i < musicDescription.getPartsCount(); i++)
		{
			musicDescription.getPart(i).setTempo(1.0);
			if (i < musicDescription.getPartsCount() - 1) {
				musicDescription.getPart(i).ornamenter = generatorSelection.getOrnamenter();
			} else {
				if (Common.getRandomInteger(0, 2) == 0) {
					musicDescription.getPart(i).ornamenter = new DefaultOrnamenter();
				} else {
					musicDescription.getPart(i).ornamenter = new Appoggiatura();
				}
			}
			
		}
		//����ʼ�Ķ���
		if (musicDescription.getPart(0).getArrangeHint() != 0)
			musicDescription.getPart(0).setArrangeHint(1);
		musicDescription.getPart(1).setArrangeHint(1);
		//����ظ������Σ����߱�ǰ��������һЩ
		if (structureString.endsWith("11")) {
			int lastTranspose = musicDescription.getPart(musicDescription.getPartsCount() - 2).getTranspose();
			musicDescription.getPart(musicDescription.getPartsCount() - 1).setTranspose(lastTranspose + Common.getRandomInteger(1, 3));
		}
		//��β����PS����Ϊ����о���̫����
		if ((musicDescription.getPartsCount() >= 4 && noVoicePartExisted) || 
				(musicDescription.getPartsCount() >= 6))
			musicDescription.getPart(musicDescription.getPartsCount() - 1).setArrangeHint(1);
		//ѡȡ����������
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			System.out.println();
			System.out.println("UniquePart " + i + ":");
			System.out.println(musicDescription.getUniquePart(i).rhythmGenerator);
			System.out.println(musicDescription.getUniquePart(i).harmonyGenerator);
			System.out.println(musicDescription.getUniquePart(i).melodyGenerator);
			System.out.println(musicDescription.getUniquePart(i).innerStructureGenerator);
		}
		System.out.println();
		//�������������Ϣ
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			System.out.println("part" + i + " -> unique part" + musicDescription.getPart(i).getUniquePartIndex()
					+ ", arrange hint = " + musicDescription.getPart(i).getArrangeHint()
					+ ", transpose = " + musicDescription.getPart(i).getTranspose());
			System.out.println(musicDescription.getPart(i).ornamenter);
			System.out.println();
		}
	}
	
	/**
	 * ��õ�ʽ
	 * @param ch ����Ƭ������
	 * @param parameter ����
	 * @return ��ʽ
	 */
	private String getScale(char ch, HashMap<String, Integer> parameter) {
		int pitch = parameter.get(Constant.PITCH_STRING);
		int speed = parameter.get(Constant.SPEED_STRING);
		if ((pitch * pitch + speed * speed) <= 2500) {
			if (ch == '3') {
				return "MAJOR";
			} else {
				return "MINOR";
			}
		} else {
			if (ch == '3') {
				return "MINOR";
			} else {
				return "MAJOR";
			}
		}
	}

	/**
	 * ���ݲ����趨��׼����
	 * @param pitch ����
	 * @return ��׼����
	 */
	private int getTranspose(int pitch) {
		if (pitch <= 10)
			return -3;
		if (pitch <= 20)
			return 0;
		if (pitch <= 30)
			return 2;
		if (pitch <= 50)
			return 5;
		if (pitch <= 70)
			return 7;
		if (pitch <= 80)
			return 9;
		if (pitch <= 90)
			return 12;
		return 14;
	}

	/**
	 * ����part������������
	 * @param ch ����Ƭ������
	 * @param transpose ��ǰ��׼��
	 * @return
	 */
	private int getTranspose(char ch, int transpose) {
		if (ch == '3')
			if (transpose <= 5)
				return transpose + 5;
			else
				return transpose - 5;
		return transpose + adjust();
	}

	/**
	 * ����part������ȡ�Ų�����ʾ
	 * @param ch ����Ƭ������
	 * @param afterbridge �Ƿ�����ŽӶ�
	 * @return �Ų�����ʾ
	 */
	private int getArrangeHint(char ch, boolean afterbridge) {
		if (afterbridge)
			return 3;
		if (ch == '1' && Common.getRandomInteger(0, 1) == 0)
			return 3;
		if (ch == '3')
			return 2;
		return 1;
	}

	/**
	 * ����part�ĸ�����ȡ�����ṹ
	 * @param partCount
	 * @return
	 */
	private String getStructureString(int partCount) {
		ArrayList<String> candidates = new ArrayList<>();
		for (String string : structures) {
			if (string.length() == partCount) {
				candidates.add(string);
			}
		}
		return candidates.get(Common.getRandomInteger(0, candidates.size()));
	}
	
	/**
	 * ���������ṹ��ȡ�����UniquePart�ĸ���
	 * @param structureString �����ṹ
	 * @return UniquePart�ĸ���
	 */
	private int getUniquePartsCount(String structureString) {
		int max = 1;
		for (int i = 0; i < structureString.length(); i++) {
			if (max < structureString.charAt(i) - '0') {
				max = structureString.charAt(i) - '0';
			}
		}
		return max;
	}
	
	/**
	 * ����һ����������Ի�׼������΢��
	 * @return �����
	 */
	private int adjust() {
		return Common.getRandomInteger(-1, 2);
	}
}
