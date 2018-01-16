package spell;

import java.util.Map;

public class Trie implements ITrie{

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
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
        return root.getWordCount();
    }

    /**
     * Returns the number of nodes in the trie
     *
     * @return The number of nodes in the trie
     */
    @Override
    public int getNodeCount() {
        return root.getNodeCount();
    }

    private Node root = new Node();

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
                count++;
            } else {
                char next = word.charAt(0);
                word = word.substring(1);
                int index = charToIndex(next);

                if(children[index] == null){
                    children[index] = new Node();
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

        int getNodeCount(){
            int nodeCount = 1;
            for (Node node:children) {
                if(node != null){
                    nodeCount += node.getNodeCount();
                }
            }
            return nodeCount;
        }

        int getWordCount(){
            int wordCount = (count == 0) ? 0 : 1;
            for(Node node : children){
                wordCount += node.getWordCount();
            }
            return wordCount;
        }

        int charToIndex(char value){
            return (int)value - (int)'a';
        }

        private int count = 0;

        private Node[] children = new Node[26];
    }
}
