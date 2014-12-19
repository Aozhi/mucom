package Generate;

import java.util.HashMap;

import File.Picture;
import MIDI.Music;

public interface ParameterConversion {

	/**
	 * ѵ������ת�����ã�ÿ������һ��ѵ�����е�����ʱ�������øú��������벥�ŵ������͵�ǰ�����еĲ���
	 * @param music ���ŵ�����
	 * @param parameter ��ǰ�����еĲ���
	 */
	public void train(Music music, HashMap<String, Integer> parameter);
	
	/**
	 * ѵ������ת�����ã�ÿ����ʾһ��ѵ�����е�ͼƬʱ�������øú�����������ʾ��ͼƬ�͵�ǰ�����еĲ���
	 * @param music ��ʾ��ͼƬ
	 * @param parameter ��ǰ�����еĲ���
	 */
	public void train(Picture picture, HashMap<String, Integer> parameter);
	
	/**
	 * ѵ������ת�����ã���ѵ���ļ����е�ͼ�������ʱ�������øú��������Խ���һЩ���ݴ������
	 */
	public void tidyPicture();
	
	/**
	 * ѵ������ת�����ã���ѵ���ļ����е����ִ������ʱ�������øú��������Խ���һЩ���ݴ������
	 */
	public void tidyMusic();
	
	/**
	 * ת�������ã���ͼ��Ĳ�����ȡ���ʱ�������ú�����ת��Ϊ���ֵĲ���
	 * @param parameter ͼ�����
	 * @return ���ɵ����ֲ���
	 */
	public HashMap<String, Integer> convert(HashMap<String, int[]> parameter);
}
