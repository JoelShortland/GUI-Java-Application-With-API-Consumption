package model.cacheHandler;

import model.marvelObjects.Character;
import model.marvelObjects.Comic;

import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for SQLHandler, ensures the correct things are returned depending on API state and the database state
 */
public class CacheHandler {
    private boolean cacheEnabled;

    public CacheHandler(boolean inputAPIIsOnline){
        SQLHandler.createDB();
        SQLHandler.setupDB();
        this.cacheEnabled = inputAPIIsOnline;
    }

    public boolean isCacheEnabled(){
        return cacheEnabled;
    }

    /**
     * Check if a character is cached if the cache is enabled
     * @param characterName the name of the character
     * @return true if cache is enabled and item is cached, else false
     */
    public boolean isCharacterCached(String characterName){
        if (!cacheEnabled) {return false;}
        if (SQLHandler.getACharacter(characterName) != null){
            return true;
        }
        return false;
    }

    /**
     * Check if a comic is cached if the cache is enabled
     * @param comicID the name of the comic
     * @return true if cache is enabled and item is cached, else false
     */
    public boolean isComicCached(int comicID){
        if (!cacheEnabled) {return false;}
        if (SQLHandler.getCharactersInComic(comicID).size() > 0){
            return true;
        }
        return false;
    }

    public void clearCache(){
        SQLHandler.clearCache();
    }

    /**
     * Gets a character from the cache with the provided name
     * @param characterName name of character
     * @return the character object
     */
    public Character getCachedCharacter(String characterName){
        if (!cacheEnabled) {return null;}
        try{
            String[] characterComponents = SQLHandler.getACharacter(characterName);
            List<String[]> comicStrings = SQLHandler.getComicsInCharacter(Integer.parseInt(characterComponents[0]));
            List<Comic> comics = new ArrayList<>();
            for (String[] s : comicStrings){
                comics.add(new Comic(s[0], s[1]));
            }
            return new Character(Integer.parseInt(characterComponents[0]), characterComponents[1], comics, characterComponents[2], characterComponents[3]);
        } catch (Exception e){
            ;
        }
        return null;
    }

    /**
     * Cache a character into the database
     * @param character the character to cache
     */
    public void cacheCharacter(Character character){
        if (!cacheEnabled || isCharacterCached(character.getName())) {return;}
        SQLHandler.saveACharacter(character.getId(), character.getName(), character.getThumbnailPath(), character.getThumbnailExtension());
        if (character.getComics().size() == 0) { return; }
        for (Comic c :character.getComics()){
            SQLHandler.saveAComicInCharacter(character.getId(), c.getName());
        }
    }

    /**
     * Cache a list of characters belonging to a comic into the database
     * @param chars the list of characters
     * @param comicID the comics ID
     */
    public void cacheCharacterList(List<String> chars, int comicID){
        if (!cacheEnabled) {return;}
        for (String s : chars){
            SQLHandler.saveACharacterInComic(comicID, s);
        }
    }

    /**
     * Get a list of characters in a comic from the cache
     * @param comicID the id of the comic
     * @return The list of characters
     */
    public List<String> getCharactersInComic(int comicID){
        if (!cacheEnabled) {return new ArrayList<>();}
        return SQLHandler.getCharactersInComic(comicID);
    }
}
