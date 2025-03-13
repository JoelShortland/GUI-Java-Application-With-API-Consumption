import model.cacheHandler.SQLHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {
    @BeforeEach
    public void createCacheTest(){
        SQLHandler.updateDBName("testDatabase");
        SQLHandler.clearCache();
        SQLHandler.createDB();
        SQLHandler.setupDB();
    }

    /**
     * Just exists for my sanity, this isn't actually needed
     */
    @AfterAll
    static void sanityFunction(){
        SQLHandler.updateDBName("marvelDatabase");
    }

    /**
     * Test all possible ways the program might try to save a character
     */
    @Test
    public void testCharacters(){
        SQLHandler.saveACharacter(1, "name1", "doesnt matter", "doesnt matter");
        SQLHandler.saveACharacter(2, "name2", "doesnt matter", "doesnt matter");
        SQLHandler.saveACharacter(3, "name3", "doesnt matter", "doesnt matter");
        SQLHandler.saveACharacter(4, "name4", "doesnt matter", "doesnt matter");

        String[] characterInfo = SQLHandler.getACharacter("name1");
        assertEquals(characterInfo[0], "1");
        assertEquals(characterInfo[1], "name1");

        String[] characterInfo2 = SQLHandler.getACharacter("name3");
        assertEquals(characterInfo2[0], "3");
        assertEquals(characterInfo2[1], "name3");

        assertNull(SQLHandler.getACharacter("name5"));
    }

    /**
     * Test all ways the program might try to save a character in a comic
     */
    @Test
    public void testCharacterInComic(){
        SQLHandler.saveACharacterInComic(1, "name1");
        SQLHandler.saveACharacterInComic(1, "name2");
        SQLHandler.saveACharacterInComic(1, "name3");

        SQLHandler.saveACharacterInComic(2, "name4");

        List<String> characterInfo = SQLHandler.getCharactersInComic(1);
        assertTrue(characterInfo.contains("name1"));
        assertTrue(characterInfo.contains("name2"));
        assertTrue(characterInfo.contains("name3"));

        List<String> characterInfo2 = SQLHandler.getCharactersInComic(2);
        assertFalse(characterInfo2.contains("name1"));
        assertFalse(characterInfo2.contains("name3"));
        assertTrue(characterInfo2.contains("name4"));

        assertEquals(SQLHandler.getCharactersInComic(5).size(), 0);
    }

    /**
     * Test all ways the program might try to save a comic in a character
     */
    @Test
    public void testComicsInCharacter(){
        SQLHandler.saveAComicInCharacter(1, "name1");
        SQLHandler.saveAComicInCharacter(1, "name2");
        SQLHandler.saveAComicInCharacter(1, "name3");
        SQLHandler.saveAComicInCharacter(1, "name4");

        SQLHandler.saveAComicInCharacter(2, "name5");
        SQLHandler.saveAComicInCharacter(2, "name6");
        SQLHandler.saveAComicInCharacter(3, "name7");

        List<String[]> characterInfo = SQLHandler.getComicsInCharacter(2);
        assertEquals(SQLHandler.getComicsInCharacter(5).size(), 0);
    }
}
