package test;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Task<U extends User & Executor> {
    public static int count = 0;
    public int ID;
    public U user;
    public Team team;
    public String desc;
    public int status;
    public float weightage;
    public String deadline;

    @Override
    public String toString() {
        return "Task ID: " + ID +
                "\nAssigned User: " + ((user != null) ? user.getName() : "Unassigned") +
                "\nTeam: " + ((team != null) ? team.tname : "No Team") +
                "\nDescription: " + desc +
                "\nStatus: " + status +
                "\nWeightage: " + weightage +
                "\nDeadline: " + deadline;
    }

    public static <U extends User & Executor> ArrayList<Task<U>> filter(ArrayList<Task<U>> tasks, int status) {
        ArrayList<Task<U>> tasksWithStatus = new ArrayList<>();
        for (Task<U> task : tasks) {
            if (task.status == status) {
                tasksWithStatus.add(task);
            }
        }
        return tasksWithStatus;
    }

    public static <U extends User & Executor> ArrayList<Task<U>> filter(ArrayList<Task<U>> tasks, String tname) {
        ArrayList<Task<U>> tasksWithTeam = new ArrayList<>();
        for (Task<U> task : tasks) {
            if (task.team.tname.equals(tname)) {
                tasksWithTeam.add(task);

            }
            //System.out.println(tname + " "+ task.team.tname);
        }
        return tasksWithTeam;
    }

    public static <U extends User & Executor> ArrayList<Task<U>> filter(ArrayList<Task<U>> tasks) {
        ArrayList<Task<U>> tasksAfterToday = new ArrayList<>();
        Date today = new Date(); // Current date

        // Format for parsing and comparing dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Task<U> task : tasks) {
            try {
                Date deadlineDate = dateFormat.parse(task.deadline);
                if (deadlineDate.before(today) && task.status==0) {
                    tasksAfterToday.add(task);
                }
            } catch (ParseException e) {
                // Handle the parsing exception, if any
                e.printStackTrace();
            }
        }
        return tasksAfterToday;
    }

    public static ArrayList<Task<?>> filter_(ArrayList<Task<?>> tasks, int status) {
        ArrayList<Task<?>> tasksWithStatus = new ArrayList<>();
        for (Task<?> task : tasks) {
            if (task.status == status) {
                tasksWithStatus.add(task);
            }
        }
        return tasksWithStatus;
    }

    public static ArrayList<Task<?>> filter_(ArrayList<Task<?>> tasks, String user) {
        ArrayList<Task<?>> tasksWithTeam = new ArrayList<>();
        for (Task<?> task : tasks) {
            if (task.user!=null && task.user.getName().equals(user)) {
                tasksWithTeam.add(task);

            }
            //System.out.println(tname + " "+ task.team.tname);
        }
        return tasksWithTeam;
    }

    public static ArrayList<Task<Member>> filter_(ArrayList<Task<Member>> tasks, Team t) {
        ArrayList<Task<Member>> tasksWithTeam = new ArrayList<>();
        for (Task<Member> task : tasks) {
            if (task.team.equals(t)) {
                tasksWithTeam.add(task);

            }
            //System.out.println(tname + " "+ task.team.tname);
        }
        return tasksWithTeam;
    }
    
    public static ArrayList<Task<Leader>> filter_l(ArrayList<Task<Leader>> tasks, Team t) {
        ArrayList<Task<Leader>> tasksWithTeam = new ArrayList<>();
        for (Task<Leader> task : tasks) {
            if (task.team.equals(t)) {
                tasksWithTeam.add(task);

            }
            System.out.println(t.tname + " "+ task.team.tname);
        }
        return tasksWithTeam;
    }

    public static ArrayList<Task<?>> filter_(ArrayList<Task<?>> tasks) {
        ArrayList<Task<?>> tasksAfterToday = new ArrayList<>();
        Date today = new Date(); // Current date

        // Format for parsing and comparing dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Task<?> task : tasks) {
            try {
                Date deadlineDate = dateFormat.parse(task.deadline);
                if (deadlineDate.before(today) && task.status==0) {
                    tasksAfterToday.add(task);
                }
            } catch (ParseException e) {
                // Handle the parsing exception, if any
                e.printStackTrace();
            }
        }
        return tasksAfterToday;
    }
}
