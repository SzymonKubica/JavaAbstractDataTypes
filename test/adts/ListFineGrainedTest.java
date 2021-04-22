package adts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListFineGrainedTest {
  @Test
  void isEmptyTest() {
    ListFineGrained<Integer> list = getSampleList();
    Assertions.assertFalse(list.isEmpty());
    ListFineGrained<Integer> list2 = new ListFineGrained<>();
    Assertions.assertTrue(list2.isEmpty());
  }

  @Test
  void sizeTest() {
    ListFineGrained<Integer> list = getSampleList();
    Assertions.assertEquals(list.size(), 10);
  }

  @Test
  void getTest() {
    ListFineGrained<Integer> list = getSampleList();
    Assertions.assertEquals((int) list.get(3), 3);
  }

  @Test
  void addTest() {
    ListFineGrained<Integer> list = getSampleList();
    list.add(7, 17);

    Assertions.assertEquals((int) list.get(7), 17);
    Assertions.assertEquals((int) list.get(8), 7);

    list.add(19);
    Assertions.assertEquals((int) list.get(0), 19);
    Assertions.assertEquals((int) list.get(1), 0);
  }

  @Test
  void addTestBigList() {
    ListFineGrained<Integer> list = getBigList();
    list.add(0, 111);
    Assertions.assertEquals((int) list.get(100), 99);
    Assertions.assertEquals(list.size(), 101);
  }

  @Test
  void remove() {
    ListFineGrained<Integer> list = getSampleList();
    list.remove(4);
    Assertions.assertNotEquals((int) list.get(4), 4);
    Assertions.assertEquals((int) list.get(4), 5);
  }

  @Test
  void concurrentModificationsTest() {
    ListFineGrained<Integer> list = getSampleList();
    //ListLinkedNodeBased<Integer> list = getSampleNodeBasedList();

    ItemDeleter<Integer> deleter1 = new ItemDeleter<>(list);
    ItemDeleter<Integer> deleter2 = new ItemDeleter<>(list);
    ItemAdder adder1 = new ItemAdder(list);
    ItemAdder adder2 = new ItemAdder(list);
    ItemAdder adder3 = new ItemAdder(list);
    ItemAdder adder4 = new ItemAdder(list);

    Thread deleter1Thread = new Thread(deleter1);
    Thread deleter2Thread = new Thread(deleter2);
    Thread adderThread1 = new Thread(adder1);
    Thread adderThread2 = new Thread(adder2);
    Thread adderThread3 = new Thread(adder3);
    Thread adderThread4 = new Thread(adder4);

    deleter1Thread.start();
    deleter2Thread.start();
    adderThread1.start();
    adderThread2.start();
    adderThread3.start();
    adderThread4.start();

    try {
      deleter1Thread.join();
      deleter2Thread.join();
      adderThread4.join();
      adderThread4.join();
      adderThread4.join();
      adderThread4.join();
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    }

    Assertions.assertEquals(4000, list.size());
  }

  private static ListFineGrained<Integer> getSampleList() {
    ListFineGrained<Integer> list = new ListFineGrained<>();
    for (int i = 0; i < 10; i++) {
      list.add(i, i);
    }
    return list;
  }

  private static ListLinkedNodeBased<Integer> getSampleNodeBasedList() {
    ListLinkedNodeBased<Integer> list = new ListLinkedNodeBased<>();
    for (int i = 0; i < 10; i++) {
      list.add(i, i);
    }
    return list;
  }

  private static ListFineGrained<Integer> getBigList() {
    ListFineGrained<Integer> list = new ListFineGrained<>();
    for (int i = 0; i < 100; i++) {
      list.add(i, i);
    }
    return list;
  }

  private class ItemDeleter<T> implements Runnable {
    private ListInterface<T> source;
    int initialSourceSize;

    public ItemDeleter(ListInterface<T> source) {
      this.source = source;
      initialSourceSize = source.size();
    }

    @Override
    public void run() {
      for (int i = 0; i < initialSourceSize / 2; i++) {
        source.remove(0);
      }
    }
  }

  private class ItemAdder implements Runnable {
    private ListInterface<Integer> destination;

    public ItemAdder(ListInterface<Integer> destination) {
      this.destination = destination;
    }

    @Override
    public void run() {
      for (int i = 0; i < 1000; i++) {
        destination.add(destination.size(), i);
      }
    }
  }
}
