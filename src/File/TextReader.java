package File;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import MIDI.Music;

/**
 * ���ı��ж�ȡ������Ϣ
 */
public class TextReader {
	
	/**
	 * ��ȡ����
	 * @param path ���ֵ�·��
	 * @return ���ֶ���
	 */
	public static Music read(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			Sequence sequence = new Sequence(Sequence.PPQ, Integer.parseInt(reader.readLine()));
			Track currentTrack = null;
			int trackNum = 0;
			while (true) {
				String line = reader.readLine();
				//�ж϶�ȡ�Ƿ����
				if (line == null || line == "") {
					break;
				}
				//����MIDI��Ϣ
				String[] numbers = line.split(" ");
				//������
				if (Integer.parseInt(numbers[0]) == -1) {
					currentTrack = sequence.createTrack();
					trackNum++;
				}
				//����������Ϣ
				if (Integer.parseInt(numbers[0]) == 12) {
					ShortMessage message = new ShortMessage();
					message.setMessage(ShortMessage.PROGRAM_CHANGE, trackNum - 1, Integer.parseInt(numbers[1]), 0);
					MidiEvent event = new MidiEvent(message, Integer.parseInt(numbers[2]));
					currentTrack.add(event);
				}
				//�����°�����̧�𰴼���Ϣ
				if (Integer.parseInt(numbers[0]) == 8 || Integer.parseInt(numbers[0]) == 9) {
					ShortMessage message = new ShortMessage();
					message.setMessage(Integer.parseInt(numbers[0]) * 16, trackNum - 1, Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2]));
					MidiEvent event = new MidiEvent(message, Integer.parseInt(numbers[3]));
					currentTrack.add(event);
				}
			}
			reader.close();
			return new Music(sequence);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
