package org.vkomlev.hierarchy.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vkomlev.hierarchy.AppContext;
import org.vkomlev.hierarchy.response.StatusResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddNodeCommand extends HierarchyModificationCommand
    implements Command<StatusResponse> {
  private String id;
  private String name;
  @JsonProperty("parent_id")
  private String parentId;

  @Override
  protected void doExecute(AppContext appContext) {
    appContext.getNodeTree().addNode(id, parentId, name);
  }
}
