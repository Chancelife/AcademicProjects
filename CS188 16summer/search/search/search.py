# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()

class Node():
    def __init__(self, path, dire, cost):
        self.path = path
        self.dire = dire
        self.cost = cost

    def __str__(self):
        print str(self.path) + str(self.dire) + str(self.cost)

def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:
    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())
    """
    "*** YOUR CODE HERE ***"
    closed = []
    fringe = util.Stack()
    fringe.push(Node([problem.getStartState()], [], [0]))
    while (not fringe.isEmpty()):
        node = fringe.pop()
        thisPos = node.path[len(node.path)-1]
        if problem.isGoalState(thisPos):
            return node.dire
        if thisPos in closed:
            continue
        closed.append(thisPos)
        for childNode in problem.getSuccessors(thisPos):
            fringe.push(Node(node.path + [childNode[0]], node.dire + [childNode[1]], node.cost + [childNode[2]]))
    return []
    util.raiseNotDefined()

def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"
    closed = []
    fringe = util.Queue()
    fringe.push(Node([problem.getStartState()], [], [0]))
    while (not fringe.isEmpty()):
        node = fringe.pop()
        thisPos = node.path[0]
        if thisPos in closed :
            continue
        if problem.isGoalState(thisPos):
			return node.dire
        closed.append(thisPos)
        for childNode in problem.getSuccessors(thisPos):
            fringe.push(Node([childNode[0]], node.dire + [childNode[1]], node.cost + [childNode[2]]))
    return []

def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    closed = []
    fringe = util.PriorityQueue()
    fringe.push(Node([problem.getStartState()], [], [0]), 0)
    while (not fringe.isEmpty()):
        node = fringe.pop()
        thisPos = node.path[len(node.path) - 1]
        thisCost = node.cost[len(node.path) - 1]
        if thisPos in closed:
            continue
        closed.append(thisPos)
        if problem.isGoalState(thisPos):
            return node.dire
        for childNode in problem.getSuccessors(thisPos):
            newCost = thisCost + childNode[2]
            fringe.push(Node(node.path + [childNode[0]], node.dire + [childNode[1]], node.cost + [thisCost + childNode[2]]), thisCost + childNode[2])
    return []
    util.raiseNotDefined()

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    closed = []
    fringe = util.PriorityQueue()
    fringe.push(Node([problem.getStartState()], [], [0]), 0)
    while (not fringe.isEmpty()):
        node = fringe.pop()
        thisPos = node.path[len(node.path) - 1]
        thisCost = node.cost[len(node.path) - 1]
        if thisPos in closed:
            continue
        if problem.isGoalState(thisPos):
            return node.dire
        closed.append(thisPos)
        for childNode in problem.getSuccessors(thisPos):
            fringe.push(Node(node.path + [childNode[0]], node.dire + [childNode[1]], node.cost + [thisCost + childNode[2]]), thisCost + childNode[2] + heuristic(childNode[0],problem))
            #step 549 can pass autograder
    return []


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
