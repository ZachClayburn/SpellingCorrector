package spell;


public class Trie implements ITrie{

    @Override
    public int hashCode() {
        return (nodeCount * 7) + (wordCount * 3);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }
        Trie trie = (Trie) o;
        if(wordCount != trie.wordCount || nodeCount != trie.nodeCount){
            return false;
        }

        return equalsHelper(root, trie.root);
    }

    private boolean equalsHelper(Node mine, Node theirs){
        if(mine.count != theirs.count){
            return false;
        }
        for(int i = 0; i < 26; i++){
            if(mine.children[i] == null){
                if (theirs.children[i] != null){
                    return false;
                }
            }else if(theirs.children[i] == null){
                return false;
            }else if(!equalsHelper(mine.children[i],theirs.children[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return root.getString("");
    }

    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count
     *
     * @param word The word being added to the trie
     */
    @Override
    public void add(String word) {
        root.add(word.toLowerCase());
    }

    /**
     * Searches the trie for the specified word
     *
     * @param word The word being searched for
     * @return A reference to the trie node that represents the word,
     * or null if the word is not in the trie
     */
    @Override
    public INode find(String word) {
        return root.find(word);
    }

    /**
     * Returns the number of unique words in the trie
     *
     * @return The number of unique words in the trie
     */
    @Override
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Returns the number of nodes in the trie
     *
     * @return The number of nodes in the trie
     */
    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    private Node root = new Node();

    private int nodeCount = 1;

    private int wordCount = 0;


    class Node implements INode{
        /**
         * Returns the frequency count for the word represented by the node
         *
         * @return The frequency count for the word represented by the node
         */
        @Override
        public int getValue() {
            return count;
        }

        void add(String word){
            if(word.length() == 0){
                if(this.count == 0) {
                    Trie.this.wordCount++;
                }
                count++;
            } else {
                char next = word.charAt(0);
                word = word.substring(1);
                int index = charToIndex(next);

                if(children[index] == null){
                    children[index] = new Node();
                    Trie.this.nodeCount++;
                }

                children[index].add(word);
            }
        }

        Node find(String word){
            if(word.equals("")){
                return this;
            }
            char next = word.charAt(0);
            word = word.substring(1);

            if(children[charToIndex(next)] == null) {
                return null;
            } else {
                return children[charToIndex(next)].find(word);
            }
        }

        String getString(String parentString){
            StringBuilder resultString = new StringBuilder();
            if(count != 0){
                resultString.append(parentString);
                resultString.append('\n');
            }
            for(int i = 0; i < 26; i++){
                if(children[i] != null){
                    resultString.append(children[i].getString(parentString + indexToChar(i)));
                }
            }
            return resultString.toString();
        }

        int charToIndex(char value){
            return (int)value - (int)'a';
        }

        char indexToChar(int index){
            return (char) (index + (int)'a');
        }

        private int count = 0;

        private Node[] children = new Node[26];
    }
}
