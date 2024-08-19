package test;
import java.util.*;

class Team
{
  public static int count=0;
  public int tid;
    public String tname;
    public Leader leader;
    public ArrayList<Tracker> trackers = new ArrayList<>();
    public ArrayList<Member> members = new ArrayList<>();
    public ArrayList<Task<?>> unassigned = new ArrayList<>();
    
    public String getTeamMembersString() {
        StringBuilder teamMembersString = new StringBuilder();

        for (Member member : members) {
            teamMembersString.append(member.getName()).append(", ");
        }

        // Remove the trailing comma and space
        if (teamMembersString.length() > 0) {
            teamMembersString.setLength(teamMembersString.length() - 2);
        }

        return teamMembersString.toString();
    }
    
    public String getTeamTrackersString() {
        StringBuilder teamTrackersString = new StringBuilder();

        for (Tracker tracker : trackers) {
            teamTrackersString.append(tracker.getName()).append(", ");
        }

        // Remove the trailing comma and space
        if (teamTrackersString.length() > 0) {
            teamTrackersString.setLength(teamTrackersString.length() - 2);
        }

        return teamTrackersString.toString();
    }
    
    // CHANGE
    public Member findMemberWithLeastTasks() {
        if (members.isEmpty()) {
            return null;
        }

        Member memberWithLeastTasks = members.get(0);

        for (Member member : members) {
            if (member.tasks.size() < memberWithLeastTasks.tasks.size()) {
                memberWithLeastTasks = member;
            }
        }

        return memberWithLeastTasks;
    }
    
  
    // CHANGE
  
    public Member findMemberWithMaxCompletionPercentage() {
        if (members.isEmpty()) {
            return null;
        }

        Member memberWithMaxCompletionPercentage = members.get(0);

        for (Member member : members) {
            if (member.getCompletionPercentage()> memberWithMaxCompletionPercentage.getCompletionPercentage()) {
                memberWithMaxCompletionPercentage = member;
            }
        }

        return memberWithMaxCompletionPercentage;
    }
    
}