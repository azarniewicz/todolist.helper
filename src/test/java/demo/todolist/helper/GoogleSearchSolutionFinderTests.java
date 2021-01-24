package demo.todolist.helper;

import demo.todolist.helper.solutions.GoogleSearchSolutionFinder;
import demo.todolist.helper.solutions.Solution;
import demo.todolist.helper.tasks.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GoogleSearchSolutionFinderTests extends TodolistHelperApplicationTests {

    @Autowired
    private GoogleSearchSolutionFinder googleSearchSolutionFinder;

    @Test
    public void returns_two_links_from_google_search() throws IOException {
        Task task = new Task("Get results from google search in java");
        List<Solution> solutions = googleSearchSolutionFinder.getSolutionsFor(task.toDto());
        assertEquals(2, solutions.size());
        for (Solution solution: solutions) {
            String url = solution.getUrl();
            assertNotNull(url);
            assertThat(url, containsString("http"));
        }
    }
}
