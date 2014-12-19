package Composition.Hongyu.Essential;

/**
 * ������
 */
public class Harmonic {

	/**
	 * ��ʼʱ��
	 */
	private Time startTime;
	
	/**
	 * ����ʱ��
	 */
	private Time endTime;
	
	/**
	 * �������
	 */
	private double volume;
	
	/**
	 * ��׼��
	 */
	private int baseNote;
	
	/**
	 * ����������ƫ��
	 */
	private int[] offsets;
	
	/**
	 * ����ģʽ
	 */
	private String chordString;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public Harmonic() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���ι��캯��
	 * @param time ��ʼʱ��
	 * @param baseNote ��׼��
	 * @param chordString ��������
	 * @param volume �������
	 */
	public Harmonic(Time time, int baseNote, String chordString, double volume) {
		this.startTime = time;
		this.baseNote = baseNote;
		this.chordString = chordString;
		this.volume = volume;
		this.offsets = new int[chordString.length()];
		for (int i = 0; i < chordString.length(); i++) {
			char numberChar = chordString.charAt(i);
			if (numberChar >= '1' && numberChar <= '7') {
				String numberString = "" + numberChar;
				this.offsets[i] = Integer.parseInt(numberString) - 1;
			}
		}
	}
	
	/**
	 * ת��Ϊ����
	 * @param meter ÿ�ڵ�����
	 * @return ת���������
	 */
	public Interval toInterval(int meter) {
		return new Interval(startTime.getPosition(meter), endTime.getPosition(meter));
	}
	
	/**
	 * ��ȡ���������Ķ�
	 * @return ���������Ķ�����
	 */
	public Integer[] getScaleDegrees() {
		Integer[] result = new Integer[offsets.length];
		for (int i = 0; i < offsets.length; i++) {
			result[i] = (baseNote - 1 + offsets[i]) % 7;
		}
		return result;
	}
	
	/**
	 * ��¡�������
	 * @return ��¡����
	 */
	public Harmonic clone() {
		Harmonic result = new Harmonic();
		result.offsets = offsets.clone();
		result.startTime = startTime.clone();
		result.endTime = endTime.clone();
		result.volume = volume;
		result.baseNote = baseNote;
		result.chordString = chordString;
		return result;
	}
	
	/**
	 * ƽ����ʼʱ�����ֹʱ��
	 * @param bars ƽ�Ƶ�С����
	 */
	public void translate(int bars) {
		startTime = startTime.translateClone(bars);
		endTime = endTime.translateClone(bars);
	}
	
	public Time getStartTime() {
		return startTime.clone();
	}
	
	public void setStartTime(Time time) {
		startTime = time.clone();
	}
	
	public Time getEndTime() {
		return endTime.clone();
	}
	
	public void setEndTime(Time time) {
		endTime = time.clone();
	}
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	
	public int getBaseNote() {
		return baseNote;
	}

	public void setBaseNote(int baseNote) {
		this.baseNote = baseNote;
	}
	
	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public String getChordString() {
		return chordString;
	}

	public void setChordString(String chordString) {
		this.chordString = chordString;
	}

}
