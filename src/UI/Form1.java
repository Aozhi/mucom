package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Composition.Hongyu.Essential.Constant;
import Config.Config;
import File.MIDIReader;
import File.MIDIWriter;
import File.Picture;
import Generate.Composition;
import Generate.ParameterConversion;
import Generate.ParameterGenerate;
import MIDI.Conversion;
import MIDI.Music;
import MIDI.Player;

public class Form1 {
	
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
	 * ��ʼѵ����ť
	 */
	private JButton pictureTrainingButton;
	
	/**
	 * ��һ��ͼ��ť
	 */
	private JButton musicTrainingButton;
	
	/**
	 * ���ɵ����ֵ�����
	 */
	private Music music;
	
	/**
	 * ��ǰͼƬ������
	 */
	private Picture picture;
	
	/**
	 * ѵ�����е�ͼ��·��
	 */
	private LinkedList<String> picturePath;
	
	/**
	 * ѵ�����е�����·��
	 */
	private LinkedList<String> musicPath;
	
	/**
	 * Ĭ�Ϲ��캯������ʼ��ÿһ������Ԫ��
	 */
	public Form1() {
		// TODO Auto-generated constructor stub
		try {
			frame = new JFrame();
			//�趨����رշ�ʽΪ������ڹرհ�ť�ر�
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//�趨�����С
			frame.setSize(768, 576);
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
			//ѡ��ͼ��ť
			selectPictrueButton = new JButton("ѡ��ͼ��", null);
			selectPictrueButton.addActionListener(new SelectPictrueActionListener());
			frame.getContentPane().add(selectPictrueButton);
			selectPictrueButton.setBounds(24, 488, 88, 24);
			//�������ְ�ť
			generateMusicButton = new JButton("��������", null);
			generateMusicButton.addActionListener(new GenerateMusicActionListener());
			frame.getContentPane().add(generateMusicButton);
			generateMusicButton.setBounds(140, 488, 88, 24);
			generateMusicButton.setEnabled(false);
			//��ʼ���Ű�ť
			startPlayButton = new JButton("��ʼ����", null);
			startPlayButton.addActionListener(new StartPlayActionListener());
			frame.getContentPane().add(startPlayButton);
			startPlayButton.setBounds(256, 488, 88, 24);
			startPlayButton.setEnabled(false);
			//ֹͣ���Ű�ť
			stopPlayButton = new JButton("ֹͣ����", null);
			stopPlayButton.addActionListener(new StopPlayActionListener());
			frame.getContentPane().add(stopPlayButton);
			stopPlayButton.setBounds(372, 488, 88, 24);
			stopPlayButton.setEnabled(false);
			//����������
			SliderPanel[] sliderPanels = new SliderPanel[Config.PARAMETER_NUMBER];
			String[] parameterName = new String[] {Constant.PITCH_STRING, Constant.SPEED_STRING, Constant.VARIATION_STRING, "��չ����", "��չ����"};
			for (int i = 0; i < sliderPanels.length; i++) {
				sliderPanels[i] = new SliderPanel(parameterName[i], 0, 100);
				frame.getContentPane().add(sliderPanels[i]);
				sliderPanels[i].setBounds(484, 28 + i * (212 - 28) / (sliderPanels.length - 1), 280, 20);
			}
			//����ɫ
			JLabel instrumentLabel1 = new JLabel("����ɫ");
			frame.getContentPane().add(instrumentLabel1);
			instrumentLabel1.setBounds(484, 256, 100, 20);
			JComboBox<String> instrumentComboBox1 = new JComboBox<String>();
			frame.getContentPane().add(instrumentComboBox1);
			instrumentComboBox1.setBounds(484, 282, 100, 20);
			//����ɫ
			JLabel instrumentLabel2 = new JLabel("����ɫ");
			frame.getContentPane().add(instrumentLabel2);
			instrumentLabel2.setBounds(600, 256, 100, 20);
			JComboBox<String> instrumentComboBox2 = new JComboBox<String>();
			frame.getContentPane().add(instrumentComboBox2);
			instrumentComboBox2.setBounds(600, 282, 100, 20);
			//������ɫ
			String[] instrumentList = new String[] {
				"piano","music box","accordian","harmonica","guitar",
				"bass","violin","viola","cello","contrabass",
				"harp","timpani","string","trumpet","trombone",
				"tuba","French horn","sax","oboe","bassoon",
				"clarinet","piccolo","flute","recorder"
			};
			for (String string : instrumentList) {
				instrumentComboBox1.addItem(string);
				instrumentComboBox2.addItem(string);
			}
			//ͼ��ѵ����ť
			pictureTrainingButton = new JButton("ͼ��ѵ��", null);
			pictureTrainingButton.addActionListener(new PictureTrainingActionListener());
			frame.getContentPane().add(pictureTrainingButton);
			pictureTrainingButton.setBounds(488, 488, 88, 24);
			//����ѵ����ť
			musicTrainingButton = new JButton("����ѵ��", null);
			musicTrainingButton.addActionListener(new MusicTrainingActionListener());
			frame.getContentPane().add(musicTrainingButton);
			musicTrainingButton.setBounds(604, 488, 88, 24);
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
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getArgs() {
		HashMap<String, Integer> args = new HashMap<String, Integer>();
		String instrumentType = "����ɫ";
		for (Component component : frame.getContentPane().getComponents()) {
			//���뻬�����Ĳ���
			if (component.toString().contains("SliderPanel")) {
				args.put(((SliderPanel)component).getName(), ((SliderPanel)component).getValue());
			}
			//��������ɫ�͸���ɫ
			if (component.toString().contains("JComboBox")) {
				if (instrumentType == "����ɫ") {
					args.put("����ɫ", Conversion.convertNoteInstrument(((JComboBox<String>)component).getSelectedItem().toString()));
					instrumentType = "����ɫ";
				} else {
					args.put("����ɫ", Conversion.convertNoteInstrument(((JComboBox<String>)component).getSelectedItem().toString()));
				}
			}
		}
		return args;
	}
	
	/**
	 * �趨�����������ϣ����趨��ɫ
	 * @param args �趨�Ĳ���
	 */
	public void setArgs(HashMap<String, Integer> args) {
		for (Component component : frame.getContentPane().getComponents()) {
			if (component.toString().contains("SliderPanel")) {
				SliderPanel panel = (SliderPanel)component;
				panel.slider.setValue(args.get(panel.getName()));
				panel.valueLabel.setText(panel.getValueString(args.get(panel.getName())));
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
				Player.stop();
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
			//�򿪱���Ի���
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
				//��������
				ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
				HashMap<String, Integer> musicParameterHashMap = parameterConversion.convert(parameterGenerate.generate(picture));
				HashMap<String, Integer> guiParameterHashMap = getArgs();
				musicParameterHashMap.put("����ɫ", guiParameterHashMap.get("����ɫ"));
				musicParameterHashMap.put("����ɫ", guiParameterHashMap.get("����ɫ"));
				music = composition.generate(musicParameterHashMap);
				//����
				MIDIWriter.write(music, path);
				//����ť
				startPlayButton.setEnabled(true);
				stopPlayButton.setEnabled(false);
				Player.stop();
			}
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
	 * ͼ��ѵ����ť���¼�������
	 */
	public class PictureTrainingActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (pictureTrainingButton.getText() == "ͼ��ѵ��") {
				selectDirectory();
			} else {
				nextPicture();
			}
		}
		
		/**
		 * �趨·��
		 */
		public void selectDirectory() {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				picturePath = new LinkedList<String>();
				for (File file : chooser.getSelectedFile().listFiles()) {
					if (file.getName().contains(".jpg") || file.getName().contains(".bmp") || file.getName().contains(".png")) {
						picturePath.add(file.getAbsolutePath());
					}
				}
				pictureTrainingButton.setText("��һ��ͼ");
			}
		}
		
		/**
		 * ��һ��ͼ
		 */
		public void nextPicture() {
			if (picturePath.size() > 0) {
				if (picture != null) {
					ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
					ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
					parameterGenerate.train(picture, getArgs());
					parameterConversion.train(picture, getArgs());
				}
				picture = new Picture(picturePath.pop());
				picture.resize(Math.min((double)(panel.getWidth()) / picture.getImage().getWidth(), (double)(panel.getHeight()) / picture.getImage().getHeight()));
				panel.setPicture(picture);
			} else {
				ParameterGenerate parameterGenerate = Config.PARAMETER_GENERATE_MAP.get(Config.CURRENT_GENERATE_STRING);
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				parameterGenerate.train(picture, getArgs());
				parameterConversion.train(picture, getArgs());
				parameterGenerate.tidy();
				parameterConversion.tidyPicture();
				picture = null;
				panel.setPicture(null);
				picturePath = null;
				pictureTrainingButton.setText("ͼ��ѵ��");
			}
		}
	}
	
	/**
	 * ����ѵ����ť���¼�������
	 */
	public class MusicTrainingActionListener implements ActionListener {
		
		/**
		 * �ող��ŵģ��ȴ��ͽ������һ����ѵ������ѵ��������
		 */
		private Music lastMusic = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (musicTrainingButton.getText() == "����ѵ��") {
				selectDirectory();
			} else {
				nextMusic();
			}
		}
		
		/**
		 * �趨·��
		 */
		public void selectDirectory() {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				musicPath = new LinkedList<String>();
				for (File file : chooser.getSelectedFile().listFiles()) {
					if (file.getName().contains(".mid")) {
						musicPath.add(file.getAbsolutePath());
					}
				}
				musicTrainingButton.setText("��һ����");
			}
		}
		
		/**
		 * ��һ����
		 */
		public void nextMusic() {
			if (musicPath.size() > 0) {
				if (lastMusic != null) {
					ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
					Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
					parameterConversion.train(lastMusic, getArgs());
					composition.train(lastMusic, getArgs());
				}
				Player.stop();
				lastMusic = MIDIReader.read(musicPath.pop());
				Player.play(lastMusic);
			} else {
				ParameterConversion parameterConversion = Config.PARAMETER_CONVERSION_MAP.get(Config.CURRENT_CONVERSION_STRING);
				Composition composition = Config.COMPOSITION_MAP.get(Config.CURRENT_COMPOSITION_STRING);
				parameterConversion.train(lastMusic, getArgs());
				composition.train(lastMusic, getArgs());
				parameterConversion.tidyMusic();
				composition.tidy();
				Player.stop();
				lastMusic = null;
				musicPath = null;
				musicTrainingButton.setText("����ѵ��");
			}
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
}
