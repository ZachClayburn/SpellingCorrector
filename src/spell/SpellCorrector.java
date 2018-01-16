package spell;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {


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

        while(in.hasNext()){
            dictionary.add(in.next());
        }

        System.out.println(dictionary.toString());
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
        if(foundWord != null){
            return inputWord.toLowerCase();
        }

        return null;
    }

    private Trie dictionary = null;
}
