package org.vkomlev.hierarchy.response;

import lombok.Data;
import org.vkomlev.hierarchy.tree.Node;

import java.util.Set;

@Data
public class NodesResponse implements HierarchyResponse {
  private Set<Node> nodes;
}
