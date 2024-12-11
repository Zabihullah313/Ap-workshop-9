import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        File folder = new File("assets");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) {
            System.out.println("No files found in the assets folder.");
            return;
        }

        Set<String> uniqueWords = Collections.synchronizedSet(new HashSet<>());
        List<String> allWords = Collections.synchronizedList(new ArrayList<>());

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(files.length);

        for (File file : files) {
            executor.execute(new FileProcessor(file, uniqueWords, allWords));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }


        Optional<String> longestWord = allWords.stream().max(Comparator.comparingInt(String::length));
        Optional<String> shortestWord = allWords.stream().min(Comparator.comparingInt(String::length));
        double averageLength = allWords.stream().mapToInt(String::length).average().orElse(0);


        System.out.println("Total unique words: " + uniqueWords.size());
        longestWord.ifPresent(word -> System.out.println("Longest word: " + word + " (" + word.length() + " characters)"));
        shortestWord.ifPresent(word -> System.out.println("Shortest word: " + word + " (" + word.length() + " characters)"));
        System.out.println("Average word length: " + averageLength);
    }
}


