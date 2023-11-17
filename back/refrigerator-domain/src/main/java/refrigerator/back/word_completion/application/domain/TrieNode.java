package refrigerator.back.word_completion.application.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TrieNode {

    private final Character key;
    private String data;
    private final Map<Character, TrieNode> children = new HashMap<>();

    public TrieNode(Character key) {
        this.key = key;
        this.data = null;
    }

    public void setData(String data) {
        this.data = data;
    }

}
