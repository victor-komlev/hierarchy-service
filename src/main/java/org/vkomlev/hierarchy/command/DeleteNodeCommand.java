package org.vkomlev.hierarchy.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vkomlev.hierarchy.AppContext;
import org.vkomlev.hierarchy.response.StatusResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteNodeCommand extends HierarchyModificationCommand
    implements Command<StatusResponse> {
  private String id;

  @Override
  public void doExecute(AppContext appContext) {
    appContext.getNodeTree().deleteNode(this.id);
  }
}
