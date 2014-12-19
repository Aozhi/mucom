package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * ���ֵ�һ�ڣ��Ƕ�����
 */
public class UniquePhrase {
	
	/**
	 * ÿ�ڵ�����
	 */
	private int meter = 4;

	/**
	 * ����������С����
	 */
	private int bars = 2;
	
	/**
	 * �����¼�
	 */
	private ArrayList<NoteEvent> events = new ArrayList<NoteEvent>();
	
	/**
	 * �Ƿ�Ϊ���εĿ�ʼ
	 */
	private boolean startsPart;
	
	/**
	 * �Ƿ�Ϊ���εĽ���
	 */
	private boolean endsPart;
	
	/**
	 * �Ƿ�Ϊ����Ŀ�ʼ
	 */
	private boolean startsSentence;
	
	/**
	 * �Ƿ�Ϊ����Ľ���
	 */
	private boolean endsSentence;
	
	/**
	 * ���к�
	 */
	private int id;
	
	/**
	 * ���һ���¼�
	 * @param start ��ʼʱ��
	 * @param end ��ֹʱ��
	 * @param volume �������
	 */
	public void addEvent(Time start, Time end, double volume) {
		NoteEvent event = new NoteEvent(start, end, volume);
		events.add(event);
	}
	
	/**
	 * ��ȡ�¼��ĸ���
	 * @return �¼��ĸ���
	 */
	public int getEventsCount() {
		return events.size();
	}
	
	/**
	 * ��ȡָ�������±���¼�
	 * @param index �����±�
	 * @return ��ȡ�����¼�
	 */
	public NoteEvent getEvent(int index) {
		return events.get(index);
	}
	
	public int getMeter() {
		return meter;
	}

	public void setMeter(int meter) {
		this.meter = meter;
	}

	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}

	public ArrayList<NoteEvent> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<NoteEvent> events) {
		this.events = events;
	}

	public boolean isStartsPart() {
		return startsPart;
	}

	public void setStartsPart(boolean startsPart) {
		this.startsPart = startsPart;
	}

	public boolean isEndsPart() {
		return endsPart;
	}

	public void setEndsPart(boolean endsPart) {
		this.endsPart = endsPart;
	}

	public boolean isStartsSentence() {
		return startsSentence;
	}

	public void setStartsSentence(boolean startsSentence) {
		this.startsSentence = startsSentence;
	}

	public boolean isEndsSentence() {
		return endsSentence;
	}

	public void setEndsSentence(boolean endsSentence) {
		this.endsSentence = endsSentence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
