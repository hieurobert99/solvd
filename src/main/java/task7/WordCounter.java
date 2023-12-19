//package task7;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class WordCounter {
//
//    public static void main(String[] args) {
//        // Read text from a file
//        File inputFile = new File("sample.txt");
//        File outputFile = new File("sampleOutput.txt");
//
//        try {
//            List<String> lines = FileUtils.readLines(inputFile, "UTF-8");
//
//            // Combine lines into a single string
//            String content = StringUtils.join(lines, " ");
//
//            // Split text into words
//            String[] words = content.split("\\s+");
//
//            // Count unique words
//            Set<String> uniqueWords = new HashSet<>();
//            for (String word : words) {
//                if (!word.isEmpty()) {
//                    uniqueWords.add(word.toLowerCase()); // Consider words case-insensitively
//                }
//            }
//
//            // Write the count of unique words to the output file
//            FileUtils.writeStringToFile(outputFile, "Number of unique words: " + uniqueWords.size(), "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
