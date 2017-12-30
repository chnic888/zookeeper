package com.chnic.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

public class EphemeralZNodeGenerator extends ZConnector {

	public void create(String parentNodeName, String nodeName) throws KeeperException, InterruptedException {
		String path = "/" + parentNodeName + "/" + nodeName;
		String createdPath = zooKeeper.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("Created EPHEMERAL node " + createdPath);
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		EphemeralZNodeGenerator generator = new EphemeralZNodeGenerator();
		generator.connect("localhost");
		generator.create("zoo", "tiger");
//		Thread.sleep(Integer.MAX_VALUE);
	}
}
