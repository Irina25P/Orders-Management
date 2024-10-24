# Description
- This project implements a queue management application aimed at minimizing the waiting time for clients in a multi-queue system.
- The application simulates the arrival, waiting, and processing of clients, ensuring that they are allocated to the queue with the shortest waiting time.
- It features a graphical user interface (GUI) to input parameters, observe real-time simulation, and visualize the state of each queue and client throughout the simulation.

# Features
- Client Management: Simulates a configurable number of clients, each with unique attributes like arrival time and service duration.
- Queue Management: Manages multiple queues using a Server class, each operating independently through multithreading.
- Scheduling Strategies: Includes two main scheduling strategies:
  - ShortestQueueStrategy: Allocates clients to the queue with the fewest clients.
  - TimeStrategy: Allocates clients based on the shortest estimated waiting time.
- Concurrency Handling: Uses threads for parallel processing of clients and synchronized data structures to ensure thread safety.
- Real-time Simulation: Displays real-time updates of client allocation, queue states, and simulation logs through a Java Swing-based GUI.
- Statistics Calculation: Computes average waiting time, average service time, and identifies peak usage times.

# Project Structure
- Main Class (Main): Entry point of the application, initializes the GUI and starts the simulation.
- GUI (SimulationFrame): Java Swing-based interface for user interaction, inputting simulation parameters, starting the simulation, and displaying logs.
- Model:
  - Task: Represents a client with attributes like arrivalTime, serviceTime, and waitTime.
  - Server: Manages a queue of tasks and processes them concurrently.
  - Scheduler: Distributes tasks to servers based on the selected scheduling strategy.
- Business Logic:
  - SimulationManager: Central controller for managing simulation time, generating tasks, and coordinating the processing of clients.
  - Strategy Interface: Defines the contract for task allocation strategies, implemented by ShortestQueueStrategy and TimeStrategy.
  - Results: Utility class for calculating and logging statistics.
