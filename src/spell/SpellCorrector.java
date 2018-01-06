package spell;

import java.io.IOException;

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
        return null;
    }

    private Trie dictionary = new Trie();
}
