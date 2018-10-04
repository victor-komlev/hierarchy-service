package org.vkomlev.hierarchy.response;

import lombok.Data;

@Data
public class StatusResponse implements HierarchyResponse {
  private Boolean ok;
}
