package org.vkomlev.hierarchy.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.vkomlev.hierarchy.AppContext;
import org.vkomlev.hierarchy.response.HierarchyResponse;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({@JsonSubTypes.Type(value = AddNodeCommand.class, name = "add_node"),
                  @JsonSubTypes.Type(value = DeleteNodeCommand.class, name = "delete_node"),
                  @JsonSubTypes.Type(value = MoveNodeCommand.class, name = "move_node"),
                  @JsonSubTypes.Type(value = QueryCommand.class, name = "query")})
public interface Command<T extends HierarchyResponse> {

  T execute(AppContext appContext);

}
