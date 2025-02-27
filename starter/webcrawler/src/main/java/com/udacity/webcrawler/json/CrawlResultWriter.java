package com.udacity.webcrawler.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Utility class to write a {@link CrawlResult} to file.
 */
public final class CrawlResultWriter {
  private final CrawlResult result;

  /**
   * Creates a new {@link CrawlResultWriter} that will write the given {@link CrawlResult}.
   */
  public CrawlResultWriter(CrawlResult result) {
    this.result = Objects.requireNonNull(result);
  }

  /**
   * Formats the {@link CrawlResult} as JSON and writes it to the given {@link Path}.
   *
   * <p>If a file already exists at the path, the existing file should not be deleted; new data
   * should be appended to it.
   *
   * @param path the file path where the crawl result data should be written.
   */
  public void write(Path path) {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(path);
    // TODO: Fill in this method.
    try (Writer writer = Files.newBufferedWriter(path)) {
      write(writer);
      writer.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Formats the {@link CrawlResult} as JSON and writes it to the given {@link Writer}.
   *
   * @param writer the destination where the crawl result data should be written.
   */
  public void write(Writer writer) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    try {
      String s = objectMapper.writeValueAsString(result);
      writer.write(s);
      writer.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
//        ALTERNATIVE IMPLEMENTATION
    //        ObjectMapper objectMapper = new ObjectMapper();
    //    objectMapper.registerModule(new JavaTimeModule());
    //    BufferedWriter br = new BufferedWriter(writer);
    //    objectMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
    //    try {
    //      objectMapper.writeValue(br, result);
    //    } catch (IOException e) {
    //      throw new RuntimeException(e);
    //    }
  }
}
