package Composition.Hongyu.Essential;

import java.util.HashMap;

/**
 * ��Ⱦ�ӿ�
 */
public interface Renderer {
	
	/**
	 * ��Ⱦ���ֶ���
	 * @param renderPart ���ֶ���
	 * @param parameter ��������
	 */
	public void render(RenderPart renderPart, HashMap<String, Integer> parameter);
	
}
