package MIDI;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

/**
 * ��������
 */
public class Player {
	
	/**
	 * �൱�ڲ�����
	 */
	private static Sequencer sequencer;
	
	private static int audioLength;
	
	private static int audioPosition;
	
	/**
	 * ʱ����һ�����
	 */
	private static final double timeCorrection = 154.0 / 148.0;
	
	/**
	 * ��ʼ��sequencer
	 */
	static {
		try {
			sequencer = MidiSystem.getSequencer();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������
	 * @param music ���ֶ���
	 */
	public static void play(Music music) {
		try {
			//�򿪲�����
			sequencer.open();
			//�趨����
			sequencer.setSequence(music.getSequence());
			//��������
			//sequencer.start();
			
			audioLength = (int) sequencer.getTickLength();
			audioPosition = 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ֹͣ����
	 */
	public static void stop() {
		if (sequencer.isOpen()) {
			//ֹͣ��������
			sequencer.stop();
			//�رղ�����
			sequencer.close();
		}
	}
	
	public static void pause() {
		sequencer.stop();
	}
	
	public static void start() {
		sequencer.start();
	}
	
	public static void setAudioLength(int audioLength) {
		audioLength /= timeCorrection;
		Player.audioLength = audioLength;
	}
	
	public static int getAudioLength() {
		return (int) (audioLength * timeCorrection);
	}
	
	public static void setAudioPosition(int audioPosition) {
		audioPosition /= timeCorrection;
		Player.audioPosition = audioPosition;
	}
	
	public static int getAudioPosition() {
		return (int) (audioPosition * timeCorrection);
	}
	
	public static void skip(int position) {
		if (position < 0 || position > audioLength){   
            return;
        }
		position /= timeCorrection;
        audioPosition = position;
        if (sequencer.isRunning()) {
        	sequencer.stop();
            sequencer.setTickPosition(position);
            sequencer.start();
        } else if (sequencer.isOpen()) {
        	sequencer.setTickPosition(position);
		}
	}
	
	public static void tick() {
		 // ��ȡ��ǰ��Ƶ���ŵ�λ�ã�����������ָ���λ��   
        if (sequencer.isRunning()) {   
            audioPosition = (int)sequencer.getTickPosition();
        }
        else {   
            reset();   
        }   
	}
        
    public static void reset() {
    	sequencer.setTickPosition(0);
    	audioPosition = 0;
    	Player.stop();
    }
    
    public static boolean isRunning() {
    	return sequencer.isRunning();
    }
}