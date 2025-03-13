import model.APIHandler.input.InputHandler;
import model.APIHandler.input.OfflineInputHandler;
import model.APIHandler.input.OnlineInputHandler;
import model.APIHandler.output.OfflineOutputHandler;
import model.APIHandler.output.OnlineOutputHandler;
import model.AppModel;
import model.cacheHandler.CacheHandler;
import model.marvelObjects.Character;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelTest {
    private static AppModel m = new AppModel("offline", "offline");


    @Test
    public void instantiationTest(){
        AppModel a1 = new AppModel(null, null);
        AppModel a2 = new AppModel("offline", "offline");
        AppModel a3 = new AppModel("online", "online");
        AppModel a4 = new AppModel("offline", "online");
        AppModel a5 = new AppModel("online", "offline");
        AppModel a6 = new AppModel("lol", "haha yes");
        AppModel a7 = new AppModel("ONLINE", "ONLINE");

        assertTrue(a1.getInputHandler() instanceof OfflineInputHandler);
        assertTrue(a1.getOutputHandler() instanceof OfflineOutputHandler);

        assertTrue(a2.getInputHandler() instanceof OfflineInputHandler);
        assertTrue(a2.getOutputHandler() instanceof OfflineOutputHandler);

        assertTrue(a3.getInputHandler() instanceof OnlineInputHandler);
        assertTrue(a3.getOutputHandler() instanceof OnlineOutputHandler);

        assertTrue(a4.getInputHandler() instanceof OfflineInputHandler);
        assertTrue(a4.getOutputHandler() instanceof OnlineOutputHandler);

        assertTrue(a5.getInputHandler() instanceof OnlineInputHandler);
        assertTrue(a5.getOutputHandler() instanceof OfflineOutputHandler);

        assertTrue(a6.getInputHandler() instanceof OfflineInputHandler);
        assertTrue(a6.getOutputHandler() instanceof OfflineOutputHandler);

        assertTrue(a7.getInputHandler() instanceof OnlineInputHandler);
        assertTrue(a7.getOutputHandler() instanceof OnlineOutputHandler);
    }

    @Test
    public void operationPerformedTest(){
        assertEquals(m.operationIsBeingPerformed(1), true);
        assertEquals(m.operationIsBeingPerformed(-1), false);

        assertEquals(m.operationIsBeingPerformed(1), true);
        assertEquals(m.operationIsBeingPerformed(1), true);
        assertEquals(m.operationIsBeingPerformed(1), true);
        assertEquals(m.operationIsBeingPerformed(-1), true);
        assertEquals(m.operationIsBeingPerformed(-1), true);
        assertEquals(m.operationIsBeingPerformed(-1), false);

        assertEquals(m.operationIsBeingPerformed(1), true);
        assertEquals(m.operationIsBeingPerformed(1), true);
        assertEquals(m.operationIsBeingPerformed(-1), true);
        assertEquals(m.operationIsBeingPerformed(1), true);
        assertEquals(m.operationIsBeingPerformed(-1), true);
        assertEquals(m.operationIsBeingPerformed(-1), false);
    }
}
