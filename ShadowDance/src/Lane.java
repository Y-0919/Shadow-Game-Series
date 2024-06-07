import bagel.*;

import java.util.ArrayList;

public class Lane {
    private static final int HEIGHT = 384;
    public static final int TARGET_HEIGHT = 657;
    private final Image image;
    private final ArrayList<Note> notes = new ArrayList<>();
    private final int location;
    private Keys relevantKey;
    private int currNoteIndex = 0;

    public Lane(Keys relevantKey, int location, String imageLocation) {
        this.relevantKey = relevantKey;
        this.location = location;
        this.image = new Image(imageLocation);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public Keys getRelevantKey() {
        return relevantKey;
    }

    public int getLocation() {
        return location;
    }

    public int update(Input input, Accuracy accuracy, int currFrame) {
        draw();

        int numNotes = notes.size();

        /*
        if (input.wasPressed(relevantKey)) {
            System.out.println(relevantKey + "  " + (Level.getCurrFrame()- 557/2));
        }
        */

        for (int i = currNoteIndex; i < numNotes; i++) {
            Note n = notes.get(i);
            n.update(currFrame);
        }

        if (currNoteIndex < numNotes) {
            Note currNote = notes.get(currNoteIndex);
            int score = currNote.checkScore(input, accuracy, this);

            if (currNote.isCompleted()) {
                currNoteIndex++;
            }

            return score;
        }
        return Accuracy.NOT_SCORED;
    }

    public void addNote(Note n) {
        notes.add(n);
    }

    public boolean isFinished() {
        for (Note n : notes) {
            if (!n.isCompleted()) {
                return false;
            }
        }

        return true;
    }

    public void deactivateAllActive() {
        for (Note n : notes) {
            if (n.isActive()) {
                n.deactivate();
            }
        }
    }

    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNoteIndex; i < notes.size(); i++) {
            notes.get(i).draw();
        }
    }
}
