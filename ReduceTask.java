import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class ReduceTask extends RecursiveTask<ReduceResult> {
    private String fileName;
    private ArrayList<Map<Integer, Integer>> listOfDictionaries;
    private List<String > longestWords;

    public ReduceTask(String fileName,
                      ArrayList<Map<Integer, Integer>> listOfDictionaries,
                      List<String> longestWords) {
        this.fileName = fileName;
        this.listOfDictionaries = listOfDictionaries;
        this.longestWords = longestWords;
    }

    protected void FibonacciListInit(ArrayList<Integer> fibonacci) {
        fibonacci.add(0);
        fibonacci.add(1);
    }

    protected void FibonacciFindElem(ArrayList<Integer> fibonacci, Integer pos) {
        if (fibonacci.size() < pos) {
            for (int i = fibonacci.size(); i <= pos; i++) {
                fibonacci.add(fibonacci.get(i - 1) + fibonacci.get(i - 2));
            }
        }
    }

    public void computeDictionary(Map<Integer, Integer> dictionary) {
        Integer appearance;

        for (int i = 0; i < listOfDictionaries.size(); i++) {
            for (Integer key : listOfDictionaries.get(i).keySet()) {
                appearance  = 0;

                if (dictionary.containsKey(key)) {
                    appearance = dictionary.get(key);
                }

                dictionary.put(key, appearance + listOfDictionaries.get(i).get(key));
            }
        }
    }


    @Override
    protected ReduceResult compute() {
        Float rang = 0f;
        Integer allWords = 0, longestWord = 0, longestWordAppearance = 0;
        Map<Integer, Integer> dictionary = new HashMap<>();
        ArrayList<Integer> fibonacci = new ArrayList<>();

        FibonacciListInit(fibonacci);
        computeDictionary(dictionary);

        for (Integer key : dictionary.keySet()) {
            if (key > longestWord) {
                longestWord = key;
                longestWordAppearance = dictionary.get(key);
            }

            allWords += dictionary.get(key);
            FibonacciFindElem(fibonacci, key + 2);
            rang += fibonacci.get(key + 1) * dictionary.get(key);
        }

        rang = rang / allWords;

        char[] charName = fileName.toCharArray();
        String stringName = "";

        for (int i = 12; i < charName.length; i++) {
            stringName += charName[i];
        }



        return new ReduceResult(rang, stringName, longestWord, longestWordAppearance);
    }
}
