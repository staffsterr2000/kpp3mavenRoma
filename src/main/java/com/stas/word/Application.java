package com.stas.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static Map<String, Integer> getWordsAndTheirCountMap(Collection<String> words) {
        Map<String, Integer> wordAndItAppearingCountMap = new LinkedHashMap<>();
        trimCollection(words);

        for (String word : words) {
            Integer value = wordAndItAppearingCountMap.get(word);
            if (value == null)
                wordAndItAppearingCountMap.put(word, 1);
            else
                wordAndItAppearingCountMap.put(word, ++value);
        }

        return wordAndItAppearingCountMap;
    }

    public static void trimCollection(Collection<String> collection) {
        collection.removeIf(value -> value == null || value.isEmpty());
    }

    public static List<String> getWordsThatAppearsOnce(Map<String, Integer> wordAndItAppearingCountMap) {
        return wordAndItAppearingCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static String formulateResult(Collection<String> collection) {
        StringBuilder builder = new StringBuilder("Result: ");

        for (String word : collection) {
            builder.append(word).append(", ");
        }

        String result = builder.toString();
        System.out.println(result);

        return result;
    }

    public static void main(String[] args) {

        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the file's path:");
            Path path = Paths.get(console.readLine());
            WordHelper helper = new WordHelper(path);

            List<String> words = helper.readWordsFromFile();
            Map<String, Integer> map = getWordsAndTheirCountMap(words);
            List<String> list = getWordsThatAppearsOnce(map);
            String result = formulateResult(list);
            helper.addResultToFile(result);
            helper.saveDocument();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
