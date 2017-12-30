package com.chnic.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public abstract class ZConnector implements Watcher {

	private static final int SESSION_TIMEOUT = 50000;
	
	protected ZooKeeper zooKeeper;
	
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	
	protected int VERSION = -1;

	public void connect(String hosts) throws IOException, InterruptedException {
		zooKeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
//		zooKeeper.addAuthInfo("digest", "chnic888:123456".getBytes());
		System.out.println("connect.....");
		connectedSignal.await();
		System.out.println("after connect");
	}
	
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			System.out.println("process.....");
            connectedSignal.countDown();
        }
	}
	
	public void close() throws InterruptedException {
		System.out.println("close.....");
		zooKeeper.close();
	}
}