package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * ��������Ľӿ�
 */
public interface RhythmGenerator {
	
	/**
	 * ��������
	 * @param uniquePhrase ������һ������
	 * @param parameter ���ֲ���
	 */
	public void generateRhythm(UniquePhrase uniquePhrase, HashMap<String, Integer> parameter);
}
