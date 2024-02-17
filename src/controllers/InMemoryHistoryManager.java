package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Task;


public class InMemoryHistoryManager implements  HistoryManager {


    private final ServiceList tasksHistory = new ServiceList();

    @Override
    public void add(Task task) {
        tasksHistory.checkAndAdd(task);
    }

    @Override
    public void remove(int id) {
        tasksHistory.removeNode(tasksHistory.getNode(id));
    }

    @Override
    public List<Task> getHistory() {
        return tasksHistory.getTasks();
    }

    private static class ServiceList {
        private final Map<Integer, Node> nodeMap = new HashMap<>();
        private Node head;
        private Node tail;

        private void checkAndAdd(Task task) {
            Node element = new Node();
            element.setTask(task);
            if (nodeMap.containsKey(task.getId())) {
                removeNode(nodeMap.get(task.getId()));
            }
            if (head == null) {
                tail = element;
                head = element;
                element.setNext(null);
                element.setPrevios(null);
            } else {
                element.setPrevios(tail);
                element.setNext(null);
                tail.setNext(element);
                tail = element;
            }
            nodeMap.put(task.getId(), element);
        }

        private List<Task> getTasks() {
            List<Task> taskList = new ArrayList<>();
            Node element = head;
            while (element != null) {
                taskList.add(element.getTask());
                element = element.getNext();
            }
            return taskList;
        }

        private void removeNode(Node node) {
            if (node != null) {
                nodeMap.remove(node.getTask().getId());
                Node prev = node.getPrevios();
                Node next = node.getNext();

                if (head == node) {
                    head = node.getNext();
                }
                if (tail == node) {
                    tail = node.getPrevios();
                }
                if (prev != null) {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrevios(prev);
                }
            }
        }

        private Node getNode(int id) {
            return nodeMap.get(id);
        }

    }

    static class Node {
        private Task task;
        private Node previos;
        private Node next;

        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public Node getPrevios() {
            return previos;
        }

        public void setPrevios(Node prev) {
            this.previos = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }


}
