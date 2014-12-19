package Composition.Hongyu.Essential;

import java.util.ArrayList;
import java.util.HashMap;

import Composition.Hongyu.HarmonyGenerators.DefaultHarmonyGenerator;
import Composition.Hongyu.InnerStructureGenerators.DefaultInnerStructureGenerator;
import Composition.Hongyu.MelodyGenerators.DefaultMelodyGenerator;
import Composition.Hongyu.RhythmGenerators.DefaultRhythmGenerator;

/**
 * ���ֵ�һ�Σ��Ƕ�����
 */
public class UniquePart {
	
	/**
	 * �ڲ��ṹ������
	 */
	public InnerStructureGenerator innerStructureGenerator = new DefaultInnerStructureGenerator();
	
	/**
	 * ����������
	 */
	public RhythmGenerator rhythmGenerator = new DefaultRhythmGenerator();
	
	/**
	 * ����������
	 */
	public HarmonyGenerator harmonyGenerator = new DefaultHarmonyGenerator();
	
	/**
	 * ����������
	 */
	public MelodyGenerator melodyGenerator = new DefaultMelodyGenerator();
		
	/**
	 * ÿ�ڵ�����
	 */
	private int meter = 4;
	
	/**
	 * ������С������
	 */
	private ArrayList<UniquePhrase> uniquePhrases = new ArrayList<>();
	
	/**
	 * ��������
	 */
	private ArrayList<Sentence> sentences = new ArrayList<>();
	
	/**
	 * ��������
	 */
	private ArrayList<Harmonic> harmonics = new ArrayList<>();
	
	/**
	 * �����¼�����
	 */
	private ArrayList<NoteEvent> noteEvents = new ArrayList<>();
	
	/**
	 * �¼�����ҵĹ�����
	 */
	private HashMap<NoteEvent, Harmonic> eventHarmonicMap = new HashMap<>();
	
	/**
	 * ���¼��ͺ��Ҷ�Ӧ
	 */
	public void assignEventsToHarmony() {
		int barsCount = getBarsCount();
		int meter = getMeter();
		Time endOfTime = new Time(barsCount - 1, meter);
		
		for (int i = 0; i < harmonics.size(); i++) {
			Harmonic harmonic1 = harmonics.get(i);
			Time time1 = harmonic1.getStartTime();
			Time time2 = endOfTime;
			if (i + 1 < harmonics.size()) {
				Harmonic harmonic2 = harmonics.get(i + 1);
				time2 = harmonic2.getStartTime();
			}
			harmonic1.setEndTime(time2.clone());
			for (NoteEvent event : noteEvents) {
				if (event.intersects(time1, time2, meter)) {
					boolean hasEvent = eventHarmonicMap.containsKey(event);
					if (!hasEvent) {
						eventHarmonicMap.put(event, harmonic1);
					} else {
						Harmonic harmonic = eventHarmonicMap.get(event);
						double oldOverlap = harmonic.toInterval(meter)
								.intersectClone(event.toInterval(meter))
								.getLength();
						double newOverlap = harmonic1.toInterval(meter)
								.intersectClone(event.toInterval(meter))
								.getLength();
						if (newOverlap > oldOverlap) {
							eventHarmonicMap.put(event, harmonic1);
						}
					}
				}
			}
		}
		
		for (NoteEvent event : noteEvents) {
			if (!eventHarmonicMap.containsKey(event)) {
				Time start = event.getStart();
				Time end = event.getEnd();
				if (start.getPosition(meter) >= endOfTime.getPosition(meter)) {
					eventHarmonicMap.put(event, harmonics.get(harmonics.size() - 1));
				} else if (end.getPosition(meter) <= 0) {
					eventHarmonicMap.put(event, harmonics.get(0));
				} else {
					
				}
			}
		}
	}
	
	/**
	 * ����������Ҷ�Ӧ
	 * @param eventIndex �¼�����
	 * @param scaleNote �������
	 * @return ��׼��
	 */
	public int alignPitchToHarmonic(int eventIndex, int scaleNote) {
		NoteEvent event = noteEvents.get(eventIndex);
		Harmonic harmonic = eventHarmonicMap.get(event);
		Integer[] scaleDegrees = harmonic.getScaleDegrees();
		Integer scaleDegree = Common.positiveMod(scaleNote - 1, 7);
		if (Common.arrayContains(scaleDegrees, scaleDegree)) {
			return scaleNote;
		}
		int closestNote = harmonic.getBaseNote();
		int closestDistance = Integer.MAX_VALUE;
		for (int i = scaleNote - 7; i < scaleNote + 7; i++) {
			scaleDegree = Common.positiveMod(i - 1, 7);
			if (Common.arrayContains(scaleDegrees, scaleDegree)) {
				int distance = Math.abs(i - scaleNote);
				if (distance < closestDistance) {
					closestDistance = distance;
					closestNote = i;
				}
			}
		}
		return closestNote;
	}
	
