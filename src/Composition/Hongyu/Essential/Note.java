package Composition.Hongyu.Essential;

/**
 * ����������
 */
public class Note {
	
	/**
	 * ��ʼʱ��
	 */
	private Time start;
	
	/**
	 * ��ֹʱ��
	 */
	private Time end;
	
	/**
	 * ����
	 */
	private int pitch;
	
	/**
	 * ����
	 */
	private int volume;
	
	/**
	 * ���ι��캯��
	 * @param start ��ʼʱ��
	 * @param end ��ֹʱ��
	 * @param pitch ����
	 * @param volume ����
	 */
	public Note(Time start, Time end, int pitch, int volume) {
		this.start = start;
		this.end = end;
		this.pitch = pitch;
		this.volume = volume;
	}
	
	/**
	 * ƽ��
	 * @param bars С����
	 */
	public void translate(int bars) {
		start = start.translateClone(bars);
		end = end.translateClone(bars);
	}
	
	/**
	 * ƽ��
	 * @param time ʱ��
	 */
	public void translate(Time time) {
		start = start.translateClone(time);
		end = end.translateClone(time);
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
