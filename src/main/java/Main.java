import lombok.SneakyThrows;

import java.io.FileWriter;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    static final String URL = "https://********";   // your URL address

    private static StringBuilder sb = new StringBuilder();
    static private final String pathForWrite = "**********";  // your path for file
    static private final String fileName = "treeSite.txt";

    @SneakyThrows
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        LinkSelection example = new LinkSelection(URL);
        ConcurrentSkipListSet<String> stringSet = forkJoinPool.invoke(example);
        treeWriter(stringSet);
        FileWriter writer = new FileWriter(pathForWrite + fileName);
        writer.write(sb.toString());
        writer.close();
    }

    static void treeWriter(ConcurrentSkipListSet<String> stringSet) {
        stringSet.forEach(s -> {
            String tab = "\t";
            String subString = s.substring(URL.length(), s.length());
            String[] urlArray = subString.split("/");
            for (int i = 0; i < (urlArray.length - 1); i++) {
                sb.append(tab);
            }
            sb.append(s).append("\n");
        });
    }
}