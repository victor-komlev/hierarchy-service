package org.vkomlev.hierarchy.command;

import lombok.extern.slf4j.Slf4j;
import org.vkomlev.hierarchy.AppContext;
import org.vkomlev.hierarchy.response.StatusResponse;

@Slf4j
public abstract class HierarchyModificationCommand {

  public StatusResponse execute(AppContext appContext) {
    try {
      doExecute(appContext);
      return new StatusResponse() {{
        setOk(true);
      }};
    } catch (Exception e) {
      log.error("Error Execution Command {} ", this.getClass(), e);
      return new StatusResponse() {{
        setOk(false);
      }};
    }
  }

  protected abstract void doExecute(AppContext appContext);
}
