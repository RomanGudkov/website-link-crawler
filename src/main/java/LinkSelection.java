import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveTask;

public class LinkSelection extends RecursiveTask<ConcurrentSkipListSet<String>> {
    private Parser parserClass;
    private String pagesUrl;
    private ConcurrentSkipListSet<String> childList;
    private static ConcurrentSkipListSet<String> urlAverageList = new ConcurrentSkipListSet<>();

    public LinkSelection(String pagesAddress) {
        pagesUrl = pagesAddress;
        parserClass = new Parser(pagesUrl);
    }

    @Override
    protected ConcurrentSkipListSet<String> compute() {
        urlAverageList.add(pagesUrl);
        childList = parserClass.getChildUrlList();
        List<LinkSelection> taskList = new ArrayList<>();
        childList.forEach(child -> {
            if (urlAverageList.contains(child)) return;
            LinkSelection task = new LinkSelection(child);
            task.fork();
            taskList.add(task);
        });
        taskList.forEach(t -> {
            t.join();
        });
        return urlAverageList;
    }
}
