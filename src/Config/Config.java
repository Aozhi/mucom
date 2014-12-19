package Config;

import java.util.HashMap;

import Composition.Hongyu.CompositionHongyu;
import Composition.Test.CompositionTest;
import Generate.Composition;
import Generate.ParameterConversion;
import Generate.ParameterGenerate;
import ParameterConversion.Test.ConversionTest;
import ParameterConversion.Test.ConversionRegression;
import ParameterGenerate.Test.GenerateTest;

/**
 * ����һ��������
 * ���������ÿ��������ʹ�õľ�����
 * ��hashMap������
 */
public class Config {
	
	/**
	 * ������������Ҫʹ�õľ��������ࣨ����������
	 */
	public static final String CURRENT_COMPOSITION_STRING = "hongyu";
	/**
	 * ������������Ҫʹ�õľ������ת���ࣨͼ�����-���ֲ�����
	 */
	public static final String CURRENT_CONVERSION_STRING = "regression";
	/**
	 * ������������Ҫʹ�õľ�����������ࣨ����ͼ�������
	 */
	public static final String CURRENT_GENERATE_STRING = "test";
	
	/**
	 * �洢�ַ���-������Ĺ�����
	 */
	public static final HashMap<String, Composition> COMPOSITION_MAP = new HashMap<>();
	/**
	 * �洢�ַ���-����ת����Ĺ�����
	 */
	public static final HashMap<String, ParameterConversion> PARAMETER_CONVERSION_MAP = new HashMap<>();
	/**
	 * �洢�ַ���-����������Ĺ�����
	 */
	public static final HashMap<String, ParameterGenerate> PARAMETER_GENERATE_MAP = new HashMap<>();
	
	/**
	 * ͼ���������
	 */
	public static int IMAGE_PARAMETER_NUM = 10;
	
	/**
	 * ת��������ֵ
	 */
	public static String[] IMAGE_PARA_KEY = {"1","2","3","4","5"};
	
	/**
	 * ���ֲ�����ֵ
	 */
	public static String[] MUSIC_PARA_KEY={"�����ߵ�","�������","����仯"};
	
	public static int IMAGE_PARA_LENGTH=256;
	
	/**
	 * ��ʼ����Щ������
	 */
	public static void configInit() {
		//��ʼ�������������
		COMPOSITION_MAP.put("test", new CompositionTest());
		COMPOSITION_MAP.put("hongyu", new CompositionHongyu());
		//��ʼ������ת���������
		PARAMETER_CONVERSION_MAP.put("test", new ConversionTest());
		PARAMETER_CONVERSION_MAP.put("regression", new ConversionRegression());
		//��ʼ�����������������
		PARAMETER_GENERATE_MAP.put("test", new GenerateTest());
	}
	
	/**
	 * �����ϲ�����Ŀ�ĸ���
	 */
	public static final int PARAMETER_NUMBER = 5;
	
	
}
