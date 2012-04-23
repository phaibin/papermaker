package com.jje.las.util;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

@SuppressWarnings({"unchecked","rawtypes"})
public class MockItoTest {
    @Test
    public void behaviour() {
        List mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void stub() {
        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        Assert.assertEquals("first", mockedList.get(0));

        try {
            mockedList.get(1);
            Assert.fail();
        } catch (RuntimeException e) {
        }

        Assert.assertNull(mockedList.get(999));

        verify(mockedList).get(0);
    }

    @Test
    public void argumentMatch() {
        LinkedList mockedList = mock(LinkedList.class);

        when(mockedList.get(anyInt())).thenReturn("element");

        when(mockedList.contains(argThat(new isValid()))).thenReturn(true);

        System.out.println(mockedList.get(999));

        verify(mockedList).get(anyInt());
    }
}

	class isValid extends ArgumentMatcher<Integer> {
	   public boolean matches(Object a) {
	       return true;
	   }
	}
