package it.marcodemartino.hangmanbot.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepository {

  public String readFile(String path) {
    try {
      return Files.readString(Path.of(path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
