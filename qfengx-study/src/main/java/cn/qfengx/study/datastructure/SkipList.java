//package cn.qfengx.study.datastructure;
//
//import java.awt.event.ItemEvent;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * 跳跃表
// * @Author YeShengtao <qffg1998@126.com>
// * @Date 2023-04-29 14:58
// */
//
//public class SkipList<E, C extends Comparable<? super C>> {
//
//	static final int MAX_LEVEL = 1 << 6; // 最大层级
//	static final int DEFAULT_LEVEL = 4;
//
//	int level; // 跳跃表 down node 的层级
//
//	Node<E, C> head;
//	ThreadLocalRandom random = ThreadLocalRandom.current();
//
//	public SkipList() {
//		this(DEFAULT_LEVEL); // 设置层级
//	}
//
//	public SkipList(int level) {
//		this.level = level;
//		int i = level;
//		Node<E, C> temp = null;
//		Node<E, C> prev = null;
//		// 从底部节点开始创建链表
//		while(i-- != 0) { // 初始化指定的层级
//			temp = new Node(null, (T) -> -1);
//			temp.down = prev;
//			prev = temp;
//		}
//		head = temp; // 头节点 指向的是第一层
//	}
//
//	public void put(E val, C score) {
//		Node<E, C> t = head;
//		// 存储每个层级,插入新节点的最左边节点
//		List<Node<E, C>> paths = new ArrayList<>();
//		// 1. 找到需要插入的位置
//		while (t != null) {
//			// 表示存在该值的点, 表示需要更新该节点
//			if (t.score.compareTo(score) == 0) {
//				Entity e = t.entity;
//				// 插入到链表尾部
//				e.next = new Entity(val, null);
//				return;
//			}
//			// 查找插入的位置
//			if (t.next == null || t.next.score.compareTo(score) > 0) {
//				paths.add(t);
//				t = t.down;
//			} else {
//				t = t.next;
//			}
//		}
//
//		// 随机生成新节点的层级
//		int lev = randomLevel();
//		/**
//		 * 如果随机的层级数, 大于head节点的层级数
//		 * 需要更新head这一列的节点数量,保证head列数量最多
//		 */
//		if (lev > level) {
//			Node<E, C> temp = null;
//			Node<E, C> prev = head;
//			while (level++ != lev) {
//				temp = new Node(null, (T) -> -1);
//				paths.add(0, temp);
//				temp.down = prev;
//				prev = temp;
//			}
//			head = temp;
//			// 更新层级
//			level = lev;
//		}
//		// 从后向前遍历 path中的每一个节点,在其后面增加一个新的节点
//		Node<E, C> down = null, newNode = null, path = null;
//		Entity<E> e = new Entity<>(val, null);
//		for (int i = level - 1; i >= level - lev; i--) {
//			// 创建新节点
//			newNode = new Node<>(e, score);
//			path = paths.get(i);
//			newNode.next = path.next;
//			path.next = newNode;
//			newNode.down = down;
//			down = newNode;
//		}
//	}
//
//	/**
//	 * 查找跳跃表中的一个值
//	 * @param score
//	 * @return
//	 */
//	public List<E> get(C score) {
//		Node<E, C> t = head;
//		while (t != null) {
//			if (t.score.compareTo(score) != 0) {
//				return t.entity.toList();
//			}
//			if (t.next == null || t.next.score.compareTo(score) > 0) {
//				t = t.down;
//			} else {
//				t = t.next;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 查找跳跃表中指定范围的值
//	 * @param begin
//	 * @param and
//	 * @return
//	 */
//	public List<E> find(final C begin, C end) {
//		Node<E, C> t = head;
//		Node<E, C> beginNode = null;
//		// 确定下界索引
//		while (t != null) {
//			beginNode = t;
//			if (t.next == null || t.next.score.compareTo(begin) >= 0) {
//				t = t.down;
//			} else {
//				t = t.next;
//			}
//		}
//		// 循环链表
//		List<E> list = new ArrayList<>();
//		for (Node n = beginNode.next; n != null; n = n.next) {
//			if (n.entity != null && n.score.compareTo(end) < 0) {
//				list.addAll(n.entity.toList());
//			}
//		}
//		return list;
//	}
//
//	/**
//	 * 根据score 的值来删除节点
//	 * @param score
//	 */
//	public void delete(C score) {
//		Node<E, C> t = head;
//		while (t != null) {
//			if (t.next == null || t.next.score.compareTo(score) > 0) {
//				// 向下查找
//				t = t.down;
//			} else if (t.next.score.compareTo(score) == 0) {
//				// 在这里说明找到了该删除的节点
//				t.next = t.next.next;
//				// 向下查找
//				t = t.down;
//			} else {
//				// 向右查找
//				t = t.next;
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		SkipList skipList = new SkipList(5);
//		skipList.put(1, null);
//		System.out.println(skipList.get(1));
//	}
//
//
//	/**
//	 * 随即生成新节点的层级
//	 * @return
//	 */
//	private int randomLevel() {
//		int lev = 1;
//		while (random.nextInt() % 2 == 0) lev++;
//		return Math.min(lev, MAX_LEVEL);
//	}
//
//	/**
//	 * 跳跃表节点
//	 * @param <E>
//	 * @param <C>
//	 */
//	static class Node<E, C extends Comparable> {
//		Entity<E> entity; // 存储的数据节点
//		C score; // 需要排序的字段
//		Node<E, C> next; // 指向下一个节点的指针
//		Node<E, C> down; // 指向下一层的指针
//
//		Node(Entity<E> entity, C score) {
//			this.entity = entity;
//			this.score = score;
//		}
//
//		@Override
//		public String toString() {
//			return "Node{" + "score=" + score + '}';
//		}
//	}
//
//	/**
//	 * 数据结构 包含一个横向链表
//	 * @param <E>
//	 */
//	static class Entity<E> {
//		E val;
//		Entity<E> next;
//		Entity(E val, Entity<E> next) {
//			this.val = val;
//			this.next = next;
//		}
//		List<E> toList() {
//			List<E> list = new ArrayList<>();
//			list.add(val);
//			// 横向遍历链表
//			for (Entity<E> e = next; e != null; e = e.next) {
//				list.add(e.val);
//			}
//			return list;
//		}
//
//		@Override
//		public String toString() {
//			return "Entity{" + "val=" + val + '}';
//		}
//	}
//
//}
