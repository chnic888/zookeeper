package com.chnic.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

/**
 * @author hcai
 *
 */
public class PersistentZNodeDeleter extends ZConnector {

	public void delete(String nodeName) throws InterruptedException, KeeperException {
		String path = "/" + nodeName;
		zooKeeper.delete(path, VERSION);
		System.out.println("Deleted " + path);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		PersistentZNodeDeleter deleter = new PersistentZNodeDeleter();
		deleter.connect("localhost");
		deleter.delete("zoo");
		deleter.close();
	}

}
