package com.chnic.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

public class PersistentZNodeGenerator extends ZConnector {

	public void create(String nodeName) throws KeeperException, InterruptedException {
		String path = "/" + nodeName;
		String createdPath = zooKeeper.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("Created PERSISTENT node " + createdPath);
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		PersistentZNodeGenerator generator = new PersistentZNodeGenerator();
		generator.connect("localhost");
		generator.create("zoo");
		generator.close();
	}
}
