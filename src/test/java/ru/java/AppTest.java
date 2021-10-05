package ru.java;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AppTest {
    @Test
    public void whenSum() {
        App ap = new App();
        assertThat(ap.sum(1, 3), is(4));
    }
}
