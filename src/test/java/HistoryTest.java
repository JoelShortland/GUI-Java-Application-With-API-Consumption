import model.history.HistoryEntry;
import model.history.HistoryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the history feature
 */
public class HistoryTest {
    private HistoryEntry h1 = new HistoryEntry("Entry1", null);
    private HistoryEntry h2 = new HistoryEntry("Entry2", null);
    private HistoryEntry h3 = new HistoryEntry("Entry3", null);
    private HistoryEntry h4 = new HistoryEntry("Entry4", null);
    private HistoryEntry h5 = new HistoryEntry("Entry5", null);
    private HistoryEntry h1DuplicateName = new HistoryEntry("Entry1", null);

    @Test
    public void initialisationTest(){
        HistoryHandler historyHandler = new HistoryHandler();
        assertNull(historyHandler.getRootEntry());
    }

    @Test
    public void addHistoryTest(){
        HistoryHandler historyHandler = new HistoryHandler();
        historyHandler.addToHistory(h1);
        assertEquals(historyHandler.getRootEntry(), h1);
        historyHandler.addToHistory(h2);
        historyHandler.addToHistory(h3);
        assertEquals(historyHandler.getRootEntry(), h1);
        assertEquals(historyHandler.getHistoryWithName("Entry2"), h2);
        assertEquals(historyHandler.getHistoryWithName("Entry5"), null);
    }

    @Test
    public void clearHistoryTest(){
        HistoryHandler historyHandler = new HistoryHandler();
        historyHandler.addToHistory(h1);
        historyHandler.addToHistory(h2);
        historyHandler.addToHistory(h3);
        assertEquals(historyHandler.getRootEntry(), h1);
        assertEquals(historyHandler.getHistory().size(), 3);
        historyHandler.clearHistory();
        assertNull(historyHandler.getRootEntry());
        assertEquals(historyHandler.getHistory().size(), 0);
    }

    @Test
    public void getHistoryTest(){
        HistoryHandler historyHandler = new HistoryHandler();
        historyHandler.addToHistory(h1);
        historyHandler.addToHistory(h2);
        historyHandler.addToHistory(h3);
        List<HistoryEntry> historyEntries = historyHandler.getHistory();
        assertEquals(historyEntries.get(0), h1);
        assertEquals(historyEntries.get(1), h2);
        assertEquals(historyEntries.get(2), h3);
    }

    @Test
    public void addHistoryAfterRevertingToPreviousState(){
        HistoryHandler historyHandler = new HistoryHandler();
        historyHandler.addToHistory(h1);
        historyHandler.addToHistory(h2);
        historyHandler.addToHistory(h3);
        h2.mark();

        historyHandler.addToHistory(h4);
        List<HistoryEntry> historyEntries = historyHandler.getHistory();
        assertEquals(historyEntries.size(), 3);
        assertFalse(historyEntries.contains(h3));
        assertFalse(h2.getMark());
    }

}
