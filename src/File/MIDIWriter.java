package File;

import java.io.File;

import javax.sound.midi.MidiSystem;

import MIDI.Music;

/**
 * ��Music����д���MIDI���ָ�ʽ
 */
public class MIDIWriter {
	
	/**
	 * ����MIDI�ļ�
	 * @param music �����ļ�
	 * @param path ·��
	 */
	public static void write(Music music, String path) {
		try {
			File file = new File(path);
			MidiSystem.write(music.getSequence(), 1, file);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
