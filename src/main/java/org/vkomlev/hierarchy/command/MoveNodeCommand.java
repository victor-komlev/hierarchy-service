package org.vkomlev.hierarchy.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vkomlev.hierarchy.AppContext;
import org.vkomlev.hierarchy.response.StatusResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class MoveNodeCommand extends HierarchyModificationCommand
    implements Command<StatusResponse> {

  private String id;
  @JsonProperty("new_parent_id")
  private String newParentId;

  @Override
  public void doExecute(AppContext appContext) {
    appContext.getNodeTree().moveNode(id, newParentId);
  }
}
