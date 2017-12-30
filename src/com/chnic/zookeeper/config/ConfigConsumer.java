package com.chnic.zookeeper.config;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

public class ConfigConsumer implements Watcher {

	private static final int SESSION_TIMEOUT = 50000;

	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	private ZooKeeper zooKeeper;
	
	public void connect(String host) throws IOException {
		 zooKeeper = new ZooKeeper(host, SESSION_TIMEOUT, this);
		 System.out.println("connect.....");
	}
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.getType());
		if (event.getType() == EventType.NodeDataChanged) {
			try {
				getConfig();
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void getConfig() throws KeeperException, InterruptedException {
		byte[] data = zooKeeper.getData("/config", this, null);
		String dataString = new String(data, UTF8);
		System.out.println("consumer: " + dataString);
	}

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ConfigConsumer consumer = new ConfigConsumer();
		consumer.connect("localhost");
		consumer.getConfig();
		Thread.sleep(Long.MAX_VALUE);
	}

}
