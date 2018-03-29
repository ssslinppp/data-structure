package com.ssslinppp.graph.directd;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * Desc:
 * <p>
 * User: liulin ,Date: 2018/3/29 , Time: 16:18 <br/>
 * Email: liulin@cmss.chinamobile.com <br/>
 * To change this template use File | Settings | File Templates.
 */
public class TaskGraphTest {
    /**
     * 测试依赖关系
     */
    @Test
    public void testDependence() {
        Map<String, NodeTask> nodeTaskMap = Maps.newConcurrentMap();
        NodeTask nodeTaskA = new NodeTask("nodeTaskA");
        NodeTask nodeTaskB = new NodeTask("nodeTaskB");
        NodeTask nodeTaskC = new NodeTask("nodeTaskC").addDependence("nodeTaskA");
        NodeTask nodeTaskD = new NodeTask("nodeTaskD").addDependence("nodeTaskB");
        NodeTask nodeTaskE = new NodeTask("nodeTaskE").addDependence("nodeTaskC").addDependence("nodeTaskD");
        NodeTask nodeTaskF = new NodeTask("nodeTaskF").addDependence("nodeTaskE");
        NodeTask nodeTaskG = new NodeTask("nodeTaskG").addDependence("nodeTaskE");
        nodeTaskMap.put(nodeTaskA.getId(), nodeTaskA);
        nodeTaskMap.put(nodeTaskB.getId(), nodeTaskB);
        nodeTaskMap.put(nodeTaskC.getId(), nodeTaskC);
        nodeTaskMap.put(nodeTaskD.getId(), nodeTaskD);
        nodeTaskMap.put(nodeTaskE.getId(), nodeTaskE);
        nodeTaskMap.put(nodeTaskF.getId(), nodeTaskF);
        nodeTaskMap.put(nodeTaskG.getId(), nodeTaskG);

        TaskGraph taskGraph = new TaskGraph();
        taskGraph.parseNodeTasksToGraph(nodeTaskMap);
        System.out.println("======== DAG（有向无环图）判断 ===========");
        if (taskGraph.isDAGraph()) {
            System.out.println("is DAG");
        } else {
            System.out.println("Not DAG");
        }

        System.out.println("=============== print ===========");
        taskGraph.print();
    }


    /**
     * 判断是否为有向无环图
     */
    @Test
    public void testDAG() {
        // E依赖D, D依赖B, B依赖E ==>(B,E,G)为一个环
        Map<String, NodeTask> nodeTaskMap = Maps.newConcurrentMap();
        NodeTask nodeTaskA = new NodeTask("nodeTaskA");
        NodeTask nodeTaskB = new NodeTask("nodeTaskB");
//        nodeTaskB.addDependence("nodeTaskE");  // 在这里控制是否有环，进行测试
        NodeTask nodeTaskC = new NodeTask("nodeTaskC").addDependence("nodeTaskA");
        NodeTask nodeTaskD = new NodeTask("nodeTaskD").addDependence("nodeTaskB");
        NodeTask nodeTaskE = new NodeTask("nodeTaskE").addDependence("nodeTaskC").addDependence("nodeTaskD");
        NodeTask nodeTaskF = new NodeTask("nodeTaskF").addDependence("nodeTaskE");
        NodeTask nodeTaskG = new NodeTask("nodeTaskG").addDependence("nodeTaskE");
        nodeTaskMap.put(nodeTaskA.getId(), nodeTaskA);
        nodeTaskMap.put(nodeTaskB.getId(), nodeTaskB);
        nodeTaskMap.put(nodeTaskC.getId(), nodeTaskC);
        nodeTaskMap.put(nodeTaskD.getId(), nodeTaskD);
        nodeTaskMap.put(nodeTaskE.getId(), nodeTaskE);
        nodeTaskMap.put(nodeTaskF.getId(), nodeTaskF);
        nodeTaskMap.put(nodeTaskG.getId(), nodeTaskG);

        TaskGraph taskGraph = new TaskGraph();
        taskGraph.parseNodeTasksToGraph(nodeTaskMap);
        System.out.println("======== DAG（有向无环图）判断 ===========");
        if (taskGraph.isDAGraph()) {
            System.out.println("It is DAG");
        } else {
            System.out.println("Not DAG");
        }
    }
}