import java.util.Map;

public class MapResult {
    private String fileName;
    private Map<Integer, Integer> dictionary;
    private String longestWord;

    public MapResult(String fileName,
                     Map<Integer, Integer> dictionary,
                     String word) {
        this.fileName = fileName;
        this.dictionary = dictionary;
        this.longestWord = word;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<Integer, Integer> getDictionary() {
        return dictionary;
    }

    public String getLongestWord() {
        return longestWord;
    }
}
