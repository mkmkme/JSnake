package ru.reverendhomer;

public enum Direction {

    LEFT, RIGHT, UP, DOWN;

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }

    public boolean isHorizontal() {
        return this == LEFT || this == DOWN;
    }
}
