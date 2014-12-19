package Composition.Hongyu.Ornamenters;

import java.util.HashMap;

import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.Ornamenter;

/**
 * ��������ȡ��
 */
public class OrnamenterGetter {
	
	/**
	 * ������ö��
	 */
	public static enum GeneratorsEnum {
		DefaultOrnamenter,
		SimpleOrnamentationModified,
		Appoggiatura,
	}
	
	/**
	 * ��������
	 */
	public static final HashMap<GeneratorsEnum, Ornamenter> ORNAMENTERS = new HashMap<>();
	
	static {
		ORNAMENTERS.put(GeneratorsEnum.DefaultOrnamenter, new DefaultOrnamenter());
		ORNAMENTERS.put(GeneratorsEnum.SimpleOrnamentationModified, new SimpleOrnamentationModified());
		ORNAMENTERS.put(GeneratorsEnum.Appoggiatura, new Appoggiatura());
	}
	
	/**
	 * ѡȡ������
	 * @param ���ֲ���
	 * @return ������
	 */
	public static Ornamenter getOrnamenter(HashMap<String, Integer> parameter) {
		return (Ornamenter) Common.getRandomElement(ORNAMENTERS.values().toArray());
	}
	
}
