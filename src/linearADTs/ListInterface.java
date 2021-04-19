package linearADTs;

public interface ListInterface<T> {

  boolean isEmpty();

  int size();

  T get(int index);

  void add(int index);

  T remove(int index);
}
