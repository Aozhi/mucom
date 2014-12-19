package Generate;

import java.util.HashMap;

import File.Picture;

public interface ParameterGenerate {

	/**
	 * ͼ������������ã�ÿ����ʾһ��ѵ�����е�ͼƬʱ�������øú�����������ʾ��ͼƬ�͵�ǰ�����еĲ���
	 * @param music ��ʾ��ͼƬ
	 * @param parameter ��ǰ�����еĲ���
	 */
	public void train(Picture picture, HashMap<String, Integer> parameter);
	
	/**
	 * ѵ��ͼ��������ã���ѵ���ļ��д������ʱ�������øú��������Խ���һЩ���ݴ������
	 */
	public void tidy();
	
	/**
	 * ��ͼ�������������������
	 * @param picture �����ͼ��
	 * @return ������ͼ������
	 */
	public HashMap<String, int[]> generate(Picture picture);
}
