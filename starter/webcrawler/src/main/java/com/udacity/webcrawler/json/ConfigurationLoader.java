package com.udacity.webcrawler.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() {
    // TODO: Fill in this method.
    try(Reader reader = Files.newBufferedReader(path)) {
      return read(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    //    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.registerModule(new JavaTimeModule());
//    CrawlerConfiguration crawlerConfiguration = null;
//    try {
//      List<String> strings = Files.readAllLines(path);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//
//    try(BufferedReader srcPath = Files.newBufferedReader(path)) {
//      crawlerConfiguration = objectMapper.readValue(srcPath, CrawlerConfiguration.class);
//      System.out.println(crawlerConfiguration);
//    } catch (IOException ex) {
//      ex.printStackTrace();
//    }
//    return crawlerConfiguration;
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(reader);
    // TODO: Fill in this method
    CrawlerConfiguration crawlerConfiguration;
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
      crawlerConfiguration = objectMapper.readValue(reader, CrawlerConfiguration.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return crawlerConfiguration;
  }
}
