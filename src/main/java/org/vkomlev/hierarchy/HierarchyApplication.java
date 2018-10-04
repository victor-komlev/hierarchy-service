package org.vkomlev.hierarchy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.vkomlev.hierarchy.tree.NodeTree;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class HierarchyApplication {

  public static void main(String[] args) throws IOException {
    log.info("Hierarchy Application Starting ...");

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    NodeTree nodeTree = new NodeTree();
    AppContext appContext = new AppContext(objectMapper, nodeTree);
    LineProcessor lineProcessor = new LineProcessor(appContext);
    Scanner scanner = new Scanner(System.in);
    String line;
    while (scanner.hasNext() && (line = scanner.nextLine()) != null) {
      String response = lineProcessor.processLine(line);
      System.out.println(response);
    }

    log.info("Hierarchy Application Ending ...");

  }



}
