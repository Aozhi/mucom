package MIDI;

import java.util.HashMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * ������
 */
public class Music {
	
	/**
	 * һ�������Լ������Ϣ
	 */
	private class TrackInfo {
		
		/**
		 * ����
		 */
		public Track track;
		
		/**
		 * ������
		 */
		public String instrument;
		
		/**
		 * ��һ���������������ʼ����ʱ��
		 */
		public long lastTiming;
		
		/**
		 * ��һ�������������ʱ��
		 */
		public long lastDuration;
		
		/**
		 * ����������õ�ͨ��
		 */
		public int channel;
	}
	
	/**
	 * ���ֵ�ʱ�����У����Է��ö������
	 */
	private Sequence sequence;
	
	/**
	 * ���ֵ����������ӳ���
	 */
	private HashMap<String, TrackInfo> tracks;
	
	/**
	 * ��ǰ���ڲ���������
	 */
	private TrackInfo currentTrack;
	
	/**
	 * Ĭ�Ϲ��캯����ȡĬ���ٶ�medium=120BPM
	 */
	public Music() {
		// TODO Auto-generated constructor stub
		this("medium");
	}
	
	/**
	 * ���캯��
	 * @param speed ���ֵ������ٶȣ��ַ�������
	 */
	public Music(String speed) {
		// TODO Auto-generated constructor stub
		this(Conversion.convertSequenceSpeed(speed));
	}
	
