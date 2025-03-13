package view.display;

public interface Display {
    /**
     * Set the visual attributes that appear in this display
     */
    void setResultBox();

    /**
     * Display the visuals in this class to the user (clearing any that would be in its way
     */
    void display();
}
