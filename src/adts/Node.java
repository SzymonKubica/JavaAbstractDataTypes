package adts;

public interface Node<T> {
  T item();

  int key();

  Node<T> next();
}
