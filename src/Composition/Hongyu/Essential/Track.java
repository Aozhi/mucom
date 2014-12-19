package Composition.Hongyu.Essential;

import java.util.ArrayList;

public class Track {
	
	/**
	 * ��Ⱦ�¼�
	 */
	private ArrayList<RenderEvent> renderEvents = new ArrayList<>();
	
	/**
	 * ����
	 */
	private String name;
	
	/**
	 * ���캯��
	 * @param name ����
	 */
	public Track(String name) {
		this.name = name;
	}

	public ArrayList<RenderEvent> getRenderEvents() {
		return renderEvents;
	}

	public void setRenderEvents(ArrayList<RenderEvent> renderEvents) {
		this.renderEvents = renderEvents;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	public int getPatch() {
		return patch;
	}

	public void setPatch(int patch) {
		this.patch = patch;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getPan() {
		return pan;
	}

	public void setPan(int pan) {
		this.pan = pan;
	}
	*/
}
