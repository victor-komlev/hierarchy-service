package org.vkomlev.hierarchy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.vkomlev.hierarchy.tree.NodeTree;

@Getter
public class AppContext {
  private final ObjectMapper objectMapper;
  private final NodeTree nodeTree;

  public AppContext(ObjectMapper objectMapper, NodeTree nodeTree) {
    this.objectMapper = objectMapper;
    this.nodeTree = nodeTree;
  }
}
