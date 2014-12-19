package Composition.Hongyu.Essential;

import java.util.ArrayList;

/**
 * ��װPart��������Ⱦ
 */
public class RenderPart {
	
	/**
	 * ��������
	 */
	private int trackIndex;

	/**
	 * ��������
	 */
	private String trackName;
	
	/**
	 * �����¼�
	 */
	private ArrayList<NoteEvent> noteEvents;
	
	/**
	 * ����
	 */
	private ArrayList<Harmonic> harmonics;
	
	/**
	 * ������Part
	 */
	private Part part;
	
	/**
	 * ��Ӧ��UniquePart
	 */
	private UniquePart uniquePart;
	
	/**
	 * ��ʽƫ������
	 */
	private int[] scaleOffsets;
	
	/**
	 * ��Ⱦ���¼�
	 */
	private RenderEvent renderEvent;

	/**
	 * ������������
	 */
	private MusicDescription musicDescription;

	/**
	 * ������������
	 */
	private ArrayList<Note> notes = new ArrayList<>();
	
	/**
	 * ���캯��
	 * @param trackIndex ��������
	 */
	public RenderPart(int trackIndex) {
		this.trackIndex = trackIndex;
	}
	
	/**
	 * �����������
	 * @param part ���ֵ�һ��
	 * @param uniquePart ���ֵ�һ�Σ������ģ�
	 * @param renderEvent ��Ⱦ�¼�
	 * @param musicDescription ������������
	 */
	public void setData(Part part, UniquePart uniquePart, RenderEvent renderEvent,
			MusicDescription musicDescription) {
		this.part = part;
		this.uniquePart = uniquePart;
		this.renderEvent = renderEvent;
		this.musicDescription = musicDescription;

		ArrayList<NoteEvent> events = part.getNoteEvents();
		this.noteEvents = new ArrayList<NoteEvent>();
		for (NoteEvent event : events) {
			this.noteEvents.add(event.clone());
		}

		ArrayList<Harmonic> harmonics = uniquePart.getHarmonics();
		this.harmonics = new ArrayList<Harmonic>();
		for (Harmonic harmonic : harmonics) {
			this.harmonics.add(harmonic.clone());
		}
		
		this.scaleOffsets = part.getCurrentScale().clone();
	}
	
	/**
	 * ��ȡ�¼�����
	 * @return �¼��ĸ���
	 */
	public int getEventsCount() {
		return noteEvents.size();
	}
	
	/**
	 * ��ȡ����
	 * @param time ʱ��
	 * @return ��ȡ���ĺ���
	 */
	public int getHarmonic(Time time) {
		int meter = uniquePart.getMeter();
		double position = time.getPosition(meter);

		for (int i = 0; i < harmonics.size(); i++) {
			Harmonic harmonic = harmonics.get(i);
			Interval interval = harmonic.toInterval(meter);
			if (interval.contains(position) && interval.contains(position + 0.01)) {
				return i;
			}
		}

		if (position < 0) {
			return 0;
		} else {
			return harmonics.size() - 1;
		}
		
	}
	
	/**
	 * ���ָ�������±���¼�����ʼʱ��
	 * @param index �����±�
	 * @return ��ʼʱ��
	 */
	public Time getEventStart(int index) {
		return noteEvents.get(index).getStart();
	}
	
	/**
	 * ���ָ�������±���¼��Ľ���ʱ��
	 * @param index �����±�
	 * @return ����ʱ��
	 */
	public Time getEventEnd(int index) {
		return noteEvents.get(index).getEnd();
	}
	
	/**
	 * ����¼�������
	 * @param index �¼��������±�
	 * @return ����
	 */
	public int getEventPitch(int index) {
		return 12 * renderEvent.getOctave()
				+ noteEvents.get(index).getPitch();
	}
	
	/**
	 * ��ȡ�¼�������
	 * @param index �¼��������±�
	 * @return �������
	 */
	public double getEventVolume(int index) {
		return noteEvents.get(index).getVolume();
	}
	
	/**
	 * ��ȡ��������
	 * @param index �����¼�����
	 * @param chordNoteIndex ������������
	 * @return ���ҵ�����
	 */
	public int getHarmonicEventPitch(int index, int chordNoteIndex) {
		Harmonic harmonic = harmonics.get(index);
		int[] offsets = harmonic.getOffsets();
		int octaveOffset = 0;
		while (chordNoteIndex < 0) {
			chordNoteIndex += offsets.length;
			octaveOffset--;
		}
		while (chordNoteIndex >= offsets.length) {
			chordNoteIndex -= offsets.length;
			octaveOffset++;
		}
		int scaleIndex = harmonic.getBaseNote()
				+ harmonic.getOffsets()[chordNoteIndex];
		return 12 * renderEvent.getOctave() + octaveOffset * 12
				+ part.getAbsolutePitch(scaleIndex);
	}
	
