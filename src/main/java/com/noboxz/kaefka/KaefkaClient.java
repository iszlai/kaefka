package com.noboxz.kaefka;

public interface KaefkaClient<K,V> {
     K get(V value);
}
