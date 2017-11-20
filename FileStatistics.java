import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStatistics {

	private File file;

	private int charsCount = 0;
	private int wordsCount = 0;
	private int linesCount = 0;

	//My KommentarCount
	private int kommcharsCount = 0;
	private int kommwordsCount = 0;
	private int kommlinesCount = 0;

	
	//My Boolean Vars
	private boolean kommentar_zeile 	= false;
	private boolean kommentar_absatz	= false;
	
	
	
	
	public FileStatistics(File file) throws FileNotFoundException {
		this.file = file;
		
		
		
		Scanner fileScanner = null;
		fileScanner = new Scanner(file);

		while (fileScanner.hasNextLine()) {
			linesCount++;
			String line = fileScanner.nextLine();
			kommentar_zeile = false;
			
			if (line.contains("//")) { kommentar_zeile	= true; }
			if (line.contains("/*")) { kommentar_absatz	= true; }
			
			
			if (kommentar_zeile || kommentar_absatz)
			{
				kommlinesCount++;
				
			}
			
			
			
			Scanner lineScanner = new Scanner(line);
			// count the characters of the file till the end
			while (lineScanner.hasNext()) {
				wordsCount++;
				String word = lineScanner.next();
				charsCount += word.length();
				
				if (kommentar_zeile || kommentar_absatz)
				{
					kommwordsCount++;
					//String word = lineScanner.next();
					kommcharsCount += word.length();
						
					
					
				}
			}
			
			
			
			if (kommentar_absatz)
			{
			
			if (line.contains("*/")) { kommentar_absatz	= false; }
				
			}
			
			
			lineScanner.close();
			
			
			
			
		}
		fileScanner.close();
	}

	
	
	
	
	
	
	public static void main(String[] args) {

		String classLocation = FileStatistics.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String probableSourcePath = new File(classLocation).getParentFile().getAbsolutePath() + "/src";
		File sourceDir = new File(probableSourcePath);

		if (!sourceDir.exists() || !sourceDir.isDirectory()) {
			System.out.println("Path '" + probableSourcePath + "' does not seem to represent a directory.");
			return;
		}

		List<File> sourceFiles = new ArrayList<>();
		FileStatistics.findJavaFiles(sourceDir, sourceFiles);

		int totalCharsCount = 0;
		int totalWordsCount = 0;
		int totalLinesCount = 0;
		int totalFileCount = 0;

		for (File sourceFile : sourceFiles) {
			try {
				FileStatistics stats = new FileStatistics(sourceFile);
				totalFileCount++;
				totalCharsCount += stats.charsCount;
				totalLinesCount += stats.linesCount;
				totalWordsCount += stats.wordsCount;
				System.out.println(stats);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		System.out.println(totalFileCount + " files processed. counted " + totalCharsCount + " chars, "
				+ totalWordsCount + " words and " + totalLinesCount + " lines.");
		

	}

	@Override
	public String toString() {
		return "FileStatistics [file=" + file.getAbsolutePath() + ", charsCount=" + charsCount + ", wordsCount="
				+ wordsCount + ", linesCount=" + linesCount + "] FileStatistics [file=" + file.getAbsolutePath() + ", kommcharsCount=" + kommcharsCount + ", wordsCount=" + 
						 kommwordsCount + ", linesCount=" + kommlinesCount + "]";
	}

	/*
	@Override
	public String toString2() {
		return "FileStatistics [file=" + file.getAbsolutePath() + ", kommcharsCount=" + kommcharsCount + ", wordsCount="
				+ kommwordsCount + ", linesCount=" + kommlinesCount + "]";
	}
	*/
	
	public static void findJavaFiles(File root, List<File> javaOnly) {
		if (root == null || javaOnly == null)
			return; // just for safety
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				findJavaFiles(file, javaOnly);
			}
		} else if (root.isFile() && root.getName().endsWith(".java")) {
			javaOnly.add(root);
		}
	}
}