# Farees_Development

**Project Status: Work In Progress (Not finished)**

This is a pathfinding visualization program, that is coded in java using the `processing` library for the graphics. Using this program the user will be able to visualize pathfinding algorithms such as `A* Search` and Dijkstra's search algorithm.

## How to use this program
You can select between 5 different search algorithms
- Dijkstra's
- A* Search
- Greedy Best First Search
- Depth First Search
- Breath First Search

Then you must select a start and end node. The first mouse click will select the start node and color it green, the second mouse click will place the end node and color it red.
After the start and end nodes have been selected you can hold down the mouse and draw walls if you wish. Then you can either click the `visualize` button or press the space bar and watch the program find the shortest route to the end node.

##### Controls
- `D` will start debug mode and print the amount of start, end, and wall nodes present in the current scene to the console
- `R` will reset the grid and get rid of all nodes
- `SPACE` will start the program and visualize the shortest path between the end and start node


### To run this program in eclipse
1. Download the `processing` library from https://processing.org/download/
2. Open Eclipse and click `file>Import>General>File System`
3. On the `From Directory` tab click `Browse` and navigate to your unzipped processing folder
4. Navigate to the  `library` folder, for me this is `processing-3.5.4/core/library` and click select folder
5. from the selections that pop up on the right side click the checkbox for `core.jar` and click finish at the bottom
6. Now the `core.jar` file should be in your package explorer, right click it and add it to the build path by doing `Build Path>Add to build path`


#### TODO:
- Implement Pathfinding algorithms
- Beautify the UI
- Add Animations
- Add Live recalculations for the search algorithms
