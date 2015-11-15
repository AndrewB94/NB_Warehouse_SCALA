package com.qa.Logic

import scalafx.collections.ObservableBuffer
import com.qa.Loader.LocationLineLoad
import scala.collection.mutable.Queue
import scala.collection.mutable.PriorityQueue

/**
 * @author abutcher
 */
class TravelingSales(warehouse: Array[Array[Int]] /*, items: ObservableBuffer[Int]*/ ) {
  var result: ObservableBuffer[ObservableBuffer[Int]] = new ObservableBuffer[ObservableBuffer[Int]]

  def findRoute(startX: Int, startY: Int, finishX: Int, finishY: Int): ObservableBuffer[Coord] = {

    var start: Coord = new Coord(1, startX, startY, 0)

    var frontier = new PriorityQueue[Coord]
    frontier.+=(start)
    var closedList: ObservableBuffer[Coord] = new ObservableBuffer[Coord]
    closedList.append(start)

    def router {
      if (!frontier.isEmpty) {
        val current = frontier dequeue


        if (current.x != finishX && current.y != finishY) {

          var cost = 1000
          var prority = 0

          try {
            val nextX = current.x
            val nextY = current.y - 1
            println(nextX + ", " + nextY)
            if (warehouse(nextX)(nextY) == 0) {
              cost = 1
              prority = 1
            }
            var neighbourNorth = new Coord(prority, nextX, nextY, cost)

            if (!checkifInClosedList(neighbourNorth, closedList)) {
              frontier.+=(neighbourNorth)
              neighbourNorth.camFrom = current
            }
          } catch {
            case e: Exception => println("Found Edge of Map North")
          }

          try {
            val nextX = current.x
            val nextY = current.y +1
            println(nextX + ", " + nextY)
            cost = 1000
            prority = 0
            if (warehouse(nextX)(nextY) == 0) {
              cost = 1
              prority = 1
            }
            var neighbourSouth = new Coord(prority,nextX ,  nextY, cost)

            if (!checkifInClosedList(neighbourSouth, closedList)) {
              frontier.+=(neighbourSouth)
              neighbourSouth.camFrom = current
            }
          } catch {
            case e: Exception => println("Found Edge of Map South")
          }

          try {
            val nextX = current.x - 1
            val nextY = current.y
            println(nextX + ", " + nextY)
            cost = 1000
            prority = 0
            if (warehouse(nextX)(nextX) == 0) {
              cost = 1
              prority = 1
            }
            var neighbourWest = new Coord(prority, nextX, nextX, cost)

            if (!checkifInClosedList(neighbourWest, closedList)) {
              frontier.+=(neighbourWest)
              neighbourWest.camFrom = current
            }
          } catch {
            case e: Exception => println("Found Edge of Map West")
          }

          try {
            val nextX = current.x + 1
            val nextY = current.y
            println(nextX + ", " + nextY)
            cost = 1000
            prority = 0
            if (warehouse(nextX)(nextY) == 0) {
              cost = 1
              prority = 1
            }
            var neighbourEast = new Coord(prority, nextX, nextY, cost)

            if (!checkifInClosedList(neighbourEast, closedList)) {
              frontier.+=(neighbourEast)
              neighbourEast.camFrom = current
            }
          } catch {
            case e: Exception => println("Found Edge of Map East")
          }

        }
      }
      router
    }
    router
    findRoute(closedList(closedList.length - 1))
  }

  def calculate = {
    println(findRoute(1, 1, 6, 6))
  }

  /**
   * Function to check if a coord is in the cameFromBuffer
   * @param next the coord to look for
   * @param cameFrom the observable buffer to search through
   * @return isIn boolean that is true if found
   */
  def checkifInClosedList(next: Coord, cameFrom: ObservableBuffer[Coord]): Boolean = {
    var isIn = false
    cameFrom.foreach { compare =>
      if (compare.x == next.x && compare.y == next.y)
        isIn = true
    }
    isIn
  }

  /**
   * Function to track back through the route
   * @param end the end coordinate of the path
   * @return buffer of the route of coord's
   */
  def findRoute(end: Coord): ObservableBuffer[Coord] = {
    var buffer: ObservableBuffer[Coord] = new ObservableBuffer[Coord]
    def trackBack(next: Coord): Unit = {
      buffer.append(next)
      if (next.camFrom != null) {
        trackBack(next.camFrom)
      }
    }
    trackBack(end)
    buffer
  }

  case class Coord(var prio: Int, x: Int, y: Int, cost: Int, var camFrom: Coord, var costSoFar: Int) extends Ordered[Coord] {
    def this(prio: Int, x: Int, y: Int, cost: Int) = this(prio, x, y, cost, null, 100000000)
    def compare(that: Coord) = that.prio compare this.prio
  }
}