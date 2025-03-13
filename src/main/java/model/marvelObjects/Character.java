package model.marvelObjects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
{
        \"id\": 1011334,
        \"name\": \"3-D Man\",
        \"description\": \"\",
        \"modified\": \"2014-04-29T14:18:17-0400\",
        \"thumbnail\": {
          \"path\": \"http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784\",
          \"extension\": \"jpg\"
        },
        \"resourceURI\": \"http://gateway.marvel.com/v1/public/characters/1011334\",
        \"comics\": {
          \"available\": 12,
          \"collectionURI\": \"http://gateway.marvel.com/v1/public/characters/1011334/comics\",
          \"items\": [
            {
              \"resourceURI\": \"http://gateway.marvel.com/v1/public/comics/21366\",
              \"name\": \"Avengers: The Initiative (2007) #14\"
            },
            {
              \"resourceURI": "http://gateway.marvel.com/v1/public/comics/24571",
              "name": "Avengers: The Initiative (2007) #14 (SPOTLIGHT VARIANT)"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/21546",
              "name": "Avengers: The Initiative (2007) #15"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/21741",
              "name": "Avengers: The Initiative (2007) #16"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/21975",
              "name": "Avengers: The Initiative (2007) #17"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/22299",
              "name": "Avengers: The Initiative (2007) #18"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/22300",
              "name": "Avengers: The Initiative (2007) #18 (ZOMBIE VARIANT)"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/22506",
              "name": "Avengers: The Initiative (2007) #19"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/8500",
              "name": "Deadpool (1997) #44"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/10223",
              "name": "Marvel Premiere (1972) #35"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/10224",
              "name": "Marvel Premiere (1972) #36"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/comics/10225",
              "name": "Marvel Premiere (1972) #37"
            }
          ],
          "returned": 12
        },

 */


public class Character {
    private int id;
    private String name;
    private List<Comic> comics;
    private String thumbnailPath;
    private String thumbnailExtension;

    public Character(JSONObject config){
        this.id = config.getInt("id");
        this.name = config.getString("name");
        this.thumbnailPath = config.getJSONObject("thumbnail").getString("path");
        this.thumbnailExtension = config.getJSONObject("thumbnail").getString("extension");
        this.comics = new ArrayList<Comic>();

        JSONArray a = config.getJSONObject("comics").getJSONArray("items");
        for (int i = 0; i < a.length(); i++){
            JSONObject o = a.getJSONObject(i);
            comics.add(new Comic(o.getString("name"), o.getString("resourceURI")));
        }
    }

    public Character(int id, String name, List<Comic> comics, String thumbnailPath, String thumbnailExtension) {
        this.id = id;
        this.name = name;
        this.comics = comics;
        this.thumbnailPath = thumbnailPath;
        this.thumbnailExtension = thumbnailExtension;
    }

    /**
     * Overwrite the data of one character with another
     * @param character the character we are overwriting with
     */
    public void overwrite(Character character){
        this.id = character.getId();
        this.name = character.getName();
        this.comics = character.getComics();
        this.thumbnailPath = character.getThumbnailPath();
        this.thumbnailExtension = character.getThumbnailExtension();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public String getThumbnailPath() {
        if (thumbnailPath.endsWith(".jpg")) {return thumbnailPath;}
        return thumbnailPath + "/portrait_uncanny." + getThumbnailExtension();
    }

    /**
     * Gets a character with a given name
     * @param characters the list of characters
     * @param s the name of the character
     * @return the character if it exists, else null
     */
    public static Character getCharacterWithName(List<Character> characters, String s){
        for (Character c : characters){
            if (c != null && c.getName().equalsIgnoreCase(s)){return c;}
        }
        return null;
    }

    public String getThumbnailExtension() {
        return thumbnailExtension;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comics=" + comics +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", thumbnailExtension='" + thumbnailExtension + '\'' +
                '}';
    }
}
