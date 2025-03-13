package model.APIHandler.input;

import model.marvelObjects.Character;
import model.marvelObjects.Comic;
import model.marvelObjects.DisplayComic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OfflineInputHandler implements InputHandler{


    @Override
    public List<Character> getCharacterList(String searchTerm) {
        ArrayList<Character> returnList = new ArrayList<>();

        File file = new File("portrait_uncanny.jpg");
        String path = file.getAbsolutePath().replace("portrait_uncanny.jpg", "src" + File.separator + "main" + File.separator + "resources" + File.separator + "portrait_uncanny.jpg");

        //HULK
        ArrayList<Comic> comics = new ArrayList<>();
        comics.add(new Comic("5 Ronin (Hardcover)", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics.add(new Comic("Age of X: Universe (2011) #1", "http://gateway.marvel.com/v1/public/comics/38524"));
        returnList.add(new Character(1009351, "Hulk", comics, path, "jpg"));

        //IRON MAN
        ArrayList<Comic> comics2 = new ArrayList<>();
        comics2.add(new Comic("Iron Comic 1", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics2.add(new Comic("Iron Comic 2", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics2.add(new Comic("Iron Comic 3", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics2.add(new Comic("Iron Comic 4", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics2.add(new Comic("Iron Comic Revenge of Iron Mann't", "http://gateway.marvel.com/v1/public/comics/41112"));
        returnList.add(new Character(1009368, "Iron Man", comics2, path, "jpg"));

        //THOR
        ArrayList<Comic> comics3 = new ArrayList<>();
        comics3.add(new Comic("Thor1", "http://gateway.marvel.com/v1/public/comics/100083"));
        comics3.add(new Comic("Thor2", "http://gateway.marvel.com/v1/public/comics/100083"));
        comics3.add(new Comic("Thor3", "http://gateway.marvel.com/v1/public/comics/100083"));
        comics3.add(new Comic("Thor4", "http://gateway.marvel.com/v1/public/comics/100083"));
        comics3.add(new Comic("Thor5", "http://gateway.marvel.com/v1/public/comics/100083"));

        returnList.add(new Character(1009664, "Thor", comics3, path , "jpg"));
        return returnList;
    }

    @Override
    public List<DisplayComic> getComicsOfCharacter(int characterID) {
        ArrayList<DisplayComic> returnList = new ArrayList<>();
        ArrayList<String> characters = new ArrayList<>();
        characters.add("Hulk"); characters.add("Thor"); characters.add("Iron Man");
        returnList.add(new DisplayComic(1, "Comic 1", characters));
        returnList.add(new DisplayComic(2, "Comic 2", characters));
        returnList.add(new DisplayComic(3, "Comic 3", characters));
        returnList.add(new DisplayComic(4, "Comic 4", characters));
        return returnList;
    }

    @Override
    public List<String> getCharactersInComic(int comicID) {
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add("Hulk");
        returnList.add("Thor");
        returnList.add("Iron Man");

        return returnList;
    }

    @Override
    public Character getCharacterWithName(String name){
        ArrayList<Comic> comics = new ArrayList<>();
        File file = new File("portrait_uncanny.jpg");
        String path = file.getAbsolutePath().replace("portrait_uncanny.jpg", "src" + File.separator + "main" + File.separator + "resources" + File.separator + "portrait_uncanny.jpg");

        comics.add(new Comic("Iron Comic 1", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics.add(new Comic("Iron Comic 2", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics.add(new Comic("Iron Comic 3", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics.add(new Comic("Iron Comic 4", "http://gateway.marvel.com/v1/public/comics/41112"));
        comics.add(new Comic("Iron Comic Revenge of Iron Mann't", "http://gateway.marvel.com/v1/public/comics/41112"));

        return new Character(1009368, "Iron Man", comics, path, "jpg");
    }
}
