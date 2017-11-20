import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStatisticsTanya {

	private File file;

	private int charsCount = 0;
	private int wordsCount = 0;
	private int linesCount = 0;

	public FileStatisticsTanya(File file) throws FileNotFoundException {
		this.file = file;
		Scanner fileScanner = null;
		fileScanner = new Scanner(file);

		while (fileScanner.hasNextLine()) {
			linesCount++;
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			// count the characters of the file till the end
			while (lineScanner.hasNext()) {
				wordsCount++;
				String word = lineScanner.next();
				charsCount += word.length();
			}
			lineScanner.close();
		}
		fileScanner.close();
	}

	public static void main(String[] args) {

		String classLocation = FileStatisticsTanya.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String probableSourcePath = new File(classLocation).getParentFile().getAbsolutePath() + "/src";
		File sourceDir = new File(probableSourcePath);

		if (!sourceDir.exists() || !sourceDir.isDirectory()) {
			System.out.println("Path '" + probableSourcePath + "' does not seem to represent a directory.");
			return;
		}

		List<File> sourceFiles = new ArrayList<>();
		FileStatisticsTanya.findJavaFiles(sourceDir, sourceFiles);

		int totalCharsCount = 0;
		int totalWordsCount = 0;
		int totalLinesCount = 0;
		int totalFileCount = 0;

		for (File sourceFile : sourceFiles) {
			try {
				FileStatisticsTanya stats = new FileStatisticsTanya(sourceFile);
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
				+ wordsCount + ", linesCount=" + linesCount + "]";
	}

	public static void findJavaFiles(File root, List<File> javaOnly) {
		if (root == null || javaOnly == null)
			return; 
		// just for safety
		
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				findJavaFiles(file, javaOnly);
			}
		} else if (root.isFile() && root.getName().endsWith(".java")) {
			javaOnly.add(root);
		}
	}
}