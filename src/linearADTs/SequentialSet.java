package linearADTs;

import javax.lang.model.util.SimpleElementVisitor6;

public class SequentialSet<T> implements SetInterface<T> {

  protected SimpleNode<T> head;
  protected SimpleNode<T> tail;
  private int size;

  public SequentialSet() {
    head = new SimpleNode<>(Integer.MIN_VALUE,null);
    tail = new SimpleNode<>(Integer.MAX_VALUE,null);
    head.setNext(tail);
    size = 0;
  }

  @Override
  public boolean add(T item) {
    SimpleNode<T> newNode = new SimpleNode<>(item.hashCode(), item);
    SimpleNode<T> position = findPosition(newNode.key());
    if(position.next().key() == newNode.key()) {
      return false;
    } else {
      SimpleNode<T> successor = position.next();
      position.setNext(newNode);
      newNode.setNext(successor);
      size++;
      return true;
    }

  }

  private SimpleNode<T> findPosition(int key) {
    SimpleNode<T> predecessor = head;
    SimpleNode<T> current = predecessor.next();
    while (current.key() < key) {
      predecessor = current;
      current = current.next();
    }
    return predecessor;
  }

  @Override
  public boolean remove(T item) {
    SimpleNode<T> position = findPosition(item.hashCode());
    if (position.next().item().equals(item)) {
      position.setNext(position.next().next());
      size--;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean contains(T item) {
    SimpleNode<T> position = findPosition(item.hashCode());
    if (position.next().equals(tail)) {
      return false;
    } else {
      return position.next().item().equals(item);
    }
  }

  public int size() {
    return size;
  }
}
