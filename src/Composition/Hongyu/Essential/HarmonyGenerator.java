package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * �������ҵĽӿ�
 */
public interface HarmonyGenerator {
	
	/**
	 * ��������
	 * @param uniquePart ������һ������
	 * @param parameter ���ֲ���
	 */
	public void generateHarmony(UniquePart uniquePart, HashMap<String, Integer> parameter);
	
}
