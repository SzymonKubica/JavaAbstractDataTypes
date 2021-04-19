package linearADTs;

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
    Node<T> pointer = head;
    for (int i = 0; i < index; i++) {
      pointer = pointer.next;
    }
    return pointer.element;
  }

  @Override
  public void add(int index, T element) {
    assert 0 <= index && index <= size : "The index must be within bounds";
    if (isEmpty()) {
      head = new Node<>(element, null);
    } else if (index == 0) {
      head = new Node<>(element, head);
    } else {
      Node<T> predecessor = head;
      for(int i = 0; i < index - 1; i++) {
        predecessor = predecessor.next;
      }
      if (predecessor.next != null) {
        Node<T> successor = predecessor.next;
        predecessor.next = new Node<>(element, successor);
      } else {
        predecessor.next = new Node<>(element, null);
      }
    }
    size++;
  }

  @Override
  public void add(T element) {
    add(0, element);
  }

  @Override
  public T remove(int index) {
    assert 0 <= index && index < size : "The index must be within bounds";
    Node<T> predecessor = head;
    for (int i = 0; i < index - 1; i++) {
      predecessor = predecessor.next;
    }
    T result;
    if (index == 0) {
      result = head.element;
      head = head.next;
    } else {
      Node<T> current = predecessor.next;
      result = current.element;
      Node<T> successor = current.next;
      predecessor.next = successor;
    }
    size--;
    return result;
  }

  private class Node<E> {
    private final E element;
    private Node<E> next;

    Node(E element, Node<E> next) {
      this.element = element;
      this.next = next;
    }
  }
}
