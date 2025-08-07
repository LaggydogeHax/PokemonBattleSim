package com.laggydogehax.pokemonbattlesim;

import java.nio.file.Path;
import java.sql.*;
import java.io.*;
import java.nio.file.*;

public class PokemonDB {
   
    public PokemonDB(){
        InputStream inputStream = PokemonDB.class.getResourceAsStream("/pbs.db");
        if (inputStream == null) {
            System.out.println("DB NOT FOUND");
        }
        Path database = null;
        try{
            // Since JAR files are read-only, we gotta make a temporary pbs.db somewhere else
            database = Files.createTempFile("pbs", ".db");
            database.toFile().deleteOnExit();
            // Copy the resource to the temporary pbd.db
            Files.copy(inputStream, database, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            // rip
        }
        
        final String url = "jdbc:sqlite:" + database.toAbsolutePath();
        
        String sql = "SELECT name FROM sqlite_master WHERE type='table'";
        
        try(var conn = DriverManager.getConnection(url)){
            try(var stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
                
                while (rs.next()){
                    System.out.println("pokemon in db: "+rs.getString("name"));
                }
                
            }catch(SQLException e){
                System.err.print(e.getMessage());
            }
            
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
}
