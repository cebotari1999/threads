import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Tema2 {
    public static void main(String[] args) throws IOException {
        Integer dimension, numberOfFiles;
        ArrayList<String> files = new ArrayList<>();
        ForkJoinPool fjp = new ForkJoinPool(Integer.parseInt(args[0]));
        File input;
        BufferedReader bufferedReader;

        if (args.length < 3) {
            System.err.println("Usage: Tema2 <workers> <in_file> <out_file>");
            return;
        }

        input = new File(args[1]);
        bufferedReader = new BufferedReader(new FileReader(input));
        dimension = Integer.parseInt(bufferedReader.readLine());
        numberOfFiles = Integer.parseInt(bufferedReader.readLine());

        for (int i = 0; i < numberOfFiles; i++) {
            files.add(bufferedReader.readLine());
        }

        bufferedReader.close();

        fjp.invoke(new Task(files, dimension, args[2]));
        fjp.shutdown();
    }
}
