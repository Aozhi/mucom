package Composition.Hongyu.Essential;

/**
 * ���ֵ�һ�ڣ���ĳ��UniquePhrase����
 */
public class Phrase {
	
	/**
	 * һ���������ĵ�����С����
	 */
	private int bars = 2;
	
	/**
	 * ��Ӧ����С�ڵ�id
	 */
	private int uniquePhraseId;
	
	/**
	 * ��Ӧ����С��
	 */
	private UniquePhrase uniquePhrase;
	
	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}

	public int getUniquePhraseId() {
		return uniquePhraseId;
	}

	public void setUniquePhraseId(int uniquePhraseId) {
		this.uniquePhraseId = uniquePhraseId;
	}

	public UniquePhrase getUniquePhrase() {
		return uniquePhrase;
	}

	public void setUniquePhrase(UniquePhrase uniquePhrase) {
		this.uniquePhrase = uniquePhrase;
	}

}
