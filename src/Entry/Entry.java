package Entry;

import Config.Config;
import UI.Form4;

/**
 * ���������
 */
public class Entry {
	
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		Config.configInit();
		Form4 form = new Form4();
		form.show();
	}
}
