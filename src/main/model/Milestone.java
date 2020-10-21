package model;

// A milestone represents a moment during a hobby progression worth saving to look back on
// It has a title and a savedTime that is set. A final area is left for a description.
public class Milestone {

    private final String title;
    private DatedHour savedTime;
    private String description;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates a Milestone with title
    public Milestone(String title) {
        this.title = title;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates a Milestone with title, description,and stores the progress and time
    public Milestone(String title, String description, int totalProgress) {
        this.title = title;
        this.description = description;
        savedTime = new DatedHour(totalProgress);
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns name of the milestone
    public String getTitle() {
        return title;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns description of the milestone
    public String getDescription() {
        return description;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns time when milestone was created
    public DatedHour getSavedTime() {
        return savedTime;
    }
}


