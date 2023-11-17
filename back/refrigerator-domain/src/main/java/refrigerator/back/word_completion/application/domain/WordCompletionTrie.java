package refrigerator.back.word_completion.application.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class WordCompletionTrie {

    private WordParserUtilsImpl parser;
    private TrieNode head;

    public WordCompletionTrie() {
        this.parser = new WordParserUtilsImpl();
        this.head = new TrieNode(null);
    }

    public boolean isBlank(){
        return this.head.getKey() == null;
    }

    public void insert(String keyword){
        String recipeName = parser.split(keyword);
        TrieNode currentNode = this.head;
        for (char value : recipeName.toCharArray()) {
            if (!currentNode.getChildren().containsKey(value)){
                currentNode.getChildren().put(value, new TrieNode(value));
            }
            currentNode = currentNode.getChildren().get(value);
        }
        currentNode.setData(keyword);
    }

    public List<String> search(String keyword){
        TrieNode currentNode = this.head;
        List<String> answer = new ArrayList<>();

        String keyword_split = this.parser.split(keyword);

        for (char value : keyword_split.toCharArray()) {
            if (currentNode.getChildren().containsKey(value)){
                currentNode = currentNode.getChildren().get(value);
            }else{
                return answer;
            }
        }

        if (currentNode.getChildren().isEmpty()){
            answer.add(currentNode.getData());
            return answer;
        }

        Deque<TrieNode> queue = new ArrayDeque<>();
        queue.add(currentNode);

        while(!queue.isEmpty()){
            TrieNode node = queue.pollFirst();
            if (node.getChildren().isEmpty() || node.getData() != null){
                answer.add(node.getData());
            }
            queue.addAll(node.getChildren().values());
        }
        return answer;
    }
}
