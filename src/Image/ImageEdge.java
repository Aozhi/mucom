package Image;

import java.awt.image.BufferedImage;

public class ImageEdge {
	/*
	 * ��ȡͼ���Ե����
	 * 1���ֽ�ͼƬת���ɻҶ�ͼ
	 * 2���ٽ��Ҷ�ͼͨ��һά��˹�˲�
	 * 3����canny���Ӽ���ͼ���ݶȼ�����
	 * 4.��Ե���
	 * 5.���ر�Ե���ݶȷ�ֵͳ��ֱ��ͼ
	 * �ο��������ӣ�http://blog.csdn.net/likezhaobin/article/details/6892629
	 * @author ����
	 */
	/*ͼƬ���
	 * 
	 */
	private int Width;
	/*
	 * ͼƬ�߶�
	 */
	private int Height;
	/*
	 * ԭʼͼƬ����
	 */
	private int[] OriginalImageData;
	/*\
	 * �Ҷ�ͼƬ����
	 */
	private int[] GreyImageData;
	/*
	 * //�Ǽ���ֵ���ƽ��
	 */
	private static int[] N;                 
	/*
	 * �ݶȷ�ֵ
	 */
	private static int[] M;
	
	public static int[] getRGB( 
			BufferedImage image,
			int x, int y,
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}
	
