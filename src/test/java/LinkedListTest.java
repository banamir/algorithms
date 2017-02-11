import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class LinkedListTest {
  @Test
  public void addElement() {

    LinkedList list = new LinkedList();
    list.add(11);
    list.add(22);
    list.add(33);
    LinkedList.Node first = list.getFirst();
    assertThat(first.getValue(), is(11));

    LinkedList.Node second = first.getNext();
    assertThat(second.getValue(), is(22));

    LinkedList.Node third = second.getNext();
    assertThat(third.getValue(), is(33));
  }

  @Test
  public void removeElement() {

    LinkedList list = new LinkedList();
    for (int i = 0; i < 10; i++) {
      list.add(i);
    }

    for (int i = 0; i < 10; i++) {
      LinkedList.Node node = list.getFirst();
      list.remove(node);
    }
    assertNull(list.getFirst());
  }

  @Test
  public void getSize() {

    LinkedList list = new LinkedList();
    for (int i = 0; i < 10; i++) {
      list.add(i);
    }

    for (int i = 0; i < 5; i++) {
      LinkedList.Node node = list.getFirst();
      list.remove(node);
    }
    assertThat(list.size(), is(5));
  }

}
