package it.marcodemartino.hangmanbot.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepository {

  private final Logger logger = LogManager.getLogger(FileRepository.class);

  public String readFile(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      logger.error("Could not read file: {}", path.getFileName(), e);
      return "";
    }
  }
}
