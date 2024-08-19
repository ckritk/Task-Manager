package test;

import java.util.ArrayList;

public class Member extends User implements Executor, Monitor {
	public ArrayList<Task<Member>> tasks = new ArrayList<>();

	public Member(int id, String email, String name, String pswd) {
		super(id, email, name, pswd);
		////// Database update add/change in User
	}

	public Member(User user) {
		super(user.getID(), user.getEmail(), user.getName(), user.getPswd());
	}

	class UnsupportedOperationException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String msg;

		UnsupportedOperationException(String m) {
			super(m);
			this.msg = m;
		}
	}

	public void leaveTeam(Team team) {
		// Remove the member from the team's members list
		team.members.remove(this);

		// Remove all tasks associated with the team from the member's tasks list
		// tasks.removeIf(task -> task.team.equals(team));
		tasks.removeIf(task -> task.team == team);

		// If the member is the leader, set the team leader to null
		if (team.leader != null && team.leader.equals(this)) {
			team.leader = null;
		}
	}

	// Implementing Monitor and Executor methods

	public void trackProgress() {
		if (tasks.isEmpty()) {
			System.out.println("No tasks to track progress for.");
			return;
		}

		int totalTasks = tasks.size();
		int completedTasks = 0;

		for (Task<Member> task : tasks) {
			if (task.status == 1) {
				completedTasks++;
			}
		}

		double completionPercentage = (double) completedTasks / totalTasks * 100;

		System.out.println("Task Completion Percentage: " + completionPercentage + "%");
	}

	// Track progress for the member's tasks
	public <U extends Executor> void trackProgress(Team team, U user) {
		try {
			throw new UnsupportedOperationException("Members are not allowed to track progress for another member.");
		} catch (UnsupportedOperationException e) {
			System.out.println(e);
		}
	}

	// Track progress for the team
	public void trackProgress(Team team) {
		try {
			throw new UnsupportedOperationException("Members are not allowed to track progress for the team.");
		} catch (UnsupportedOperationException e) {
			System.out.println(e);
		}
	}

	public void updateProgress(ArrayList<Task<Member>> tasksToUpdate, int status) {
		for (Task<Member> memberTask : tasks) {
			for (Task<?> taskToUpdate : tasksToUpdate) {
				// Compare tasks based on their IDs
				if (memberTask.ID == taskToUpdate.ID) {
					memberTask.status = status;
					break; // No need to check further
				}
			}
		}
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Member ID: ").append(getID()).append("\n");
		stringBuilder.append("Email: ").append(getEmail()).append("\n");
		stringBuilder.append("Name: ").append(getName()).append("\n");

		// Append information about tasks
		stringBuilder.append("\nTasks:\n");
		for (Task<Member> task : tasks) {
			stringBuilder.append(task.toString()).append("\n\n");
		}

		return stringBuilder.toString();
	}

	// CHANGE
	public int getCompletionPercentage() {
		int comp = Task.filter(this.tasks, 1).size();

		int all = this.tasks.size();
		return comp * 100 / all;
	}

}
