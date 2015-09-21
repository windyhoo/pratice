package notify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


/*
 * 现在你知道wait应该永远在被synchronized的背景下和那个被多线程共享的对象上调用，
 * 下一个一定要记住的问题就是，你应该永远在while循环，而不是if语句中调用wait。
 * 因为线程是在某些条件下等待的——在我们的例子里，即“如果缓冲区队列是满的话，那么生产者线程应该等待”，
 * 你可能直觉就会写一个if语句。但if语句存在一些微妙的小问题，导致即使条件没被满足，你的线程你也有可能被错误地唤醒。
 * 所以如果你不在线程被唤醒后再次使用while循环检查唤醒条件是否被满足，
 * 你的程序就有可能会出错——例如在缓冲区为满的时候生产者继续生成数据，
 * 或者缓冲区为空的时候消费者开始小号数据。
 * 所以记住，永远在while循环而不是if语句中使用wait！
 * 我会推荐阅读《Effective Java》，这是关于如何正确使用wait和notify的最好的参考资料。
 */
public class ProducerConsumerInJava {

	
	public static void main(String args[]) {
		System.out.println("How to use wait and notify method in Java");
		
		Queue<Integer> buffer=new LinkedList<>();
		int maxSize=100000;
		
		Thread producer=new Producer(buffer,maxSize,"PRODUCER");
		Thread consumer=new Consumer(buffer,maxSize,"CONSUMER");
		
		producer.start();
		consumer.start();
	}
}

class Producer extends Thread {
	private Queue<Integer> queue;
	public int maxSize;
	
	public Producer(Queue<Integer> queue,int maxSize,String name) {
		super(name);
		
		this.queue=queue;
		this.maxSize=maxSize;
	}
	
	public void run() {
		while(true) {
			synchronized(queue) {
				if(queue.size()==maxSize) {
					
//					try {
						System.out.println("producer queue is full");
						break;
//						queue.wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
				
				Random random=new Random();
				int i=random.nextInt();
				System.out.println("producing value is:"+i);
				queue.add(i);
				queue.notifyAll();
			}
		}
	}
	
}

class Consumer extends Thread {
	private Queue<Integer> queue;
	public int maxSize;
	
	public Consumer(Queue<Integer> queue,int maxSize,String name) {
		super(name);
		
		this.queue=queue;
		this.maxSize=maxSize;
	}
	
	public void run() {
		while(true) {
			synchronized(queue) {
				while(queue.isEmpty()) {					
					try {
						System.out.println("Consumer queue is empty");

						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("Consumer value:"+queue.remove());
				queue.notifyAll();
			}
		}
	}
}