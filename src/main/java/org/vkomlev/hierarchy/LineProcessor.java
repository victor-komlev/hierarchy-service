package org.vkomlev.hierarchy;

import lombok.extern.slf4j.Slf4j;
import org.vkomlev.hierarchy.command.Command;

import java.io.IOException;

@Slf4j
public class LineProcessor {

  private AppContext appContext;

  public LineProcessor(AppContext appContext) {
    this.appContext = appContext;
  }

  public String processLine(String line) throws IOException {
    log.debug("Received Line For processing: {}", line);
    Command command = appContext.getObjectMapper().readValue(line, Command.class);
    //    log.debug("Got Command : {}", command);
    Object result = command.execute(appContext);
    return appContext.getObjectMapper().writeValueAsString(result);
  }
}
