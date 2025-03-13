package model.marvelObjects;

import java.util.List;

public class DisplayComic {
    private int id;
    private String name;
    private List<String> characters;

    public DisplayComic(int id, String name, List<String> characters) {
        this.id = id;
        this.name = name;
        this.characters = characters;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static DisplayComic getComicWithName(List<DisplayComic> comics, String s){
        for (DisplayComic c : comics){
            if (c != null && c.getName().equalsIgnoreCase(s)){return c;}
        }
        return null;
    }

    public List<String> getCharacters() {
        return characters;
    }
}
