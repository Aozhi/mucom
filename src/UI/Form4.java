package UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.awt.AWTUtilities;

import Config.Config;
import File.MIDIWriter;
import File.Picture;
import Generate.Composition;
import Generate.ParameterConversion;
import Generate.ParameterGenerate;
import MIDI.Music;
import MIDI.Player;

/**
 * ��ʽʹ��ʱ��Form
 */
public class Form4 {
	
	static Point origin = new Point();  //ȫ�ֵ�λ�ñ��������ڱ�ʾ����ڴ����ϵ�λ��
	static Point helpOrigin = new Point();
	
	/**
	 * ���ڿ�
	 */
	public static final int FRAME_WIDTH = 768;
	
	/**
	 * ���ڸ�
	 */
	public static final int FRAME_HEIGHT = 530;
	
	/**
	 * ���׼
	 */
	public static final int LEFT_BASIS = 24;
	
	/**
	 * �һ�׼
	 */
	public static final int RIGHT_BASIS = 270;
	
	/**
	 * �ϻ�׼
	 */
	public static final int TOP_BASIS = 95;
	
	/**
	 * �׻�׼
	 */
	public static final int BOTTOM_BASIS = 28;
	
	/**
	 * ˮƽ���
	 */
	public static final int HORIZONTAL_INTERVAL = 80;
	
	/**
	 * ��ֱ���
	 */	
	public static final int VERTICAL_INTERVAL = 60;
	
	public static final int PICTURE_WH = 370;
	
	public static final int PICTURE_X = 50;
	
	public static final int PICTURE_Y = 60;
	
	ImageIcon buttonImage = new ImageIcon("button\\normal\\mini.png");
	
	private final int ICON_WIDTH = buttonImage.getIconWidth();
	
	private final int ICON_HEIGHT = buttonImage.getIconHeight();
	
	/**
	 * ���������
	 */
	private JFrame frame;
	
	/**
	 * ͼ��Panel������
	 */
	private PicturePanel panel;
	
	/**
	 * ѡ��ͼ��ť
	 */
	private IButton selectPictureButton;
	
	/**
	 * �������ְ�ť
	 */
	private IButton generateMusicButton;
	
	/**
	 * ��ʼ���Ű�ť
	 */
	private ClickButton startPlayButton;
	
	/**
	 * ֹͣ���Ű�ť
	 */
	private IButton stopPlayButton;
	
	/**
	 * ������ť
	 */
	private IButton exportButton;
	
	/**
	 * ʹ�ð���
	 */
	private IButton helpButton;
	
	/**
	 * �˳�
	 */
	private IButton exitButton;
	
	/**
	 * ��С��
	 */
	private IButton miniButton;
	
	protected static Timer timerClose;
	
	protected static Timer timerStart;
	
	private PlayerSlider playerSlider;
	
	static float value = 1.0f;
	
	/**
	 * ���ɵ����ֵ�����
	 */
	private Music music;
	
	/**
	 * ��ǰͼƬ������
	 */
	private Picture picture;

	/**
	 * ��������
	 */
	private JFrame helpFrame;
	
