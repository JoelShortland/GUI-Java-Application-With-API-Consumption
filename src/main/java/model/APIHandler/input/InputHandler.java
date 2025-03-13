package model.APIHandler.input;

import model.marvelObjects.Character;
import model.marvelObjects.DisplayComic;

import java.util.List;

public interface InputHandler {
    /**
     * Get a list of characters given a search term
     * @param searchTerm the search term
     * @return the found characters
     */
    public List<Character> getCharacterList(String searchTerm);

    /**
     * Get comics belonging to a character
     * @param characterID The characters ID
     * @return the list of comics
     */
    public List<DisplayComic> getComicsOfCharacter(int characterID);

    /**
     * get characters in a given comic
     * @param comicID the comics id
     * @return the list of characters
     */
    public List<String> getCharactersInComic(int comicID);

    /**
     * get a character info given its name
     * @param name the characters name
     * @return the found character
     */
    public Character getCharacterWithName(String name);

}
