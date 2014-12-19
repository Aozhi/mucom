package Composition.Hongyu.Essential;

/**
 * ʱ����
 */
public class Time {
	
	/**
	 * ��ʼС��
	 */
	public int startBar;
	/**
	 * ���λ��
	 */
	public double position;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public Time() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ���ι��캯��
	 * @param startBar ��ʼС��
	 * @param position ���λ��
	 */
	public Time(int startBar, double position) {
		this.startBar = startBar;
		this.position = position;
	}
	
	/**
	 * ��ȡ��ǰ����λ��
	 * @param meter ÿ�ڵ�����
	 * @return ��ǰ����λ��
	 */
	public double getPosition(int meter) {
		return startBar + position / meter;
	}
	
	/**
	 * ��¡ʱ�����
	 * @return ��¡���ʱ�����
	 */
	public Time clone() {
		return new Time(startBar, position);
	}
	
	/**
	 * ƽ��ʱ��
	 * @param bar ƽ�ƵĽ�����
	 */
	public void translate(int bars) {
		startBar += bars;
	}
	
	/**
	 * ƽ��ʱ��
	 * @param time ƽ�Ƶ�ʱ�����
	 */
	public void translate(Time time) {
		startBar += time.startBar;
		position += time.position;
	}
	
	/**
	 * ƽ�Ʋ���¡����
	 * @param bars ƽ�ƵĽ�����
	 * @return ƽ�Ʋ���¡���ʱ�����
	 */
	public Time translateClone(int bars) {
		Time cloneTime = clone();
		cloneTime.translate(bars);
		return cloneTime;
	}

	/**
	 * ƽ�Ʋ���¡����
	 * @param time ƽ�Ƶ�ʱ�����
	 * @return ƽ�Ʋ���¡���ʱ�����
	 */
	public Time translateClone(Time time) {
		Time cloneTime = clone();
		cloneTime.translate(time);
		return cloneTime;
	}
	
}
