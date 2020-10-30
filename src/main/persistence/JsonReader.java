package persistence;
// class taken from JsonSerializationDemo then modified

import model.DatedHour;
import model.Hobby;
import model.HobbyList;
import model.Milestone;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Stream;

// Represents a reader that reads hobbyList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads hobbyList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HobbyList read() throws IOException, ParseException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHobbyList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses hobbyList from JSON object and returns it
    private HobbyList parseHobbyList(JSONObject jsonObject) throws ParseException {
        String name = jsonObject.getString("name");
        HobbyList hobbyList = new HobbyList();
        hobbyList.setName(name);
        addHobbies(hobbyList, jsonObject);
        return hobbyList;
    }

    // MODIFIES: hobbyList
    // EFFECTS: parses hobbies from JSON object and adds them to hobbyList
    private void addHobbies(HobbyList hobbyList, JSONObject jsonObject) throws ParseException {
        JSONArray jsonArray = jsonObject.getJSONArray("Hobbies");
        for (Object json : jsonArray) {
            JSONObject nextHobby = (JSONObject) json;
            addHobby(hobbyList, nextHobby);
        }
    }

    // MODIFIES: hobbyList
    // EFFECTS: parses Hobby from JSON object and adds it to hobbyList
    private void addHobby(HobbyList hobbyList, JSONObject jsonObject) throws ParseException {
        String name = jsonObject.getString("name");
        int totalProgress = jsonObject.getInt("total progress");

        Hobby hobby = new Hobby(name);
        hobby.setTotalProgress(totalProgress);

        LinkedList<DatedHour> progressList = getProgressList(hobby, jsonObject);
        hobby.setProgressList(progressList);

        LinkedList<Milestone> milestoneList = getMilestoneList(hobby, jsonObject);
        hobby.setMilestoneList(milestoneList);
        hobbyList.addHobby(hobby);
    }

    // MODIFIES:
    // EFFECTS: creates a milestone list from the JSON object
    private LinkedList<Milestone> getMilestoneList(Hobby hobby, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("milestone list");
        LinkedList<Milestone> milestoneList = new LinkedList<>();
        Milestone milestoneEntry = null;
        for (Object json : jsonArray) {
            JSONObject milestoneEntryJson = (JSONObject) json;
            try {
                milestoneEntry = makeMilestoneEntry(milestoneEntryJson);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            milestoneList.add(milestoneEntry);
        }
        return milestoneList;
    }

    // MODIFIES:
    // EFFECTS: returns one milestone entry with fields set accordingly
    private Milestone makeMilestoneEntry(JSONObject milestoneJson) throws ParseException {
        String title = milestoneJson.getString("title");
        String description = milestoneJson.getString("description");

        JSONObject savedTimeJson = milestoneJson.getJSONObject("savedTime");
        String dateString = savedTimeJson.getString("date");
        Date savedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateString);
        int savedHour = savedTimeJson.getInt("progressHour");
        DatedHour savedTime = new DatedHour(savedHour, savedDate);

        Milestone milestone = new Milestone(title, description, savedTime);
        return milestone;
    }

    // EFFECTS: makes list of progress from JSONArray
    private LinkedList<DatedHour> getProgressList(Hobby hobby, JSONObject jsonObject) throws ParseException {
        JSONArray jsonArray = jsonObject.getJSONArray("progress list");
        LinkedList<DatedHour> progressList = new LinkedList<>();
        DatedHour progressEntryDatedHour = null;
        for (Object json : jsonArray) {
            JSONObject progressEntry = (JSONObject) json;
            progressEntryDatedHour = makeProgressEntry(progressEntry);
            progressList.add(progressEntryDatedHour);
        }
        return progressList;
    }

    // MODIFIES:
    // EFFECTS: returns one progress entry with set dated hour
    private DatedHour makeProgressEntry(JSONObject progressEntry) throws ParseException {
        String dateString = progressEntry.getString("date");
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateString);
        int progressHour = progressEntry.getInt("progressHour");
        DatedHour datedHour = new DatedHour(progressHour, date);
        return datedHour;
    }
}
