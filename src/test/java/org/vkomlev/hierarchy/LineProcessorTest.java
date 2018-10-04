package org.vkomlev.hierarchy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.vkomlev.hierarchy.tree.NodeTree;

import java.io.IOException;

@Slf4j
public class LineProcessorTest {

  private LineProcessor lineProcessor;

  private String lines =
      "{\"add_node\":{\"name\":\"tortulous-bilipurpurin\",\"id\":\"1\",\"parent_id\":\"0\"}} \n"
          + "{\"add_node\":{\"name\":\"raiiform-italicize\",\"id\":\"1\",\"parent_id\":\"\"}} \n"
          + "{\"add_node\":{\"name\":\"hookish-fustigate\",\"id\":\"2\",\"parent_id\":\"\"}} \n"
          + "{\"delete_node\":{\"id\":\"1\"}} \n"
          + "{\"add_node\":{\"name\":\"raiiform-italicize\",\"id\":\"1\",\"parent_id\":\"\"}} \n"
          + "{\"add_node\":{\"name\":\"mimetesite-tetravalent\",\"id\":\"2\",\"parent_id\":\"1\"}} \n"
          + "{\"add_node\":{\"name\":\"catholical-poliorcetics\",\"id\":\"3\",\"parent_id\":\"1\"}} \n"
          + "{\"add_node\":{\"name\":\"promissive-forecool\",\"id\":\"9\",\"parent_id\":\"1\"}} \n"
          + "{\"query\":{}} \n"
          + "{\"add_node\":{\"name\":\"catholical-poliorcetics\",\"id\":\"4\",\"parent_id\":\"1\"}} \n"
          + "{\"add_node\":{\"name\":\"catholical-poliorcetics\",\"id\":\"4\",\"parent_id\":\"2\"}} \n"
          + "{\"add_node\":{\"name\":\"onehearted-inexactitude\",\"id\":\"4\",\"parent_id\":\"3\"}} \n"
          + "{\"query\":{}} \n" + "{\"query\":{\"names\":[\"catholical-poliorcetics\"]}} \n"
          + "{\"delete_node\":{\"id\":\"2\"}} \n" + "{\"delete_node\":{\"id\":\"4\"}} \n"
          + "{\"query\":{}} \n"
          + "{\"add_node\":{\"name\":\"slanderfully-counselable\",\"id\":\"4\",\"parent_id\":\"2\"}} \n"
          + "{\"add_node\":{\"name\":\"inkindle-kittenish\",\"id\":\"5\",\"parent_id\":\"2\"}} \n"
          + "{\"add_node\":{\"name\":\"stockproof-quaternion\",\"id\":\"6\",\"parent_id\":\"3\"}} \n"
          + "{\"add_node\":{\"name\":\"foreleg-otherness\",\"id\":\"7\",\"parent_id\":\"3\"}} \n"
          + "{\"add_node\":{\"name\":\"coordinators-concepts\",\"id\":\"8\",\"parent_id\":\"7\"}} \n"
          + "{\"query\":{}} \n" + "{\"query\":{\"max_depth\":1}} \n"
          + "{\"query\":{\"min_depth\":2,\"max_depth\":3}} \n"
          + "{\"query\":{\"ids\":[\"4\",\"3\",\"5\"]}} \n"
          + "{\"query\":{\"min_depth\":1,\"root_ids\":[\"2\",\"3\"]}} \n"
          + "{\"query\":{\"names\":[\"raiiform-italicize\",\"inkindle-kittenish\",\"foreleg-otherness\"]}} \n"
          + "{\"query\":{\"names\":[\"partanhanded-doveweed\",\"kilovar-nonswimmer\"]}} \n"
          + "{\"query\":{\"root_ids\":[\"myrmotherine-sinuated\",\"westmost-picklock\"]}} \n"
          + "{\"move_node\":{\"id\":\"3\",\"new_parent_id\":\"8\"}} \n"
          + "{\"move_node\":{\"id\":\"3\",\"new_parent_id\":\"3\"}} \n"
          + "{\"move_node\":{\"id\":\"1\",\"new_parent_id\":\"2\"}} \n"
          + "{\"move_node\":{\"id\":\"3\",\"new_parent_id\":\"2\"}} \n"
          + "{\"move_node\":{\"id\":\"3\",\"new_parent_id\":\"42\"}} \n" + "{\"query\":{}} \n"
          + "{\"query\":{\"min_depth\":2}} \n"
          + "{\"query\":{\"names\":[\"foreleg-otherness\",\"stockproof-quaternion\",\"slanderfully-counselable\"],\"ids\":[\"6\",\"7\",\"9\"]}} \n"
          + "{\"add_node\":{\"name\":\"epiploic-ruffianlike\",\"id\":\"\",\"parent_id\":\"1\"}} \n";

  @Before
  public void initLineProcessor() {
    lineProcessor = new LineProcessor(new AppContext(new ObjectMapper(), new NodeTree()));
  }

  @Test
  public void testprocessLine() throws IOException {
    for (String line : StringUtils.split(lines, "\n")) {
      log.info(lineProcessor.processLine(line));
    }

  }
}
