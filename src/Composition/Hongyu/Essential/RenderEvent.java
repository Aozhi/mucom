package Composition.Hongyu.Essential;

/**
 * ��Ⱦ���¼�
 */
public class RenderEvent {

	/**
	 * ���õ���Ⱦ��ʽ
	 */
	private final Renderer renderer;
	
	/**
	 * ��ʼ
	 */
	private final int initialStep;
	
	/**
	 * ��ֹ
	 */
	private final int finalStep;

	/**
	 * �˶�
	 */
	private final int octave;
	
	/**
	 * ʱ��ƫ��
	 */
	private final Time timeOffset;
	
	/**
	 * ����ϵ��
	 */
	private final double volume;
	
	/**
	 * ���캯��
	 * @param track �����
	 * @param initialStep ��ʼλ��
	 * @param finalStep ��ֹλ��
	 * @param octave �˶�
	 * @param timeOffset ʱ��ƫ��
	 * @param volume ����ϵ��
	 */
	public RenderEvent(Renderer renderer, int initialStep, int finalStep, int octave, Time timeOffset, double volume) {
		this.renderer = renderer;
		this.initialStep = initialStep;
		this.finalStep = finalStep;
		this.octave = octave;
		this.timeOffset = timeOffset;
		this.volume = volume;
	}
	
	public Renderer getRenderer() {
		return renderer;
	}
	
	public int getInitialStep() {
		return initialStep;
	}

	public int getFinalStep() {
		return finalStep;
	}

	public int getOctave() {
		return octave;
	}

	public Time getTimeOffset() {
		return timeOffset;
	}

	public double getVolume() {
		return volume;
	}
	
}
