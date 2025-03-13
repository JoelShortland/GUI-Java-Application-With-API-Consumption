package model.history;

import view.display.Display;

public class HistoryEntry {
    private String name;
    private Display display;
    private HistoryEntry nextEntry;
    private boolean markedToDeleteNextEntry=false;

    public HistoryEntry(String name, Display display) {
        this.name = name;
        this.display = display;
        this.nextEntry = null;
    }

    public boolean getMark(){
        return this.markedToDeleteNextEntry;
    }

    public void mark(){
        this.markedToDeleteNextEntry = true;
    }

    public void unMark(){
        this.markedToDeleteNextEntry = false;
    }

    public void setNextEntry(HistoryEntry historyEntry){
        this.nextEntry = historyEntry;
    }

    public HistoryEntry getNextEntry(){
        return nextEntry;
    }

    public String getName() {
        return name;
    }

    public Display getDisplay() {
        return display;
    }

    @Override
    public String toString() {
        return "HistoryEntry{" +
                "name='" + name + '\'' +
                ", display=" + display +
                '}';
    }
}
