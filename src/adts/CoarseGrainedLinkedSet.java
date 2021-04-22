package adts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedLinkedSet<T> extends SequentialSet<T> {

  private Lock lock = new ReentrantLock();

  @Override
  public boolean add(T item) {
    lock.lock();
    try {
      return super.add(item);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean remove(T item) {
    lock.lock();
    try {
      return super.remove(item);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean contains(T item) {
    lock.lock();
    try {
      return super.contains(item);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public int size() {
    lock.lock();
    try {
      return super.size();
    } finally {
      lock.unlock();
    }
  }
}
