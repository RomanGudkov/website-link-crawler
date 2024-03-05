import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;
@Getter
public class Parser {
    private ConcurrentSkipListSet<String> childUrlList;
    private String parentUrl;

    public Parser(String url) {
        this.parentUrl = url;
        childUrlList = new ConcurrentSkipListSet<>();
        connectionSetup(parentUrl);
    }

    protected void connectionSetup(String url) {
        try {
            Thread.sleep(250);
            Document document = null;
            document = Jsoup.connect(url)
                    .userAgent("Chrome/121.0.6167.185")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .get();
            extractLinks(document);
        } catch (InterruptedException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void extractLinks(Document document) {
        String regexLink = "^[\\/]{1}.+[\\/]$";
        String regexOutGrille = "[#]$";
        Elements elements = document.select("a[href]");
        for (Element element : elements
        ) {
            String linkElement = element.attr("href");
            if (!linkElement.matches(regexLink) || linkElement.matches(regexOutGrille)) {
                continue;
            }
            String linkAdd = element.attr("abs:href");
            childUrlList.add(linkAdd);
        }
    }
}