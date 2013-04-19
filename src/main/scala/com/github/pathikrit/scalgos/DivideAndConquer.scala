package com.github.pathikrit.scalgos

import scala.math.Ordered._

/**
 * Collection of divide and conquer algorithms
 */
object DivideAndConquer {

  /**
   * Finds largest rectangle (parallel to axes) under histogram with given heights and unit width
   * Basically creates min-heap with smallest height at root plus its left and right
   * O(n * depth-of-heap)
   * A faster O(n) worst case DP algorithm exists
   *
   * @param heights heights of histogram
   * @return area of largest rectangle under histogram
   */
  def maxRectangleInHistogram(heights: Seq[Int]): Int = if (heights isEmpty) 0 else {
    val (left, smallest :: right) = heights splitAt (heights indexOf heights.min)
    Seq(maxRectangleInHistogram(left), smallest * heights.length, maxRectangleInHistogram(right)).max
  }

  /**
   * Generic binary search in (min,max) f to achieve target goal
   * O(log n)
   *
   * @param f the function to binary search over - most be monotonically increasing
   * @param min starting minimum guess (must be exclusive)
   * @param max starting maximum guess (must be exclusive)
   * @param avg mid function usually (min+max)/2
   * @param goal target to achieve
   * @tparam A input type of f
   * @tparam B output type of f
   * @return Some(x) such that f(x) is goal else None
   */
  def binarySearch[A: Ordering, B: Ordering](f: A => B, min: A, max: A, avg: (A, A) => A, goal: B): Option[A] = {
    if (min >= max) {
      None
    } else {
      val mid = avg(min, max)
      f(mid) compare goal match {
        case  1 => binarySearch(f, min, mid, avg, goal)
        case -1 => binarySearch(f, mid, max, avg, goal)
        case  0 => Some(mid)
      }
    }
  }
}