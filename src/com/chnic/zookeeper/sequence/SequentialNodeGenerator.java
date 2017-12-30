package com.chnic.zookeeper.sequence;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class SequentialNodeGenerator implements Watcher {

	private ZooKeeper zooKeeper;
	
	public void connect(String host) throws IOException {
		zooKeeper = new ZooKeeper(host, 5000, this);
	}
	
	@Override
	public void process(WatchedEvent event) {
		
	}

	public void createNode(String nodeName) throws KeeperException, InterruptedException {
		String createdNode = zooKeeper.create(nodeName, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
		System.out.println(createdNode);
	}
	
	public void close() throws InterruptedException {
		zooKeeper.close();
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		SequentialNodeGenerator generator = new SequentialNodeGenerator();
		generator.connect("localhost");
		generator.createNode("/seq-");
		generator.close();
	}
}
