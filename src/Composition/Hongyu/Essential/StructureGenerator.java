package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * �����ṹ�Ľӿ�
 */
public interface StructureGenerator {
	
	/**
	 * �����ṹ
	 * @param musicDescription ������������
	 * @param parameter ���ֲ���
	 */
	public void generateStructure(MusicDescription musicDescription, HashMap<String, Integer> parameter);
	
}
