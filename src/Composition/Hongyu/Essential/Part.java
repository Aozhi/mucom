package Composition.Hongyu.Essential;

import java.util.ArrayList;

import Composition.Hongyu.Ornamenters.DefaultOrnamenter;

/**
 * ���ֵ�һ�Σ���ĳ��UniquePart����
 */
public class Part {
	
	/**
	 * װ����
	 */
	public Ornamenter ornamenter = new DefaultOrnamenter();
	
	/**
	 * ��ʼС��
	 */
	private int startBar;
	
	/**
	 * ��ֹС��
	 */
	private int endBar;
	
	/**
	 * �������ֵ�����
	 */
	private int uniquePartIndex;
	
	/**
	 * ����
	 */
	private double tempo = 1.0;
	
	/**
	 * �Ƶ�
	 */
	private int transpose;
	
	/**
	 * ��ǰ�ĵ�ʽ��
	 */
	private String scale = "MAJOR";
	
	/**
	 * ��ǰ�ĵ�ʽ
	 */
	private int[] currentScale = Constant.MAJOR_ABSOLUTE_STEPS;
	
	/**
	 * �����¼�
	 */
	private ArrayList<NoteEvent> noteEvents = new ArrayList<NoteEvent>();

	/**
	 * �ڰ���ÿ������ʱ����Ҫ�õı��
	 */
	private int arrangeHint = 3;
	
	/**
	 * ����һ���¼�
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param pitch ����
	 * @param volume �������
	 */
	public void addEvent(Time start, Time end, int pitch, double volume) {
		noteEvents.add(new NoteEvent(start.clone(), end.clone(), pitch, volume));
	}
	
	/**
	 * ��þ��Ե����ߣ����ڼ����¼�
	 * @param scaleNote �������
	 * @return ���Ե�����
	 */
	public int getAbsolutePitch(int scaleNote) {
		int scaleIndex = scaleNote - 1;
		int octaveOffset = 0;
		while (scaleIndex < 0) {
			scaleIndex += 7;
			octaveOffset--;
		}
		while (scaleIndex > 6) {
			scaleIndex -= 7;
			octaveOffset++;
		}
		return Constant.CHROMATIC_BASE + transpose + 
				currentScale[scaleIndex] + 12 * octaveOffset;
	}
	
	public int getStartBar() {
		return startBar;
	}
	
	public void setStartBar(int startBar) {
		this.startBar = startBar;
	}
	
	public int getEndBar() {
		return endBar;
	}
	
	public void setEndBar(int endBar) {
		this.endBar = endBar;
	}
	
	public int getUniquePartIndex() {
		return uniquePartIndex;
	}
	
	public void setUniquePartIndex(int uniquePartIndex) {
		this.uniquePartIndex = uniquePartIndex;
	}
	
	public double getTempo() {
		return tempo;
	}
	
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}
	
	public int getTranspose() {
		return transpose;
	}
	
	public void setTranspose(int transpose) {
		this.transpose = transpose;
	}
	
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
		currentScale = Constant.SCALE_OFFSETS_MAP.get(scale);
	}
	
	public int[] getCurrentScale() {
		return currentScale;
	}
	
	public void setCurrentScale(int[] currentScale) {
		this.currentScale = currentScale;
	}
	
	public ArrayList<NoteEvent> getNoteEvents() {
		return noteEvents;
	}
	
	public void setNoteEvents(ArrayList<NoteEvent> noteEvents) {
		this.noteEvents = noteEvents;
	}
	
	public int getArrangeHint() {
		return arrangeHint;
	}

	public void setArrangeHint(int arrangeHint) {
		this.arrangeHint = arrangeHint;
	}
}
