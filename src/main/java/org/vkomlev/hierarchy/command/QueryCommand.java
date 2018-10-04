package org.vkomlev.hierarchy.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.vkomlev.hierarchy.AppContext;
import org.vkomlev.hierarchy.response.NodesResponse;

import java.util.Set;

@Data
public class QueryCommand implements Command<NodesResponse> {

  @JsonProperty("min_depth")
  private Integer minDepth;
  @JsonProperty("max_depth")
  private Integer maxDepth;
  @JsonProperty("names")
  private Set<String> names;
  @JsonProperty("ids")
  private Set<String> ids;
  @JsonProperty("root_ids")
  private Set<String> rootIds;

  @Override
  public NodesResponse execute(AppContext appContext) {
    NodesResponse response = new NodesResponse();
    response.setNodes(appContext.getNodeTree().searchNodes(ids, rootIds, names, minDepth, maxDepth));
    return response;
  }
}
