package com.chnic.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

public class ListZooKeeperNode extends ZConnector {
	
	public void list(String path, int level) throws KeeperException, InterruptedException {
		List<String> childList = zooKeeper.getChildren(path, false);
		if (childList.isEmpty()) {
			return;
		}
		
		for (String childPath : childList) {
			if (level == 0) {
				System.out.println("/" + childPath);
				list("/" + childPath, level + 1);
			} else {
				System.out.println(path + "/" + childPath);
				list(path + "/" + childPath, level + 1);
			}
		}
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		ListZooKeeperNode listNode = new ListZooKeeperNode();
		listNode.connect("localhost");
		listNode.list("/", 0);
		listNode.close();
	}
}
