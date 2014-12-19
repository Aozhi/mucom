package Composition.Hongyu.MelodyGenerators;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

/**
 * WideRandomMelody
 */
public class WideRandomMelody implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		int note = uniquePart.getEventBasis(0);

		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			note = uniquePart.alignPitchToHarmonic(i, note);
			uniquePart.setEventPitch(i, note);
			if (i < uniquePart.getEventsCount() - 2)
				note += Common.getRandomInteger(-3, 4); 
			if (note < uniquePart.getEventBasis(0) - 8 && Common.getRandomInteger(0, 3) == 0)
				note = uniquePart.getEventBasis(0) + Common.getRandomInteger(-2, 7);
			if (note > uniquePart.getEventBasis(0) + 8 && Common.getRandomInteger(0, 3) == 0)
				note = uniquePart.getEventBasis(0) - Common.getRandomInteger(-2, 7);
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
