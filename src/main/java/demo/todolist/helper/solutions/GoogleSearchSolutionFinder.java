package demo.todolist.helper.solutions;

import demo.todolist.helper.tasks.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleSearchSolutionFinder implements SolutionFinder {

    private final GoogleSearchScraper googleSearchScraper;

    @Autowired
    public GoogleSearchSolutionFinder(GoogleSearchScraper googleSearchScraper) {
        this.googleSearchScraper = googleSearchScraper;
    }

    public List<Solution> getSolutionsFor(TaskDto task) throws IOException {
        List<String> urls = googleSearchScraper.scrapTopTwoResultsFromSearch("How to " + task.title);
        return urls.stream().map(url -> new Solution(url, task)).collect(Collectors.toList());
    }
}
