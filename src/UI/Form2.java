package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

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
public class Form2 {
	
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
	public static final int RIGHT_BASIS = 284;
	
	/**
	 * �ϻ�׼
	 */
	public static final int TOP_BASIS = 28;
	
	/**
	 * �׻�׼
	 */
	public static final int BOTTOM_BASIS = 28;
	
	/**
	 * ˮƽ���
	 */
	public static final int HORIZONTAL_INTERVAL = 116;
	
	/**
	 * ��ֱ���
	 */	
	public static final int VERTICAL_INTERVAL = 50;
	
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
	private JButton selectPictrueButton;
	
	/**
	 * �������ְ�ť
	 */
	private JButton generateMusicButton;
	
	/**
	 * ��ʼ���Ű�ť
	 */
	private JButton startPlayButton;
	
	/**
	 * ֹͣ���Ű�ť
	 */
	private JButton stopPlayButton;
	
	/**
	 * ������ť
	 */
	private JButton exportButton;
	
	/**
	 * ʹ�ð���
	 */
	private JButton helpButton;
	
	/**
	 * ���ɵ����ֵ�����
	 */
	private Music music;
	
	/**
	 * ��ǰͼƬ������
	 */
	private Picture picture;
	
	/**
	 * Ĭ�Ϲ��캯������ʼ��ÿһ������Ԫ��
	 */
	public Form2() {
		// TODO Auto-generated constructor stub
		try {
			frame = new JFrame();
			//�趨����رշ�ʽΪ������ڹرհ�ť�ر�
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//�趨�����С
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			//���д���
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
			//�趨���ڱ���
			frame.setTitle("Musical Creation");
			//�趨���ַ�ʽΪ�ֶ�����
			frame.setLayout(null);
			//Panel�ؼ�
			panel = new PicturePanel();
			frame.getContentPane().add(panel);
			panel.setBounds(24, 28, 436, 436);
			//����������
			String[] argNames = new String[] {Config.MUSIC_PARA_KEY[0], 
					Config.MUSIC_PARA_KEY[1], 
					Config.MUSIC_PARA_KEY[2], 
					"����ʱ��"};
			int[] mins = new int[] {0, 0, 0, 30};
			int[] maxs = new int[] {100, 100, 100, 150};
			SliderPanel[] sliderPanels = new SliderPanel[argNames.length];
			for (int i = 0; i < sliderPanels.length; i++) {
				sliderPanels[i] = new SliderPanel(argNames[i], mins[i], maxs[i]);
				frame.getContentPane().add(sliderPanels[i]);
				sliderPanels[i].setBounds(484, 28 + i * VERTICAL_INTERVAL, 280, 20);
			}
			//ѡ��ͼ��ť
			selectPictrueButton = new JButton("ѡ��ͼ��", null);
			selectPictrueButton.addActionListener(new SelectPictrueActionListener());
			frame.getContentPane().add(selectPictrueButton);
			selectPictrueButton.setBounds(frame.getWidth() - RIGHT_BASIS, 28 + (sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			//�������ְ�ť
			generateMusicButton = new JButton("��������", null);
			generateMusicButton.addActionListener(new GenerateMusicActionListener());
			frame.getContentPane().add(generateMusicButton);
			generateMusicButton.setBounds(frame.getWidth() - RIGHT_BASIS + HORIZONTAL_INTERVAL, 28 + (sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			generateMusicButton.setEnabled(true);
			//��ʼ���Ű�ť
			startPlayButton = new JButton("��ʼ����", null);
			startPlayButton.addActionListener(new StartPlayActionListener());
			frame.getContentPane().add(startPlayButton);
			startPlayButton.setBounds(frame.getWidth() - RIGHT_BASIS, 28 + (1 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			startPlayButton.setEnabled(false);
			//ֹͣ���Ű�ť
			stopPlayButton = new JButton("ֹͣ����", null);
			stopPlayButton.addActionListener(new StopPlayActionListener());
			frame.getContentPane().add(stopPlayButton);
			stopPlayButton.setBounds(frame.getWidth() - RIGHT_BASIS + HORIZONTAL_INTERVAL, 28 + (1 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			stopPlayButton.setEnabled(false);
			//������ť
			exportButton = new JButton("��������", null);
			exportButton.addActionListener(new ExportActionListener());
			frame.getContentPane().add(exportButton);
			exportButton.setBounds(frame.getWidth() - RIGHT_BASIS, 28 + (2 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			exportButton.setEnabled(false);
			//������ť
			helpButton = new JButton("ʹ�ð���", null);
			helpButton.addActionListener(new HelpActionListener());
			frame.getContentPane().add(helpButton);
			helpButton.setBounds(frame.getWidth() - RIGHT_BASIS + HORIZONTAL_INTERVAL, 28 + (2 + sliderPanels.length) * VERTICAL_INTERVAL, 88, 24);
			helpButton.setEnabled(true);
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
	public HashMap<String, Integer> getArgs() {
		HashMap<String, Integer> args = new HashMap<String, Integer>();
		for (Component component : frame.getContentPane().getComponents()) {
			//���뻬�����Ĳ���
			if (component.toString().contains("SliderPanel")) {
				args.put(((SliderPanel)component).getName(), ((SliderPanel)component).getValue());
			}
		}
		return args;
	}
	
	/**
	 * �趨������������
	 * @param args �趨�Ĳ���
	 */
	public void setArgs(HashMap<String, Integer> args) {
		for (Component component : frame.getContentPane().getComponents()) {
			if (component.toString().contains("SliderPanel")) {
				SliderPanel panel = (SliderPanel)component;
				if (args.containsKey(panel.getName())) {
					panel.slider.setValue(args.get(panel.getName()));
					panel.valueLabel.setText(panel.getValueString(args.get(panel.getName())));
				}
			}
		}
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
				double percentage = Math.min((double)(panel.getWidth()) / picture.getImage().getWidth(), (double)(panel.getHeight()) / picture.getImage().getHeight());
				picture.resize(percentage);
				//��ʾͼƬ
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
				setArgs(musicParameterHashMap);
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
			music = composition.generate(getArgs());
			//����ť
			startPlayButton.setEnabled(true);
			stopPlayButton.setEnabled(false);
			exportButton.setEnabled(true);
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
			startPlayButton.setEnabled(false);
			stopPlayButton.setEnabled(true);
			Player.play(music);
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
	 * ������ť��Ӧ�¼�
	 */
	public class HelpActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	/**
	 * ������ʾͼƬ��Panel
	 */
	public class PicturePanel extends JPanel {
		
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
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			//�����߿�
			g.setColor(Color.gray);
			g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
			//���ͼƬ��Ϊ�գ��򻭳�ͼƬ
			if (picture != null) {
				BufferedImage image = picture.getImage();
				g.drawImage(image, (this.getWidth() - image.getWidth()) / 2, (this.getHeight() - image.getHeight()) / 2, this);
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
	
	/**
	 * ������ʾ������Ϣ��Panel
	 */
	public class SliderPanel extends JPanel implements ChangeListener {

		/**
		 * ���л�ID
		 */
		private static final long serialVersionUID = 5141083245464373171L;

		/**
		 * ����
		 */
		public JLabel nameLabel;
		
		/**
		 * ������
		 */
		public JSlider slider;
		
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
		public SliderPanel(String name, int minValue, int maxValue, int initialValue) {
			// TODO Auto-generated constructor stub
			nameLabel = new JLabel(name);
			slider = new JSlider(minValue, maxValue, initialValue);
			valueLabel = new JLabel(getValueString(initialValue));
			this.add(nameLabel);
			this.add(slider);
			this.add(valueLabel);
			this.setLayout(null);
			nameLabel.setBounds(0, 0, 60, 20);
			slider.setBounds(60, 0, 140, 20);
			valueLabel.setBounds(208, 0, 80, 20);
			slider.addChangeListener(this);
		}
		
		/**
		 * ���캯��
		 * @param name ����
		 * @param minValue ��Сֵ
		 * @param maxValue ���ֵ
		 */
		public SliderPanel(String name, int minValue, int maxValue) {
			// TODO Auto-generated constructor stub
			this(name, minValue, maxValue, (minValue + maxValue) / 2);
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
			return slider.getValue();
		}
		
		/**
		 * ��ȡ����
		 * @return ����
		 */
		public String getName() {
			return nameLabel.getText();
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			valueLabel.setText(getValueString(slider.getValue()));
		}
	}
	
	public class MusicLength extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5203856266711331525L;
		
		/**
		 * ����
		 */
		public JLabel nameLabel;
		
		/**
		 * ��λ
		 */
		public JLabel unit;
		
		/**
		 * ֵ
		 */
		public JTextField musicTime;
		
		/**
		 * ���캯��
		 * @param name ����
		 * @param Unit ��λ
		 */
		public MusicLength(String name, String Unit) {
			nameLabel = new JLabel(name);
			unit = new JLabel(Unit);
			musicTime = new JTextField();
			this.add(nameLabel);
			this.add(musicTime);
			this.add(unit);
			this.setLayout(null);
			nameLabel.setBounds(0, 0, 60, 20);
			musicTime.setBounds(68, 0, 125, 20);
			unit.setBounds(208, 0, 40, 20);
			musicTime.setText("5.0");
		}
		
		/**
		 * ��ȡʱ��
		 */
		public double getTimeLength() {
			return Double.valueOf(musicTime.getText());
		}
	}
}
