package com.ssslinppp.graph.directd;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 */
@Data
@NoArgsConstructor
public class NodeTask {
    private String id;
    private Set<String> dependences = Sets.newConcurrentHashSet();  //依赖的taskID

    public NodeTask(String id) {
        this.id = id;
    }

    public NodeTask addDependence(String nodeTaskId) {
        this.dependences.add(nodeTaskId);
        return this;
    }
}
