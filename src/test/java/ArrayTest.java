


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArrayTest {

  @Test
  public void testSize() {
    Array a = new Array();
    a.add(2);
    a.add(3);
    assertThat(a.size(), is(2));
  }

  @Test
  public void dynamicSizing() {
    Array b = new Array();
    for (int i = 0; i < 100; i++) {
      b.add(i);
    }
    assertThat(b.size(), is(100));
  }

  @Test
  public void shrinkTest() {
    Array b = new Array();
    for (int i = 0; i < 1_000; i++) {
      b.add(i);
    }
    for (int i = 0; i < 1_000; i++) {
      b.remove(0);
    }
    assertThat(b.size(), is(0));
    assertThat(b.capacity(), is(2));

  }
  @Test
  public void getElement(){
    Array arr =new Array();
    arr.add(11);
    arr.add(22);
    arr.add(33);
    assertThat(arr.get(0), is(11));
    assertThat(arr.get(1), is(22));
    assertThat(arr.get(2), is(33));

  }

}
