package abd.com.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import abd.com.pojo.TouchModel;

public class TouchQueue {
	private static Queue<TouchModel> queue = new LinkedList<TouchModel>();
	private static int size = 20000;
	private static Object object = new Object();

	public static void add(List<TouchModel> faceModels) {
		synchronized (object) {
			queue.addAll(faceModels);
		}
	}

	public static boolean offer(TouchModel faceModel) {
		return queue.offer(faceModel);
	}

	public static boolean isEmpty() {
		return queue.size() == 0;
	}

	public static boolean isFull() {
		return queue.size() >= size;
	}

	public static List<TouchModel> poll() {
		synchronized (object) {
			List<TouchModel> faceModels = new ArrayList<TouchModel>();
			int i = 0;
			while (i < 1000 && !queue.isEmpty()) {
				faceModels.add(queue.poll());
				i++;
			}
			return faceModels;
		}
	}

}
