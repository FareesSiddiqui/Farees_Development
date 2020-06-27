# Farees_Development

**Project Status: Work In Progress (Not finished)**

This is a pathfinding visualization program, that is coded in java using the `processing` library for the graphics. Using this program the user will be able to visualize pathfinding algorithms such as `A* Search` and Dijkstra's search algorithm.

## How to use this program
You can select between 5 different search algorithms
- Dijkstra's (Implemented on next push)
- A* Search
- Greedy Best First Search (Implemented on next push)
- Depth First Search (Implemented on next push)
- Breath First Search (Implemented on next push)

Then you must select a start and end node. The start node is selected in code and can be adjusted my adjusting the `start` variable in code, the end node is selected in code and can be adjusted by adjusting the `end` variable in code.
After the start and end nodes have been selected and the walls have been generated (automatic) you can click the left mouse button to start the visualization.

##### Controls
- `Left Mouse Button` will start the visualization

### To run this program in eclipse
1. Download the `processing` library from https://processing.org/download/
2. Open Eclipse and click `file>Import>General>File System`
3. On the `From Directory` tab click `Browse` and navigate to your unzipped processing folder
4. Navigate to the  `library` folder, for me this is `processing-3.5.4/core/library` and click select folder
5. from the selections that pop up on the right side click the checkbox for `core.jar` and click finish at the bottom
6. Now the `core.jar` file should be in your package explorer, right click it and add it to the build path by doing `Build Path>Add to build path`


#### TODO:
- Add user controllable start, end and wall nodes
- Beautify the UI / re-implement
- Add Live recalculations for the search algorithms
- Implement more algorithms, next one should be Greedy best first search;