	/**
	 * Ĭ�Ϲ��캯������ʼ��ÿһ������Ԫ��
	 */
	public Form4() {
		// TODO Auto-generated constructor stub
		try {
			frame = new JFrame();
			frame.setUndecorated(true);
			
			//�趨����رշ�ʽΪ������ڹرհ�ť�ر�
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			ImageIcon img = new ImageIcon("BG.png");//���Ǳ���ͼƬ
			img = new ImageIcon(img.getImage().getScaledInstance(FRAME_WIDTH, FRAME_HEIGHT, Image.SCALE_DEFAULT));
			JLabel imgLabel = new JLabel(img);//������ͼ���ڱ�ǩ�
			imgLabel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//ע�������ǹؼ�����������ǩ��ӵ�jframe��LayeredPane����  
			imgLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);//���ñ�����ǩ��λ��  
			Container cp = frame.getContentPane(); 
			cp.setLayout(new BorderLayout());
			 
			((JPanel)cp).setOpaque(false); //ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������   
			
			AWTUtilities.setWindowOpacity(frame, 0.02f);
			timerStart = new Timer(15, new timerStartActionListener());
			timerStart.start();
			//�趨�����С
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			//���д���
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
			//�趨���ڱ���
			frame.setTitle("Musical Creation");
			JFrame.setDefaultLookAndFeelDecorated(true);
	        //Round Corner  
	        //AWTUtilities.setWindowShape(frame, new RoundRectangle2D.Double(  
	        //  0.0D, 0.0D, frame.getWidth(), frame.getHeight(), 100.0D,  
	        //100.0D));
	        AWTUtilities.setWindowOpaque(frame, false);
	        frame.setVisible(true);
			//�趨���ַ�ʽΪ�ֶ�����
			frame.setLayout(null);
			
			//Panel�ؼ�
			panel = new PicturePanel();
			frame.getContentPane().add(panel);
			//panel.setBounds(24, 28, PICTURE_WH, PICTURE_WH);
			panel.setOpaque(false);
			
			//����������
			String[] argNames = new String[] {Config.MUSIC_PARA_KEY[0], 
					Config.MUSIC_PARA_KEY[1], 
					Config.MUSIC_PARA_KEY[2], 
					"����ʱ��"};
			int[] mins = new int[] {0, 0, 0, 30};
			int[] maxs = new int[] {100, 100, 100, 150};
			CustomSlider[] customSliders = new CustomSlider[argNames.length];
			for (int i = 0; i < customSliders.length; i++) {
				customSliders[i] = new CustomSlider(argNames[i], mins[i], maxs[i], 140);
				frame.getContentPane().add(customSliders[i]);
				customSliders[i].setOpaque(false);
				customSliders[i].setBounds(frame.getWidth() - RIGHT_BASIS, TOP_BASIS + i * VERTICAL_INTERVAL, 280, 20);
			}
			//ѡ��ͼ��ť
			selectPictureButton = new IButton("button\\normal\\select.png", "button\\highlight\\select.png", "button\\click\\select.png", "button\\inable\\select.png");
			selectPictureButton.addActionListener(new SelectPictrueActionListener());
			frame.getContentPane().add(selectPictureButton);
			selectPictureButton.setBounds(frame.getWidth() - RIGHT_BASIS + 10, TOP_BASIS + (customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			//�������ְ�ť
			generateMusicButton = new IButton("button\\normal\\generate.png", "button\\highlight\\generate.png", "button\\click\\generate.png", "button\\inable\\generate.png");
			generateMusicButton.addActionListener(new GenerateMusicActionListener());
			frame.getContentPane().add(generateMusicButton);
			generateMusicButton.setBounds(selectPictureButton.getX() + HORIZONTAL_INTERVAL, TOP_BASIS + (customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			generateMusicButton.setEnabled(true);
			//��ʼ���Ű�ť
			startPlayButton = new ClickButton("button\\normal\\start.png", "button\\highlight\\start.png", "button\\click\\start.png", "button\\inable\\start.png",
					"button\\normal\\pause.png", "button\\highlight\\pause.png", "button\\click\\pause.png", "button\\inable\\pause.png");
			startPlayButton.addActionListener(new StartPlayActionListener());
			frame.getContentPane().add(startPlayButton);
			startPlayButton.setBounds(frame.getWidth() - RIGHT_BASIS + 10, TOP_BASIS + (1 + customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			startPlayButton.setEnabled(false);
			//ֹͣ���Ű�ť
			stopPlayButton = new IButton("button\\normal\\stop.png", "button\\highlight\\stop.png", "button\\click\\stop.png", "button\\inable\\stop.png");
			stopPlayButton.addActionListener(new StopPlayActionListener());
			frame.getContentPane().add(stopPlayButton);
			stopPlayButton.setBounds(startPlayButton.getX() + HORIZONTAL_INTERVAL, TOP_BASIS + (1 + customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			stopPlayButton.setEnabled(false);
			//������ť
			exportButton = new IButton("button\\normal\\export.png", "button\\highlight\\export.png", "button\\click\\export.png", "button\\inable\\export.png");
			exportButton.addActionListener(new ExportActionListener());
			frame.getContentPane().add(exportButton);
			exportButton.setBounds(startPlayButton.getX() + 2 * HORIZONTAL_INTERVAL, TOP_BASIS + (1 + customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			exportButton.setEnabled(false);
			//������ť
			helpButton = new IButton("button\\normal\\help.png", "button\\highlight\\help.png", "button\\click\\help.png", "button\\inable\\help.png");
			helpButton.addActionListener(new HelpActionListener());
			frame.getContentPane().add(helpButton);
			helpButton.setBounds(selectPictureButton.getX() + 2 * HORIZONTAL_INTERVAL, TOP_BASIS + (customSliders.length) * VERTICAL_INTERVAL, ICON_WIDTH, ICON_HEIGHT);
			helpButton.setEnabled(true);
			//exit
			timerClose = new Timer(15, new timerCloseActionListener());
			exitButton = new IButton("button\\normal\\exit.png", "button\\highlight\\exit.png", "button\\click\\exit.png", "button\\inable\\exit.png");
			exitButton.addActionListener(new ExitActionListener());
			frame.getContentPane().add(exitButton);
			exitButton.setBounds(frame.getWidth() - 50 - (int)(0.5 * ICON_WIDTH), 45 - (int)(0.5 * ICON_HEIGHT), ICON_WIDTH, ICON_HEIGHT);
			exitButton.setEnabled(true);
			//mini
			miniButton = new IButton("button\\normal\\mini.png", "button\\highlight\\mini.png", "button\\click\\mini.png", "button\\inable\\mini.png");
			miniButton.addActionListener(new MiniActionListener());
			frame.getContentPane().add(miniButton);
			miniButton.setBounds(exitButton.getX() - 50, exitButton.getY(), ICON_WIDTH, ICON_HEIGHT);
			miniButton.setEnabled(true);
			
			playerSlider = new PlayerSlider("����", 0, 150, 400);
			playerSlider.setBounds(LEFT_BASIS + 100, frame.getHeight() - 60, 520, 20);
			frame.getContentPane().add(playerSlider);
			playerSlider.setOpaque(false);
			
			frame.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {  //���£�mousePressed ���ǵ����������걻����û��̧��
                       	if (e.getY() < TOP_BASIS){
                       		origin.x = e.getX();  //����갴�µ�ʱ���ô��ڵ�ǰ��λ��
                       		origin.y = e.getY();
                       	}
                }
			});
			frame.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {  //�϶���mouseDragged ָ�Ĳ�������ڴ������ƶ�������������϶���
                    if (e.getY() < 28){
                    	Point p = frame.getLocation();  //������϶�ʱ��ȡ���ڵ�ǰλ��
                        //���ô��ڵ�λ��
                        //���ڵ�ǰ��λ�� + ��굱ǰ�ڴ��ڵ�λ�� - ��갴�µ�ʱ���ڴ��ڵ�λ��
                        frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
                    }
                        
                }
        	});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void show() {
		//���ÿɼ���
		frame.setVisible(true);
	}
	
	/**
	 * ��UI�ϻ�ȡ��ʾ�����Ĳ���
	 * @return ��õĲ���
	 */
	
	public HashMap<String, Integer> getArgsCustom() {
		HashMap<String, Integer> args = new HashMap<String, Integer>();
		for (Component component : frame.getContentPane().getComponents()) {
			//���뻬�����Ĳ���
			if (component.toString().contains("CustomSlider")) {
				args.put(((CustomSlider)component).getName(), ((CustomSlider)component).getValue());
			}
		}
		return args;
	}
	
	/**
	 * �趨������������
	 * @param args �趨�Ĳ���
	 */
	// TODO
	
	public void setArgsCustom(HashMap<String, Integer> args) {
		for (Component component : frame.getContentPane().getComponents()) {
			if (component.toString().contains("CustomSlider")) {
				CustomSlider panel = (CustomSlider)component;
				if (args.containsKey(panel.getName())) {
					panel.setValue(args.get(panel.getName()));
					panel.valueLabel.setText(panel.getValueString(args.get(panel.getName())));
				}
			}
		}
	}
	
	/**
	 * ������Ϣ
	 */
	public class HelpActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (helpFrame == null) {
				initialHelpFrame();
			}
			if (helpFrame.isVisible()) {
				helpFrame.setVisible(false);
			}
			else {
				helpFrame.setVisible(true);
			}
		}
		
	}
	
	public void initialHelpFrame() {
		// TODO Auto-generated method stub
		final int width = 320;
		final int height = 240;
		JFrame.setDefaultLookAndFeelDecorated(true);
		helpFrame = new JFrame();
		helpFrame.setUndecorated(true);
		helpFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		helpFrame.setSize(width, height);
		ImageIcon img = new ImageIcon("HELP_BG.png");//���Ǳ���ͼƬ
		img = new ImageIcon(img.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		JLabel imgLabel = new JLabel(img);//������ͼ���ڱ�ǩ�
		imgLabel.setSize(width, height);
		helpFrame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//ע�������ǹؼ�����������ǩ��ӵ�jframe��LayeredPane����  
		imgLabel.setBounds(0, 0, width, height);//���ñ�����ǩ��λ��  
		Container cp = helpFrame.getContentPane(); 
		cp.setLayout(new BorderLayout());
		((JPanel)cp).setOpaque(false); //ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������   
		com.sun.awt.AWTUtilities.setWindowOpacity(helpFrame, 1f);
		//�趨�����С
		helpFrame.setSize(width, height);
		//���д���
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		helpFrame.setLocation(dimension.width / 2 - helpFrame.getWidth() / 2, dimension.height / 2 - helpFrame.getHeight() / 2);
		//�趨���ڱ���
		helpFrame.setTitle("Help");
		//�趨���ַ�ʽΪ�ֶ�����
        helpFrame.setLayout(null);
        
        helpFrame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {  //���£�mousePressed ���ǵ����������걻����û��̧��
                helpOrigin.x = e.getX();  //����갴�µ�ʱ���ô��ڵ�ǰ��λ��
                helpOrigin.y = e.getY();
            }
		});
        helpFrame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {  //�϶���mouseDragged ָ�Ĳ�������ڴ������ƶ�������������϶���
                Point p = helpFrame.getLocation();  //������϶�ʱ��ȡ���ڵ�ǰλ��
                //���ô��ڵ�λ��
                //���ڵ�ǰ��λ�� + ��굱ǰ�ڴ��ڵ�λ�� - ��갴�µ�ʱ���ڴ��ڵ�λ��
                helpFrame.setLocation(p.x + e.getX() - helpOrigin.x, p.y + e.getY() - helpOrigin.y);         
            }
    	});
        
        JLabel label1 = new JLabel("��ӭ��ʹ��ͼ���������");
        helpFrame.getContentPane().add(label1);
        label1.setBounds(100, 40, width, 20);
        JLabel label2 = new JLabel("ѡ��ͼƬ�������Ӻš�");
        helpFrame.getContentPane().add(label2);
        label2.setBounds(100, 65, width, 20);
        JLabel label3 = new JLabel("�������֣��������ݡ�");
        helpFrame.getContentPane().add(label3);
        label3.setBounds(100, 90, width, 20);
        JLabel label4 = new JLabel("��������������ʺš�");
        helpFrame.getContentPane().add(label4);
        label4.setBounds(100, 115, width, 20);
        JLabel label5 = new JLabel("�رհ��������ٴε����");
        helpFrame.getContentPane().add(label5);
        label5.setBounds(100, 140, width, 20);
        JLabel label6 = new JLabel("�������֣��������ǡ�");
        helpFrame.getContentPane().add(label6);
        label6.setBounds(100, 165, width, 20);
        JLabel label7 = new JLabel("Ҳ���ò������������֡�");
        helpFrame.getContentPane().add(label7);
        label7.setBounds(100, 190, width, 20);
	}
	
	/**
	 * ѡ��ͼ��ť���¼�������
	 */
	public class SelectPictrueActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("ͼ���ļ�(*.jpg, *.bmp, *.png)", "jpg", "bmp", "png"));
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			int result = chooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				//���·��
				String path = chooser.getSelectedFile().getPath();
				//��ȡͼƬ
				picture = new Picture(path);
				//����ͼƬ
				double percentage = Math.min((double)PICTURE_WH / picture.getImage().getWidth(), (double)PICTURE_WH / picture.getImage().getHeight());
				picture.resize(percentage);
				panel.setPicture(picture);
				
				//����ť
				generateMusicButton.setEnabled(true);
				startPlayButton.setEnabled(false);
				stopPlayButton.setEnabled(false);
				exportButton.setEnabled(false);
				Player.stop();
				//����ת��
				ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				HashMap<String, Integer> musicParameterHashMap = parameterConversion.convert(parameterGenerate.generate(picture));
				setArgsCustom(musicParameterHashMap);
				//setArgs(musicParameterHashMap);
			}
		}
	}
	
	/**
	 * �������ְ�ť���¼�������
	 */
	public class GenerateMusicActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//��������
			Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
			music = composition.generate(getArgsCustom());
			//����ť
			Player.play(music);
			startPlayButton.setEnabled(true);
			startPlayButton.setInitial(true);
			startPlayButton.setClick(false);
			startPlayButton.paintComponent(startPlayButton.getGraphics());
			stopPlayButton.setEnabled(false);
			exportButton.setEnabled(true);
			playerSlider.setMaxValue(Player.getAudioLength());
			Player.stop();
		}
	}
	
	/**
	 * �������ְ�ť���¼�������
	 */
	public class StartPlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (startPlayButton.isInitial()) {
				stopPlayButton.setEnabled(true);
				Player.play(music);
				Player.start();
				Player.skip(playerSlider.getValue());
				playerSlider.timer.start();
				startPlayButton.setInitial(false);
				startPlayButton.setClick(true);
				return;
			}
			
			if (startPlayButton.isClick()) {
				startPlayButton.setClick(false);
				Player.pause();
				playerSlider.timer.stop();
			}
			else {
				startPlayButton.setClick(true);
				Player.start();
				playerSlider.timer.start();
			}
			
		}
	}
	
	/**
	 * ֹͣ���Ű�ť���¼�������
	 */
	public class StopPlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			startPlayButton.setEnabled(true);
			stopPlayButton.setEnabled(false);
			Player.reset();
			playerSlider.setValue(0);
			playerSlider.paintComponents(playerSlider.getGraphics());
			playerSlider.timer.stop();
			startPlayButton.setClick(false);
			startPlayButton.setInitial(true);
			startPlayButton.paintComponent(startPlayButton.getGraphics());
			Player.stop();
		}
	}
	
	/**
	 * ������ť��Ӧ�¼�
	 */
	public class ExportActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("MIDI�����ļ�(*.mid)", "mid"));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			int result = chooser.showOpenDialog(frame);
			//�ж��Ƿ񱣴�
			if (result == JFileChooser.APPROVE_OPTION) {
				//��ȡ·��
				String path = chooser.getSelectedFile().getPath();
				if (!path.contains(".")) {
					path += ".mid";
				}
				MIDIWriter.write(music, path);
			}
		}
	}
	
	/**
	 * exitButtonActionListener
	 */
	public class ExitActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			timerClose.start();
		}
		
	}
	
	/**
	 * timerActionListener
	 */
	public class timerCloseActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			value -= 0.02f;  
            if (value >= 0.02f) {  
                SwingUtilities.invokeLater(new Runnable() {  
                    public void run() {  
                        com.sun.awt.AWTUtilities.setWindowOpacity(frame, value);  
                    }  
                });  
            } else {  
                System.exit(0);  
            }  
		}
	}
	
	/**
	 * timerActionListener
	 */
	public class timerStartActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			value -= 0.02f;  
            if (value >= 0.02f) {  
                SwingUtilities.invokeLater(new Runnable() {  
                    public void run() {  
                        com.sun.awt.AWTUtilities.setWindowOpacity(frame, 1 - value);  
                    }  
                });  
            } else {  
                value = 1.0f;
                timerStart.stop();
                com.sun.awt.AWTUtilities.setWindowOpacity(frame, value);
            }  
		}
	}
	
	/**
	 * MiniActionListener
	 */
	
	public class MiniActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.exit(0);
			frame.setState(JFrame.ICONIFIED); 
		}
	}
	
	/**
	 * ������ʾͼƬ��Panel
	 */
	public class PicturePanel extends JPanel {
		// TODO Auto-generated method stub
		/**
		 * ���л�ID
		 */
		private static final long serialVersionUID = -7312575960417775755L;
		
		/**
		 * ͼƬ
		 */
		private Picture picture;
		
		@Override
		public void paintComponent(Graphics g) {
			
			//���ͼ��
			setVisible(false);
			g.clearRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
			
			//���ͼƬ��Ϊ�գ��򻭳�ͼƬ
			if (picture != null) {
				setBounds((int) (PICTURE_X + 0.5 * (PICTURE_WH - picture.getImage().getWidth())), (int) (PICTURE_Y + 0.5 * (PICTURE_WH - picture.getImage().getHeight())), picture.getImage().getWidth(), picture.getImage().getHeight());
				BufferedImage image = picture.getImage();
				//g.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100);
				g.drawImage(image, (getWidth() - image.getWidth()) / 2, (getHeight() - image.getHeight()) / 2, this);
				setVisible(true);
			}
			else {
				setVisible(false);
			}
		}
		
		/**
		 * �趨JPanel��ͼ��
		 * @param picture
		 */
		public void setPicture(Picture picture) {
			this.picture = picture;
			paintComponent(getGraphics());
		}
	}
	
	public class CustomSlider extends JPanel implements ChangeListener {

		/**
		 * ���л�ID
		 */
		private static final long serialVersionUID = 5141083245464373171L;
		
		private static final int NAMELABEL_LENGTH = 60;
		
		private static final int HEIGHT = 20;
		
		protected int minValue;
		
		protected int maxValue;
		
		ImageIcon tmp = new ImageIcon("slider\\normal.png");
		
		private final int SLIDER_WIDTH = tmp.getIconWidth();
		
		private final int SLIDER_HEIGHT = tmp.getIconHeight();
		
		ImageIcon tmp2 = new ImageIcon("slider\\bar.png");
		
		private final int BAR_HEIGHT = tmp2.getIconHeight();
		
		//private final int BAR_WIDTH = 140;
		
		/**
		 * ����
		 */
		public JLabel nameLabel;
		
		public IButton slider;
		
		public JLabel bar;
		
		private Point origin = new Point();
		
		protected int value;
		
		/**
		 * ֵ
		 */
		public JLabel valueLabel;
		
		/**
		 * ���캯��
		 * @param name ����
		 * @param minValue ��Сֵ
		 * @param maxValue ���ֵ
		 * @param initialValue ��ʼֵ
		 */
		public CustomSlider(String name, int minValue, int maxValue, int initialValue, int sliderLength) {
			// TODO Auto-generated constructor stub
			
			nameLabel = new JLabel(name);
			nameLabel.setFont(new Font("���䷱����", Font.PLAIN, 15));
			value = initialValue;
			this.minValue = minValue;
			this.maxValue = maxValue;
			valueLabel = new JLabel(getValueString(initialValue));
			valueLabel.setFont(new Font("����", Font.PLAIN, 15));
			slider = new IButton("slider\\normal.png", "slider\\highlight.png", "slider\\highlight.png", "slider\\highlight.png");
			slider.setEnabled(true);
			bar = new JLabel(new ImageIcon("slider\\bar.png"));
			bar.setSize(sliderLength, BAR_HEIGHT);
			this.add(nameLabel);
			this.add(valueLabel);
			this.add(slider);
			this.add(bar);
			this.setLayout(null);
			nameLabel.setBounds(0, 0, NAMELABEL_LENGTH, HEIGHT);
			valueLabel.setBounds(NAMELABEL_LENGTH + sliderLength + SLIDER_WIDTH, 0, 80, HEIGHT);
			bar.setBounds((int) (NAMELABEL_LENGTH + 0.5 * SLIDER_WIDTH), (int) (0.5 * (HEIGHT - BAR_HEIGHT)), sliderLength, BAR_HEIGHT);
			slider.setBounds(0, (int)(0.5 * (HEIGHT - SLIDER_HEIGHT)), SLIDER_WIDTH, SLIDER_HEIGHT);
			origin = slider.getLocation();
			setValue(initialValue);
			slider.addChangeListener(this);
			
			slider.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {  //���£�mousePressed ���ǵ����������걻����û��̧��
	            	origin.x = e.getX();
	            	origin.y = slider.getY();//����갴�µ�ʱ����slider��ǰ��λ��

	            }
			});
			
			slider.addMouseMotionListener(new MouseMotionAdapter() {
	            public void mouseDragged(MouseEvent e) {  //�϶���mouseDragged ָ�Ĳ�������ڴ������ƶ�������������϶���
	                	Point p = slider.getLocation();  //������϶�ʱ��ȡ���ڵ�ǰλ��
	                    //���ô��ڵ�λ��
	                    //���ڵ�ǰ��λ�� + ��굱ǰ�ڴ��ڵ�λ�� - ��갴�µ�ʱ���ڴ��ڵ�λ��
	                    slider.setLocation(p.x + e.getX() - origin.x, origin.y);
	                    if (slider.getX() + 0.5 * SLIDER_WIDTH < bar.getX()) {
	                    	slider.setLocation((int) (bar.getX() - 0.5 * SLIDER_WIDTH), origin.y);
	                    }
	                    if (slider.getX() + 0.5 * SLIDER_WIDTH > bar.getWidth() + bar.getX()) {
	                    	slider.setLocation((int) (bar.getWidth() + bar.getX() - 0.5 * SLIDER_WIDTH), origin.y);
	                    }
	            }
	    	});
		}
		
		/**
		 * ���캯��
		 * @param name ����
		 * @param minValue ��Сֵ
		 * @param maxValue ���ֵ
		 */
		public CustomSlider(String name, int minValue, int maxValue, int sliderLength) {
			// TODO Auto-generated constructor stub
			this(name, minValue, maxValue, (minValue + maxValue) / 2, sliderLength);
		}
		
		/**
		 * ����ֵ��ȡ��Ϣ
		 * @param value ֵ
		 * @return ֵ���ַ�����ʽ
		 */
		public String getValueString(int value) {
			return Integer.toString(value);
		}
		
		/**
		 * ��ȡ��������ֵ
		 * @return ��������ֵ
		 */
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
			slider.setLocation((int) ((value - minValue) * bar.getWidth() / (maxValue - minValue) - 0.5 * SLIDER_WIDTH + bar.getX()), origin.y);
			valueLabel.setText(getValueString(value));
		}
		
		/**
		 * ��ȡ����
		 * @return ����
		 */
		public String getName() {
			return nameLabel.getText();
		}

		public void stateChanged(ChangeEvent arg0) {
			value = (int) (slider.getX() + 0.5 * SLIDER_WIDTH - bar.getX()) * (maxValue - minValue) / bar.getWidth() + minValue;
			valueLabel.setText(getValueString(value));
		}
		
	}
	
	public class PlayerSlider extends CustomSlider implements ChangeListener{

		/**
		 * SeiralVersionUID
		 */
		private static final long serialVersionUID = -6368720754983954860L;

		public Timer timer;
		
		private boolean play = false;

		public PlayerSlider(String name, int minValue, int maxValue,
				int sliderLength) {
			super(name, minValue, maxValue, sliderLength);
			this.nameLabel.setBounds(25, 0, 35, 20);
			this.remove(slider);
			slider = new IButton("slider\\player.png", "slider\\player-highlight.png", "slider\\player-highlight.png", "slider\\player.png");
			slider.setEnabled(true);
			this.add(slider);
			this.remove(bar);
			this.add(bar);
			slider.setBounds(0, (int)(0.5 * (CustomSlider.HEIGHT - super.SLIDER_HEIGHT)), slider.buttonWidth, slider.buttonHeight);
			origin = slider.getLocation();
			setValue(0);
			
			slider.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {  //���£�mousePressed ���ǵ����������걻����û��̧��
	            	origin.x = e.getX();
	            	origin.y = slider.getY();//����갴�µ�ʱ����slider��ǰ��λ��

	            }
			});
			
			slider.addMouseMotionListener(new MouseMotionAdapter() {
	            public void mouseDragged(MouseEvent e) {  //�϶���mouseDragged ָ�Ĳ�������ڴ������ƶ�������������϶���
	                	Point p = slider.getLocation();  //������϶�ʱ��ȡ���ڵ�ǰλ��
	                    //���ô��ڵ�λ��
	                    //���ڵ�ǰ��λ�� + ��굱ǰ�ڴ��ڵ�λ�� - ��갴�µ�ʱ���ڴ��ڵ�λ��
	                    slider.setLocation(p.x + e.getX() - origin.x, origin.y);
	                    if (slider.getX() + 0.5 * slider.getWidth() < bar.getX()) {
	                    	slider.setLocation((int) (bar.getX() - 0.5 * slider.getWidth()), origin.y);
	                    }
	                    if (slider.getX() + 0.5 * slider.getWidth() > bar.getWidth() + bar.getX()) {
	                    	slider.setLocation((int) (bar.getWidth() + bar.getX() - 0.5 * slider.getWidth()), origin.y);
	                    }
	            }
	    	});
			
			timer = new Timer(100, new ActionListener() {   
	            public void actionPerformed(ActionEvent e) {   
	                Player.tick();
	                setValue(Player.getAudioPosition());
	                if (!Player.isRunning()) {
	                	startPlayButton.setClick(false);
	                	startPlayButton.setInitial(true);
	                	stopPlayButton.setEnabled(false);
	                	startPlayButton.paintComponent(startPlayButton.getGraphics());
	                }
	            }   
	        });
			
			bar.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {  //���£�mousePressed ���ǵ����������걻����û��̧��
	            	int tmp = e.getX() * (getMaxValue() - getMinValue()) / bar.getWidth() + getMinValue();
	            	setValue(tmp);
	            	Player.skip(value);
	            }
			});
			// TODO Auto-generated constructor stub
		}
		
		public boolean isPlay() {
			return play;
		}
		
		public void setValue(int value) {
			this.value = value;
			slider.setLocation((int) ((value - minValue) * bar.getWidth() / (maxValue - minValue) - 0.5 * slider.getWidth() + bar.getX()), origin.y);
			valueLabel.setText(getValueString(value / 100));
		}
		
		public void stateChanged(ChangeEvent arg0) {
			value = (int) (slider.getX() + 0.5 * slider.getWidth() - bar.getX()) * (maxValue - minValue) / bar.getWidth() + minValue;
			valueLabel.setText(getValueString(value));
			if (value != Player.getAudioPosition()) {   
                Player.skip(value);   
            }
		}
		
		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}
		
		public int getMaxValue() {
			return maxValue;
		}
		
		public int getMinValue() {
			return minValue;
		}
	}
	
	public class IButton extends JButton{
		// TODO Auto-generated method stub
        
		private static final long serialVersionUID = 5357507583085033763L;
		protected BufferedImage image_over;     //����ڰ�ť�ϵ�ͼƬ
		protected BufferedImage image_off;      //��겻�ڰ�ť�ϵ�ͼƬ
        protected BufferedImage image_pressed;  //��갴�°�ťʱ��ͼƬ
        protected BufferedImage image_inable;   //
        protected int buttonWidth;              //��
        protected int buttonHeight;             //��
        protected int[] pixels;                 //����ͼƬ���ݵ����飬���ڼ���contains
        protected boolean mouseOn;
        protected boolean mousePressed;
         
        public IButton() {};
        
        public IButton(String pic1, String pic2, String pic3, String pic4){
                mouseOn = false;
                mousePressed = false;
                //����ͼƬ
                image_over = loadImage(pic2);
                image_off = loadImage(pic1);
                image_pressed = loadImage(pic3);
                image_inable = loadImage(pic4);
                
                buttonWidth = image_off.getWidth();
                buttonHeight = image_off.getHeight();
                
                //��ȡͼƬ����
                pixels = new int[buttonWidth * buttonHeight];
                //ץȡ��������
                PixelGrabber pg = new PixelGrabber(image_off, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
                try{
                        pg.grabPixels();
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                
                //�������ã�������в�Ӱ��
                this.setOpaque(false);
                
                this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                this.addMouseListener(new MouseHandler());
        }
        
        //��ȡͼƬ�ļ�
        public BufferedImage loadImage(String filename){
                File file = new File(filename);
                
                if(!file.exists())
                        return null;
                
                try{
                        return ImageIO.read(file);
                }
                catch(IOException e){
                        e.printStackTrace();
                        return null;
                }
        }
        
        //���Ǵ˷��������Զ����ͼƬ
        public void paintComponent(Graphics g){
                g.drawImage(image_off, 0, 0, this);
                if (this.isEnabled()){
                	if(mouseOn)
                		g.drawImage(image_over, 0, 0, this);
                	else if(mousePressed)
                        g.drawImage(image_pressed, 0, 0, this);
                }
                else {
                	g.drawImage(image_inable, 0, 0, this);
                }
        }
        
        //���Ǵ˷��������Զ���ı߿�
        public void paintBorder(Graphics g){
                //��Ҫ�߿�
        }
        
        public boolean contains(int x, int y){
                //���ж��Ļ���Խ�磬�����֮��Ҳ�ἤ���������
                if(!super.contains(x, y))
                        return false;
                
                int alpha = (pixels[(buttonWidth * y + x)] >> 24) & 0xff;

                repaint();
                if(alpha == 0){
                        return false;
                }
                else{
                        return true;
                }
        }
             
        //������롢�뿪ͼƬ��Χ����Ϣ
        class MouseHandler extends MouseAdapter  {
                public void mouseExited(MouseEvent e){
                        mouseOn = false;
                        repaint();
                }
                public void mouseEntered(MouseEvent e){
                        mouseOn = true;
                        repaint();
                }
                public void mousePressed(MouseEvent e){
                        mouseOn = false;
                        mousePressed = true;
                        repaint();
                }
                public void mouseReleased(MouseEvent e){
                        //��ֹ�ڰ�ť֮��ĵط��ɿ����
                        if(contains(e.getX(), e.getY()))
                                mouseOn = true;
                        else
                                mouseOn = false;
                        
                        mousePressed = false;
                        repaint();
                }
        }
        
	}

	public class ClickButton extends IButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4990454727481081866L;
		// TODO
		private BufferedImage image_click_off;
		private BufferedImage image_click_over;
		private BufferedImage image_click_pressed;
		private BufferedImage image_click_inable;
		private boolean click;
		private boolean initial;
		
		public ClickButton(String pic1, String pic2, String pic3, String pic4, String pic5, String pic6, String pic7, String pic8){
            mouseOn = false;
            mousePressed = false;
            click = false;
            initial = true;
            
            //����ͼƬ
            image_over = loadImage(pic2);
            image_off = loadImage(pic1);
            image_pressed = loadImage(pic3);
            image_inable = loadImage(pic4);
            
            image_click_over = loadImage(pic6);
            image_click_off = loadImage(pic5);
            image_click_pressed = loadImage(pic7);
            image_click_inable = loadImage(pic8);
            //double percentage = Math.min((double)(image_over.getWidth()) / this.getWidth(), (double)(image_over.getHeight()) / this.getHeight());
            
            buttonWidth = image_off.getWidth();
            buttonHeight = image_off.getHeight();
            
            //��ȡͼƬ����
            pixels = new int[buttonWidth * buttonHeight];
            //ץȡ��������
            PixelGrabber pg = new PixelGrabber(image_off, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
            try{
                    pg.grabPixels();
            }
            catch(Exception e){
                    e.printStackTrace();
            }
            
            //�������ã�������в�Ӱ��
            this.setOpaque(false);
            
            this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            this.addMouseListener(new MouseHandler());
		}
		
		public void setClick(boolean state) {
			click = state;
		}
		
		public boolean isClick() {
			return click;
		}
		
		public boolean isInitial() {
			return initial;
		}
		
		public void setInitial(boolean state) {
			initial = state;
		}
		
		public void paintComponent(Graphics g){
			if (!click) {
				g.drawImage(image_off, 0, 0, this);
		        if (this.isEnabled()){
		        	if(mouseOn)
		            	g.drawImage(image_over, 0, 0, this);
		            else if(mousePressed)
		                g.drawImage(image_pressed, 0, 0, this);
		        }
		        else {
		        	g.drawImage(image_inable, 0, 0, this);
		        }
			}
			else {
				g.drawImage(image_click_off, 0, 0, this);
	            if (this.isEnabled()){
	            	if(mouseOn)
	            		g.drawImage(image_click_over, 0, 0, this);
	            	else if(mousePressed)
	                    g.drawImage(image_click_pressed, 0, 0, this);
	            }
	            else {
	            	g.drawImage(image_click_inable, 0, 0, this);
	            }
			}
		}
	}
	
}

