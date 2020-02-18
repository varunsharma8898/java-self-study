package com.varun.selfstudy.datastructures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrieTest {

    private Trie dictionary;

    @Before
    public void setUp() {
        dictionary = new Trie();
        String[] values = new String[] { "the", "their", "there", "this", "hello" };
        for (String key : values) {
            dictionary.insert(key);
        }
    }

    @Test
    public void testSearch() {
        Assert.assertTrue(dictionary.search("there"));
        Assert.assertTrue(dictionary.search("this"));
        Assert.assertTrue(dictionary.search("their"));
        Assert.assertTrue(dictionary.search("hello"));
        Assert.assertFalse(dictionary.search("theres"));
        Assert.assertFalse(dictionary.search("hi"));
        Assert.assertFalse(dictionary.search("nope"));
    }
}
