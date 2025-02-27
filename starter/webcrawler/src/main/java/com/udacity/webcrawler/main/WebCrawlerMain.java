package com.udacity.webcrawler.main;

import com.google.inject.Guice;
import com.udacity.webcrawler.WebCrawler;
import com.udacity.webcrawler.WebCrawlerModule;
import com.udacity.webcrawler.json.ConfigurationLoader;
import com.udacity.webcrawler.json.CrawlResult;
import com.udacity.webcrawler.json.CrawlResultWriter;
import com.udacity.webcrawler.json.CrawlerConfiguration;
import com.udacity.webcrawler.profiler.Profiler;
import com.udacity.webcrawler.profiler.ProfilerModule;

import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public final class WebCrawlerMain {

  private final CrawlerConfiguration config;

  private WebCrawlerMain(CrawlerConfiguration config) {
    this.config = Objects.requireNonNull(config);
  }

  @Inject
  private WebCrawler crawler;

  @Inject
  private Profiler profiler;

  private void run() throws Exception {
    Guice.createInjector(new WebCrawlerModule(config), new ProfilerModule()).injectMembers(this);

    List<String> startPages = config.getStartPages();
    CrawlResult result = crawler.crawl(startPages);
    CrawlResultWriter resultWriter = new CrawlResultWriter(result);
    // 1 TODO: Write the crawl results to a JSON file (or System.out if the file name is empty)
    // 2 TODO: Write the profile data to a text file (or System.out if the file name is empty)
    //1
    String resultPath = config.getResultPath();
    if (!resultPath.isEmpty()) {
      Path path = Paths.get(resultPath);
      resultWriter.write(path);
    } else {
      resultWriter.write(new OutputStreamWriter(System.out));
    }
    //2
    String profileOutputPath = config.getProfileOutputPath();
    if (!profileOutputPath.isEmpty()) {
      Path path = Paths.get(profileOutputPath);
      profiler.writeData(path);
    } else {
      profiler.writeData(new OutputStreamWriter(System.out));
    }
  }

  public static void main(String[] args) throws Exception {
//    if (args.length != 1) {
//      System.out.println("Usage: WebCrawlerMain [starting-url]");
//      return;
//    }

    Path path = Paths.get(args[0]);
    ConfigurationLoader configurationLoader = new ConfigurationLoader(path);
    CrawlerConfiguration config = configurationLoader.load();
    new WebCrawlerMain(config).run();
  }
}
