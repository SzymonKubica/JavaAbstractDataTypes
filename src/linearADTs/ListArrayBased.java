package linearADTs;

public class ListArrayBased<T> implements ListInterface<T>{
  public static final int  DEFAULT_INITIAL_SIZE = 100;
  private int MAX_SIZE = DEFAULT_INITIAL_SIZE;
  private int size;
  private T[] elements;

  public ListArrayBased() {
    elements = (T[]) new Object[MAX_SIZE];
    size = 0;
  }
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public T get(int index) {
    assert 0 <= index && index < size : "The index must be within bounds";
    return elements[index];
  }

  @Override
  public void add(T element) {
    add(0, element);
  }

  @Override
  public void add(int index, T element) {
    assert 0 <= index && index <= size : "The index must be within bounds";
    if (size() < MAX_SIZE) {
      if (elements[index] != null) {
        translateRightStartingFrom(index);
      }
      elements[index] = element;
      size++;
    } else {
      resize();
      add(index, element);
    }
  }

  private void resize() {
    MAX_SIZE *= 1.5;
    T[] newElements = (T[]) new Object[MAX_SIZE];
    System.arraycopy(elements, 0, newElements, 0, elements.length);
    elements = newElements;
  }

  private void translateRightStartingFrom(int index) {
    for (int i = size(); i >= index; i--){
      swap(i, i + 1);
    }
  }

  private void translateLeftStartingFrom(int index) {
    if (size() - 1 - index >= 0)
      System.arraycopy(elements, index + 1, elements, index, size() - 1 - index);
  }

  private void swap(int i, int j) {
    T temp = elements[i];
    elements[i] = elements[j];
    elements[j] = temp;
  }

  @Override
  public T remove(int index) {
    assert 0 <= index && index < size : "The index must be within bounds";
    T result = elements[index];
    translateLeftStartingFrom(index);
    return result;
  }
}
