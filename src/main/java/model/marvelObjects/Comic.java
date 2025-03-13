package model.marvelObjects;

public class Comic {
    private final String name;
    private final String imageLink;

    public Comic(String name, String imageLink){
        this.name = name;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }
}