	public int[] HistogramEdge( BufferedImage image)
	{
		Width = image.getWidth();
		Height = image.getHeight();
		OriginalImageData = new int[Height * Width];
		getRGB(image, 0, 0, Width, Height, OriginalImageData);
		

		int i, j;
		
		GreyImageData = new int[Height * Width];
		for (i = 0; i < Height; i++)
			for (j = 0; j < Width; j++)
			{
				int data = OriginalImageData[i * Width + j];
				int red = (data >> 16) &0xff;
				int green = (data >> 8) &0xff;
				int blue = data & 0xff;
				GreyImageData[i * Width + j] = (int)(0.114*blue + 0.587*green + 0.299*red);//(int)(0.072169*blue + 0.715160*green + 0.212671*red);
			}
			
		/*
		 * ƽ����ͼ������
		 */
		int[] pCanny = new int[Width * Height];
		/*
		 * ��˹�˲�
		 */
		double nSigma = 0.4;                            //�����˹�����ı�׼��  
		int nWidowSize = 1+2*(int)(3*nSigma);            //�����˲����ڵĴ�С  
		int nCenter = (nWidowSize)/2;                   //�����˲��������ĵ�����  
		double[] pdKernal = new double[nWidowSize * nWidowSize];
		double dSum_2 = 0.0;
		///////////////////////��ά��˹������ʽ////////////////////////////////////      
		////    x*x+y*y                        ///////////////  
		////-1*--------------                ///////////////  
		////1             2*Sigma*Sigma                ///////////////  
		////---------------- e                                   ///////////////  
		////2*pi*Sigma*Sigma                                     ///////////////  
		///////////////////////////////////////////////////////////////////////////
		for(i=0; i<nWidowSize; i++)  
		{  
		    for(j=0; j<nWidowSize; j++)  
		    {  
		        int nDis_x = i-nCenter;  
		        int nDis_y = j-nCenter;  
		        pdKernal[i+j*nWidowSize]=Math.exp(-(1/2)*(nDis_x*nDis_x+nDis_y*nDis_y)  
		            /(nSigma*nSigma))/(2*3.1415926*nSigma*nSigma);  
		        dSum_2 += pdKernal[i+j*nWidowSize];  
		    }  
		}  
		for(i=0; i<nWidowSize; i++)  
		{  
		    for(j=0; j<nWidowSize; j++)                 //���й�һ��  
		        {  
		        pdKernal[i+j*nWidowSize] /= dSum_2;  
		    }  
		}  
		
		int x;  
		int y;  
		for(i=0; i<Height; i++)  
		{  
		    for(j=0; j<Width; j++)  
		    {  
		        double dFilter=0.0;  
		        double dSum = 0.0;  
		        for(x=(-nCenter); x<=nCenter; x++)                     //��  
		        {  
		                        for(y=(-nCenter); y<=nCenter; y++)             //��  
		            {  
		                if( (j+x)>=0 && (j+x)<Width && (i+y)>=0 && (i+y)<Height) //�жϱ�Ե  
		                {  
		                    dFilter += (double)GreyImageData [(i+y)*Width + (j+x)] * pdKernal[(y+nCenter)*nWidowSize+(x+nCenter)];  
		                    dSum += pdKernal[(y+nCenter)*nWidowSize+(x+nCenter)];  
		                }  
		            }  
		        }  
		        pCanny[i*Width+j] = (int)(dFilter/dSum);  
		    }  
		}  
		/*
		 * ͼ����ǿ
		 * Canny����
		 * P[i,j]=(S[i,j+1]-S[i,j]+S[i+1,j+1]-S[i+1,j])/2
		 * Q[i,j]=(S[i,j]-S[i+1,j]+S[i,j+1]-S[i+1,j+1])/2
		 */
		double[] P = new double [Height * Width];
		double[] Q = new double [Height * Width];
		M = new int [Height * Width];
		double[] Theta = new double[Height * Width];
		//����x,y�����ƫ����  
		for(i=0; i<(Height-1); i++)  
		{  
		        for(j=0; j<(Width-1); j++)  
		        {  
		              P[i*Width+j] = (double)(pCanny[i*Width + Math.min(j+1, Width-1)] - pCanny[i*Width+j] + pCanny[Math.min(i+1, Height-1)*Width+Math.min(j+1, Width-1)] - pCanny[Math.min(i+1, Height-1)*Width+j])/2;  
		              Q[i*Width+j] = (double)(pCanny[i*Width+j] - pCanny[Math.min(i+1, Height-1)*Width+j] + pCanny[i*Width+Math.min(j+1, Width-1)] - pCanny[Math.min(i+1, Height-1)*Width+Math.min(j+1, Width-1)])/2;  
		    }  
		}  
		for(i=0; i<Height; i++)  
		{  
		        for(j=0; j<Width; j++)  
		        {  
		              M[i*Width+j] = (int)(Math.sqrt(P[i*Width+j]*P[i*Width+j] + Q[i*Width+j]*Q[i*Width+j])+0.5);  
		              Theta[i*Width+j] = Math.atan2(Q[i*Width+j], P[i*Width+j]) * 57.3;  
		              if(Theta[i*Width+j] < 0)  
		                    Theta[i*Width+j] += 360;              //������Ƕ�ת����0~360��Χ  
		    }  
		}
		N = new int[Width*Height];                       //�Ǽ���ֵ���ƽ��  
		double g1=0, g2=0, g3=0, g4=0;                            //���ڽ��в�ֵ���õ������ص�����ֵ  
		double dTmp1=0.0, dTmp2=0.0;                           //�������������ص��ֵ�õ��ĻҶ�����  
		double dWeight=0.0;                                    //��ֵ��Ȩ�� 
		
		for(i=0; i<Width; i++)  
		{  
		        N[i] = 0;  
		        N[(Height-1)*Width+i] = 0;  
		}  
		for(j=0; j<Height; j++)  
		{  
		        N[j*Width] = 0;  
		        N[j*Width+(Width-1)] = 0;  
		} 
		
		for(i=1; i<(Width-1); i++)  
		{  
		    for(j=1; j<(Height-1); j++)  
		    {  
		        int nPointIdx = i+j*Width;       //��ǰ����ͼ�������е�����ֵ  
		        if(M[nPointIdx] == 0)  
		            N[nPointIdx] = 0;         //�����ǰ�ݶȷ�ֵΪ0�����Ǿֲ����Ըõ㸳Ϊ0  
		        else  
		        {  
		        ////////�����ж��������������Ȼ����������ֵ///////  
		        ////////////////////��һ�����///////////////////////  
		        /////////       g1  g2                  /////////////  
		        /////////           C                   /////////////  
		        /////////           g3  g4              /////////////  
		        /////////////////////////////////////////////////////  
		        if( ((Theta[nPointIdx]>=90)&&(Theta[nPointIdx]<135)) ||   
		                ((Theta[nPointIdx]>=270)&&(Theta[nPointIdx]<315)))  
		            {  
		                //////����б�ʺ��ĸ��м�ֵ���в�ֵ���  
		                g1 = M[nPointIdx-Width-1];  
		                g2 = M[nPointIdx-Width];  
		                g3 = M[nPointIdx+Width];  
		                g4 = M[nPointIdx+Width+1];  
		                dWeight = Math.abs(P[nPointIdx])/Math.abs(Q[nPointIdx]);   //������  
		                dTmp1 = g1*dWeight+g2*(1-dWeight);  
		                dTmp2 = g4*dWeight+g3*(1-dWeight);  
		            }  
		        ////////////////////�ڶ������///////////////////////  
		        /////////       g1                      /////////////  
		        /////////       g2  C   g3              /////////////  
		        /////////               g4              /////////////  
		        /////////////////////////////////////////////////////  
		            else if( ((Theta[nPointIdx]>=135)&&(Theta[nPointIdx]<180)) ||   
		                ((Theta[nPointIdx]>=315)&&(Theta[nPointIdx]<360)))  
		            {  
		                g1 = M[nPointIdx-Width-1];  
		                g2 = M[nPointIdx-1];  
		                g3 = M[nPointIdx+1];  
		                g4 = M[nPointIdx+Width+1];  
		                dWeight = Math.abs(Q[nPointIdx])/Math.abs(P[nPointIdx]);   //����  
		                dTmp1 = g2*dWeight+g1*(1-dWeight);  
		                dTmp2 = g4*dWeight+g3*(1-dWeight);  
		            }  
		        ////////////////////���������///////////////////////  
		        /////////           g1  g2              /////////////  
		        /////////           C                   /////////////  
		        /////////       g4  g3                  /////////////  
		        /////////////////////////////////////////////////////  
		            else if( ((Theta[nPointIdx]>=45)&&(Theta[nPointIdx]<90)) ||   
		                ((Theta[nPointIdx]>=225)&&(Theta[nPointIdx]<270)))  
		            {  
		                g1 = M[nPointIdx-Width];  
		                g2 = M[nPointIdx-Width+1];  
		                g3 = M[nPointIdx+Width];  
		                g4 = M[nPointIdx+Width-1];  
		                dWeight = Math.abs(P[nPointIdx])/Math.abs(Q[nPointIdx]);   //������  
		                dTmp1 = g2*dWeight+g1*(1-dWeight);  
		                dTmp2 = g3*dWeight+g4*(1-dWeight);  
		            }  
		            ////////////////////���������///////////////////////  
		            /////////               g1              /////////////  
		            /////////       g4  C   g2              /////////////  
		            /////////       g3                      /////////////  
		            /////////////////////////////////////////////////////  
		            else if( ((Theta[nPointIdx]>=0)&&(Theta[nPointIdx]<45)) ||   
		                ((Theta[nPointIdx]>=180)&&(Theta[nPointIdx]<225)))  
		            {  
		                g1 = M[nPointIdx-Width+1];  
		                g2 = M[nPointIdx+1];  
		                g3 = M[nPointIdx+Width-1];  
		                g4 = M[nPointIdx-1];  
		                dWeight = Math.abs(Q[nPointIdx])/Math.abs(P[nPointIdx]);   //����  
		                dTmp1 = g1*dWeight+g2*(1-dWeight);  
		                dTmp2 = g3*dWeight+g4*(1-dWeight);  
		            }  
		        }         
		        //////////���оֲ����ֵ�жϣ���д������////////////////  
		        if((M[nPointIdx]>=dTmp1) && (M[nPointIdx]>=dTmp2))  
		            N[nPointIdx] = 128;  
		        else  
		            N[nPointIdx] = 0;  
		        }  
		}  
		
		int[] HistCounter = new int[1024];
		int pEdgeNum;             //���ܱ߽���  
		int MaxMag = 0;           //����ݶ���  
		int HighCount;  
		for(i=0;i<1024;i++)  
			HistCounter[i] = 0;  
		for(i=0; i<Height; i++)  
		{  
			for(j=0; j<Width; j++)  
	        	{  
					if(N[i*Width+j]!=0)  
						HistCounter[M[i*Width+j]]++;  
	        	}  
		}  
		pEdgeNum = HistCounter[0];  
		MaxMag = 0;                    //��ȡ�����ݶ�ֵ        
		for(i=1; i<1024; i++)           //ͳ�ƾ����������ֵ���ơ����ж�������  
		{  
		    if(HistCounter[i] != 0)       //�ݶ�Ϊ0�ĵ��ǲ�����Ϊ�߽���  
		    {  
		        MaxMag = i;  
		    }     
		    pEdgeNum += HistCounter[i]; 
		}  
		double  dRatHigh = 0.79;  
		double  dThrHigh;  
		//double  dThrLow;  
		//double  dRatLow = 0.5;  
		HighCount = (int)(dRatHigh * pEdgeNum + 0.5);  
		j=1;  
		int counter = HistCounter[1];  
		while((j<(MaxMag-1)) && (counter < HighCount))  
		{  
		       j++;  
		       counter += HistCounter[j];  
		}  
		dThrHigh = j;                                   //����ֵ  
		//dThrLow = (int)((dThrHigh) * dRatLow + 0.5);    //����ֵ
		
		for(i=0; i<Height; i++)  
		{  
		    for(j=0; j<Width; j++)  
		    {  
		        if((N[i*Width+j]==128) && (M[i*Width+j] >= dThrHigh))  
		        {  
		            N[i*Width+j] = 255;  
		            //TraceEdge(Height, Width, i, j, dThrLow);  
		        }  
		    }  
		}  
		
		int[] histogramEdge = new int[401];
		for (i=0; i<400;i++) histogramEdge[i] = 0;
		for(i=0; i<Height; i++)  
		{  
		    for(j=0; j<Width; j++)  
		    {  
		        if((N[i*Width+j]==255))  
		        {  
		            histogramEdge[M[i*Width+j]]++;
		        }
		    }  
		}  
		return histogramEdge;
	}//
}
