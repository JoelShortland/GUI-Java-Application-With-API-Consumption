import model.marvelObjects.Character;
import model.marvelObjects.Comic;
import model.marvelObjects.DisplayComic;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MarvelObjectsTest {

    //COMIC TESTS
    @Test
    public void getComicWithNameTest(){
        DisplayComic d1 = new DisplayComic(1, "comic 1", null);
        DisplayComic d2 = new DisplayComic(2, "comic 2", null);
        DisplayComic d3 = new DisplayComic(3, "comic 3", null);
        DisplayComic d4 = new DisplayComic(4, "comic 4", null);
        DisplayComic d5 = new DisplayComic(5, "comic 5", null);

        List<DisplayComic> dList = new ArrayList<DisplayComic>();
        dList.add(d1);
        dList.add(d2);
        dList.add(d3);
        dList.add(d4);
        dList.add(d5);

        assertEquals(DisplayComic.getComicWithName(dList, "comic 1"), d1);
        assertEquals(DisplayComic.getComicWithName(dList, "comic 5"), d5);
        assertEquals(DisplayComic.getComicWithName(dList, "I dont exist"), null);
        assertEquals(DisplayComic.getComicWithName(dList, null), null);
        assertEquals(DisplayComic.getComicWithName(dList, "comic"), null);

        dList.add(null);
        DisplayComic d6 = new DisplayComic(6, "comic 6", null);
        dList.add(d6);
        assertEquals(DisplayComic.getComicWithName(dList, "comic 6"), d6);
    }

    //CHARACTER TESTS
    @Test
    public void characterInitialisationTest(){
        JSONObject jo = new JSONObject("{\n" +
                "        \"id\": 1011334,\n" +
                "        \"name\": \"3-D Man\",\n" +
                "        \"description\": \"\",\n" +
                "        \"modified\": \"2014-04-29T14:18:17-0400\",\n" +
                "        \"thumbnail\": {\n" +
                "          \"path\": \"http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784\",\n" +
                "          \"extension\": \"jpg\"\n" +
                "        },\n" +
                "        \"resourceURI\": \"http://gateway.marvel.com/v1/public/characters/1011334\",\n" +
                "        \"comics\": {\n" +
                "          \"available\": 12,\n" +
                "          \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1011334/comics\",\n" +
                "          \"items\": [\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21366\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #14\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/24571\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #14 (SPOTLIGHT VARIANT)\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21546\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #15\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21741\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #16\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21975\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #17\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/22299\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #18\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/22300\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #18 (ZOMBIE VARIANT)\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/22506\",\n" +
                "              \"name\": \"Avengers: The Initiative (2007) #19\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/8500\",\n" +
                "              \"name\": \"Deadpool (1997) #44\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/10223\",\n" +
                "              \"name\": \"Marvel Premiere (1972) #35\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/10224\",\n" +
                "              \"name\": \"Marvel Premiere (1972) #36\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/10225\",\n" +
                "              \"name\": \"Marvel Premiere (1972) #37\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"returned\": 12\n" +
                "        }}");

        Character c1 = new Character(jo);
        assertEquals(c1.getName(), "3-D Man");
        assertEquals(c1.getComics().size(), 12);
        assertEquals(c1.getComics().get(0).getName(), "Avengers: The Initiative (2007) #14");
        assertEquals(c1.getId(), 1011334);
        assertEquals(c1.getThumbnailExtension(), "jpg");
        assertEquals(c1.getThumbnailPath(), "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784/portrait_uncanny.jpg");
    }

    @Test
    public void getCharacterWithNameTest(){
        Character c1 = new Character(1, "name1", null, "", "");
        Character c2 = new Character(2, "name2", null, "", "");
        Character c3 = new Character(3, "name3", null, "", "");
        Character c4 = new Character(4, "name4", null, "", "");
        Character c5 = new Character(5, "name5", null, "", "");
        List<Character> characters = new ArrayList<>();
        characters.add(c1);
        characters.add(c2);
        characters.add(c3);
        characters.add(c4);
        characters.add(c5);

        assertEquals(Character.getCharacterWithName(characters, "name1"), c1);
        assertEquals(Character.getCharacterWithName(characters, "name3"), c3);
        assertEquals(Character.getCharacterWithName(characters, "name6"), null);
        assertEquals(Character.getCharacterWithName(characters, null), null);

    }

    @Test
    public void characterOverwriteTest(){
        Comic c1 = new Comic("comic 1", "comic 1");
        Comic c2 = new Comic("comic 2", "comic 2");
        Comic c3 = new Comic("comic 3", "comic 3");
        Comic c4 = new Comic("comic 4", "comic 4");
        Comic c5 = new Comic("comic 5", "comic 5");

        ArrayList<Comic> comicList1 = new ArrayList<>();
        comicList1.add(c1);
        comicList1.add(c2);

        ArrayList<Comic> comicList2 = new ArrayList<>();
        comicList2.add(c3);
        comicList2.add(c4);
        comicList2.add(c5);

        Character ch1 = new Character(1, "name1", comicList1, "tp1", "te1");
        Character ch2 = new Character(2, "name2", comicList2, "tp2", "te2");
        ch1.overwrite(ch2);

        assertEquals(ch1.getId(), 2);
        assertEquals(ch1.getName(), "name2");
        assertEquals(ch1.getComics().size(), 3);
        assertTrue(ch1.getComics().contains(c3));
        assertFalse(ch1.getComics().contains(c1));
    }
}
