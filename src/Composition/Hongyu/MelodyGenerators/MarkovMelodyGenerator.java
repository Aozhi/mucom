package Composition.Hongyu.MelodyGenerators;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import Composition.Hongyu.CompositionHongyu;
import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Constant;
import Composition.Hongyu.Essential.MarkovChain;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.UniquePart;

public class MarkovMelodyGenerator implements MelodyGenerator {

	@Override
	public void generateMelody(UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// �ָ�����Ʒ���
		MarkovChain markovChain = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new FileInputStream(CompositionHongyu.TRAINING_RESULT));
			markovChain = (MarkovChain) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			System.err.println("load markov chain failed");
			DefaultMelodyGenerator defaultMelodyGenerator = new DefaultMelodyGenerator();
			defaultMelodyGenerator.generateMelody(uniquePart, parameter);
			return;
		}
		// ��������
		int basis = uniquePart.getEventBasis(0);
		int note = basis;
		// ���ݲ���������׼
		int pitchParameter = parameter.get(Constant.PITCH_STRING);
		if (pitchParameter >= 100) pitchParameter = 99;
		if (pitchParameter <= 0) pitchParameter = 0;
		int[] offsets = {-4, -4, -2, -2, 0, 0, 2, 2, 4, 4};
		basis = basis + offsets[pitchParameter / 10];
		// ��¼�������������
		ArrayList<Integer> condition = new ArrayList<>();
		ArrayList<Integer> notes = new ArrayList<>();
		// ѭ����������
		for (int i = 0; i < uniquePart.getEventsCount() - 1; i++) {
			if (i == uniquePart.getEventsCount() - 2)
				note = (note + 1) / 2;
			// ����note
			int backup = note;
			// ����
			note = uniquePart.alignPitchToHarmonic(i, note);
			// ����A-B-A-B-A����
			if (notes.size() >= 4) {
				int n1 = notes.get(notes.size() - 4);
				int n2 = notes.get(notes.size() - 3);
				int n3 = notes.get(notes.size() - 2);
				int n4 = notes.get(notes.size() - 1);
				if (n1 == n3 && n2 == n4 && n3 == note) {
					note = 2 * n2 - n1;
					note = uniquePart.alignPitchToHarmonic(i, note);
				}
			}
			// ����A-A-A���ͺ�A-B-B����
			if (notes.size() >= 2) {
				int n1 = notes.get(notes.size() - 2);
				int n2 = notes.get(notes.size() - 1);
				if (n1 != n2 && n2 == note) {
					note = n1;
					note = uniquePart.alignPitchToHarmonic(i, note);
				}
				if (n1 == n2 && n2 == note) {
					n2 = note - 3;
					n2 = uniquePart.alignPitchToHarmonic(i - 1, n2);
					uniquePart.setEventPitch(i - 1, n2);
				}
			}
			uniquePart.setEventPitch(i, note);
			// ������ֵ�������ܿ����������
			if (note != backup && test(condition.size() * 0.4))
				condition.clear();
			// �������һ�����ʣ��������������
			if (condition.size() > 3 && test(condition.size() * 0.1))
				condition.clear();
			// ������ӵ�������������
			condition.add(note);
			notes.add(note);
			// ��ȡ����
			if (i < uniquePart.getEventsCount() - 2) {
				backup = note;
				note = markovChain.getNote(condition);
				// ��������������ϴ�����Զ�������ѡȡ
				double distance = Math.abs(note - backup);
				if (distance > 2 && test(0.2 * distance + 0.2))
					note = backup + getDelta(notes, basis, parameter);
				// �������������ϴ���ͬʱ������ѡȡ
				double coefficient = (double)(parameter.get(Constant.VARIATION_STRING)) / 50.0 + Double.MIN_NORMAL;
				while (note == backup && test(coefficient)) {
					if (test(0.8))
						note = markovChain.getNote(condition);
					else
						note = backup + getDelta(notes, basis, parameter);
				}
				// �������һ�����ʣ������ѡȡ
				if (test(0.2))
					note = backup + getDelta(notes, basis, parameter);
				// �������߻���͵ĵ���
				if (note < basis - 8 * coefficient) {
					if (test(0.6))
						note += Common.getRandomInteger(0, 3);
					if (test(0.2))
						note = basis - Common.getRandomInteger(0, (int)(4 * coefficient) + 1);
				}
				if (note > basis + 8 * coefficient) {
					if (test(0.6))
						note -= Common.getRandomInteger(0, 3);
					if (test(0.2))
						note = basis + Common.getRandomInteger(0, (int)(4 * coefficient) + 1);
				}
			}
		}
		// �������һ����
		int last_note = uniquePart.getEventBasis(0);
		if (note > 5)
			last_note += 7;
		if (note <= -3)
			last_note -= 7;
		uniquePart.setEventPitch(uniquePart.getEventsCount() - 1, last_note);
	}

	/**
	 * ����һ��ʵ��ɹ��ĸ��ʣ����ոø��ʷ���ʵ����
	 * @param probability �����ĸ���
	 * @return ʵ����
	 */
	private boolean test(double probability) {
		return probability > Common.getRandomDouble(0, 1);
	}
	
	/**
	 * ��ȡ�¸������������������
	 * @param notes ��������
	 * @param basis ��׼��
	 * @return
	 */
	private int getDelta(ArrayList<Integer> notes, int basis,
			HashMap<String, Integer> parameter) {
		// ͳ�����8����������ƫ��
		int start = notes.size() >= 8 ? notes.size() - 8 : 0;
		double sum = 0;
		for (int i = start; i < notes.size(); i++) {
			sum += notes.get(i);
		}
		double average = sum / (notes.size() - start);
		double offset = average - basis;
		double coefficient = Common.getCoefficient(parameter.get(Constant.VARIATION_STRING), 0, 100);
		// ����ƫ�ƾ�������
		if (offset <= -4 * coefficient)
			return 2;
		else if (offset <= -2.5 * coefficient)
			return Common.getRandomInteger(1, 3);//1��2
		else if (offset <= -1 * coefficient)
			return Common.getRandomInteger(0, 3);//0��1��2
		else if (offset <= 1 * coefficient)
			return Common.getRandomInteger(-1, 2) * 2;//-2��0��2
		else if (offset <= 2.5 * coefficient)
			return Common.getRandomInteger(-2, 1);//-2��-1��0
		else if (offset <= 4 * coefficient)
			return Common.getRandomInteger(-2, 0);//-2��-1
		else
			return -2;
	}
}
