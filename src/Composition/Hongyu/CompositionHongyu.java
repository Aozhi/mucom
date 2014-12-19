package Composition.Hongyu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Composition.Hongyu.Essential.Arranger;
import Composition.Hongyu.Essential.Common;
import Composition.Hongyu.Essential.GeneratorSelection;
import Composition.Hongyu.Essential.HarmonyGenerator;
import Composition.Hongyu.Essential.InnerStructureGenerator;
import Composition.Hongyu.Essential.MarkovChain;
import Composition.Hongyu.Essential.MelodyGenerator;
import Composition.Hongyu.Essential.MusicDescription;
import Composition.Hongyu.Essential.Note;
import Composition.Hongyu.Essential.NoteEvent;
import Composition.Hongyu.Essential.Ornamenter;
import Composition.Hongyu.Essential.Part;
import Composition.Hongyu.Essential.Phrase;
import Composition.Hongyu.Essential.RenderEvent;
import Composition.Hongyu.Essential.RenderPart;
import Composition.Hongyu.Essential.Renderer;
import Composition.Hongyu.Essential.RhythmGenerator;
import Composition.Hongyu.Essential.Sentence;
import Composition.Hongyu.Essential.StructureGenerator;
import Composition.Hongyu.Essential.Time;
import Composition.Hongyu.Essential.Track;
import Composition.Hongyu.Essential.UniquePart;
import Composition.Hongyu.Essential.UniquePhrase;
import Composition.Hongyu.GeneratorSelections.GeneratorSelectionGetter;
import File.TextWriter;
import Generate.Composition;
import MIDI.Music;
/**
 * �������ֹ����ʵ��
 */
public class CompositionHongyu implements Composition {


	
	/**
	 * ԭʼ���ݴ洢·��
	 */
	public static final String RAW_DATA_PREFIX = "./TrainingData/PicthTrainingRawData";
	
	/**
	 * ����Ʒ����·��
	 */
	public static final String TRAINING_RESULT = "./MarkovTrainingResult.obj";
	/**
	 * һ���˶Ȱ����İ�����
	 */
	private static final int OCTAVE = 12;
	
	/**
	 * �����ϵİ׼�
	 */
	private static final Integer[] ALLOW_PITCH = {
		-12, -10, -8, -7, -5, -3, -1,
		0, 2, 4, 5, 7, 9, 11,
		12, 14, 16, 17, 19, 21, 23,
		};
	
