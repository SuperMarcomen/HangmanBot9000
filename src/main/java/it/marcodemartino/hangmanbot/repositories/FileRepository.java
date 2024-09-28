package it.marcodemartino.hangmanbot.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * Reads files from disk.
 */
@Repository
public class FileRepository {

  private final Logger logger = LogManager.getLogger(FileRepository.class);

  /**
   * Reads the file at the given path.
   *
   * @param path the path of the file to be read
   * @return the content of the file
   */
  public String readFile(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      logger.error("Could not read file: {}", path.getFileName(), e);
      return "";
    }
  }
}
