package MIDI;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ������ʾ�����ֱ�ʾ��ת����
 */
public class Conversion {
	
	/**
	 * ����-��ֵת����
	 */
	private static HashMap<String, Integer> pitchConversion;
	
	/**
	 * ���������ٶ�ת��
	 * @param speed �ַ�����ʽ���ٶ�
	 * @return ������ʽ���ٶ�
	 */
	public static int convertSequenceSpeed(String speed) {
		switch (speed) {
		//60BPM
		case "slow":
			return 2;
		//90BPM
		case "slower":
			return 3;
		//120BPM
		case "medium":
			return 4;
		//150BPM
		case "faster":
			return 5;
		//180BPM
		case "fast":
			return 6;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ���������ٶ�ת��
	 * @param speed ������ʽ���ٶ�
	 * @return �ַ�����ʽ���ٶ�
	 */
	public static String convertSequenceSpeed(int speed) {
		switch (speed) {
		//60BPM
		case 2:
			return "slow";
		//90BPM
		case 3:
			return "slower";
		//120BPM
		case 4:
			return "medium";
		//150BPM
		case 5:
			return "faster";
		//180BPM
		case 6:
			return "fast";
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ����ʱֵ-����ʱֵת��
	 * @param duration ����ʱֵ
	 * @return ����ʱֵ
	 */
	public static int convertNoteDuration(String duration) {
		switch (duration) {
		//��ʱ������
		case "zero":
			return 0;
		//ʮ��������
		case "sixteenth":
			return 1;
		//�˷�����
		case "eighth":
			return 2;
		//����˷�����
		case "eighth_dot":
			return 3;
		//�ķ�����
		case "quarter":
			return 4;
		//�����ķ�����
		case "quarter_dot":
			return 6;
		//��������
		case "half":
			return 8;
		//�����������
		case "half_dot":
			return 12;
		//ȫ����
		case "whole":
			return 16;
		//����ȫ����
		case "whole_dot":
			return 24;
		//�������
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ����ʱֵ-����ʱֵת��
	 * @param duration ����ʱֵ
	 * @return ����ʱֵ
	 */
	public static String convertNoteDuration(int duration) {
		switch (duration) {
		//��ʱ������
		case 0:
			return "zero";
		//ʮ��������
		case 1:
			return "sixteenth";
		//�˷�����
		case 2:
			return "eighth";
		//����˷�����
		case 3:
			return "eighth_dot";
		//�ķ�����
		case 4:
			return "quarter";
		//�����ķ�����
		case 6:
			return "quarter_dot";
		//��������
		case 8:
			return "half";
		//�����������
		case 12:
			return "half_dot";
		//ȫ����
		case 16:
			return "whole";
		//����ȫ����
		case 24:
			return "whole_dot";
		//�������
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ����ǿ��-��������ת��
	 * @param strength ����ǿ��
	 * @return ��������
	 */
	public static int convertNoteStrength(String strength) {
		switch (strength) {
		case "ppp":
			return 36;
		case "pp":
			return 48;
		case "p":
			return 60;
		case "mp":
			return 72;
		case "mf":
			return 84;
		case "f":
			return 96;
		case "ff":
			return 108;
		case "fff":
			return 120;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ��������-����ǿ��ת��
	 * @param strength ��������
	 * @return ����ǿ��
	 */
	public static String convertNoteStrength(int strength) {
		switch (strength) {
		case 36:
			return "ppp";
		case 48:
			return "pp";
		case 60:
			return "p";
		case 72:
			return "mp";
		case 84:
			return "mf";
		case 96:
			return "f";
		case 108:
			return "ff";
		case 120:
			return "fff";
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ��ɫת��
	 * @param instrument �ַ�����ʽ����ɫ
	 * @return ������ʽ����ɫ
	 */
	public static int convertNoteInstrument(String instrument) {
		switch (instrument) {
		//����
		case "piano":
			return 0;
		//������
		case "music box":
			return 10;
		//�ַ���
		case "accordian":
			return 21;
		//����
		case "harmonica":
			return 22;
		//����
		case "guitar":
			return 24;
		//������
		case "bass":
			return 32;
		//С����
		case "violin":
			return 40;
		//������
		case "viola":
			return 41;
		//������
		case "cello":
			return 42;
		//��������
		case "contrabass":
			return 43;
		//����
		case "harp":
			return 46;
		//������
		case "timpani":
			return 47;
		//���ֺ���
		case "string":
			return 48;
		//С��
		case "trumpet":
			return 56;
		//����
		case "trombone":
			return 57;
		//���
		case "tuba":
			return 58;
		//Բ��
		case "French horn":
			return 60;
		//����˹
		case "sax":
			return 64;
		//˫�ɹ�
		case "oboe":
			return 68;
		//���
		case "bassoon":
			return 70;
		//���ɹ�
		case "clarinet":
			return 71;
		//�̵�
		case "piccolo":
			return 72;
		//����
		case "flute":
			return 73;
		//����
		case "recorder":
			return 74;
		//�������
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ��ɫת��
	 * @param instrument ������ʽ����ɫ
	 * @return �ַ�����ʽ����ɫ
	 */
	public static String convertNoteInstrument(int instrument) {
		switch (instrument) {
		//����
		case 0:
			return "piano";
		//������
		case 10:
			return "music box";
		//�ַ���
		case 21:
			return "accordian";
		//����
		case 22:
			return "harmonica";
		//����
		case 24:
			return "guitar";
		//��˾
		case 32:
			return "bass";
		//С����
		case 40:
			return "violin";
		//������
		case 41:
			return "viola";
		//������
		case 42:
			return "cello";
		//��������
		case 43:
			return "contrabass";
		//����
		case 46:
			return "harp";
		//������
		case 47:
			return "timpani";
		//���ֺ���
		case 48:
			return "string";
		//С��
		case 56:
			return "trumpet";
		//����
		case 57:
			return "trombone";
		//���
		case 58:
			return "tuba";
		//Բ��
		case 60:
			return "French horn";
		//����˹
		case 64:
			return "sax";
		//˫�ɹ�
		case 68:
			return "oboe";
		//���
		case 70:
			return "bassoon";
		//���ɹ�
		case 71:
			return "clarinet";
		//�̵�
		case 72:
			return "piccolo";
		//����
		case 73:
			return "flute";
		//����
		case 74:
			return "recorder";
		//�������
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ����ת��
	 * @param pitch �ַ�����ʽ����
	 * @return ��ֵ��ʽ����
	 */
	public static int convertNotePitch(String pitch) {
		//�ж�ת�����Ƿ񱻳�ʼ��
		if (pitchConversion == null) {
			initialPitchConversion();
		}
		//����ת����ת��
		if (pitchConversion.containsKey(pitch)) {
			return pitchConversion.get(pitch);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ����ת��
	 * @param pitch ��ֵ��ʽ����
	 * @return �ַ�����ʽ���ߣ����ڲ�Ψһ�������ArrayList<String>��ʾ
	 */
	public static ArrayList<String> convertNotePitch(int pitch) {
		//�ж�ת�����Ƿ񱻳�ʼ��
		if (pitchConversion == null) {
			initialPitchConversion();
		}
		//Ѱ���������ߵ�����ֵ������
		ArrayList<String> result = new ArrayList<String>();
		for (String key : pitchConversion.keySet()) {
			if (pitch == pitchConversion.get(key)) {
				result.add(key);
			}
		}
		//�жϲ����ĺϷ���
		if (!result.isEmpty()) {
			return result;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * ��ʼ������-��ֵת����
	 */
	private static void initialPitchConversion() {
		pitchConversion = new HashMap<String, Integer>();
		//������Сд��ĸ��ͷ������
		String[] lowerCase = new String[] {"c", "d", "e", "f", "g", "a", "b"};
		//�����ڴ�д��ĸ��ͷ������
		String[] upperCase = new String[] {"C", "D", "E", "F", "G", "A", "B"};
		//���������C���ľ���
		int[] increment = new int[] {0, 2, 4, 5, 7, 9, 11}; 
		//��������͵ļ�����
		pitchConversion.put("A2", 21);
		pitchConversion.put("#_A2", 22);
		pitchConversion.put("b_B2", 22);
		pitchConversion.put("B2", 23);
		pitchConversion.put("#_B2", 24);
		//C1~B1
		for (int i = 0; i < upperCase.length; i++) {
			pitchConversion.put("b_" + upperCase[i] + "1", 24 + increment[i] - 1);
			pitchConversion.put(upperCase[i] + "1", 24 + increment[i]);
			pitchConversion.put("#_" + upperCase[i] + "1", 24 + increment[i] + 1);
		}
		//C~B
		for (int i = 0; i < upperCase.length; i++) {
			pitchConversion.put("b_" + upperCase[i], 36 + increment[i] - 1);
			pitchConversion.put(upperCase[i], 36 + increment[i]);
			pitchConversion.put("#_" + upperCase[i], 36 + increment[i] + 1);
		}
		//c~b
		for (int i = 0; i < lowerCase.length; i++) {
			pitchConversion.put("b_" + lowerCase[i], 48 + increment[i] - 1);
			pitchConversion.put(lowerCase[i], 48 + increment[i]);
			pitchConversion.put("#_" + lowerCase[i], 48 + increment[i] + 1);
		}
		//c1~b1, c2~b2, c3~b3, c4~b4
		for (int j = 1; j <= 4; j++) {
			for (int i = 0; i < lowerCase.length; i++) {
				pitchConversion.put("b_" + lowerCase[i] + j, 48 + j * 12 + increment[i] - 1);
				pitchConversion.put(lowerCase[i] + j, 48 + j * 12 + increment[i]);
				pitchConversion.put("#_" + lowerCase[i] + j, 48 + j * 12 + increment[i] + 1);
			}
		}
		//��������ߵļ�����
		pitchConversion.put("b_c5", 107);
		pitchConversion.put("c5", 108);
	}
}
