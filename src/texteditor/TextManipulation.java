package texteditor;


public class TextManipulation {
	
	public int getWordCount(String textArea) {
		
		int wordCount = 0;
		
		for (int i = 0; i < textArea.length(); i++) {
			
			if (textArea.charAt(i) == ' ') {
				wordCount++;
			}
			
		}
		
		wordCount++;
		
		return wordCount;
		
	}
	
}
