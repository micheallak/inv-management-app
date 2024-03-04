package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// SOURCE: methods in this class modelled after Event Class from AlarmSystem
//         https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

// represents a log of store inventory events
public class EventLog implements Iterable<Event> {
    private static EventLog log;
    private Collection<Event> events;

    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: get instance of EventLog - creates it if it doesn't exist
    public static EventLog getInstance() {
        if (log == null) {
            log = new EventLog();
        }
        return log;
    }

    // EFFECTS: add event to eventlog
    public void logEvent(Event e) {
        events.add(e);
    }

    // EFFECTS: clear event log and logs event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log is cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