	/**
	 * ��ȡ�����¼�����ʼʱ��
	 * @param index �����±�
	 * @return ��ʼʱ��
	 */
	public Time getHarmonicEventStart(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getStartTime();
	}
	
	/**
	 * ��ȡ�����¼�����ֹʱ��
	 * @param index �����±�
	 * @return ��ֹʱ��
	 */
	public Time getHarmonicEventEnd(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getEndTime();
	}
	
	/**
	 * ��ȡ�����¼����������
	 * @param index �����±�
	 * @return �������
	 */
	public double getHarmonicEventVolume(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getVolume();
	}
	
	/**
	 * ���׶���
	 * @param chromaticChordNote ����
	 * @param scaleOffset �����±�
	 * @return ����������
	 */
	public int alignPitch(int chromaticChordNote, int scaleOffset) {

		int baseChromaticScaleNote = part.getAbsolutePitch(1);
		int[] scaleOffsets = part.getCurrentScale();
		Integer[] pitchClasses = new Integer[scaleOffsets.length];
		for (int i = 0; i < scaleOffsets.length; i++) {
			pitchClasses[i] = (baseChromaticScaleNote + scaleOffsets[i]) % 12;
		}
		int inPitchClass = chromaticChordNote % 12;
		int theOriginalScaleIndex = 0;
		if (!Common.arrayContains(pitchClasses, inPitchClass)) {
			for (int i = chromaticChordNote - 7; i < chromaticChordNote + 8; i++) {
			}
		}
		for (int i = 0; i < pitchClasses.length; i++) {
			if (inPitchClass == pitchClasses[i]) {
				theOriginalScaleIndex = i;
				break;
			}
		}
		int newPitchClass = pitchClasses[Common.positiveMod(
				theOriginalScaleIndex + scaleOffset, pitchClasses.length)];
		int increment = scaleOffset > 0 ? 1 : -1;
		int currentNote = chromaticChordNote;
		while (true) {
			if ((currentNote % 12) == newPitchClass) {
				return currentNote;
			}
			currentNote += increment;
			while (currentNote < 0)
				currentNote += 12;
		}
	}
	
	/**
	 * �������
	 * @param start ��ʼʱ��
	 * @param end ��ֹʱ��
	 * @param pitch ����
	 * @param absoluteVolume ��������
	 */
	public void addNote(Time start, Time end, int pitch, int absoluteVolume) {
		int initialStep = renderEvent.getInitialStep() - part.getStartBar();
		int finalStep = renderEvent.getFinalStep() - part.getStartBar();
		if (start.startBar >= initialStep && start.startBar <= finalStep) {
			notes.add(new Note(start.clone(), end.clone(), pitch,
					(int) (absoluteVolume * renderEvent.getVolume())));
		}
	}
	
	/**
	 * ƽ������
	 * @param bars С����
	 */
	public void translateNotes(int bars) {
		for (Note n : notes) {
			n.translate(bars);
		}
	}

	/**
	 * ƽ������
	 * @param time ƽ��ʱ��
	 */
	public void translateNotes(Time time) {
		for (Note n : notes) {
			n.translate(time);
		}
	}
	
	/**
	 * ��ȡ������������Ĵ�С
	 * @param index ���ҵ������±�
	 * @return �����С
	 */
	public int getHarmonicOffsetCount(int index) {
		Harmonic harmonic = harmonics.get(index);
		return harmonic.getOffsets().length;
	}
	
	public int getTrackIndex() {
		return trackIndex;
	}

	public void setTrackIndex(int trackIndex) {
		this.trackIndex = trackIndex;
	}
	
	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public ArrayList<NoteEvent> getNoteEvents() {
		return noteEvents;
	}

	public void setNoteEvents(ArrayList<NoteEvent> noteEvents) {
		this.noteEvents = noteEvents;
	}

	public ArrayList<Harmonic> getHarmonics() {
		return harmonics;
	}

	public void setHarmonics(ArrayList<Harmonic> harmonics) {
		this.harmonics = harmonics;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public UniquePart getUniquePart() {
		return uniquePart;
	}

	public void setUniquePart(UniquePart uniquePart) {
		this.uniquePart = uniquePart;
	}

	public int[] getScaleOffsets() {
		return scaleOffsets;
	}

	public void setScaleOffsets(int[] scaleOffsets) {
		this.scaleOffsets = scaleOffsets;
	}

	public RenderEvent getRenderEvent() {
		return renderEvent;
	}

	public void setRenderEvent(RenderEvent renderEvent) {
		this.renderEvent = renderEvent;
	}
	
	public MusicDescription getMusicDescription() {
		return musicDescription;
	}

	public void setMusicDescription(MusicDescription musicDescription) {
		this.musicDescription = musicDescription;
	}
	
	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}
}
