package task7;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Read the text from the file and calculate the numbers of the unique words. Write the result to the file.
//The main requirement is: using StringUtils and FileUtils to implement it with minimum lines of code.
public class WordCounter {

    public static void main(String[] args) {

        try {
            // Read text from a file
            List<String> lines = FileUtils.readLines(new File("sample.txt"));


            // Combine lines into a single string
            String content = StringUtils.join(lines, " ");

            // Split text into words
            String[] words = content.split("\\s+");

            // Count unique words
            Set<String> uniqueWords = new HashSet<>();
            for (String word : words) {
                if (!word.isEmpty()) {
                    uniqueWords.add(word.toLowerCase()); // Consider words case-insensitively
                }
            }

            // Write the count of unique words to the output file
            FileUtils.writeStringToFile(new File("output.txt"), "Number of unique words: " + uniqueWords.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

