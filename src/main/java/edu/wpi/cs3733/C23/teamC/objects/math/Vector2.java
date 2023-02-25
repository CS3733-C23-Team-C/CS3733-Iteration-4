package edu.wpi.cs3733.C23.teamC.objects.math;

import lombok.Getter;
import lombok.Setter;

public class Vector2 {
  @Getter @Setter private double x;
  @Getter @Setter private double y;

  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2(Vector2 other) {
    x = other.x;
    y = other.y;
  }

  public Vector2 add(Vector2 addend) {
    x += addend.x;
    y += addend.y;
    return this;
  }

  public Vector2 subtract(Vector2 subtrahend) {
    x -= subtrahend.x;
    y -= subtrahend.y;
    return this;
  }

  public Vector2 multiply(double multiplicand) {
    x *= multiplicand;
    y *= multiplicand;
    return this;
  }

  public Vector2 divide(double divisor) {
    x /= divisor;
    y /= divisor;
    return this;
  }

  public static Vector2 plus(Vector2 lhsAddend, Vector2 rhsAddend) {
    return new Vector2(lhsAddend).add(rhsAddend);
  }

  public static Vector2 minus(Vector2 minuend, Vector2 subtrahend) {
    return new Vector2(minuend).subtract(subtrahend);
  }

  @Override
  public String toString() {
    return String.format("(%f, %f)", x, y);
  }
}
