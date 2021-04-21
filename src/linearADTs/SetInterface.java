package linearADTs;

public interface SetInterface<T> {

  boolean add(T item);

  boolean remove(T item);

  boolean contains(T item);
}
