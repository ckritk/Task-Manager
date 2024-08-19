package test;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Misc {
    public static ArrayList<Leader> leaders = new ArrayList<>();
    public static ArrayList<Member> members = new ArrayList<>();
    public static ArrayList<Tracker> trackers = new ArrayList<>();

    public static void init() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/taskmanager";
            String username = "root";
            String password = "Root09";

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement ss = connection.createStatement()) {

                ResultSet rset = ss.executeQuery("select * from users");

                while (rset.next()) {
                    Leader leader = new Leader(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4));
                    Member member = new Member(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4));
                    Tracker tracker = new Tracker(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4));

                    leaders.add(leader);
                    members.add(member);
                    trackers.add(tracker);
                }

                ArrayList<Task<?>> tasks = new ArrayList<>();
                rset = ss.executeQuery("select * from task");
                while (rset.next()) {
                    int id = rset.getInt(1);
                    String desc = rset.getString(2);
                    int s = rset.getInt(3);
                    float w = (float) rset.getFloat(4);
                    String deadline = rset.getString(5);

                    Task.count++;
                    Task<?> task = new Task<>();
                    task.ID = id;
                    task.desc = desc;
                    task.status = s;
                    task.weightage = w;
                    task.deadline = deadline;

                    tasks.add(task);
                }
                
                ArrayList<Team> teams = new ArrayList<>();
                rset = ss.executeQuery("select * from teams");

                while (rset.next()) {
                    Team team = new Team();
                    Team.count++;
                    team.tid = rset.getInt(1);
                    team.tname = rset.getString(2);

                    Leader leader = search(leaders, rset.getInt(3));
                    team.leader = leader;
                    leader.teams.add(team);

                    List<Integer> tids = toList__(rset.getString(5));
                    List<Integer> unassigned = toList__(rset.getString(6));

                    for (int i : tids) {
                        Tracker t1 = search(trackers, i);
                        t1.teams.add(team);
                        team.trackers.add(t1);
                    }

                    for (int i : unassigned) {
                        Task<?> task = searchTask(tasks, i);
                        task.team = team;
                        team.unassigned.add(task);
                    }

                    teams.add(team);
                }

                rset = ss.executeQuery("select * from teamtasks");
                while (rset.next()) {
                    Team team = searchTeam(teams, rset.getInt(1));
                    if (rset.getInt(2) != team.leader.getID()) {
                        Member member = search(members, rset.getInt(2));
                        team.members.add(member);

                        List<Integer> tids = toList__(rset.getString(3));
                        for (int i : tids) {
                            Task<?> task = searchTask(tasks, i);
                            @SuppressWarnings("unchecked")
							Task<Member> task1 = (Task<Member>) task;
                            task1.user = member;
                            task1.team=team;
                            member.tasks.add(task1);
                        }
                    } else {
                        Leader leader = search(leaders, rset.getInt(2));

                        List<Integer> tids = toList__(rset.getString(3));
                        for (int i : tids) {
                            Task<?> task = searchTask(tasks, i);
                            @SuppressWarnings("unchecked")
							Task<Leader> task1 = (Task<Leader>) task;
                            task1.user = leader;
                            task1.team=team;
                            leader.tasks.add(task1);
                        }
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    
    static <T extends User> T search(ArrayList<T> users, int id) {
        for (T user : users) {
            if (user.getID() == id) return user;
        }
        return null;
    }
    
    
    static <T extends User> T searchName(ArrayList<T> users, String name) {
        for (T user : users) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }
    
    
    static Task<?> searchTask(ArrayList<Task<?>> tasks, int id) {
        for (Task<?> task : tasks) {
            if (task.ID == id) return task;
        }
        return null;
    }
    
    static Task<Member> searchTask(ArrayList<Task<Member>> tasks, String tname, String name) {
        for (Task<Member> task : tasks) {
            if (task.team.tname.equals(tname) && task.desc.equals(name)) return task;
        }
        return null;
    }
    

    static Task<Leader> searchTaskL(ArrayList<Task<Leader>> tasks, String tname, String name) {
        for (Task<Leader> task : tasks) {
            if (task.team.tname.equals(tname) && task.desc.equals(name)) return task;
        }
        return null;
    }

    static Team searchTeam(ArrayList<Team> teams, int id) {
        for (Team team : teams) {
            if (team.tid == id) return team;
        }
        return null;
    }
    
    static Team searchTeam(ArrayList<Team> teams, String tname) {
        for (Team team : teams) {
            if (team.tname.equals(tname)) return team;
        }
        return null;
    }

    static List<Integer> toList__(String integersString) {
        if (integersString.isEmpty()) {
            return Collections.emptyList();  // Return an empty list if the input string is empty
        }

        return Arrays.stream(integersString.split(","))
        		.map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
    
    public static int compareDates(String givenDateString) {
        return LocalDate.now().isAfter(LocalDate.parse(givenDateString, DateTimeFormatter.ISO_DATE)) ? 1 : 0;
    }
    
    static void insertTeamData(Connection connection, Team team) throws SQLException {
        String insertTeamQuery = "INSERT INTO Teams (TeamID, Name, Leader, TeamMembers, Trackers, Unassigned) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertTeamQuery)) {
            preparedStatement.setInt(1, team.tid);
            preparedStatement.setString(2, team.tname);
            preparedStatement.setInt(3, team.leader.getID()); // Assuming Leader class has a getName() method
            preparedStatement.setString(4, getMemberIdsAsString(team.members));
            preparedStatement.setString(5, getTrackerIdsAsString(team.trackers));
            preparedStatement.setString(6, getTaskIdsAsString(team.unassigned));
            preparedStatement.executeUpdate();
        }
    }
    
    static String getMemberIdsAsString(ArrayList<Member> members) {
        ArrayList<Integer> memberIds = new ArrayList<>();
        for (Member member : members) {
            memberIds.add(member.getID());
        }
        return joinIds(memberIds);
    }
    
    static String getTrackerIdsAsString(ArrayList<Tracker> trackers) {
        ArrayList<Integer> memberIds = new ArrayList<>();
        for (Tracker tracker : trackers) {
            memberIds.add(tracker.getID());
        }
        return joinIds(memberIds);
    }
    
    static String getTaskIdsAsString(ArrayList<Task<?>> tasks) {
        ArrayList<Integer> tIds = new ArrayList<>();
        for (Task<?> task : tasks) {
            tIds.add(task.ID);
        }
        return joinIds(tIds);
    }

    static String joinIds(ArrayList<Integer> ids) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            result.append(ids.get(i));
            if (i < ids.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    static void insertTask(Statement stmt, Task<?> task) throws SQLException {
        String insertTaskSQL = "INSERT INTO Task (ID, Description, Status, Weightage, Deadline) " +
                "VALUES (" + task.ID + ", '" + task.desc + "', " + task.status + ", " +
                task.weightage + ", '" + task.deadline + "')";
        stmt.executeUpdate(insertTaskSQL);
    }

    static void insertTeamTask(Statement stmt, int teamID, int taskID, int uid) throws SQLException {
        String selectTeamTasksSQL = "SELECT Tasks FROM TeamTasks WHERE TeamID=" + teamID;
        ResultSet resultSet = stmt.executeQuery(selectTeamTasksSQL);

        String taskIDs = "";
        if (resultSet.next()) {
            taskIDs = resultSet.getString("Tasks");
        }

        taskIDs = taskIDs.isEmpty() ? String.valueOf(taskID) : taskIDs + "," + taskID;

        String updateTeamTasksSQL = "INSERT INTO TeamTasks (TeamID, TeamMemberID, Tasks) " +
                "VALUES (" + teamID + ","+uid+", '" + taskIDs + "') " +
                "ON DUPLICATE KEY UPDATE Tasks='" + taskIDs + "'";
        stmt.executeUpdate(updateTeamTasksSQL);
    }
}