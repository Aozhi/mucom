package File;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import MIDI.Music;

/**
 * ��MIDI�ļ��ж�ȡ����
 */
public class MIDIReader {
	
	/**
	 * ��ȡMIDI����
	 * @param path MIDI·��
	 * @return Music�ļ�
	 */
	public static Music read(String path) {
		try {
			File file = new File(path);
			Sequence sequence = MidiSystem.getSequence(file);
			Music music = new Music(sequence);
			return music;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
