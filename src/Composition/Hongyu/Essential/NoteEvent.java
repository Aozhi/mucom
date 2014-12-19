package Composition.Hongyu.Essential;

/**
 * �����¼���
 */
public class NoteEvent {
	
	/**
	 * ����
	 */
	private int pitch;
	
	/**
	 * ��ʼʱ��
	 */
	private Time start;
	
	/**
	 * ��ֹʱ��
	 */
	private Time end;

	/**
	 * ����ϵ��
	 */
	private double volume;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public NoteEvent() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * �趨��ʼʱ�����ֹʱ��Ĺ��캯��
	 * @param start ��ʼʱ��
	 * @param end ��ֹʱ��
	 * @param volume �������
	 */
	public NoteEvent(Time start, Time end, double volume) {
		this.start = start;
		this.end = end;
		this.volume = volume;
	}
	
	/**
	 * �趨��ʼʱ�䡢��ֹʱ������ߵĹ��캯��
	 * @param start ��ʼʱ��
	 * @param end ��ֹʱ��
	 * @param volume �������
	 * @param pitch ����
	 */
	public NoteEvent(Time start, Time end, int pitch, double volume) {
		this.start = start;
		this.end = end;
		this.pitch = pitch;
		this.volume = volume;
	}
	
	/**
	 * ��¡����¼�����
	 * @return ��¡��Ķ���
	 */
	public NoteEvent clone() {
		return new NoteEvent(start.clone(), end.clone(), pitch, volume);
	}
	
	/**
	 * �ж�ĳ��ʱ���Ƿ�������¼��ĳ�����Χ��
	 * @param time ���ж�ʱ��
	 * @param meter ÿ�ڵ�����
	 * @return �жϽ��
	 */
	public boolean contains(Time time, int meter) {
		return contains(start, end, time, meter);
	}
	
	/**
	 * �ж�ĳ��ʱ���Ƿ��ڸ���ʱ��εĳ�����Χ��
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param time ���ж�ʱ��
	 * @param meter ÿ�ڵ�����
	 * @return �жϽ��
	 */
	public boolean contains(Time start, Time end, Time time, int meter) {
		double startPosition = start.getPosition(meter);
		double endPosition = end.getPosition(meter);
		double timePosition = time.getPosition(meter);
		return (timePosition >= startPosition && timePosition <= endPosition);
	}
	
	/**
	 * �ж�ĳ��ʱ���Ƿ�������¼��ĳ�����Χ�ཻ
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param meter ÿ�ڵ�����
	 * @return �жϽ��
	 */
	public boolean intersects(Time start, Time end, int meter) {
		return intersects(this.start, this.end, start, end, meter);
	}
	
	/**
	 * �ж�����ʱ���Ƿ��ཻ
	 * @param oneStart ��һ�ε���ʼ
	 * @param oneEnd ��һ�εĽ���
	 * @param anotherStart �ڶ��ε���ʼ
	 * @param anotherEnd �ڶ��εĽ���
	 * @param meter ÿ�ڵ�����
	 * @return �жϽ��
	 */
	public boolean intersects(Time oneStart, Time oneEnd, Time anotherStart, Time anotherEnd, int meter) {
		double oneStartPosition = oneStart.getPosition(meter);
		double oneEndPosition = oneEnd.getPosition(meter);
		double anotherStartPosition = anotherStart.getPosition(meter);
		double anotherEndPosition = anotherEnd.getPosition(meter);
		return (anotherEndPosition >= oneStartPosition && anotherStartPosition <= oneEndPosition);
	}
	
	/**
	 * ƽ�Ƹ�����С����
	 * @param bars ������С����
	 * @return ƽ�ƺ�Ķ�����
	 */
	public NoteEvent translate(int bars) {
		start.translate(bars);
		end.translate(bars);
		return this;
	}
	
	/**
	 * ת��Ϊ����
	 * @param meter ÿ�ڵ�����
	 * @return ת���������
	 */
	public Interval toInterval(int meter) {
		return new Interval(start.getPosition(meter), end.getPosition(meter));
	}
	
	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	
	public Time getStart() {
		return start.clone();
	}

	public void setStart(Time start) {
		this.start = start.clone();
	}

	public Time getEnd() {
		return end.clone();
	}

	public void setEnd(Time end) {
		this.end = end.clone();
	}
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}
	
}
