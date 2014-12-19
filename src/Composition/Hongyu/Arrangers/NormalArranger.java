package Composition.Hongyu.Arrangers;

import java.util.HashMap;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Renderers.AccentedMelody;
import Composition.Hongyu.Renderers.ArpeggioChords;
import Composition.Hongyu.Renderers.DefaultRenderer;
import Composition.Hongyu.Renderers.RandomBassExtended;
import Composition.Hongyu.Renderers.ShortestWayChordsSimple;
import Composition.Hongyu.Renderers.SimpleChords;
import Composition.Hongyu.Renderers.SimpleChordsSmooth;

/**
 * ����PianoAdvancedClassical��DefaultArranger֮����Ų���
 */
public class NormalArranger implements Arranger {

	@Override
	public void arrange(MusicDescription musicDescription,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		
		musicDescription.addTrack("Melody");
		musicDescription.addTrack("Alt Voice");
		musicDescription.addTrack("Accomp1");
		musicDescription.addTrack("Bass");
		musicDescription.addTrack("Chords");
		musicDescription.addTrack("Accomp2");
		
		Renderer melody = new DefaultRenderer();
		if (rndInt(0, 2) == 0)
			melody = new AccentedMelody();
		
		int octiveOffsetCount = 0;
		if (parameter.get(Constant.PITCH_STRING) >= 70)
			octiveOffsetCount = 1;

		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			if (musicDescription.getPart(i).getArrangeHint() == 0) {
				//�򵥵ı�������
				musicDescription.addRenderEvent(new ShortestWayChordsSimple(),
						4, musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, 0,
						new Time(0, 0), 0.6);
				//���е�����
				musicDescription.addRenderEvent(new ArpeggioChords(), 5,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, octiveOffsetCount,
						new Time(0, 0), 0.5);
			} else if (musicDescription.getPart(i).getArrangeHint() == 1) {
				//��ͨ����
				musicDescription
						.addRenderEvent(melody, 0, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 1, new Time(0, 0), 0.8);
				//��ͨ����
				musicDescription
						.addRenderEvent(new SimpleChords(), 4, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 0, new Time(0, 0), 0.6);

			} else if (musicDescription.getPart(i).getArrangeHint() == 2) {
				//��ͨ����
				musicDescription
						.addRenderEvent(melody, 1, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 1, new Time(0, 0), 0.8);
				//��ͨ����
				musicDescription
						.addRenderEvent(new SimpleChords(), 4, musicDescription
								.getPartStartBar(i), musicDescription
								.getPartEndBar(i), 0, new Time(0, 0), 0.6);
			} else if (musicDescription.getPart(i).getArrangeHint() == 3) {
				//��ͨ������
				musicDescription.addRenderEvent(melody, 1,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i), 1,
						new Time(0, 0), 1);
				//��ͨ����
				musicDescription.addRenderEvent(new SimpleChords(), 4,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i) - 1, 0,
						new Time(0, 0), 0.5);
			}
			//ÿ��Part�Ľ�β���ֵ�������
			musicDescription.addRenderEvent(new SimpleChords(), 4, 
					musicDescription.getPartEndBar(i) - 1, musicDescription
							.getPartEndBar(i), 1, new Time(0, 0),
							0.5);
			if (musicDescription.getPart(i).getArrangeHint() == 3) {
				//�ȶ��ĵ�����-��
				musicDescription.addRenderEvent(new RandomBassExtended(), 3,
						musicDescription.getPartStartBar(i),
						musicDescription.getPartEndBar(i), -1, new Time(0, 0),
						0.7);
				//���е�����
				musicDescription.addRenderEvent(new ArpeggioChords(), 2,
						musicDescription.getPartStartBar(i), musicDescription
								.getPartEndBar(i) - 1, octiveOffsetCount, new Time(0, 0), 0.4);
			}
		}
		//������β����
		int bars = musicDescription.getBarsCount();
		musicDescription.addRenderEvent(
				new SimpleChordsSmooth(), 2,
				bars - 1, bars, 3,
				new Time(0, musicDescription.getUniquePart(
						musicDescription.getPart(0).getUniquePartIndex())
						.getMeter() / 2), 0.7);
	}

	private int rndInt(int i, int j) {
		// TODO Auto-generated method stub
		return Common.getRandomInteger(i, j + 1);
	}
	
}
