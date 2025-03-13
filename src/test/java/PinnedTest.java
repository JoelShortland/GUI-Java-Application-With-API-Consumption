import controller.AppController;
import model.AppModel;
import model.PinnedHandler;
import model.marvelObjects.DisplayComic;
import org.junit.jupiter.api.Test;
import view.AppView;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests the PinnedHandler class
 */
public class PinnedTest {
    /**
     * Test we can make the class and the default message is correct
     */
    @Test
    public void testDefaultResponse(){
        PinnedHandler pinnedHandler = new PinnedHandler();
        assertEquals(pinnedHandler.getPinnedComic(), "None");
    }

    /**
     * Make sure we can set the pinned comic
     */
    @Test
    public void setPinnedComicTest(){
        PinnedHandler pinnedHandler = new PinnedHandler();
        ArrayList<String> charList = new ArrayList<>();
        charList.add("Char1");
        charList.add("Char3");
        charList.add("Char2");
        DisplayComic displayComic = new DisplayComic(1, "Comic1", charList);
        pinnedHandler.setPinnedComic(displayComic);
        assertEquals(displayComic.getName(), pinnedHandler.getPinnedComic());
    }

    /**
     * Make sure the alert is only called when the character is in the comic
     */
    @Test
    public void isCharacterInPinnedComicTest() {
        //Create pinnedhandler and other classes
        PinnedHandler pinnedHandler = new PinnedHandler();
        ArrayList<String> charList = new ArrayList<>();
        charList.add("Char1");
        charList.add("Char3");
        charList.add("Char2");
        DisplayComic displayComic = new DisplayComic(1, "Comic1", charList);
        pinnedHandler.setPinnedComic(displayComic);

        //verify
        assertFalse(pinnedHandler.checkIfCharacterInPinnedComic("Char4"));
        assertFalse(pinnedHandler.checkIfCharacterInPinnedComic("Char44"));
        assertFalse(pinnedHandler.checkIfCharacterInPinnedComic("Char33"));

        assertTrue(pinnedHandler.checkIfCharacterInPinnedComic("Char1"));
        assertTrue(pinnedHandler.checkIfCharacterInPinnedComic("Char2"));
        assertTrue(pinnedHandler.checkIfCharacterInPinnedComic("Char3"));

    }

    /**
     * Test we can reset the message
     */

    public void testResetComic(){
        PinnedHandler pinnedHandler = new PinnedHandler();
        ArrayList<String> charList = new ArrayList<>();
        charList.add("Char1");
        charList.add("Char3");
        charList.add("Char2");
        DisplayComic displayComic = new DisplayComic(1, "Comic1", charList);
        pinnedHandler.setPinnedComic(displayComic);

        assertTrue(pinnedHandler.checkIfCharacterInPinnedComic("Char1"));
        pinnedHandler.resetPinnedComic();
        assertEquals(pinnedHandler.getPinnedComic(), "None");
        assertFalse(pinnedHandler.checkIfCharacterInPinnedComic("Char1"));

    }
}
