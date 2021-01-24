package demo.todolist.helper.solutions;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleSearchScraper {

    private static final String url = "https://www.google.com/search";
    private final WebClient webClient;

    @Autowired
    public GoogleSearchScraper(WebClient webClient) {
        this.webClient = webClient;
        this.webClient.getOptions().setCssEnabled(false);
        this.webClient.getOptions().setThrowExceptionOnScriptError(false);
    }

    public List<String> scrapTopTwoResultsFromSearch(String search) throws IOException {
        HtmlPage page = webClient.getPage(url + "?q=" + search);
        DomElement resultsContainer = page.getElementById("search");
        List<HtmlAnchor> items = resultsContainer.getByXPath(".//a[starts-with(@href, 'http') and not(contains(@href, 'google.com'))]") ;
        return items.stream().map(HtmlAnchor::getHrefAttribute).distinct().limit(2).collect(Collectors.toList());
    }

}
