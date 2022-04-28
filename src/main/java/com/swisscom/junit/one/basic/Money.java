package com.swisscom.junit.one.basic;

import java.util.Objects;

public class Money {

    int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money dollar = (Money) o;
        return amount == dollar.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