	/**
	 * ���캯��
	 * @param speed ���ֵ������ٶȣ�����������Խ�߱�ʾԽ��
	 */
	public Music(int speed) {
		// TODO Auto-generated constructor stub
		try {
			sequence = new Sequence(Sequence.PPQ, speed);
			tracks = new HashMap<String, TrackInfo>();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ͨ��ʱ�����й������ֶ���������MIDI��ʽת��Music����
	 * @param sequence ����
	 */
	public Music(Sequence sequence) {
		//����sequence
		this.sequence = sequence;
		//����tracks
		this.tracks = new HashMap<String, TrackInfo>();
		for (int i = 0; i < sequence.getTracks().length; i++) {
			//�½�һ��TrackInfo
			TrackInfo trackInfo = new TrackInfo();
			trackInfo.channel = -1;
			trackInfo.instrument = "unknown";
			trackInfo.lastDuration = 0;
			trackInfo.lastTiming = 1;
			trackInfo.track = sequence.getTracks()[i];
			//��trackһ������
			String trackName = "track" + i;
			//���뵽tracks
			this.tracks.put(trackName, trackInfo);
		}
		//���õ�ǰ���ڱ༭��������
		setTrack("track0");
	}
	
	/**
	 * ���õ�ǰҪ���������
	 * @param trackName Ҫ�����������
	 */
	public void setTrack(String trackName) {
		if (tracks.containsKey(trackName)) {
			currentTrack = tracks.get(trackName);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * �½�һ�����죬���л���������
	 * @param trackName ���������
	 * @param instrument ���������
	 */
	public void createTrack(String trackName, String instrument) {
		try {
			//��������Ƿ��Ѿ�����
			if (tracks.containsKey(trackName)) {
				throw new IllegalArgumentException();
			}
			//���ֻ����16��channel
			if (tracks.size() >= 16) {
				throw new InvalidMidiDataException("channel out of range");
			}
			//���������죬��д��Ϣ
			currentTrack = new TrackInfo();
			currentTrack.track = sequence.createTrack();
			currentTrack.instrument = instrument;
			currentTrack.lastTiming = 1;
			currentTrack.lastDuration = 0;
			currentTrack.channel = tracks.size();
			//������������
			tracks.put(trackName, currentTrack);
			//ͨ��ShortMessage�趨��ɫ
			ShortMessage message = new ShortMessage();
			message.setMessage(ShortMessage.PROGRAM_CHANGE, currentTrack.channel, Conversion.convertNoteInstrument(currentTrack.instrument), 0);
			MidiEvent event = new MidiEvent(message, 0);
			currentTrack.track.add(event);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ��һ�����죬����ǵ�ǰ�༭�ģ�������л�����������
	 * @param trackName Ҫɾ����������
	 */
	public void deleteTrack(String trackName) {
		try {
			if (tracks.containsKey(trackName)) {
				//�����ǰ�������Ҫɾ�������죬����ɾ���������趨������������
				if (currentTrack == tracks.get(trackName)) {
					throw new Exception("you can't delete current track");
				}
				sequence.deleteTrack(tracks.get(trackName).track);
				tracks.remove(trackName);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�����ʱ�����У�����ת��MIDI
	 * @return ����ʱ������
	 */
	public Sequence getSequence() {
		return sequence;
	}
	
	/**
	 * �趨�����ʱ������
	 * @param sequence ʱ������
	 */
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * �������
	 * @param pitch ���ߵ��ַ�������
	 * @param noteTiming ����ʱ��������
	 * @param duration ����ʱֵ���ַ�������
	 * @param strength ����ǿ�����ַ�������
	 */
	public void pushNote(String pitch, NoteTiming noteTiming, String duration, String strength) {
		pushNote(pitch, noteTiming, "zero", duration, strength);
	}
	
	/**
	 * �������
	 * @param pitch ���ߵ��ַ�������
	 * @param noteTiming ����ʱ��������
	 * @param delay �ӳٶ���ʱֵ
	 * @param duration ����ʱֵ���ַ�������
	 * @param strength ����ǿ�����ַ�������
	 */
	public void pushNote(String pitch, NoteTiming noteTiming, String delay, String duration, String strength) {
		//�����ǰ����ָ��Ϊ�գ����½�����
		if (currentTrack == null) {
			createTrack("piano", "piano");
		}
		//��ȡ����ʱ���ľ���ֵ
		long timing = 1;
		if (noteTiming == NoteTiming.meanwhile) {
			timing = currentTrack.lastTiming;
		} else {
			timing = currentTrack.lastTiming + currentTrack.lastDuration;
		}
		timing += Conversion.convertNoteDuration(delay);
		pushNote(Conversion.convertNotePitch(pitch), timing, Conversion.convertNoteDuration(duration), Conversion.convertNoteStrength(strength));
	}
	
	/**
	 * �������
	 * @param pitch ���ߵ���ֵ����
	 * @param noteTiming ����ʱ��������
	 * @param delay �ӳٵ�ʱ�䣬��������
	 * @param duration ����ʱ�䣬��������
	 * @param volume ������������������
	 */
	public void pushNote(int pitch, NoteTiming noteTiming, int delay, int duration, int volume) {
		//�����ǰ����ָ��Ϊ�գ����½�����
		if (currentTrack == null) {
			createTrack("piano", "piano");
		}
		//��ȡ����ʱ���ľ���ֵ
		long timing = 1;
		if (noteTiming == NoteTiming.meanwhile) {
			timing = currentTrack.lastTiming;
		} else {
			timing = currentTrack.lastTiming + currentTrack.lastDuration;
		}
		timing += delay;
		pushNote(pitch, timing, duration, volume);
	}
	
	/**
	 * �������
	 * @param pitch ���ߵ���ֵ����
	 * @param timing ����ʱ������������
	 * @param duration ����ʱ�䣬��������
	 * @param volume ������������������
	 */
	public void pushNote(int pitch, long timing, int duration, int volume) {
		//�����ǰ����ָ��Ϊ�գ����½�����
		if (currentTrack == null) {
			createTrack("piano", "piano");
		}
		try {
			//�����Ǵ��ļ��ж�ȡ�����ģ���ǰ��ͨ����δ֪
			if (currentTrack.channel == -1) {
				throw new Exception("you can't push note in this track");
			}
			//�����ǰ����ָ��Ϊ�գ����½�����
			if (currentTrack == null) {
				createTrack("piano", "piano");
			}
			//�����ʼ
			ShortMessage startMessage = new ShortMessage();
			startMessage.setMessage(ShortMessage.NOTE_ON, currentTrack.channel, pitch, volume);
			MidiEvent startEvent = new MidiEvent(startMessage, timing);
			currentTrack.track.add(startEvent);
			//�����ֹ
			ShortMessage endMessage = new ShortMessage();
			endMessage.setMessage(ShortMessage.NOTE_OFF, currentTrack.channel, pitch, 0);
			MidiEvent endEvent = new MidiEvent(endMessage, timing + duration);
			currentTrack.track.add(endEvent);
			//��¼
			currentTrack.lastTiming = timing;
			currentTrack.lastDuration = duration;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ֹ��
	 * @param duration ��ֹ����ʱֵ���ַ�����ʽ
	 */
	public void pushRest(String duration) {
		pushRest(Conversion.convertNoteDuration(duration));
	}
	
	/**
	 * �����ֹ��
	 * @param duration ��ֹ����ʱֵ����ֵ��ʽ
	 */
	public void pushRest(int duration) {
		currentTrack.lastTiming = currentTrack.lastTiming + currentTrack.lastDuration;
		currentTrack.lastDuration = duration;
	}
}
