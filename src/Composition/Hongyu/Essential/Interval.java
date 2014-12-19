package Composition.Hongyu.Essential;

public class Interval {
	
	/**
	 * �½�
	 */
	private double lower = 0.0;
	
	/**
	 * �Ͻ�
	 */
	private double upper = 1.0;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public Interval() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��ʼ���������½�Ĺ��캯��
	 * @param lower �½�
	 * @param upper �Ͻ�
	 */
	public Interval(double lower, double upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	/**
	 * ��¡����
	 * @return ��¡��Ķ���
	 */
	public Interval clone() {
		return new Interval(lower, upper);
	}
	
	/**
	 * ����������Ľ���
	 * @param interval ��һ������
	 * @return ������Ľ��
	 */
	public Interval intersect(Interval interval) {
		lower = Math.max(lower, interval.lower);
		upper = Math.min(upper, interval.upper);
		return this;
	}
	
	/**
	 * ����������Ľ�������¡
	 * @param interval ��һ������
	 * @return ��������¡��Ľ��
	 */
	public Interval intersectClone(Interval interval) {
		return clone().intersect(interval);
	}
	
	/**
	 * ��ȡ���䳤��
	 * @return ����ĳ���
	 */
	public double getLength() {
		return Math.max(upper - lower, 0);
	}

	/**
	 * �ж�������Ƿ����ָ��ʱ��
	 * @param position
	 * @return
	 */
	public boolean contains(double position) {
		return (position >= this.lower && position <= this.upper);
	}
}
