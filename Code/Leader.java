package test;
import java.util.*;

public class Leader extends User implements Executor, Monitor{
  ArrayList<Team> teams = new ArrayList<>();
  ArrayList<Task<Leader>> tasks = new ArrayList<>();

  public Leader(int id, String email, String name, String pswd){
    super(id, email,name,pswd);
  }

  public void trackProgress(){}
  public <U extends Executor> void trackProgress(Team team, U user){}
  public void trackProgress(Team team) {}

  public Leader(User user){
    super(user.getID(), user.getEmail(),user.getName(),user.getPswd());
  }

  
  public void displayLeaderDetails() {
      System.out.println("Leader Details:");
      System.out.println("ID: " + getID());
      System.out.println("Name: " + getName());
      System.out.println("Email: " + getEmail());
      
      System.out.println("\nTeams:");

      for (Team team : teams) {
          System.out.println("Team ID: " + team.tid);
          System.out.println("Team Name: " + team.tname);

          System.out.println("\tMembers:");
          for (Member member : team.members) {
              System.out.println("\t\tMember ID: " + member.getID());
              System.out.println("\t\tMember Name: " + member.getName());
              System.out.println("\t\tMember Email: " + member.getEmail());
              
          }

          System.out.println("\tTrackers:");
          for (Tracker tracker : team.trackers) {
              System.out.println("\t\tTracker ID: " + tracker.getID());
              System.out.println("\t\tTracker Name: " + tracker.getName());
              System.out.println("\t\tTracker Email: " + tracker.getEmail());
              
          }
      }
  }

  // CHANGE
  public Team createTeam(String tname){
	  
    Team t=new Team();
    t.tid=++Team.count;
    t.tname=tname;
    t.leader=this;
    teams.add((t));
    return t;
  }

  // CHANGE
  public void deleteTeam(Team team) throws Exception {
    if (teams.contains(team)) 
    { 
      teams.remove(team);
      for (Member member: team.members)
      {
    	  member.tasks.removeIf(task -> task.team==team);
      }
    }
    
    else throw new TeamNotFoundException("");
  }

  //CHANGE
  public void addMember(Team team, int id) throws Exception {
	  
	  
      if (teams.contains(team)) {
      Member newmem = Misc.search(Misc.members,id);
      if (newmem!=null) {
    	
        team.members.add(newmem);
        System.out.println("Added");
      }
      else throw new UserNotFoundException("The member is not found");    
    }
    else throw new TeamNotFoundException("The team is not found");  
  }
  
  //CHANGE
  public void removeMember(Team team, Member member) throws Exception {
	    if (!teams.contains(team)) throw new TeamNotFoundException("");
	    if (!team.members.contains(member)) throw new MemberNotInTeamException(""); 

	    Iterator<Task<Member>> iterator = member.tasks.iterator();
	    while (iterator.hasNext()) {
	        Task<?> task = iterator.next();
	        if (task.team == team) {
	            iterator.remove();
	            team.unassigned.add(task);
	        }
	    }

	    team.members.remove(member);
	}

  //CHANGE
  public void addTracker(Team team, int id) throws Exception {
    if (teams.contains(team)) {
      Tracker tracker = Misc.search(Misc.trackers, id); 
      if (tracker!=null) {
        team.trackers.add(tracker);
      }
      else throw new UserNotFoundException("");
    }
    else throw new TeamNotFoundException("");
  }

  // CHANGE
  public void removeTracker(Team team, Tracker tracker) throws Exception {
    if (!teams.contains(team)) throw new TeamNotFoundException("");
    if (!team.trackers.contains(tracker)) throw new TrackerNotInTeamException(""); 

    team.trackers.remove(tracker); 
  }
  
  // CHANGE

 
public Task<?> createTask(Team team, String desc, float weightage, String deadline) throws Exception {
    Task<?> t1=new Task<>();
    if (!teams.contains(team)) throw new TeamNotFoundException("");
    t1.ID=++Task.count;
    t1.team=team;
    t1.desc=desc;
    t1.weightage=weightage;
    t1.deadline=deadline;
    t1.status=0;
    team.unassigned.add(t1);
    return t1;
  }

  public void assignTasktoSelf(Task<?> task,Team team) throws Exception {
	    if (!teams.contains(team)) throw new TeamNotFoundException("");
	    if (!team.unassigned.contains(task)) throw new TaskNotFoundException(""); 

	      @SuppressWarnings("unchecked")
		Task<Leader> t1= (Task<Leader>)task;
	      t1.user=this;

	      tasks.add(t1);
	      return;
  }  
  
  public void assignTask(Task<?> task,Team team, Member member) throws Exception {
    if (!teams.contains(team)) throw new TeamNotFoundException("");
    if (!team.unassigned.contains(task)) throw new TaskNotFoundException("");
    if (!team.members.contains(member)) throw new MemberNotInTeamException(""); 
    @SuppressWarnings("unchecked")
	Task<Member> t1= (Task<Member>)task;
    t1.user=member;

    member.tasks.add(t1);
  }

  void deleteTask(Task<?> task, Team team) throws  Exception{
    if (!teams.contains(team)) throw new TeamNotFoundException("");
    if (tasks.contains(task)) tasks.remove(task);
    else if (team.unassigned.contains(task)) team.unassigned.remove(task);
    else {
      boolean found=false;
      for (Member member : team.members) {
        if (member.tasks.contains(task)){
          member.tasks.remove(task);
          Task.count--;
          found=true;
        }
      }
      if (!found) throw new TaskNotFoundException(""); 
    }
  }

  // ISOKA CHANGED
  public void updateProgress (ArrayList<Task<Leader>> tasksToUpdate, int status) {
      for (Task<?> task : tasks) {
          for (Task<?> taskToUpdate : tasksToUpdate) {
              // Compare tasks based on their IDs
              if (task.ID == taskToUpdate.ID) {
                  task.status = status;
                  break;  // No need to check further
              }
          }
      }
  }

  public class TeamNotFoundException extends Exception {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

      TeamNotFoundException(String m) {
          super(m);
          this.msg = m;
      }
  }

  // Inner class for UserNotFoundException
  public class UserNotFoundException extends Exception {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

      UserNotFoundException(String m) {
          super(m);
          this.msg = m;
      }
  }

  // Inner class for MemberNotInTeamException
  public class MemberNotInTeamException extends Exception {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

      MemberNotInTeamException(String m) {
          super(m);
          this.msg = m;
      }
  }

  // Inner class for TrackerNotInTeamException
  public class TrackerNotInTeamException extends Exception {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

      TrackerNotInTeamException(String m) {
          super(m);
          this.msg = m;
      }
  }

  // Inner class for TaskNotFoundException
  public class TaskNotFoundException extends Exception {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

      TaskNotFoundException(String m) {
          super(m);
          this.msg = m;
      }
  }
}