import model.APIHandler.input.InputHandler;
import model.APIHandler.input.OfflineInputHandler;
import model.APIHandler.input.OnlineInputHandler;
import model.APIHandler.output.OfflineOutputHandler;
import model.APIHandler.output.OutputHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Since the output of these are static, just make sure they run.
 */
public class APITest {

    @Test
    public void MarvelTest(){
        InputHandler ih = new OfflineInputHandler();

        ih.getCharacterList("");
        ih.getCharactersInComic(3);
        ih.getCharacterWithName("");
        ih.getComicsOfCharacter(1);
    }

    @Test
    public void TwilioTest(){
        OutputHandler oh = new OfflineOutputHandler();
        assertEquals(oh.sendMessage(null), false);
    }

}
