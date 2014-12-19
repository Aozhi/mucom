package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.UniquePart;

public class SimpleOrnamentation implements Ornamenter {
	
	double getEventLen(UniquePart up, int i) {
		return (up.getEventEnd(i).startBar - up.getEventStart(i).startBar)
				* up.getMeter()
				+ (up.getEventEnd(i).position - up.getEventStart(i).position);
	}

	@Override
	public void ornament(Part part, UniquePart uniquePart,
			HashMap<String, Integer> parameter) {
		// TODO Auto-generated method stub
		// ͨ��������֮�����̴ٵ������ӿ����������仯 ͬʱ�����߼���΢��
		int off_mode = rndInt(0, 1); // off_mode �����������λ�ã�+1/-1��
		if (off_mode == 0)
			off_mode = -1;

		int last_off = rndInt(0, 1);

		for (int i = 0; i < uniquePart.getEventsCount(); i++) {
			Time t1 = uniquePart.getEventStart(i);
			Time t2 = uniquePart.getEventEnd(i);

			boolean tril = false;

			//66%���� �Գ��ȴ���1.4���¼���������
			if (rndInt(0, 2) != 0 && getEventLen(uniquePart, i) > 1.4) {
				double len = 0.25;

				// �ڳ�����֮������4��ʮ�������� ����+0 1 0 -1�� +0 -1 0 +1
				part.addEvent(t1, new Time(t1.startBar, t1.position + len), part
						.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i));
				part.addEvent(new Time(t1.startBar, t1.position + len * 2), new Time(
						t1.startBar, t1.position + len * 3), part.getAbsolutePitch(uniquePart
						.getEventPitch(i)), uniquePart.getEventVolume(i));

				if (rndInt(0, 1) == 0) {
					part.addEvent(new Time(t1.startBar, t1.position + len), new Time(
							t1.startBar, t1.position + len * 2), part.getAbsolutePitch(uniquePart
							.getEventPitch(i)) - 1, uniquePart.getEventVolume(i));
					part.addEvent(new Time(t1.startBar, t1.position + len * 3),
							new Time(t1.startBar, t1.position + len * 4), part
									.getAbsolutePitch(uniquePart.getEventPitch(i) + 1), uniquePart.getEventVolume(i));
				} else {
					part.addEvent(new Time(t1.startBar, t1.position + len), new Time(
							t1.startBar, t1.position + len * 2), part.getAbsolutePitch(uniquePart
							.getEventPitch(i) + 1), uniquePart.getEventVolume(i));
					part.addEvent(new Time(t1.startBar, t1.position + len * 3),
							new Time(t1.startBar, t1.position + len * 4), part
									.getAbsolutePitch(uniquePart.getEventPitch(i)) - 1, uniquePart.getEventVolume(i));
				}

				tril = true;

				t1.position += len * 4; //��ԭ�����ƺ����̡�

			}
			//66%���ʶԳ��ȴ���0.9���¼���������
			else if (getEventLen(uniquePart, i) > 0.9 && rndInt(0, 2) != 0) {
				double len = 0.25;

				// �ڳ�����֮������2��ʮ�������� ����+0 1�� +0 -1
				part.addEvent(t1, new Time(t1.startBar, t1.position + len), part
						.getAbsolutePitch(uniquePart.getEventPitch(i)), uniquePart.getEventVolume(i));
				if (rndInt(0, 1) == 0)
					part.addEvent(new Time(t1.startBar, t1.position + len), new Time(
							t1.startBar, t1.position + len * 2), part.getAbsolutePitch(uniquePart
							.getEventPitch(i)) - 1, uniquePart.getEventVolume(i));
				else
					part.addEvent(new Time(t1.startBar, t1.position + len), new Time(
							t1.startBar, t1.position + len * 2), part.getAbsolutePitch(uniquePart
							.getEventPitch(i) + 1), uniquePart.getEventVolume(i));
				t1.position += len * 2;//��ԭ�����ƺ����̡�

				tril = true;
			}

			//l1 l2��ʾǰ���¼�����ʼλ��
			double l1 = -100;
			if (i > 0)
				l1 = uniquePart.getEventEnd(i - 1).startBar * uniquePart.getMeter()
						+ uniquePart.getEventEnd(i - 1).position;
			double l2 = uniquePart.getEventStart(i).startBar * uniquePart.getMeter()
					+ uniquePart.getEventStart(i).position;

			if (!tril && l1 < l2 - 0.1 && rndInt(0, 1) == 0) {
				double len = 0.1;
				if (rndInt(0, 1) == 0)
					part.addEvent(new Time(t1.startBar, t1.position - len), t1, part
							.getAbsolutePitch(uniquePart.getEventPitch(i)) - 1, uniquePart.getEventVolume(i));
				else
					part.addEvent(new Time(t1.startBar, t1.position - len), t1, part
							.getAbsolutePitch(uniquePart.getEventPitch(i) + 1), uniquePart.getEventVolume(i));

				tril = true;
			}

			int off_diat = 0;
			int off_chrom = 0;
			if (i > 0 && i < uniquePart.getEventsCount() - 1 && !tril && last_off == 0) {
				if (uniquePart.getEventPitch(i + off_mode) == uniquePart.getEventPitch(i)) {
					off_diat = 1;
					last_off = 1;
				} else
					last_off = 0;
			} else
				last_off = 0;

			part.addEvent(t1, t2, part.getAbsolutePitch(uniquePart.getEventPitch(i) + off_diat)
					+ off_chrom, uniquePart.getEventVolume(i));

		}
	}

	private int rndInt(int i, int j) {
		return Common.getRandomInteger(i, j + 1);
	}

}
