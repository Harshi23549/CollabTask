# CollabTask 📝✨
### *Smart Task Management & Collaboration, Simplified.*

Welcome to **CollabTask**! This is a lightweight, desktop-based task management and collaboration tool designed to help team leads and members stay perfectly aligned, track their work, and hit deadlines without the clutter of heavy, over-engineered tools.

Built using Java Swing for a clean desktop user interface and SQLite for a zero-configuration local database, CollabTask focuses on what matters: clarity, accountability, and tracking.

---

## 🌟 What makes CollabTask special?

We designed CollabTask around two distinct roles to match how real teams work:

### 👑 The Leader Dashboard
As a **Leader**, you have the birds-eye view:
- **Team Progress Bar**: Instantly see how many tasks have been completed by the entire team.
- **Task Management**: Create tasks, assign them to team members, set clear deadlines, and clean up completed/outdated tasks.
- **Instant Reporting**: Generate a clean `report.txt` file summarizing the team's metrics with a single click.

### 👥 The Member Dashboard
As a **Member**, your focus is kept simple and neat:
- **Personalized Progress**: A progress bar showing just *your* tasks so you know exactly where you stand.
- **Status Updates**: Update the state of your tasks (from *Pending* to *In Progress* or *Done*) as you work on them.
- **Deadline Alerts**: The moment you log in, CollabTask checks for any tasks that are past their deadline and displays a friendly warning banner to keep you on track.

---

## 🛠️ The Technical Blueprint

CollabTask is organized using a clean, separation-of-concerns architecture similar to **MVC (Model-View-Controller)**:

```
CollabTask/
├── Main.java                 # Application entry point
├── model/                    # Data blueprints (User, Task, Project)
├── dao/                      # Data Access Objects (direct database queries)
├── service/                  # Business logic (auth, task management, alerts)
├── ui/                       # Swing UI screens (Login, Register, Dashboard, Task UI)
├── utils/                    # Shared helpers (DB connections, validators)
└── lib/                      # External dependencies (SQLite JDBC driver)
```

- **Database**: SQLite (local file database: `collabtask.db`)
- **UI Framework**: Java Swing & AWT (featuring custom-drawn circular user avatars and clean, warm color schemes)
- **Dependency**: SQLite JDBC library

---

## 🚀 Getting Started

Setting up and running CollabTask is straightforward and requires no complex setups.

### Prerequisites
Make sure you have **Java Development Kit (JDK 21 or later)** installed on your machine.

### 1. Compile the Project
Open a command prompt in the project root directory and run:
```cmd
compile.bat
```
This compiles all Java source files and safely stores them in an `out/` directory.

### 2. Run the Application
Start the application using:
```cmd
run.bat
```
This launches the Login screen. If it's your first time, you can click on **Register** to create a new user profile as either a *Leader* or *Member*.

---

## 💡 How It Works Under the Hood
1. **DB Initialization**: On startup, `DBConnection.initialize()` checks if `collabtask.db` exists. If not, it creates it and prepares the necessary tables (`Users` and `Tasks`).
2. **Session Security**: When logging in, the `AuthService` queries the `Users` table via `UserDAO`. Once validated, the user is navigated to their custom dashboard, keeping track of their login email and permissions throughout the session.
3. **Task Lifecycle**: All tasks are tracked dynamically. Modifying a task status immediately recalculates dashboard progress bars in real-time.
