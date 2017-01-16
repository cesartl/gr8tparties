package com.ctl.gr8tparties.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Cesar on 15/01/2017.
 */
public class FriendshipTest {

    @Test
    public void testEquals() throws Exception {
        final Friendship f1 = new Friendship("a", "b");
        final Friendship f2 = new Friendship("b", "a");

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());

        final Set<Friendship> set = new HashSet<>();
        set.add(f1);

        assertTrue(set.contains(f2));
        assertFalse(set.add(f2));
    }
}