package main.java.solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PuzzleSolver {
    private static List<List<String>> longest = new ArrayList(List.of(new ArrayList()));

    public static String solvePuzzles(String pathToFile) {
        List<String> puzzles = new ArrayList<>();
        List<String> answerList = new ArrayList<>();
        Set<String> used = new HashSet<>();
        Map<String, List<String>> pairs = new HashMap<>();
        StringBuilder answer = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            while (br.ready()) {
                puzzles.add(br.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String p : puzzles) {
            pairs.put(p, new ArrayList<>());
        }

        for (String k : pairs.keySet()) {
            for (String p : puzzles) {
                if (!k.equals(p) && k.endsWith(p.substring(0, 2))) {
                    pairs.get(k).add(p);
                }
            }
        }

        for (int i = 0; i < puzzles.size(); i++) {
            answerList.add(puzzles.get(i));
            used.add(puzzles.get(i));

            if (!checkPuzzle(puzzles, answerList, used, pairs)) {
                answerList.removeLast();
                used.remove(puzzles.get(i));
            }
        }

        for (String res : longest.getFirst()) {
            if (answer.isEmpty()) {
                answer.append(res);
            } else answer.append(res.substring(2));
        }

        return answer.toString();
    }

    private static boolean checkPuzzle(List<String> puzzles, List<String> answerList, Set<String> used, Map<String, List<String>> pairs) {
        if (answerList.size() == puzzles.size()) return true;
        if (longest.getFirst().size() < answerList.size()) {
            longest.set(0, new ArrayList<>(answerList));
        }

        String curWord = answerList.getLast();

        for(int i = 0; i < pairs.get(curWord).size(); i++) {
            if (pairs.get(curWord).isEmpty()) return false;

            if (!used.contains(pairs.get(curWord).get(i))) {
                answerList.add(pairs.get(curWord).get(i));
                used.add(pairs.get(curWord).get(i));

                if (checkPuzzle(puzzles, answerList, used, pairs)) {
                    return true;
                }

                answerList.removeLast();
                used.remove(pairs.get(curWord).get(i));
            }
        }

        return false;
    }
}