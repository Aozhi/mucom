package Image;

import java.util.HashMap;

import  File.Picture;
import Config.Config;

/**
 * ��ȡ�������������£�
 * 1.ASM(����):
 *		�Ҷȹ�������Ԫ��ֵ��ƽ���ͣ���ӳ��ͼ��Ҷȷֲ����ȳ̶Ⱥ������ϸ�ȡ�
 *		����������������ֵ����ȣ���ASMֵС���෴���������һЩֵ�������ֵС����ASMֵ��
 *		������������Ԫ�ؼ��зֲ�ʱ����ʱASMֵ��ASMֵ�����һ�ֽϾ�һ�͹���仯������ģʽ��
 * 2.Contrast:(�Աȶ�)
 * 		��ӳ��ͼ��������Ⱥ���������ǳ�ĳ̶ȡ�
 * 		������Խ���Աȶ�Խ���Ӿ�Ч��Խ��������֮���Աȶ�С������ǳ��Ч��ģ����
 * 		�ҶȲ�Աȶȴ�����ض�Խ�࣬���ֵԽ�󡣻Ҷȹ���������Զ��Խ��ߵ�Ԫ��ֵԽ��CONԽ��
 * 3.IDM:(����)
 * 		��ӳ������������̶Ⱥ͹���̶ȡ�
 * 		���������������Խ�ǿ�����������ģ�ֵ�ϴ��������µģ����������ģ�ֵ��С��
 * 4.Entropy:(��)
 * 		����ͼ��ĸ��ӳ��򣬵����ӳ����ʱ����ֵ�ϴ󣬷�֮���С
 * 5.Correlation(�����):
 * 		���ֵ��С��ӳ��ͼ���оֲ��Ҷ�����ԡ�
 * 		������Ԫ��ֵ�������ʱ�����ֵ�ʹ�
 * 		�෴�����������Ԫֵ���ܴ������ֵС��
 * 
 * �����ڲ�ͬ������(0, 90, 180, 270)��ȡ��������
 * Angle           Offset[x,y]
 * 	0              [ D  0]
 *	45             [ D -D]
 *	90             [ 0 -D]
 *	135            [-D -D]
 * 
 * @author LY
 *
 */

public class ImageTexture {
	
	private static int size = 256;
	private static boolean doIcalculateASM = true;
	private static boolean doIcalculateContrast = true;
	private static boolean doIcalculateCorrelation = true;
	private static boolean doIcalculateIDM = true;
	private static boolean doIcalculateEntropy = true;	
	
	private double [][] glcm;
	
	public void calGLCM(Picture picture, int stepX, int stepY){
		// This part get al the pixel values into the pixel [ ] array 

		int [][] pixels =picture.getGrayscales();
		int width = picture.getImageWidth();
		int height = picture.getImageHeight();
		
		// The variable a holds the value of the pixel where the Image Processor is sitting its attention
		// The varialbe b holds the value of the pixel which is the neighbor to the  pixel where the Image Processor is sitting its attention

		int a;
		int b;
		double pixelCounter=0;	

		//====================================================================================================
		// This part computes the Gray Level Correlation Matrix based in the step selected by the user
		
		glcm= new double [size][size];
		for (int y=0; y<height; y++) 	
		{
			for (int x=0; x<width; x++)	 
			{				
				a = 0xff & pixels[x][y];
				b = 0xff &	(picture.getGrayscale(x+stepX, y+stepY));					
				glcm [a][b] +=1;
				glcm [b][a] +=1;
				pixelCounter +=2;
			}
		}	
		
		//=====================================================================================================

		// This part divides each member of the glcm matrix by the number of pixels. The number of pixels was stored in the pixelCounter variable
		// The number of pixels is used as a normalizing constant

		for (a=0;  a<size; a++)  
		{
			for (b=0; b<size;b++) 
			{
				glcm[a][b]=(glcm[a][b])/(pixelCounter);
			}
		}
	}
	
	/**
	 * ������׾�
	 * @return
	 */
	public double calASM(){
		double asm = 0.0;
		for (int a=0;  a<size; a++)  {
			for (int b=0; b<size;b++) {
				asm=asm+ (glcm[a][b]*glcm[a][b]);
			}
		}
		return asm;
	}
	
