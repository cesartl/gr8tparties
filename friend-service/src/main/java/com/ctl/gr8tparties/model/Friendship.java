package com.ctl.gr8tparties.model;

import java.util.Objects;

/**
 * Created by Cesar on 15/01/2017.
 */
public class Friendship {
    private String userLeft;
    private String userRight;

    public Friendship(String userLeft, String userRight) {
        this.userLeft = userLeft;
        this.userRight = userRight;
    }

    Friendship() {
    }

    public String getUserLeft() {
        return userLeft;
    }

    public void setUserLeft(String userLeft) {
        this.userLeft = userLeft;
    }

    public String getUserRight() {
        return userRight;
    }

    public void setUserRight(String userRight) {
        this.userRight = userRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(userLeft, that.userLeft) &&
                Objects.equals(userRight, that.userRight) ||
                Objects.equals(userLeft, that.userRight) &&
                        Objects.equals(userRight, that.userLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLeft, userRight) + Objects.hash(userRight, userLeft);
    }
}
