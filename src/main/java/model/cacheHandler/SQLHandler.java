package model.cacheHandler;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for interacting with the database
 */
public class SQLHandler {
    private static String dbName = "marvelDatabase";
    private static String dbURL = "jdbc:sqlite:"+dbName;

    /**
     * Makes a database if its possible
     */
    public static void createDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {

            return;
        }
        try  {
            Connection ignored = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            ;
        }
    }

    /**
     * Used for testing to not clash with the actual database
     */
    public static void updateDBName(String name){
        SQLHandler.dbName = name;
    }

    public static void setupDB() {
        String createCharactersTableSQL =
                """
                CREATE TABLE IF NOT EXISTS characters (
                    id integer PRIMARY KEY,
                    name text NOT NULL,
                    thumbnailPath text NOT NULL,
                    thumbnailExtension text NOT NULL
                );
                """;

        String characterComicsTableSQL =
                """
                CREATE TABLE IF NOT EXISTS comics_in_character (
                    name text PRIMARY KEY,
                    id integer NOT NULL
                );
                """;

        String createDisplayComicTableSQL =
                """
                CREATE TABLE IF NOT EXISTS display_comics (
                    id integer PRIMARY KEY,
                    name text NOT NULL
                );
                """;

        String createCharactersInDisplayComicsSQL =
                """
                CREATE TABLE IF NOT EXISTS characters_in_comic (
                    name text PRIMARY KEY,
                    id integer NOT NULL
                );
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(createCharactersTableSQL);
            statement.execute(characterComicsTableSQL);
            statement.execute(createDisplayComicTableSQL);
            statement.execute(createCharactersInDisplayComicsSQL);
        } catch (SQLException e) {
            ;
        }
    }

    /**
     * Clears the database
     */
    public static void clearCache(){
        String clearTableString1 = "DELETE FROM characters;";
        String clearTableString2 = "DELETE FROM display_comics;";
        String clearTableString3 = "DELETE FROM characters_in_comic;";
        String clearTableString4 = "DELETE FROM comics_in_character;";
        String[] clearTables = new String[]{clearTableString1, clearTableString2, clearTableString3, clearTableString4};

        //Now delete
        for (String clear : clearTables){
            try (Connection conn = DriverManager.getConnection(dbURL);
                 PreparedStatement preparedStatement = conn.prepareStatement(clear)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                ;
            }
        }
    }

    /**
     * Saves a character into the database
     * @param id character attribute
     * @param name character attribute
     * @param thumbnailPath character attribute
     * @param thumbNailExtension character attribute
     */
    public static void saveACharacter(int id, String name, String thumbnailPath, String thumbNailExtension) {
        String addGameStateSQL =
                """
                INSERT INTO characters(id, name, thumbnailPath, thumbnailExtension) VALUES
                    (?, ?, ?, ?)
                """;
        //Update gamestates table
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addGameStateSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, thumbnailPath);
            preparedStatement.setString(4, thumbNailExtension);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ;
        }
    }

    /**
     * Saves a comic that belongs to a character
     * @param id ID of the character
     * @param name Name of the comic
     */
    public static void saveAComicInCharacter(int id, String name) {
        String addGameStateSQL =
                """
                INSERT INTO comics_in_character(id, name) VALUES
                    (?, ?)
                """;

        //Update gamestates table
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addGameStateSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ;
        }
    }

    /**
     * Saves a character that appears in a comic
     * @param id ID of the comic
     * @param name name of the character
     */
    public static void saveACharacterInComic(int id, String name) {
        String addGameStateSQL =
                """
                INSERT INTO characters_in_comic(id, name) VALUES
                    (?, ?)
                """;

        //Update gamestates table
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addGameStateSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ;
        }
    }


    /**
     * Gets a character from the database
     * @param name Name of character
     * @return Info of that character
     */
    public static String[] getACharacter(String name){
        String studentRangeSQL =
                """
                SELECT *
                FROM characters
                WHERE ? = name
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setString(1, name);
            ResultSet results = preparedStatement.executeQuery();
            return new String[] { String.valueOf(results.getInt("id")), results.getString("name"), results.getString("thumbnailPath"), results.getString("thumbnailExtension")};

        } catch (SQLException e) {
            ;
        }
        return null;
    }

    /**
     * Gets all comics belonging to a character
     * @param characterID ID of character
     * @return list of comics
     */
    public static List<String[]> getComicsInCharacter(int characterID){
        String studentRangeSQL =
                """
                SELECT *
                FROM comics_in_character
                WHERE id = ?
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setInt(1, characterID);
            ResultSet results = preparedStatement.executeQuery();

            List<String[]> names = new ArrayList<>();
            while (results.next()){
                names.add( new String[]{ results.getString("name"), results.getString("imageLink"), String.valueOf(results.getString("id"))});
            }
            return names;
        } catch (SQLException e) {
            ;
        }
        return new ArrayList<>();
    }

    /**
     * Gets all characters that appear in a given comic
     * @param comicID ID of comic
     * @return list of character names
     */
    public static List<String> getCharactersInComic(int comicID){
        String studentRangeSQL =
                """
                SELECT *
                FROM characters_in_comic 
                WHERE id = ?
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(studentRangeSQL)) {
            preparedStatement.setInt(1, comicID);
            ResultSet results = preparedStatement.executeQuery();

            List<String> names = new ArrayList<>();
            while (results.next()){
                names.add(results.getString("name"));
            }
            return names;

        } catch (SQLException e) {
            ;
        }
        return new ArrayList<>();
    }
}
