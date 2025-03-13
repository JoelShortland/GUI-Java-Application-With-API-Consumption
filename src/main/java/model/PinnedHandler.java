package model;

import model.marvelObjects.DisplayComic;

/**
 * Handles the data for the pinned comic class
 */
public class PinnedHandler {
    private String pinnedComic;
    private DisplayComic displayComic;

    public PinnedHandler(){
        this.pinnedComic=null;
    }

    public void setPinnedComic(DisplayComic pinnedComic){
        this.displayComic = pinnedComic;
        this.pinnedComic= pinnedComic.getName();
    }

    public void resetPinnedComic(){
        this.pinnedComic = null;
        this.displayComic = null;
    }

    public String getPinnedComic(){
        if (pinnedComic == null){ return "None"; }
        return this.pinnedComic;
    }

    /**
     * Checks if a character is in the pinned comic
     * @param charName the name of the character
     * @return true if yes, else false
     */
    public boolean checkIfCharacterInPinnedComic(String charName){
        if (displayComic == null || displayComic.getCharacters() == null){ return false; }
        for (String s : displayComic.getCharacters()){
            if (s.equalsIgnoreCase(charName)){
                return true;
            }
        }
        return false;
    }
}
