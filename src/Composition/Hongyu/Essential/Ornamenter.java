package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * װ�����ֵĽӿ�
 */
public interface Ornamenter {
	
	/**
	 * װ������
	 * @param part һ������
	 * @param uniquePart ������һ������
	 * @param parameter ���ֲ���
	 */
	public void ornament(Part part, UniquePart uniquePart, HashMap<String, Integer> parameter);
	
}
