package org.vkomlev.hierarchy.tree;

import org.apache.commons.lang3.StringUtils;
import org.vkomlev.hierarchy.exeption.DataValidationException;
import org.vkomlev.hierarchy.exeption.HierarchyException;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class NodeTree {

  private Node rootNode;

  private Map<String, Node> nodeMap = new LinkedHashMap<>();

  private <T> Predicate<T> not(Predicate<T> predicate) {
    return predicate.negate();
  }

  public void addNode(String id, String parentId, String name) {
    Optional.ofNullable(id).filter(value -> !StringUtils.isEmpty(value))
        .orElseThrow(() -> new DataValidationException("ID cannot be NULL"));
    Optional.ofNullable(name).filter(value -> !StringUtils.isEmpty(value))
        .orElseThrow(() -> new DataValidationException("NAME cannot be NULL"));
    if (nodeMap.containsKey(id)) {
      throw new HierarchyException("Already contains node with id " + id);
    }
    if (StringUtils.isEmpty(parentId) && rootNode != null) {
      throw new HierarchyException("There can be only one Root Node");
    }
    if (StringUtils.isEmpty(parentId)) {
      rootNode = new Node(id, name, null);
      nodeMap.put(rootNode.getId(), rootNode);
    } else {
      doNodeInsert(id, parentId, name);
    }
  }

  private void doNodeInsert(String id, String parentId, String name) {
    Node parent = Optional.ofNullable(nodeMap.get(parentId))
        .orElseThrow(() -> new HierarchyException("No parent node with ID " + parentId));

    Optional.ofNullable(parent.getChildren().get(name)).map(n -> {
      throw new HierarchyException("Siblings cannot have the same name");
    });
    Node newNode = new Node(id, name, parent);
    parent.getChildren().put(name, newNode);
    nodeMap.put(id, newNode);
  }

  public void deleteNode(String id) {
    Optional.ofNullable(id).orElseThrow(() -> new DataValidationException("ID cannot be NULL"));
    Node nodeToDelete = Optional.ofNullable(nodeMap.get(id))
        .orElseThrow(() -> new HierarchyException("No node with ID " + id));
    if (nodeToDelete.getChildren().isEmpty()) {
      Optional.ofNullable(nodeToDelete.getParent())
          .ifPresent(parent -> parent.getChildren().remove(nodeToDelete.getName()));
      nodeMap.remove(nodeToDelete.getId());
      if (nodeToDelete.equals(rootNode)) {
        rootNode = null;
      }
    } else {
      throw new HierarchyException("Cannot delete node with children");
    }
  }

  public void moveNode(String id, String newParentId) {
    Optional.ofNullable(id).orElseThrow(() -> new DataValidationException("ID cannot be NULL"));
    Optional.ofNullable(newParentId)
        .orElseThrow(() -> new DataValidationException("New ParentId cannot be NULL"));
    Node nodeToMove = Optional.ofNullable(nodeMap.get(id))
        .orElseThrow(() -> new HierarchyException("No node with ID " + id));
    Node newParentNode = Optional.ofNullable(nodeMap.get(newParentId))
        .orElseThrow(() -> new HierarchyException("No node with ID " + newParentId));
    Optional.ofNullable(newParentNode.getChildren().get(nodeToMove.getName())).map(n -> {
      throw new HierarchyException("Siblings cannot have the same name");
    });
    if (nodeToMove.equals(rootNode)) {
      throw new HierarchyException("You cannot move ROOT node");
    }
    doNodeMove(nodeToMove, newParentNode);
  }

  private void doNodeMove(Node nodeToMove, Node newParentNode) {
    Node oldParent = nodeToMove.getParent();

    newParentNode.getChildren()
        .put(nodeToMove.getName(), oldParent.getChildren().remove(nodeToMove.getName()));
    nodeToMove.setParent(newParentNode);

    try {
      testForCycle();
    } catch (HierarchyException e) {
      // Rolling back
      oldParent.getChildren()
          .put(nodeToMove.getName(), newParentNode.getChildren().remove(nodeToMove.getName()));
      nodeToMove.setParent(oldParent);
      throw e;
    }
  }

  private void testForCycle() {
    LinkedList<String> whiteSet = new LinkedList<>(nodeMap.keySet());
    Set<String> greySet = new LinkedHashSet<>();
    Set<String> blackSet = new LinkedHashSet<>();
    Map<String, String> dfsMap = new LinkedHashMap<>();
    while (!whiteSet.isEmpty()) {
      String nodeId = whiteSet.getFirst();
      recursiveCycleCheck(nodeId, whiteSet, greySet, blackSet, dfsMap);
    }
  }

  private void recursiveCycleCheck(String id, List<String> whiteSet, Set<String> greySet,
      Set<String> blackSet, Map<String, String> dfsMap) {
    if (greySet.contains(id)) {
      throw new HierarchyException("Cycle Detected \n" + dfsMap);
    }
    if (blackSet.contains(id)) {
      return;
    }
    whiteSet.remove(id);
    greySet.add(id);
    Node node = nodeMap.get(id);
    node.getChildren().forEach(
        (nodeName, childNode) -> recursiveCycleCheck(childNode.getId(), whiteSet, greySet, blackSet,
            dfsMap));
    greySet.remove(id);
    blackSet.add(id);
  }

  public Set<Node> searchNodes(Set<String> ids, Set<String> parentIds, Set<String> names,
      Integer minDepth, Integer maxDepth) {
    Set<Node> nodes = new LinkedHashSet<>();
    if (Optional.ofNullable(parentIds).filter(not(Set::isEmpty)).isPresent()) {
      parentIds.forEach(
          parentId -> doRecursiveSearch(parentId, nodes, Optional.ofNullable(minDepth),
              Optional.ofNullable(maxDepth), Optional.ofNullable(names), Optional.ofNullable(ids),
              0));
    } else {
      doRecursiveSearch(rootNode.getId(), nodes, Optional.ofNullable(minDepth),
          Optional.ofNullable(maxDepth), Optional.ofNullable(names), Optional.ofNullable(ids), 0);
    }
    return nodes;
  }

  private void doRecursiveSearch(String nodeId, Set<Node> nodes, Optional<Integer> minDepth,
      Optional<Integer> maxDepth, Optional<Set<String>> names, Optional<Set<String>> ids,
      Integer currentDepth) {

    Optional<Node> node = Optional.ofNullable(nodeMap.get(nodeId));
    node.filter(
        n -> names.filter(not(Set::isEmpty)).map(set -> set.contains(n.getName())).orElse(true))
        .filter(n -> ids.filter(not(Set::isEmpty)).map(set -> set.contains(n.getId())).orElse(true))
        .filter(n -> currentDepth.compareTo(minDepth.orElse(0)) >= 0).ifPresent(nodes::add);
    node.ifPresent(n -> {
      if (maxDepth.map(value -> value.compareTo(currentDepth) > 0).orElse(true)) {

        Optional.ofNullable(n.getChildren()).filter(not(Map::isEmpty)).map(Map::entrySet)
            .map(Collection::stream).ifPresent(
            entryStream -> entryStream.map(Map.Entry::getValue).map(Node::getId).forEach(
                childNodeId -> doRecursiveSearch(childNodeId, nodes, minDepth, maxDepth, names, ids,
                    currentDepth + 1)));
      }
    });
  }
}
