package Generate;

import java.util.HashMap;

import MIDI.Music;

public interface Composition {
	
	/**
	 * ѵ���������ã�ÿ������һ��ѵ�����е�����ʱ�������øú��������벥�ŵ������͵�ǰ�����еĲ���
	 * @param music ���ŵ�����
	 * @param parameter ��ǰ�����еĲ���
	 */
	public void train(Music music, HashMap<String, Integer> parameter);
	
	/**
	 * ѵ���������ã���ѵ���ļ��д������ʱ�������øú��������Խ���һЩ���ݴ������
	 */
	public void tidy();
	
	/**
	 * ���������ã��������������ʱ�������øú�������ͼ��-���ֲ���ת����ת���Ľ����������
	 * @param parameter ͼ��-���ֲ���ת����ת���Ľ��
	 * @return ���ɵ�����
	 */
	public Music generate(HashMap<String, Integer> parameter);
}
