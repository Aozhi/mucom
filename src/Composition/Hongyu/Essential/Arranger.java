package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * �������ֽӿ�
 */
public interface Arranger {
	
	/**
	 * ��������
	 * @param musicDescription ������������
	 * @param parameter ���ֲ���
	 */
	public void arrange(MusicDescription musicDescription, HashMap<String, Integer> parameter);
	
}
