package model.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler {
    private HistoryEntry rootEntry;

    public HistoryHandler(){
        this.rootEntry=null;
    }

    public HistoryEntry getRootEntry(){
        return this.rootEntry;
    }

    /**
     * Add an item to history, taking care to delete anything that would come after our latest entry
     * @param historyEntry The history entry to add
     */
    public void addToHistory(HistoryEntry historyEntry){
        if (rootEntry == null){ rootEntry = historyEntry; }
        else{
            HistoryEntry currentEntry = rootEntry;
            while (true){
                //If we have gone back in history
                if (currentEntry.getMark()){
                    currentEntry.unMark();
                    historyEntry.setNextEntry(null);
                    currentEntry.setNextEntry(historyEntry);
                    return;
                }

                //Adding normally
                if (currentEntry.getNextEntry() == null){
                    currentEntry.setNextEntry(historyEntry);
                    return;
                }
                currentEntry = currentEntry.getNextEntry();
            }
        }
    }

    /**
     * Gets the entry passed into the function, uses memory address not string matching to make sure we dont clear duplicates
     * @param s the selected entry's string
     * @return the entry with said string
     */
    public HistoryEntry getHistoryWithName(String s){
        HistoryEntry currentEntry = rootEntry;
        while (currentEntry != null){
            if (currentEntry.getName()==s){return currentEntry;} //Needs to be == to deal with duplicates
            currentEntry = currentEntry.getNextEntry();
        }
        return null;
    }

    public void clearHistory(){
        this.rootEntry=null;
    }

    /**
     * Gets the history of the application
     * @return a list of history entries
     */
    public List<HistoryEntry> getHistory(){
        ArrayList<HistoryEntry> returnList = new ArrayList<>();
        HistoryEntry currentEntry = rootEntry;
        while (currentEntry != null){
            returnList.add(currentEntry);
            currentEntry = currentEntry.getNextEntry();
        }
        return returnList;
    }
}
