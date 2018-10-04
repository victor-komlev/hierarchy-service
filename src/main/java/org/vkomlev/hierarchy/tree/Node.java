package org.vkomlev.hierarchy.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Data
@EqualsAndHashCode(exclude = {"parent", "children"})
@ToString(exclude = {"parent", "children"})
public class Node {
  private String id;
  private String name;
  @JsonIgnore
  private Node parent;
  @JsonIgnore
  private Map<String, Node> children = new TreeMap<>();

  public Node(String id, String name, Node parent) {
    this.id = id;
    this.name = name;
    this.parent = parent;
  }

  @JsonProperty("parent_id")
  public String getParentId() {
    return Optional.ofNullable(parent).map(Node::getId).orElse(null);
  }
}
