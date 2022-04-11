import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class Task extends RecursiveTask<Void> {
    private ArrayList<String> fileNames;
    private Integer dimension;
    private String outFile;

    public Task(ArrayList<String> fileNames,
                Integer dimension,
                String outFile) {
        this.fileNames = fileNames;
        this.dimension = dimension;
        this.outFile = outFile;
    }

    protected void writeString(List<ReduceResult> reduceResults) throws IOException {
        FileWriter writer = new FileWriter(outFile);


        Collections.sort(reduceResults, new ReduceResult());

        for (int i = 0; i < reduceResults.size(); i++) {
            writer.write(reduceResults.get(i).getFileName() + ","
                    + String.format("%.2f", reduceResults.get(i).getRang()) + ","
                    + reduceResults.get(i).getLongestWord() + ","
                    + reduceResults.get(i).getAppearance() + "\n");
        }

        writer.close();
    }

    protected void createMapTasks(List<MapTask> mapTasks) throws IOException {
        Integer offset = 0;
        Long size;

        for (String fileName : fileNames) {
            size = Files.size(Path.of(fileName)) + 1;
            offset = 0;

            while (offset + dimension < size) {
                MapTask mapTask = new MapTask(fileName, offset, dimension);
                mapTasks.add(mapTask);
                mapTask.fork();
                offset += dimension;
            }

            if (size - offset > 0) {
                MapTask mapTask = new MapTask(fileName, offset, (int) (size - offset));
                mapTasks.add(mapTask);
                mapTask.fork();
            }
        }
    }

    protected void createReduceTasks(List<ReduceTask> reduceTasks, List<MapResult> mapResults) {
        ArrayList<Map<Integer, Integer>> fileDictionaries;
        List<String> longestWords;

        for (int i = 0; i < fileNames.size(); i++) {
            fileDictionaries = new ArrayList<>();
            longestWords = new ArrayList<>();

            for (int j = 0; j < mapResults.size(); j++) {
                if (mapResults.get(j).getFileName() == fileNames.get(i)) {
                    fileDictionaries.add(mapResults.get(j).getDictionary());
                    longestWords.add(mapResults.get(j).getLongestWord());

                }
            }

            ReduceTask reduceTask = new ReduceTask(fileNames.get(i), fileDictionaries, longestWords);
            reduceTasks.add(reduceTask);
            reduceTask.fork();
        }

    }

    @Override
    protected Void compute() {
        List<MapTask> mapTasks = new ArrayList<>();
        List<ReduceTask> reduceTasks = new ArrayList<>();
        List<MapResult> mapResults = new ArrayList<>();
        List<ReduceResult> reduceResults = new ArrayList<>();

        try {
            createMapTasks(mapTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (MapTask task : mapTasks) {
            mapResults.add(task.join());
        }

        createReduceTasks(reduceTasks, mapResults);

        for (ReduceTask task : reduceTasks) {
            reduceResults.add(task.join());
        }

        try {
            writeString(reduceResults);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
