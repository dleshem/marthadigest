package com.marthastewart.gae;

import java.util.List;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class QueueUtils {
	private QueueUtils() {}
	
	public static int MAX_TASKS_PER_ADD = 100;
	
	public static void addTasks(String queueName, List<TaskOptions> tasks) {
	    Queue queue = QueueFactory.getQueue(queueName);

	    final int numTasks = tasks.size();
	    int i = 0;
	    while (i < numTasks) {
	    	final int j = Math.min(i + MAX_TASKS_PER_ADD, numTasks);
	    	queue.add(tasks.subList(i, j));
	    	i = j;
	    }
	}

}
