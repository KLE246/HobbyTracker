package model;

// A milestone represents a moment during a hobby progression worth saving to look back on
// It has a title and a savedTime that is set. A final area is left for a description.
public class Milestone {

    public String title;
    public DatedHour savedTime;
    public String description;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates a Milestone with given string as the title
    public Milestone(String title) {
        this.title = title;
    }

    // REQUIRES: savedTime has not yet been set
    // MODIFIES: this
    // EFFECTS: adds the time to be saved for this milestone
    public void setTime(int totalProgress) {
        savedTime = new DatedHour(totalProgress);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a description to the milestone
    public void setDescription(String description) {
        this.description = description;
    }
}


