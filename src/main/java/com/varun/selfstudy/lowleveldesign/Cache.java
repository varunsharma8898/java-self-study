package com.varun.selfstudy.lowleveldesign;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.junit.Assert;

public class Cache {

    public static void main(String[] args) {

        // Test LRU Cache - capacity 3
        MyCache<String, Integer> cache = CacheFactory.getCacheImpl(EvictionPolicy.LRU_POLICY, 3);
        cache.put("one", 1);
        cache.put("two", 2);
        cache.put("three", 3);
        Assert.assertEquals(java.util.Optional.of(1).get(), cache.get("one"));
        Assert.assertEquals(java.util.Optional.of(2).get(), cache.get("two"));
        cache.put("four", 4);
        Assert.assertNull(cache.get("three"));
        Assert.assertEquals(java.util.Optional.of(4).get(), cache.get("four"));

        // Test LFU Cache - capacity 2
        MyCache<String, Integer> lfuCache = CacheFactory.getCacheImpl(EvictionPolicy.LFU_POLICY, 2);
        lfuCache.put("one", 1);
        lfuCache.put("two", 2);
        Assert.assertEquals(java.util.Optional.of(1).get(), lfuCache.get("one"));
        lfuCache.put("three", 3);
        Assert.assertNull(lfuCache.get("two"));
        Assert.assertEquals(java.util.Optional.of(3).get(), lfuCache.get("three"));
        lfuCache.put("four", 4);
        Assert.assertNull(lfuCache.get("one"));
        Assert.assertEquals(java.util.Optional.of(3).get(), lfuCache.get("three"));
        Assert.assertEquals(java.util.Optional.of(4).get(), lfuCache.get("four"));
    }

    interface MyCache<K, V> {

        V get(K key);

        void put(K key, V value);
    }

    static class LRUCacheImpl<K, V> implements MyCache<K, V> {

        Map<K, Node> cacheMap;
        int capacity;
        Node head, tail;

        public LRUCacheImpl(int capacity) {
            this.capacity = capacity;
            cacheMap = new HashMap<>();
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        @Override
        public V get(K key) {

            if (cacheMap.containsKey(key)) {
                Node node = cacheMap.get(key);
                moveToFront(node);
                return (V) node.val;
            }
            return null;
        }

        @Override
        public void put(K key, V value) {
            if (cacheMap.containsKey(key)) {
                Node node = cacheMap.get(key);
                node.val = value;
                cacheMap.put(key, node);
                moveToFront(node);
                return;
            }

            if (cacheMap.size() == capacity) {
                Node evictNode = removeFromTail();
                cacheMap.remove(evictNode.key);
            }

            Node node = new Node(key, value);
            cacheMap.put(key, node);
            moveToFront(node);

        }

        private Node removeFromTail() {
            Node last = tail.prev;
            Node prev = last.prev;
            prev.next = tail;
            tail.prev = prev;
            return last;
        }

        private void moveToFront(Node node) {
            // remove from current position
            Node prev = node.prev;
            Node next = node.next;
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }

            // attach to head
            Node tmp = head.next;
            head.next = node;
            node.prev = head;
            node.next = tmp;
            tmp.prev = node;
        }

        class Node<T, U> {
            T key;
            U val;
            Node next, prev;
            Node() {}
            Node(T key, U val) {
                this.key = key;
                this.val = val;
                next = null;
                prev = null;
            }
        }
    }

    static class LFUCacheImpl<K, V> implements MyCache<K, V> {

        int min;
        int capacity;
        Map<K, V> cacheMap;
        Map<K, Integer> freqMap;
        Map<Integer, LinkedHashSet<K>> freqBucketList;

        public LFUCacheImpl(int capacity) {
            this.capacity = capacity;
            this.min = 1;
            cacheMap = new HashMap<>();
            freqMap = new HashMap<>();
            freqBucketList = new HashMap<>();
            freqBucketList.put(1, new LinkedHashSet<>());
        }

        @Override
        public V get(K key) {
            if (!cacheMap.containsKey(key)) return null;

            int freq = freqMap.get(key);
            freqBucketList.get(freq).remove(key);
            if (!freqBucketList.containsKey(freq + 1)) {
                freqBucketList.put(freq + 1, new LinkedHashSet<>());
            }
            freqBucketList.get(freq + 1).add(key);

            if (freq == min && freqBucketList.get(min).isEmpty()) {
                min++;
            }

            return cacheMap.get(key);
        }

        @Override
        public void put(K key, V value) {
            if (cacheMap.containsKey(key)) {
                cacheMap.put(key, value);
                get(key);
                return;
            }

            if (this.capacity == cacheMap.size()) {
                K evictItem = freqBucketList.get(min).iterator().next();
                cacheMap.remove(evictItem);
                freqMap.remove(evictItem);
                freqBucketList.get(min).remove(evictItem);
            }

            cacheMap.put(key, value);
            freqMap.put(key, 1);
            min = 1;
            freqBucketList.get(min).add(key);
        }
    }

    static class CacheFactory {

        static MyCache getCacheImpl(EvictionPolicy policy, int capacity) {
            switch (policy) {
                case LFU_POLICY:
                    return new LFUCacheImpl(capacity);
                case LRU_POLICY:
                    return new LRUCacheImpl(capacity);
            }
            return new LRUCacheImpl(capacity);
        }
    }

    enum EvictionPolicy {
        LRU_POLICY,
        LFU_POLICY
    }
}