	/**
	 * ��ȡ�ܵĽ�����
	 * @return
	 */
	public int getBarsCount() {
		int bars = 0;
		for (Sentence sentence : sentences) {
			bars += sentence.getBarsCount();
		}
		return bars;
	}
	
	/**
	 * ����¼����ҵĻ�׼��
	 * @param index �¼��������±�
	 * @return ��׼��
	 */
	public int getEventBasis(int index) {
		NoteEvent event = noteEvents.get(index);
		Harmonic harmonic = eventHarmonicMap.get(event);
		return harmonic.getBaseNote();
	}
	
	/**
	 * �����µĺ���
	 * @param time ʱ��
	 * @param baseNote ��׼��
	 * @param chordString ����ģʽ
	 * @param volume �������
	 */
	public void addHarmonic(Time time, int baseNote, String chordString, double volume) {
		harmonics.add(new Harmonic(time, baseNote, chordString, volume));
	}
	
	/**
	 * ����һ�������¼�
	 * @param event �����ӵ��¼�
	 */
	public void addEvent(NoteEvent event) {
		noteEvents.add(event);
	}
	
	/**
	 * ��ȡָ���¼���Ӧ�ĺ���
	 * @param event ָ�����¼�
	 * @return ��Ӧ�ĺ���
	 */
	public Harmonic getEventHarmonic(NoteEvent event) {
		return eventHarmonicMap.get(event);
	}
	
	/**
	 * ��ȡһ���¼��Ŀ�ʼʱ��
	 * @param index �����±�
	 * @return ��ʼʱ��
	 */
	public Time getEventStart(int index) {
		return noteEvents.get(index).getStart();
	}
	
	/**
	 * ��ȡһ���¼��Ľ���ʱ��
	 * @param index �����±�
	 * @return ����ʱ��
	 */
	public Time getEventEnd(int index) {
		return noteEvents.get(index).getEnd();
	}
	
	/**
	 * ��ȡһ���¼�������
	 * @param index �����±�
	 * @return ����
	 */
	public int getEventPitch(int index) {
		return noteEvents.get(index).getPitch();
	}
	
	/**
	 * ��ȡһ���¼����������
	 * @param index �����±�
	 * @return �������
	 */
	public double getEventVolume(int index) {
		return noteEvents.get(index).getVolume();
	}
	
	/**
	 * ��ȡ�¼��ĸ���
	 * @return �¼��ĸ���
	 */
	public int getEventsCount() {
		return noteEvents.size();
	}
	
	/**
	 * �趨һ���¼�������
	 * @param index �����±�
	 * @param pitch ���õ�����
	 */
	public void setEventPitch(int index, int pitch) {
		noteEvents.get(index).setPitch(pitch);
	}
	
	/**
	 * �趨��������
	 * @param count �趨������
	 */
	public void setSentencesCount(int count) {
		Common.setSize(count, sentences, Sentence.class);
	}
	
	/**
	 * ��ȡ��������
	 * @return ���ӵ�����
	 */
	public int getSentencesCount() {
		return sentences.size();
	}
	
	/**
	 * ��ȡָ�������±�ľ���
	 * @param index �����±�
	 * @return ��ȡ���ľ���
	 */
	public Sentence getSentence(int index) {
		return Common.getElementSafe(index, sentences, Sentence.class);
	}
	
	/**
	 * �趨����С�ڵ�����
	 * @param count �趨������
	 */
	public void setUniquePhrasesCount(int count) {
		Common.setSize(count, uniquePhrases, UniquePhrase.class);
	}
	
	/**
	 * ��ȡ����С�ڵ�����
	 * @return ����С�ڵ�����
	 */
	public int getUniquePhrasesCount() {
		return uniquePhrases.size();
	}
	
	/**
	 * ��ȡָ�������±�Ķ���С��
	 * @param index �����±�
	 * @return ��ȡ���Ķ���С��
	 */
	public UniquePhrase getUniquePhrase(int index) {
		return Common.getElementSafe(index, uniquePhrases, UniquePhrase.class);
	}
	
	public int getMeter() {
		return meter;
	}

	public void setMeter(int meter) {
		this.meter = meter;
	}

	public ArrayList<UniquePhrase> getUniquePhrases() {
		return uniquePhrases;
	}

	public void setUniquePhrases(ArrayList<UniquePhrase> uniquePhrases) {
		this.uniquePhrases = uniquePhrases;
	}

	public ArrayList<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}

	public ArrayList<Harmonic> getHarmonics() {
		return harmonics;
	}

	public void setHarmonics(ArrayList<Harmonic> harmonics) {
		this.harmonics = harmonics;
	}

	public ArrayList<NoteEvent> getNoteEvents() {
		return noteEvents;
	}

	public void setNoteEvents(ArrayList<NoteEvent> noteEvents) {
		this.noteEvents = noteEvents;
	}

	public HashMap<NoteEvent, Harmonic> getEventHarmonicMap() {
		return eventHarmonicMap;
	}

	public void setEventHarmonicMap(HashMap<NoteEvent, Harmonic> eventHarmonicMap) {
		this.eventHarmonicMap = eventHarmonicMap;
	}

}
