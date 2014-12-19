package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * ���ֵ������࣬��������������Ҫ����Ϣ
 */
public class MusicDescription {
	
	/**
	 * ���ж������ֶ�
	 */
	private ArrayList<UniquePart> uniqueParts = new ArrayList<UniquePart>();
	
	/**
	 * �����ֶ�
	 */
	ArrayList<Part> parts = new ArrayList<Part>();
	
	/**
	 * �ٶ�
	 */
	private int tempo = 120;
	
	/**
	 * ��������
	 */
	ArrayList<Track> tracks = new ArrayList<Track>();

	/**
	 * ��ö����ֶεĸ���
	 * @return ��õĸ���
	 */
	public int getUniquePartsCount() {
		return uniqueParts.size();
	}
	
	/**
	 * ��ȡָ�������±�Ķ����ֶ�
	 * @param index �����±�
	 * @return �����ֶ�
	 */
	public UniquePart getUniquePart(int index) {
		return Common.getElementSafe(index, uniqueParts, UniquePart.class);
	}
	
	/**
	 * �趨�����ֶεĸ���
	 * @param count �趨�ĸ���
	 */
	public void setUniquePartsCount(int count) {
		Common.setSize(count, uniqueParts, UniquePart.class);
	}
	
	/**
	 * ����ֶεĸ���
	 * @return ��õĸ���
	 */
	public int getPartsCount() {
		return parts.size();
	}
	
	/**
	 * ��ȡָ�������±���ֶ�
	 * @param index �����±�
	 * @return �ֶ�
	 */
	public Part getPart(int index) {
		return Common.getElementSafe(index, parts, Part.class);
	}
	
	/**
	 * �趨�ֶεĸ���
	 * @param count �趨�ĸ���
	 */
	public void setPartsCount(int count) {
		Common.setSize(count, parts, Part.class);
	}
	
	/**
	 * ��ȡС�ڵ�����
	 * @return С�ڵ�����
	 */
	public int getBarsCount() {
		int bars = 0;
		for (Part part : parts) {
			int uniquePartIndex = part.getUniquePartIndex();
			UniquePart uniquePart = uniqueParts.get(uniquePartIndex);
			bars += uniquePart.getBarsCount();
		}
		return bars;
	}
	
	/**
	 * ��ȡָ���ֶε���ʼС�ڱ��
	 * @param index �ֶε������±�
	 * @return ��ʼС�ڱ��
	 */
	public int getPartStartBar(int index) {
		Part part = parts.get(index);
		return part.getStartBar();
	}

	/**
	 * ��ȡָ���ֶεĽ���С�ڱ��
	 * @param index �ֶε������±�
	 * @return ����С�ڱ��
	 */
	public int getPartEndBar(int index) {
		Part part = parts.get(index);
		return part.getEndBar();
	}
	
	/**
	 * ���һ����Ⱦ�¼�
	 * @param renderer ��Ⱦ��
	 * @param trackIndex �����
	 * @param initialStep ��ʼλ��
	 * @param finalStep ��ֹλ��
	 * @param octave �˶�
	 * @param timeOffset ʱ��ƫ��
	 * @param volume ����ϵ��
	 */
	public void addRenderEvent(Renderer renderer, int trackIndex, int initialStep, int finalStep, int octave, Time timeOffset, double volume) {
		tracks.get(trackIndex).getRenderEvents().add(new RenderEvent(renderer, initialStep, finalStep, octave, timeOffset.clone(), volume));
	}
	
	/**
	 * ���һ������
	 * @param name ������
	 */
	public void addTrack(String name) {
		tracks.add(new Track(name));
	}
	
	public ArrayList<UniquePart> getUniqueParts() {
		return uniqueParts;
	}

	public void setUniqueParts(ArrayList<UniquePart> uniqueParts) {
		this.uniqueParts = uniqueParts;
	}

	public ArrayList<Part> getParts() {
		return parts;
	}

	public void setParts(ArrayList<Part> parts) {
		this.parts = parts;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}
	
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	
}
