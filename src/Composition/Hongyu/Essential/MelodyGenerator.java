package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * �������ɵĽӿ�
 */
public interface MelodyGenerator {
	
	/**
	 * ��������
	 * @param uniquePart ������һ������
	 * @param parameter ���ֲ���
	 */
	public void generateMelody(UniquePart uniquePart, HashMap<String, Integer> parameter);

}
