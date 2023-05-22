package cn.qfengx.study.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class LinkedNode {
	public Integer key;
	public Integer value;

	public LinkedNode prev;
	public LinkedNode next;

	public LinkedNode(Integer key, Integer value) {
		this.key = key;
		this.value = value;
	}

}

/**
 * @Author YeShengtao <qffg1998@126.com>
 * @Date 2023-05-08 00:11
 */
public class LRUCache {

	public Map<Integer, LinkedNode> map; // 这个缓存用于快读查询
	public Integer size;
	public Integer capacity;
	public LinkedNode head; // 投喂链表用户实现LRU
	public LinkedNode tail;

	public LRUCache(Integer capacity) {
		this.size = 0;
		this.capacity = capacity;
		this.head = new LinkedNode(null, null);
		this.tail = new LinkedNode(null, null);
		this.head.next = this.tail;
		this.tail.prev = this.head;
		this.map = new HashMap<>();
	}




	private Integer get(Integer key) {
		LinkedNode linkedNode = this.map.get(key);
		if (Objects.isNull(linkedNode)) return -1;
		this.moveToHead(linkedNode); // 使用过一次就添加到头部 这样保持尾部就是最少使用的
		return linkedNode.value;
	}

	private void removeNode(LinkedNode node) { // 移除当前节点, 将前后节点直接相连
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	private void addToHead(LinkedNode node) { // 当前节点添加到头节点 this.head后
		node.prev = this.head;
		node.next = this.head.next;
		this.head.next.prev = node;
		this.head.next = node;
	}

	private void moveToHead(LinkedNode node) {
		this.removeNode(node);
		this.addToHead(node);
	}

	private LinkedNode removeTail() { // 移除末尾
		LinkedNode node = this.tail.prev;
		this.removeNode(node);
		return node;
	}

	public void put(Integer key, Integer value) {
		LinkedNode node = this.map.get(key);
		if (Objects.isNull(node)) {
			LinkedNode newNode = new LinkedNode(key, value);
			this.map.put(key, newNode);
			this.addToHead(newNode);
			this.size++;
			if (this.size > this.capacity) { // 超过大小 要回收
				LinkedNode tailNode = this.removeTail(); // 尾部就是最少使用的
				this.map.remove(tailNode.key);
				this.size--;
			}
		} else {
			node.value = value;
			this.moveToHead(node); // 最新的添加到头部
		}
	}
	public static void main(String[] args) {
		LRUCache lruCache = new LRUCache(5);
		System.out.println(lruCache.get(111));
	}

}
