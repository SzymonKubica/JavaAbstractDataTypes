package linearADTs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO: Make it work!!.
public class ListFineGrained<T> implements ListInterface<T> {
  private LockableNode<T> head;
  private AtomicInteger size;

  public ListFineGrained() {
    head = null;
    size = new AtomicInteger(0);
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public T get(int index) {
    assert 0 <= index && index < size() : "The index must be within bounds";
    LockableNode<T> position = findPosition(index + 1);
    T result = position.element;
    if (position.hasNext()) {
      position.next.unlock();
    }
    position.unlock();
    return result;
  }

  @Override
  public void add(int index, T element) {
    assert 0 <= index && index <= size() : "The index must be within bounds";
    if (isEmpty()) {
      initializeList(element);
    } else if (index == 0) {
      prepend(element);
    } else {
      traverseAndAdd(index, element);
    }
    size.incrementAndGet();
  }

  private void traverseAndAdd(int index, T element) {
    LockableNode<T> predecessor = findPosition(index);
    if (predecessor.hasNext()) {
      disconnectAndAdd(predecessor, element);
    } else {
      appendTo(predecessor, element);
    }
  }

  // The following two methods are synchronized because they are working with the head.
  private synchronized void initializeList(T element) {
    head = new LockableNode<>(element, null);
  }

  private synchronized void prepend(T element) {
    head = new LockableNode<>(element, head);
  }

  private void disconnectAndAdd(LockableNode<T> predecessor, T element) {
    LockableNode<T> successor = predecessor.next;
    predecessor.next = new LockableNode<>(element, successor);
    successor.unlock();
    predecessor.unlock();
  }

  private void appendTo(LockableNode<T> tail, T element) {
    assert !tail.hasNext() && tail.isLocked();
    tail.next = new LockableNode<>(element, null);
    tail.unlock();
  }

  @Override
  public void add(T element) {
    add(0, element);
  }

  @Override
  public T remove(int index) {
    assert 0 <= index && index < size() : "The index must be within bounds";
    if (index == 0) {
      return removeHead();
    } else {
      return deleteNodeAndRestoreConnection(index);
    }
  }

  private T removeHead() {
    head.lock();
    LockableNode<T> next = head.next;
    T result;
    if (next != null) {
      next.lock();
      result = head.element;
      head.unlock();
      head = next;
      next.unlock();
    } else {
      result = head.element;
      head.unlock();
      head = null;
    }
    size.decrementAndGet();
    return result;
  }

  private T deleteNodeAndRestoreConnection(int index) {
    LockableNode<T> predecessor = findPosition(index);
    LockableNode<T> current = predecessor.next;
    predecessor.next = current.next;
    predecessor.unlock();
    current.unlock();
    size.decrementAndGet();
    return current.element;
  }

  private LockableNode<T> findPosition(int index) {
    // The position is found using the hand-over-hand approach.
    LockableNode<T> predecessor = head;
    predecessor.lock();
    if (predecessor.next != null) {
      LockableNode<T> current = predecessor.next;
      current.lock();
      for (int i = 0; i < index - 1; i++) {
        LockableNode<T> successor = current.next;
        if (successor != null) {
          successor.lock();
          predecessor.unlock();
          predecessor = current;
          current = successor;
        } else {
          predecessor.unlock();
          predecessor = current;
          break;
        }
      }
    }
    return predecessor;
  }

  private class LockableNode<E> {
    private final E element;
    private LockableNode<E> next;
    private Lock lock = new ReentrantLock();
    private boolean locked;

    LockableNode(E element, LockableNode<E> next) {
      this.element = element;
      this.next = next;
      locked = false;
    }

    public boolean hasNext() {
      return next != null;
    }

    public boolean isLocked() {
      return locked;
    }

    public void lock() {
      lock.lock();
      locked = true;
    }

    public void unlock() {
      lock.unlock();
      locked = false;
    }
  }
}
