package model.core;

public abstract class SelectionStrategy<I> {
    abstract void orderItems(I[] items);
}
