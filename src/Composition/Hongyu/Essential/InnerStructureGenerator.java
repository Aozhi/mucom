package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * �����ڲ��ṹ�Ľӿ�
 */
public interface InnerStructureGenerator {
	
	/**
	 * �����ڲ��ṹ
	 * @param uniquePart ������һ������
	 * @param parameter ���ֲ���
	 */
	public void generateInnerStructure(UniquePart uniquePart, HashMap<String, Integer> parameter);

}
