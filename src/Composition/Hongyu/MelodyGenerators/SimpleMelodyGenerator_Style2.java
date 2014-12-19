package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * SimpleRandomMelody
 */
public class SimpleMelodyGenerator_Style2 implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		//ȡ[-5,5]�е�������Ϊ����ֵ�����Ե������е���
		int note = uniquePart.getEventBasis(0);
		int delta, lastdelta = 0;
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			uniquePart.setEventPitch(i, note);
			if (i < uniquePart.getEventsCount() - 2)
			{
				delta = Common.getRandomInteger(-5, 6);
				while (delta % 2 == 0 && delta != 0)
				{
					delta = Common.getRandomInteger(-5, 6);
				}
				if (lastdelta == -5 && delta <= 0)
					delta += Common.getRandomInteger(0, 4) * 2;
				note += delta;
				lastdelta = delta;
			}
		}

		int last_note = 1;

		while (note > 5) {
			note -= 7;
			last_note += 7;
		}

		while (note < -3) {
			note += 7;
			last_note -= 7;
		}

		if (note == 5 && Common.getRandomInteger(0, 2) == 0)
			last_note += 7;

		uniquePart.setEventPitch(uniquePart.getEventsCount() - 1, last_note);
	}

}