	@Override
	public void train(Music music, HashMap<String, Integer> parameter) {
		try {
			//�������л�ȡ����
			ArrayList<ArrayList<Integer>> pitchs = getPitchs(music);
			//����׷��ԭʼ���ݵ�writer
			BufferedWriter[] writers = new BufferedWriter[MarkovChain.MARKOV_CHAIN_LENGTH];
			for (int i = 0; i < writers.length; i++) {
				writers[i] = new BufferedWriter(new FileWriter(RAW_DATA_PREFIX + (i + 1) + ".txt", true));
			}
			//��¼ԭʼ���ݴ��룬ԭʼ������ÿһ�е�һ����������12ƽ�����е�����λ�ã���߼�����������������Ե�һ�����ߵ�ƫ��
			for (int i = 0; i < writers.length; i++) {
				for (int j = 0; j < pitchs.size(); j++) {
					for (int k = 0; k < pitchs.get(j).size() - i - 1; k++) {
						ArrayList<Integer> record = new ArrayList<>();
						//��¼������
						record.add(pitchs.get(j).get(k) % OCTAVE);
						//��¼�������������������λ�ã�������쳬��һ�����ߣ���ȡ����
						for (int l = 1; l < i + 1; l++) {
							record.add((pitchs.get(j).get(k + l) - pitchs.get(j).get(k)) % OCTAVE + record.get(0));
						}
						//��¼���յĽ������
						record.add((pitchs.get(j).get(k + i + 1) - pitchs.get(j).get(k)) % OCTAVE + record.get(0));
						//��Ч�Ĳ���
						if (isValid(record)) {
							for (int l = 0; l < record.size() - 1; l++) {
								writers[i].write(record.get(l) + " ");
							}
							writers[i].write(record.get(record.size() - 1) + "\n");
						}
					}
				}
				writers[i].flush();
				writers[i].close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private boolean isValid(ArrayList<Integer> record) {
		//����Ƿ��ڰ׼���
		for (Integer integer : record) {
			if (!Common.arrayContains(ALLOW_PITCH, integer)) {
				return false;
			}
		}
		//�������ĸ���Ϊ��ͬ�������������ܵ�����
		HashSet<Integer> unique = new HashSet<>(record);
		int randomNumber = Common.getRandomInteger(0, record.size());
		if (randomNumber >= unique.size())
			return false;
		//������Ӧ��note
		for (int i = 0; i < record.size(); i++) {
			for (int j = 0; j < ALLOW_PITCH.length; j++) {
				if (record.get(i) == ALLOW_PITCH[j]) {
					//C��Ӧj=7��Ӧת��Ϊ1
					record.set(i, j - 6);
				}
			}
		}
		return true;
	}

	/**
	 * ����������������
	 * @param music �����������
	 * @return ������������ÿһ��ArrayList<Integer>����һ������
	 */
	private static ArrayList<ArrayList<Integer>> getPitchs(Music music) {
		ArrayList<ArrayList<Integer>> pitchs = null;
		try {
			//��musicת�����ı��ٶ�ȡ�����㴦��
			TextWriter.write(music, "music.txt");
			//��ȡmusic����ȡÿ�����������
			pitchs = new ArrayList<ArrayList<Integer>>();
			BufferedReader reader = new BufferedReader(new FileReader("music.txt"));
			while(true) {
				//��ȡһ��
				String line = reader.readLine();
				//�ж��Ƿ��ļ�β
				if (line == null || line == "") {
					break;
				}
				//��������
				String[] split = line.split(" ");
				//�ж��Ƿ���������
				if (Integer.parseInt(split[0]) == -1) {
					pitchs.add(new ArrayList<Integer>());
					continue;
				}
				//�ж��Ƿ�Ϊ�ٶ�����
				if (split.length == 1) {
					continue;
				}
				//�ж��Ƿ�������
				if (Integer.parseInt(split[0]) == 8 || Integer.parseInt(split[0]) == 9) {
					if (Integer.parseInt(split[2]) != 0) {
						pitchs.get(pitchs.size() - 1).add(Integer.parseInt(split[1]));
					}
				}
			}
			reader.close();
			//ɾ����ʱ�ļ� 
			File file = new File("music.txt");
			file.delete();
			//���������
			for (int i = pitchs.size() - 1; i >= 0; i--) {
				if (pitchs.get(i).size() == 0) {
					pitchs.remove(i);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pitchs;
	}
	
	@Override
	public void tidy() {
		try {
			ArrayList<Integer[]> rawData = new ArrayList<>();
			//��ȡ�ļ�
			for (int i = 1; i <= MarkovChain.MARKOV_CHAIN_LENGTH; i++) {
				BufferedReader reader = new BufferedReader(new FileReader(RAW_DATA_PREFIX + i + ".txt"));
				while (true) {
					//��ȡ����Ʒ�������������
					String line = reader.readLine();
					if (line == null || line == "") {
						break;
					}
					//�ֿ��������ֵ����֣���ߵ�������ֵ��array��ǰ�ߵĻ�׼��ֵ��baseNote
					String[] lineSplit = line.split(" ");
					Integer[] integers = new Integer[lineSplit.length];
					for (int j = 0; j < lineSplit.length; j++) {
						integers[j] = Integer.parseInt(lineSplit[j]);
					}
					rawData.add(integers);
				}
				reader.close();
			}
			MarkovChain markovChain = new MarkovChain(rawData);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(TRAINING_RESULT));
			objectOutputStream.writeObject(markovChain);
			objectOutputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public Music generate(HashMap<String, Integer> parameter) {
		// ���������
		MusicDescription musicDescription = new MusicDescription();
		//StructureGenerator��Arranger���������ã�Ornamenters��Part�ı�����������UniquePart�ı���������StructureGenerator������
		GeneratorSelection generatorSelection = GeneratorSelectionGetter.getGeneratorSelection(parameter);
		StructureGenerator structureGenerator = generatorSelection.getStructureGenerator();
		Arranger arranger = generatorSelection.getArranger();
		// һЩ��Ϣ
		System.out.println("**********");
		System.out.println();
		System.out.println(structureGenerator);
		System.out.println(arranger);
		// �����ṹ
		structureGenerator.generateStructure(musicDescription, parameter);
		// �����ڲ��ṹ
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			InnerStructureGenerator innerStructureGenerator = uniquePart.innerStructureGenerator;
			innerStructureGenerator.generateInnerStructure(uniquePart,
					parameter);
		}
		// ����Part��UniquePart
		int currentBar = 0;
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			Part part = musicDescription.getPart(i);
			int uniquePartIndex = part.getUniquePartIndex();
			UniquePart uniquePart = musicDescription
					.getUniquePart(uniquePartIndex);
			// ��֤ÿ��part����ȷ����ʼС�ںź���ֹС�ں�
			part.setStartBar(currentBar);
			part.setEndBar(currentBar + uniquePart.getBarsCount() - 1);
			int sentenceCount = uniquePart.getSentencesCount();
			for (int j = 0; j < sentenceCount; j++) {
				Sentence sentence = uniquePart.getSentence(j);
				int phraseCount = sentence.getPhrasesCount();
				for (int k = 0; k < phraseCount; k++) {
					Phrase phrase = sentence.getPhrase(k);
					int uniquePhraseId = phrase.getUniquePhraseId();
					UniquePhrase uniquePhrase = uniquePart
							.getUniquePhrase(uniquePhraseId);
					phrase.setUniquePhrase(uniquePhrase);
					phrase.setBars(uniquePhrase.getBars());
					uniquePhrase.setStartsSentence(k == 0);
					uniquePhrase.setEndsSentence(k == phraseCount - 1);
					uniquePhrase.setStartsPart(j == 0 && k == 0);
					uniquePhrase.setEndsPart(j == sentenceCount - 1
							&& k == phraseCount - 1);
				}
			}
			int bars = uniquePart.getBarsCount();
			currentBar += bars;
		}
		// ��������
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			RhythmGenerator rhythmGenerator = uniquePart.rhythmGenerator;
			for (int j = 0; j < uniquePart.getUniquePhrasesCount(); j++) {
				UniquePhrase uniquePhrase = uniquePart.getUniquePhrase(j);
				rhythmGenerator.generateRhythm(uniquePhrase, parameter);
			}
		}
		// ����UniquePhrase��������UniquePart��
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			int bars = 0;
			for (int j = 0; j < uniquePart.getSentencesCount(); j++) {
				Sentence sentence = uniquePart.getSentence(j);
				int phrases = sentence.getPhrasesCount();
				for (int k = 0; k < phrases; k++) {
					Phrase phrase = sentence.getPhrase(k);
					UniquePhrase uniquePhrase = phrase.getUniquePhrase();
					for (int l = 0; l < uniquePhrase.getEventsCount(); l++) {
						NoteEvent event = uniquePhrase.getEvent(l);
						uniquePart.addEvent(event.clone().translate(bars));
					}
					bars += phrase.getBars();
				}
			}
		}
		// ��������
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			HarmonyGenerator harmonyGenerator = uniquePart.harmonyGenerator;
			harmonyGenerator.generateHarmony(uniquePart, parameter);
		}
		// ���������������¼�
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			uniquePart.assignEventsToHarmony();
		}
		// ��������
		for (int i = 0; i < musicDescription.getUniquePartsCount(); i++) {
			UniquePart uniquePart = musicDescription.getUniquePart(i);
			MelodyGenerator melodyGenerator = uniquePart.melodyGenerator;
			melodyGenerator.generateMelody(uniquePart, parameter);
		}
		// ��������
		for (int i = 0; i < musicDescription.getPartsCount(); i++) {
			Part part = musicDescription.getPart(i);
			int uniquePartIndex = part.getUniquePartIndex();
			UniquePart uniquePart = musicDescription.getUniquePart(uniquePartIndex);
			Ornamenter ornamenter = part.ornamenter;
			ornamenter.ornament(part, uniquePart, parameter);
		}
		
		// ��������
		arranger.arrange(musicDescription, parameter);
		// ��Ⱦ����
		ArrayList<Track> tracks = musicDescription.getTracks();
		ArrayList<RenderPart> renderParts = new ArrayList<>();
		for (int i = 0; i < tracks.size(); i++) {
			Track track = tracks.get(i);
			ArrayList<RenderEvent> renderEvents = track.getRenderEvents();
			for (RenderEvent renderEvent : renderEvents) {
				int initialStep = renderEvent.getInitialStep();
				int finalStep = renderEvent.getFinalStep();

				ArrayList<Part> parts = new ArrayList<Part>();
				for (int j = 0; j < musicDescription.getPartsCount(); j++) {
					Part part = musicDescription.getPart(j);
					if ((part.getStartBar() >= initialStep && part.getEndBar() <= finalStep)
							|| (initialStep >= part.getStartBar() && finalStep <= part
									.getEndBar())) {
						parts.add(part);
					}
				}

				for (Part part : parts) {
					UniquePart uniquePart = musicDescription.getUniquePart(part
							.getUniquePartIndex());
					RenderPart renderPart = new RenderPart(i);
					renderParts.add(renderPart);
					renderPart.setData(part, uniquePart, renderEvent,
							musicDescription);
					Renderer renderer = renderPart.getRenderEvent().getRenderer();
					renderer.render(renderPart, parameter);
					renderPart.translateNotes(part.getStartBar());
					Time timeOffset = renderEvent.getTimeOffset();
					renderPart.translateNotes(timeOffset);
				}
			}
		}
		// ��������
		return getMusic(musicDescription, renderParts, parameter);
	}

	Music getMusic(MusicDescription musicDescription, ArrayList<RenderPart> renderParts, HashMap<String, Integer> parameter) {
		//ÿ�������ŵ�ticks
		int ticksPerSecond = 96;
		//ÿС�ڵ���ռ�õ�ticks
		int ticksPerBar = 192;
		//�ӳ�һ��ȫ�����ĳ��ȣ�һ��Ϊ2��
		int offsetBars = 1;
		//�������ʱ��
		double originalTime = (musicDescription.getBarsCount() + offsetBars) * ticksPerBar / (double)(ticksPerSecond);
		System.out.println("Original Time = " + originalTime + " BPM = " + ticksPerSecond * 1.25);
		/*
		//����ʱ��
		double exceptTime = parameter.get(Constant.TIME_STRING);
		double coefficient = originalTime / exceptTime;
		if (coefficient > 1.05) { coefficient = 1.05; }
		if (coefficient < 0.95) { coefficient = 0.95; }
		ticksPerSecond *= coefficient;
		double finalTime = (musicDescription.getBarsCount() + offsetBars) * ticksPerBar / (double)(ticksPerSecond);
		System.out.println("Final Time = " + finalTime + " BPM = " + ticksPerSecond * 1.25);
 		*/
		System.out.println();
		//����Ϊÿ���������ŵ�ticks
		Music music = new Music(ticksPerSecond / 2);
		//��������
		ArrayList<Track> tracks = musicDescription.getTracks();
		for (int i = 0; i < tracks.size(); i++) {
				music.createTrack("" + i, "piano");
		}
		//��Ⱦ
		for (RenderPart renderPart : renderParts) {
			ArrayList<Note> notes = renderPart.getNotes();
			int trackIndex = renderPart.getTrackIndex();
			music.setTrack("" + trackIndex);
			UniquePart uniquePart = renderPart.getUniquePart();
			int meter = uniquePart.getMeter();
			for (Note note : notes) {
				Time start = note.getStart();
				Time end = note.getEnd();
				int pitch = note.getPitch();
				int volume = note.getVolume();
				while (pitch < 36) pitch += 12;
				while (pitch > 96) pitch -= 12;
				if (pitch < 48) volume *= (double)(pitch) / 48.0;
				long onTime = (long) (ticksPerBar * (offsetBars + start.startBar + start.position / meter));
				long offTime = (long) (ticksPerBar * (offsetBars + end.startBar + end.position / meter));
				// ��ֹ����
				volume = (int)(volume * 0.8);
				music.pushNote(pitch, onTime, (int)(offTime - onTime), volume);
			}
		}
		return music;
	}
}
