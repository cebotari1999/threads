import java.util.Comparator;

public class ReduceResult implements Comparator<ReduceResult> {
    private Float rang;
    private String fileName;
    private Integer longestWord;
    private Integer appearance;

    public ReduceResult() {
    }

    public ReduceResult(Float rang,
                        String fileName,
                        Integer longestWord,
                        Integer apperance) {
        this.rang = rang;
        this.fileName = fileName;
        this.longestWord = longestWord;
        this.appearance = apperance;
    }

    public Float getRang() {
        return rang;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getLongestWord() {
        return longestWord;
    }

    public Integer getAppearance() {
        return appearance;
    }

    @Override
    public int compare(ReduceResult o1, ReduceResult o2) {
        return Float.compare(o2.getRang(), o1.getRang());
    }
}
