package Composition.Hongyu.Essential;

import java.util.HashMap;

import Config.Config;

public class Constant {
	
	/**
	 * ���
	 */
	public static final int[] MAJOR_ABSOLUTE_STEPS = new int[] { 0, 2, 4, 5, 7, 9, 11 };
	
	/**
	 * С��
	 */
	public static final int[] MINOR_ABSOLUTE_STEPS = new int[] { 0, 2, 3, 5, 7, 8, 10 };
	
	/**
	 * ���е�ʽ
	 */
	public static final String[] SCALES = 
			new String[] { "MAJOR", "MINOR"};
	
	/**
	 * ��ʽ���͵�ʽ�Ĺ���
	 */
	public static final HashMap<String, int[]> SCALE_OFFSETS_MAP = new HashMap<String, int[]>();
	static {
		SCALE_OFFSETS_MAP.put("MAJOR", MAJOR_ABSOLUTE_STEPS);
		SCALE_OFFSETS_MAP.put("MINOR", MINOR_ABSOLUTE_STEPS);
	}
	
	/**
	 * ��׼����
	 */
	public static final int CHROMATIC_BASE = 45;
	
	/**
	 * �����ߵ�
	 */
	public static final String PITCH_STRING = Config.MUSIC_PARA_KEY[0];
	
	/**
	 * �������
	 */
	public static final String SPEED_STRING = Config.MUSIC_PARA_KEY[1];
	
	/**
	 * �仯����
	 */
	public static final String VARIATION_STRING = Config.MUSIC_PARA_KEY[2];
	
	/**
	 * ����ʱ��
	 */
	public static final String TIME_STRING = "����ʱ��";
}
