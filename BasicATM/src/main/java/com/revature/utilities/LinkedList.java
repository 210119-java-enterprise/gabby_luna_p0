package com.revature.utilities;

/**
 * Custom linked list implementation. USed to pull lists of keys from maps
 * <p>
 * @param <T> generic data type stored in list nodes
 * @author Wezley Singlton
 * @author Gabrielle Luna
 */
public class LinkedList <T> {

    //Linked List attributes ----------------------------
    private int size;
    public Node<T> head;
    private Node<T> tail;

    /**
     * Used to add items to the list
     * @param data  generic type used to populate node data
     */
    public void insert(T data) {
        //Initial conditions
        if (data == null) {
            return;
        }

        Node<T> newNode = new Node<>(data);
        //list is empty
        if (head == null) {
            tail = head = newNode;
        } else { //contains nodes
            tail.nextNode = newNode;
            newNode.prevNode = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Takes the first item in the linked list, returns its data and deletes its reference from list.
     * @return  generic data stored in node
     */
    public T pop() {
        //Initial condition
        if (head == null) {
            return null;
        }

        //Save data and remove node from list
        T popped = head.data;
        head = head.nextNode;

        //update head pointer
        if (head != null) {
            head.prevNode = null;
        }

        size--;
        return popped;
    }

//    public T peek() {
//        return (head == null) ? null : head.data;
//    }

    /**
     * Search list for given data and return true if found
     * @param data      data list is being searched for
     * @return          true if found
     */
    public boolean contains(T data) {
        //Initial condition
        if (data == null) {
            return false;
        }
        //Search for node
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.nextNode) {
            if (currentNode.data.equals(data)) {
                return true;
            }
        }
        //Node not found
        return false;
    }

//    public int size() {
//        return size;
//    }
//
//    public void printList() {
//        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.nextNode) {
//            System.out.println(currentNode.data);
//        }
//    }

    /**
     * Node class containing data of type generic
     * @param <T>   data being stored
     */
    public static class Node<T> {
        public T data;
        public Node<T> nextNode;
        public Node<T> prevNode;

        Node(T data) {
            this.data = data;
        }
    }

}