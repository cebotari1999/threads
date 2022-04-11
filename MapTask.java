import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;


public class MapTask extends RecursiveTask<MapResult> {
    private String fileName;
    private Integer offset;
    private Integer dimension;

    public MapTask(String fileName,
                   Integer offset,
                   Integer dimension) {
        this.fileName = fileName;
        this.offset = offset;
        this.dimension = dimension;
    }

    private void readText(char[] text) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        br.skip(offset);
        br.read(text, 0, dimension);
        br.close();
    }

    private void readPrevious(char previousChar[]) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        if (offset > 0) {
            br.skip(offset - 1);
            br.read(previousChar, 0, 1);

        }
        br.close();
    }

    private void lastWord(Map<Integer, Integer> dictionary, Integer count,
                          String longestWord, String lastWord) throws IOException {
        Integer i = 0, appearance = 0;
        char charLine[];
        String stringLine;
        BufferedReader br =  new BufferedReader(new FileReader(fileName));

        br.skip(offset + dimension);
        stringLine = br.readLine();
        br.close();

        if (stringLine == null) {
            return;
        }

        charLine = stringLine.toCharArray();

        while (i < stringLine.length() && (Character.isLetter(charLine[i])
                                        || Character.isDigit(charLine[i]))) {
            lastWord += String.valueOf(charLine[i]);
            count++;
            i++;

        }

        if (count >  longestWord.length()) {
            longestWord = lastWord;
        }


        if(dictionary.containsKey(count)) {
            appearance = dictionary.get(count);
        }

        dictionary.put(count, appearance + 1);
    }


    @Override
    protected MapResult compute() {
        char[] text = new char[dimension];
        char[] previousChar = new char[1];
        Integer count = 0, start = 0, appearance = 0;
        String longestWord = "", lastWord = "";
        Map<Integer, Integer> dictionary = new HashMap<>();

        try {
            readText(text);
            readPrevious(previousChar);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (previousChar != null && (Character.isLetter(previousChar[0])
                                    || Character.isDigit(previousChar[0]))) {
            while (start < dimension && (Character.isLetter(text[start])
                                     || Character.isDigit(text[start]))) {
                start++;
            }
        }


        for (int i = start; i < dimension; i++) {
            if (Character.isLetter(text[i]) || Character.isDigit(text[i])) {
                lastWord += String.valueOf(text[i]);
                count++;

                if (i == dimension - 1) {
                    try {
                        lastWord(dictionary, count, longestWord, lastWord);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (count > 0) {
                    appearance = 0;

                    if(dictionary.containsKey(count)) {
                        appearance = dictionary.get(count);
                    }

                    dictionary.put(count, appearance + 1);

                    if (count >  longestWord.length()) {
                        longestWord = lastWord;
                    }
                }

                count = 0;
                lastWord = "";
            }
        }

        return new MapResult(fileName, dictionary, longestWord);
    }
}
