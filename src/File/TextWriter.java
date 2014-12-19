package File;

import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import MIDI.Music;

/**
 * �����ֵ���Ϣд�뵽�ı�
 */
public class TextWriter {
	
	/**
	 * д�����ֵ��ı���ʽ
	 * @param music ���ֶ���
	 * @param path �洢·��
	 */
	public static void write(Music music, String path) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			Sequence sequence = music.getSequence();
			//д���ٶ�
			long speed = 500000 * sequence.getTickLength() / sequence.getMicrosecondLength();
			writer.write(speed + "\n");
			//д��ÿһ������
			for (Track track : sequence.getTracks()) {
				writer.write(-1 + "\n");
				for (int i = 0; i < track.size(); i++) {
					MidiEvent event = track.get(i);
					MidiMessage message = event.getMessage();
					//ֻд��̧��������������������������������Ϣ
					if (message.getStatus() / 16 == 8 || message.getStatus() / 16 == 9 || message.getStatus() / 16 == 12) {
						writer.write(message.getStatus() / 16 + " ");
						for (int j = 1; j < message.getMessage().length; j++) {
							writer.write(message.getMessage()[j] + " ");
						}
						writer.write(event.getTick() + "\n");
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
