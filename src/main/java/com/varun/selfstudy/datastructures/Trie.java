package com.varun.selfstudy.datastructures;

public class Trie {

    private static final int ALPHABET_SIZE = 26;

    TrieNode root;

    Trie() {
        this.root = new TrieNode();
    }

    static class TrieNode {

        boolean isEndOfWord;

        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        TrieNode() {
            this.isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }
    }

    public void insert(String key) {
        TrieNode node = root;
        for (int level = 0; level < key.length(); level++) {
            int charIndex = key.charAt(level) - 'a';
            if (node.children[charIndex] == null) {
                node.children[charIndex] = new TrieNode();
            }
            node = node.children[charIndex];
        }
        node.isEndOfWord = true;
    }

    public boolean search(String key) {
        TrieNode node = root;
        for (int level = 0; level < key.length(); level++) {
            int charIndex = key.charAt(level) - 'a';
            if (node.children[charIndex] != null) {
                node = node.children[charIndex];
            } else {
                return false;
            }
        }
        return true;
    }

//    public static void main(String[] args) {
//
//        String[] dictionary = new String[] { "the", "their", "there", "this", "hello" };
//        Trie trie = new Trie();
//        for (String key : dictionary) {
//            trie.insert(key);
//        }
//
//        String[] searchItems = new String[] { "there", "thus", "hello" };
//        for (String key : searchItems) {
//            if (trie.search(key)) {
//                System.out.println(key + " is present in the dictionary");
//            }
//        }
//    }
}
