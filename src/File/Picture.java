package File;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * ͼ���࣬������ȡͼƬ��Ϣ�ͻ�������
 */
public class Picture {
	
	/**
	 * �洢��ȡ��ͼ��
	 */
	private BufferedImage image;
	
	/**
	 * ������
	 */
	public class Pixel {
		
		/**
		 * ��ɫ����ֵ
		 */
		public int r;
		
		/**
		 * ��ɫ����ֵ
		 */
		public int g;
		
		/**
		 * ��ɫ����ֵ
		 */
		public int b;
		
		/**
		 * x����
		 */
		public int x;
		
		/**
		 * y����
		 */
		public int y;
	}
	
	/**
	 * ���캯������ָ��·���ж�ȡͼ��
	 * @param path ͼ����ļ�·��
	 */
	public Picture(String path) {
		// TODO Auto-generated constructor stub
		try {
			image = ImageIO.read(new File(path));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ���캯������ָ����·����ȡͼ�񣬲����ŵ�ָ����С
	 * @param path ͼ����ļ�·��
	 * @param width ���
	 * @param height �߶�
	 */
	public Picture(String path, int width, int height) {
		// TODO Auto-generated constructor stub
		try {
			image = ImageIO.read(new File(path));
			AffineTransform transform = new AffineTransform();
			transform.scale((double)(width) / image.getWidth(), (double)(height) / image.getHeight());
			AffineTransformOp transformOp = new AffineTransformOp(transform, null);
			BufferedImage result = new BufferedImage(width, height, image.getType());
			transformOp.filter(image, result);
			image = result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ���캯������ָ����·����ȡͼ�񣬲������Ϳ����ŵ�ָ���ٷֱ�
	 * @param path ͼ����ļ�·��
	 * @param percentage �ٷֱ�
	 */
	public Picture(String path, double percentage) {
		// TODO Auto-generated constructor stub
		try {
			image = ImageIO.read(new File(path));
			AffineTransform transform = new AffineTransform();
			transform.scale(percentage, percentage);
			AffineTransformOp transformOp = new AffineTransformOp(transform, null);
			BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
			transformOp.filter(image, result);
			image = result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ���캯������ָ����·����ȡͼ�񣬲����ŵ�ָ����С�����ֳ����������
	 * @param path ͼ����ļ�·��
	 * @param size ���
	 */
	public Picture(String path, int size) {
		try {
			image = ImageIO.read(new File(path));
			double percentage = Math.sqrt((double)size / image.getWidth() / image.getHeight());
			AffineTransform transform = new AffineTransform();
			transform.scale(percentage, percentage);
			AffineTransformOp transformOp = new AffineTransformOp(transform, null);
			BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
			transformOp.filter(image, result);
			image = result; 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * �����趨ͼƬ��С
	 * @param percentage ͼƬ��С�İٷֱ�
	 */
	public void resize(double percentage) {
		AffineTransform transform = new AffineTransform();
		transform.scale(percentage, percentage);
		AffineTransformOp transformOp = new AffineTransformOp(transform, null);
		BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
		transformOp.filter(image, result);
		image = result;
	}
	
	public static BufferedImage resize(BufferedImage image, double percentage) {
		AffineTransform transform = new AffineTransform();
		transform.scale(percentage, percentage);
		AffineTransformOp transformOp = new AffineTransformOp(transform, null);
		BufferedImage result = new BufferedImage((int)(image.getWidth() * percentage), (int)(image.getHeight() * percentage), image.getType());
		transformOp.filter(image, result);
		image = result;
		return image;
	}
	
	/**
	 * ���ָ�����������
	 * @param x ������
	 * @param y ������
	 * @return ����
	 */
	public Pixel getPixel(int x, int y) {
		int result = image.getRGB(x, y);
		Pixel pixel = new Pixel();
		pixel.r = (result & 0xff0000) >> 16;
		pixel.g = (result & 0xff00) >> 8;
		pixel.b = (result & 0xff);
		pixel.x = x;
		pixel.y = y;
		return pixel;
	}
	
	/**
	 * �����������
	 * @return �������ص�����
	 */
	public Pixel[][] getPixels() {
		Pixel[][] result = new Pixel[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				//getPixel�����Ĳ���Ϊ�������ꡢ������
				result[i][j] = getPixel(j, i);
			}
		}
		return result;
	}
	
	/**
	 * ��ȡָ������ĻҶ�
	 * @param x ������
	 * @param y ������
	 * @return ���صĻҶ�
	 */
	public int getGrayscale(int x, int y) {
		Pixel pixel = getPixel(x, y);
		return (pixel.r * 30 + pixel.g * 59 + pixel.b * 11) / 100;
	}
	
	/**
	 * ��ȡ�������صĻҶ�
	 * @return ���صĻҶ�����
	 */
	public int[][] getGrayscales() {
		int[][] result = new int[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				//getGrayscale�����Ĳ���Ϊ�������ꡢ������
				result[i][j] = getGrayscale(j, i);
			}
		}
		return result;
	}
	
	/**
	 * ��ȡͼ��Ŀ�
	 * @return ͼ��Ŀ�
	 */
	
	public int getImageWidth(){
		return image.getWidth();
	}
	
	/**
	 * ��ȡͼ��ĸ�
	 * @return ͼ��ĸ�
	 */

	public int getImageHeight(){
		return image.getHeight();
	}
	
	/**
	 * ��ȡͼ���RGBֵ
	 * @return ͼ���RGB����
	 */
	public int[] getRGB( 
			int x, int y,
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}
	
	public static int[] getRGB( 
			BufferedImage image,int x, int y,
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}

	/**
	 * ����ͼ���RGBֵ
	 */
	public void setRGB( 
			int x, int y, 
			int width, int height, int[] pixels ) 
	{
		int t = image.getType();
		if ( t == BufferedImage.TYPE_INT_ARGB || t == BufferedImage.TYPE_INT_RGB )
			image.getRaster().setDataElements(x, y, width, height, pixels);
		image.setRGB(x, y, width, height, pixels, 0, width);
	}
	
	/**
	 * ��ȡͼ�����ɫ�Ҷ�ֱ��ͼ
	 * @return ͼ���256λ��ɫ�Ҷ�ֱ��ͼ
	 */
	
	public int[] getHistogram() {
		int[] srcPixels = new int[image.getWidth() * image.getHeight()];
		int[] itensity = new int[256];
		//initialzie the itensity
		for ( int i = 0; i < itensity.length; i++ ) itensity[i] = 0;
		//fill the srcPixels
		getRGB(0, 0, image.getWidth(), image.getHeight(), srcPixels);
		
		int index = 0;
		int r, g, b;
		int gray;
		for ( int row = 0; row < image.getHeight(); row++ ) {
			for ( int col = 0; col < image.getWidth(); col++ ) {
				index = row * image.getWidth() + col;
				//a = (srcPixels[index] >> 24) & 0xFF;
				r = (srcPixels[index] >> 16) & 0xFF;
				g = (srcPixels[index] >> 8 ) & 0xFF;
				b = (srcPixels[index] >> 0 ) & 0xFF;
				gray = (int) (0.299 * (double) r)
						+ (int) (0.587 * (double) g)
						+ (int) (0.114 * (double) b);
				itensity[gray]++;
			}
		}
		return itensity;
	}
	
	/**
	 * ��ȡͼ��
	 * @return ��ǰ��ͼ��
	 */
	public BufferedImage getImage() {
		return image;
	}

}
