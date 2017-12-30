package com.chnic.zookeeper.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import com.chnic.zookeeper.ZConnector;

public class ConfigProducer extends ZConnector {

	private static final Charset UTF8 = Charset.forName("UTF-8");

	private static Random random = new Random();

	public void createConfigNode(String nodeName, String value) throws KeeperException, InterruptedException {
		String path = "/" + nodeName;
		if (zooKeeper.exists(path, false) == null) {
			String createdPath = zooKeeper.create(path, value.getBytes(UTF8), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
			System.out.println("Created PERSISTENT node " + createdPath);
		}
	}

	public void updateConfigNode(String nodeName, String value) throws KeeperException, InterruptedException {
		String path = "/" + nodeName;
		zooKeeper.setData(path, value.getBytes(UTF8), -1);
		System.out.println("producer: " + value);
	}

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ConfigProducer updater = new ConfigProducer();
		updater.connect("localhost");
		updater.createConfigNode("config", String.valueOf(random.nextInt(100)));
		while (true) {
			Thread.sleep((random.nextInt(10) + 1) * 1000);
			updater.updateConfigNode("config", String.valueOf(random.nextInt(100)));
		}
	}

}
