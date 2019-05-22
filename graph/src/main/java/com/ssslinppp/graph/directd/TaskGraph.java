package com.ssslinppp.graph.directd;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 */
public class TaskGraph {
    private MutableGraph<NodeTask> taskGraph = GraphBuilder.directed().allowsSelfLoops(false).build();

    /**
     * 转换节点任务为Graph
     *
     * @param nodeTasks
     */
    public void parseNodeTasksToGraph(Map<String, NodeTask> nodeTasks) {
        for (NodeTask nodeTask : nodeTasks.values()) {
            if (!taskGraph.nodes().contains(nodeTask)) {
                taskGraph.addNode(nodeTask);
            }

            for (String dependence : nodeTask.getDependences()) {
                taskGraph.putEdge(nodeTask, nodeTasks.get(dependence));
            }
        }
    }

    /**
     * 判断是否为DAG(Directed Acyclic Graph）有向无环图
     * <p>
     * 算法思路：
     * <ul>
     * <li>1. 根据"拓扑排序"算法判断：拓扑排序之后，若还剩有点，则表示有环</li>
     * <li>2. 拓扑排序算法：找到图中所有入度为0的点，放入序列，删除这些点和以这些点为出度的边，再找所有入度为0的点，依次循环</li>
     * </ul>
     *
     * @return
     */
    public boolean isDAGraph() {
        Map<String, Integer> nodeInDegreeMap = Maps.newHashMap();
        Queue<NodeTask> queue = Queues.newArrayDeque();
        List<String> topologicalSortList = Lists.newArrayList(); //拓扑排序列表维护

        // 获取所有入度为0的节点
        for (NodeTask nodeTask : taskGraph.nodes()) {
            int indegree = taskGraph.inDegree(nodeTask);
            nodeInDegreeMap.put(nodeTask.getId(), indegree);
            if (indegree == 0) {
                queue.add(nodeTask);
                topologicalSortList.add(nodeTask.getId());
            }
        }

        while (!queue.isEmpty()) {
            NodeTask preNode = queue.poll(); //获取并删除

            for (NodeTask successorNode : taskGraph.successors(preNode)) {
                int indegree = nodeInDegreeMap.get(successorNode.getId());
                if (--indegree == 0) {//-1：等效删除父节点以及相应的边
                    queue.offer(successorNode); //insert
                    topologicalSortList.add(successorNode.getId());
                }
                nodeInDegreeMap.put(successorNode.getId(), indegree);
            }
        }

        System.out.println("拓扑排序（topologicalSortList）:" + topologicalSortList);

        if (topologicalSortList.size() != taskGraph.nodes().size()) {
            return false;
        }

        return true;
    }

    /**
     * 打印Graph中task的依赖关系
     */
    public void print() {
        System.out.println("=============NodeTask count: " + taskGraph.nodes().size());
        for (NodeTask nodeTask : taskGraph.nodes()) {
            System.out.println("-------- NodeTask: " + nodeTask.getId() + "--------");
            System.out.print("Dependent on: ");
            taskGraph.successors(nodeTask).forEach((v) -> System.out.print(v.getId() + ", "));
            System.out.println();
            System.out.print("all predecessors: ");
            taskGraph.predecessors(nodeTask).forEach((v) -> System.out.print(v.getId() + ", "));
            System.out.println();
            System.out.println();
        }
    }
}
