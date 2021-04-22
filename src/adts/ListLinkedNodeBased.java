package adts;

public class ListLinkedNodeBased<T> implements ListInterface<T> {
  private Node<T> head;
  private int size;

  public ListLinkedNodeBased() {
    head = null;
    size = 0;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public T get(int index) {
    assert 0 <= index && index < size : "The index must be within bounds";
    return findPosition(index + 1).element;
  }

  @Override
  public void add(int index, T element) {
    assert 0 <= index && index <= size : "The index must be within bounds";
    if (isEmpty()) {
      initializeList(element);
    } else if (index == 0) {
      prepend(element);
    } else {
      traverseAndAdd(index, element);
    }
    size++;
  }

  private void traverseAndAdd(int index, T element) {
    Node<T> predecessor = findPosition(index);
    if (predecessor.hasNext()) {
      disconnectAndAdd(predecessor, element);
    } else {
      appendTo(predecessor, element);
    }
  }

  private void initializeList(T element) {
    head = new Node<>(element, null);
  }

  private void prepend(T element) {
    head = new Node<>(element, head);
  }

  private void disconnectAndAdd(Node<T> predecessor, T element) {
    Node<T> successor = predecessor.next;
    predecessor.next = new Node<>(element, successor);
  }

  private void appendTo(Node<T> tail, T element) {
    assert !tail.hasNext();
    tail.next = new Node<>(element, null);
  }

  @Override
  public void add(T element) {
    add(0, element);
  }

  @Override
  public T remove(int index) {
    assert 0 <= index && index < size : "The index must be within bounds";
    if (index == 0) {
      return removeHead();
    } else {
      return deleteNodeAndRestoreConnection(index);
    }
  }

  private T removeHead() {
    T result = head.element;
    head = head.next;
    size--;
    return result;
  }

  private T deleteNodeAndRestoreConnection(int index) {
    Node<T> predecessor = findPosition(index);
    Node<T> current = predecessor.next;
    predecessor.next = current.next;
    size--;
    return current.element;
  }

  private Node<T> findPosition(int index) {
    Node<T> predecessor = head;
    for (int i = 0; i < index - 1; i++) {
      predecessor = predecessor.next;
    }
    return predecessor;
  }

  private class Node<E> {
    private final E element;
    private Node<E> next;

    Node(E element, Node<E> next) {
      this.element = element;
      this.next = next;
    }

    public boolean hasNext() {
      return next != null;
    }
  }
}
