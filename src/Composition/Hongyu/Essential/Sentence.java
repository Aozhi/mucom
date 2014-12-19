package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * ���ֵ�һ��
 */
public class Sentence {

	/**
	 * ����С��
	 */
	private ArrayList<Phrase> phrases = new ArrayList<Phrase>();
	
	/**
	 * ��ȡС�ڵ�����
	 * @return С�ڵ�����
	 */
	public int getPhrasesCount() {
		return phrases.size();
	}
	
	/**
	 * ��ȡָ�������±��С��
	 * @param index �����±�
	 * @return ��ȡ����С��
	 */
	public Phrase getPhrase(int index) {
		return Common.getElementSafe(index, phrases, Phrase.class);
	}
	
	/**
	 * �趨С�ڵ�����
	 * @param count �趨������
	 */
	public void setPhrasesCount(int count) {
		Common.setSize(count, phrases, Phrase.class);
	}
	
	/**
	 * ��ȡ������ʵ��С�ڵ�����
	 * @return ��ȡ��������
	 */
	public int getBarsCount() {
		int sum = 0;
		for (Phrase phrase : phrases) {
			sum += phrase.getBars();
		}
		return sum;
	}

}
