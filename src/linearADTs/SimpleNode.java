package linearADTs;

public class SimpleNode<T> implements Node<T> {
  private final int key;
  private final T item;
  private SimpleNode<T> next;

  public SimpleNode(int key, T item) {
    this.key = key;
    this.item = item;
  }

  public void setNext(SimpleNode<T> next) {
    this.next = next;
  }


  @Override
  public T item() {
    return item;
  }

  @Override
  public int key() {
    return key;
  }

  @Override
  public SimpleNode<T> next() {
    return next;
  }
}
