# Bin Packing Problem – Java & JavaFX Visualization

This project is a Java-based implementation and visualization of the **Bin Packing Problem**, a well-known **NP-complete combinatorial optimization problem**.  
The application provides an interactive JavaFX user interface to generate instances, run algorithms, and visually inspect packing solutions.

---

## 📦 Problem Description

The **Bin Packing Problem (BPP)** consists of placing a set of rectangles into the minimum number of fixed-size square bins without overlap.

- Each rectangle has a width and height
- All bins have the same fixed length
- Rectangles may be rotated (optional)
- The objective is to minimize the number of bins used

The Bin Packing Problem is **NP-complete**, meaning no known polynomial-time algorithm can solve all instances optimally.

---

## 🛠 Technologies Used

- **Java (JDK 21+)**
- **JavaFX** (UI and visualization)
- **FXML** (UI layout)
- **MVC architecture**
- Custom algorithm implementations

---

## 🚀 Features

- Interactive **JavaFX GUI**
- Random instance generation with configurable parameters
- Support for multiple algorithms:
  - **Greedy algorithms**
    - Area-based strategy
    - Height-based strategy
  - (Extensible for Local Search / Metaheuristics)
- Real-time visualization of solutions
- Display of:
  - Used bins
  - Packed rectangles
  - Runtime of the algorithm
- Bottom-left placement strategy visualization
- Scrollable solution view

---

## 🧠 Algorithms

### Greedy Algorithms
Greedy strategies place rectangles sequentially based on a heuristic:

- **Area-based**: Larger rectangles are placed first
- **Height-based**: Taller rectangles are prioritized

Each rectangle is placed using a **Bottom-Left placement heuristic**, ensuring feasible and compact packing.

### Local Search (Planned / Extensible)
The architecture allows future integration of:
- Geometry-based neighborhoods
- Rule-based 
- Partially Overlapped

## Running Steps
Use the bash script to execute the JavaFX App
```bash
bash run.sh
```
