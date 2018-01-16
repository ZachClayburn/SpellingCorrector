package spell;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {

    public static void main(String[] args) throws IOException{
        String fileName = args[0];
        String word = args[1];
        SpellCorrector spellCorrector = new SpellCorrector();
        spellCorrector.useDictionary(fileName);
        TreeSet<String> deleteDistance = spellCorrector.getDeleteDistance(word);
        System.out.println(deleteDistance);
        TreeSet<String> transposeDistance = spellCorrector.getTransposeDistance(word);
        System.out.println(transposeDistance);
        TreeSet<String> alterDistance = spellCorrector.getAlterDistance(word);
        System.out.println(alterDistance);
        TreeSet<String> insertDistance = spellCorrector.getInsertDistance(word);
        System.out.println(insertDistance);
    }


    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     *
     * @param dictionaryFileName File containing the words to be used
     * @throws IOException If the file cannot be read
     */
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Path dictionaryFilePath = Paths.get(dictionaryFileName);
        Scanner in = new Scanner(dictionaryFilePath, StandardCharsets.UTF_8.displayName());

        dictionary = new Trie();

        while (in.hasNext()) {
            dictionary.add(in.next());
        }

    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>
     *
     * @param inputWord
     * @return The suggestion or null if there is no similar word in the dictionary
     */
    @Override
    public String suggestSimilarWord(String inputWord) {

        ITrie.INode foundWord = dictionary.find(inputWord);
        if (foundWord != null && foundWord.getValue() > 0) {
            return inputWord.toLowerCase();
        }

        TreeSet<String> oneDistance = getWordsOneDistanceFrom(inputWord.toLowerCase());

        String match = searchForMatch(oneDistance);

        if (match != null) {
            return match;
        }

        TreeSet<String> twoDistance = getWordsTwoDistanceFrom(oneDistance);

        match = searchForMatch(twoDistance);

        if (match != null) {
            return match;
        }

        return null;
    }

    private Trie dictionary = null;

    private TreeSet<String> getWordsOneDistanceFrom(String word) {
        TreeSet<String> wordSet = new TreeSet<>();

        wordSet.addAll(getDeleteDistance(word));
        wordSet.addAll(getTransposeDistance(word));
        wordSet.addAll(getAlterDistance(word));
        wordSet.addAll(getInsertDistance(word));

        return wordSet;
    }

    private TreeSet<String> getWordsTwoDistanceFrom(TreeSet<String> words) {
        TreeSet<String> wordSet = new TreeSet<>();
        for(String word : words){
            wordSet.addAll(getWordsOneDistanceFrom(word));
        }
        return wordSet;
    }

    private String searchForMatch(TreeSet<String> options) {
        int maxCount = 0;
        String bestMatch = null;

        for(String word : options){
            ITrie.INode match = dictionary.find(word);
            if(match != null && match.getValue() > maxCount){
                maxCount = match.getValue();
                bestMatch = word;
            }
        }

        return bestMatch;
    }

    private TreeSet<String> getDeleteDistance(String word){
        TreeSet<String> options = new TreeSet<>();
        if(word.length() > 1) {
            for (int i = 0; i < word.length(); i++) {
                options.add(word.substring(0, i) + word.substring(i + 1));
            }
        }
        return options;
    }

    private TreeSet<String> getTransposeDistance(String word){
        TreeSet<String> options = new TreeSet<>();
        for (int i = 0; i < word.length()-1; i++){
            char char1 = word.charAt(i);
            char char2 = word.charAt(i+1);
            String before = (i > 0) ? word.substring(0,i) : "";
            String after = word.substring(i+2);

            options.add(before + char2 + char1 + after);
        }
        return options;
    }

    private TreeSet<String> getAlterDistance(String word){
        TreeSet<String> options = new TreeSet<>();
        return options;
    }

    private TreeSet<String> getInsertDistance(String word){
        TreeSet<String> options = new TreeSet<>();
        return options;
    }

}
