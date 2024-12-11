import java.io.*;
import java.util.*;

    public class FileProcessor implements Runnable {
        private final File file;
        private final Set<String> uniqueWords;
        private final List<String> allWords;

        public FileProcessor(File file, Set<String> uniqueWords, List<String> allWords) {
            this.file = file;
            this.uniqueWords = uniqueWords;
            this.allWords = allWords;
        }


        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    synchronized (uniqueWords) {
                        uniqueWords.addAll(Arrays.asList(words));
                    }
                    synchronized (allWords) {
                        allWords.addAll(Arrays.asList(words));
                    }
                }
            } catch (IOException e) {
                System.err.println("Error processing file: " + file.getName());
            }
        }
    }