	/**
	 * ����Աȶ�	
	 * @return
	 */
	public double calContrast(){
		double contrast = 0;
		for (int a=0;  a<size; a++)  {
			for (int b=0; b<size;b++) {
				contrast=contrast+ (a-b)*(a-b)*(glcm[a][b]);
			}
		}
		return contrast;
	}
	
	/**
	 * ��������
	 * @return
	 */
	public double calIDM(){
		double IDM=0.0;
		for (int a=0;  a<size; a++)  
		{
			for (int b=0; b<size;b++) 
			{
				IDM=IDM+ (glcm[a][b]/(1+(a-b)*(a-b)))  ;
			}
		}
		return IDM;
	}
	
	/**
	 * ������
	 * @return
	 */
	public double calEntropy(){
		double entropy=0.0;
		for (int a=0;  a<size; a++)  
		{
			for (int b=0; b<size;b++) {
				if (glcm[a][b]>0) {
					entropy=entropy-(glcm[a][b]*(Math.log(glcm[a][b])));
				}
			}
		}
		return entropy;
	}
	
	/**
	 * ������ض�
	 * @return
	 */
	public double calCorrelation(){
		// px []  and py [] are arrays to calculate the correlation
		// meanx and meany are variables  to calculate the correlation
		//  stdevx and stdevy are variables to calculate the correlation

		//First step in the calculations will be to calculate px [] and py []
		int a = 0, b = 0;
		double correlation=0.0;
		double px=0, py=0;
		//double meanx=0.0;
		//double meany=0.0;
		double stdevx=0.0;
		double stdevy=0.0;

		for (a=0; a<size;a++)
		{
		   for (b=0; b <size; b++)
		   {
              px=px+a*glcm [a][b];  
              py=py+b*glcm [a][b];
		   } 
		}

		// Now calculate the standard deviations
		for (a=0; a<size; a++)
		{
			for (b=0; b <size; b++)
			{
				stdevx=stdevx+(a-px)*(a-px)*glcm [a][b];
				stdevy=stdevy+(b-py)*(b-py)*glcm [a][b];
			}
		}

		// Now finally calculate the correlation parameter
		for (a=0;  a<size; a++)  
		{
			for (b=0; b<size;b++) 
			{
				correlation=correlation+((a-px)*(b-py)*glcm [a][b]/(stdevx*stdevy)) ;
			}
		}
		
		return correlation;
	}
	
	/**
	 * ����ͼ���������
	 * @return ͼ���������
	 */
	public HashMap<String, Integer> getParameter(Picture picture){
		HashMap<String, Integer> parameter = new HashMap<>();

		//=====================================================================================================
		// This part calculates the angular second moment; the value is stored in glsm
		calGLCM(picture, 1, 0);

		//=====================================================================================================
		// This part calculates the angular second moment; the value is stored in asm

		if (doIcalculateASM==true){
			double asm = calASM();
			parameter.put(Config.IMAGE_PARA_KEY[0],(int)(asm));
		}

		//=====================================================================================================
		// This part calculates the contrast; the value is stored in contrast

		if (doIcalculateContrast==true){
			double contrast=calContrast();
			parameter.put(Config.IMAGE_PARA_KEY[1],(int)(contrast));
		}

		//=====================================================================================================
		//This part calculates the correlation; the value is stored in correlation
		
		if(doIcalculateCorrelation){
			double correlation = calCorrelation();
			parameter.put(Config.IMAGE_PARA_KEY[2],(int)(correlation));
		}
		
		//===============================================================================================
		// This part calculates the inverse difference moment

		if(doIcalculateIDM){
			double IDM = calIDM();
			parameter.put(Config.IMAGE_PARA_KEY[3],(int)(IDM));
		}

		//===============================================================================================
		// This part calculates the entropy

		if (doIcalculateEntropy==true){
			double entropy=calEntropy();
			parameter.put(Config.IMAGE_PARA_KEY[4],(int)(entropy));
		}		
		
		return parameter;
	}
}
