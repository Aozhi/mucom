package Composition.Hongyu.Essential;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ����Ʒ����Լ���صĲ���
 */
public class MarkovChain implements Serializable {
	
	/**
	 * ����Ʒ����ĳ���
	 */
	public static final int MARKOV_CHAIN_LENGTH = 3;

	/**
	 * ���л�ID
	 */
	private static final long serialVersionUID = 4265830282812668463L;
	
	/**
	 * keyΪǰ������������ϣ�valueΪÿ�����������Ӧ�ĸ���
	 */
	HashMap<String, HashMap<Integer, Double>> probability = new HashMap<>();
	
	/**
	 * ���캯��
	 * @param ���е�ԭʼ����
	 */
	public MarkovChain(ArrayList<Integer[]> rawData) {
		//�̶�������ÿ�����ܽ�����ֵ�Ƶ��
		HashMap<String, HashMap<Integer, Integer>> frequency = new HashMap<>();
		for (Integer[] integers : rawData) {
			//��ȡ�����ַ���
			String condition = "";
			for (int i = 0; i < integers.length - 1; i++) {
				condition += (integers[i] + ",");
			}
			int resultPicth = integers[integers.length - 1];
			//����Ƿ�Ϊ�³�������
			if (!frequency.containsKey(condition)) {
				HashMap<Integer, Integer> hashMap = new HashMap<>();
				hashMap.put(resultPicth, 1);
				frequency.put(condition, hashMap);
			} else {
				//���ý�������Ƿ���ֹ�
				HashMap<Integer, Integer> hashMap = frequency.get(condition);
				if (hashMap.containsKey(resultPicth)) {
					hashMap.put(resultPicth, hashMap.get(resultPicth) + 1);
				} else {
					hashMap.put(resultPicth, 1);
				}
			}
		}
		//ת��Ϊ������ʽ
		for (String condition : frequency.keySet()) {
			HashMap<Integer, Integer> frequencyMap = frequency.get(condition);
			HashMap<Integer, Double> probabilityMap = new HashMap<>();
			int rawCount = 0;
			for (Integer pitch : frequencyMap.keySet()) {
				rawCount += frequencyMap.get(pitch);
			}
			int count = 0;
			for (Integer pitch : frequencyMap.keySet()) {
				if ((double)(frequencyMap.get(pitch)) / (double)(rawCount) > 0.1) {
					count += frequencyMap.get(pitch);
				}
			}
			for (Integer pitch : frequencyMap.keySet()) {
				if ((double)(frequencyMap.get(pitch)) / (double)(rawCount) > 0.1) {
					probabilityMap.put(pitch, (double)(frequencyMap.get(pitch)) / (double)(count));
				}
			}
			probability.put(condition, probabilityMap);
		}
	}
	
	/**
	 * ��������Ʒ�������������
	 * @param condition ����
	 * @return ����
	 */
	public int getNote(ArrayList<Integer> condition) {
		int length = Math.min(MARKOV_CHAIN_LENGTH, condition.size());
		int note = Integer.MIN_VALUE;
		for (int i = length; i > 0; i--) {
			//�˶�ƽ��
			int octiveCount = 0;
			int startPosition = condition.size() - i;
			int startPitch = condition.get(startPosition);
			while (startPitch <= 0) {
				startPitch += 7;
				octiveCount--;
			}
			while (startPitch >= 8) {
				startPitch -= 7;
				octiveCount++;
			}
			//��ȡ����
			String conditionString = "";
			for (int j = startPosition; j < condition.size(); j++) {
				int pitch = condition.get(j) - octiveCount * 7;
				conditionString += (pitch + ",");
			}
			if (probability.containsKey(conditionString)) {
				HashMap<Integer, Double> hashMap = probability.get(conditionString);
				Integer[] pitchs = new Integer[hashMap.keySet().size()];
				Double[] sumProbability = new Double[hashMap.keySet().size()];
				int index = 0;
				for (Integer integer : hashMap.keySet()) {
					pitchs[index] = integer;
					sumProbability[index] = hashMap.get(integer);
					index++;
				}
				//�����ۼ�
				double sum = 0;
				for (int j = 0; j < sumProbability.length; j++) {
					sumProbability[j] += sum;
					sum = sumProbability[j];
				}
				//��ֹdouble��������
				sumProbability[sumProbability.length - 1] = 1.0;
				//ѡȡnote
				double random = Common.getRandomDouble(0, 1);
				index = sumProbability.length - 1;
				for (; index >= 0; index--) {
					if (sumProbability[index] < random) {
						break;
					}
				}
				return pitchs[index + 1] + octiveCount * 7;
			}
		}
		//û�ҵ�����
		if (note == Integer.MIN_VALUE) {
			note = condition.get(condition.size() - 1);
		}
		return note;
	}
	
}
