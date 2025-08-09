package com.laggydogehax.pokemonbattlesim;

import java.nio.file.Path;
import java.sql.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class PokemonDB {

	private final String url;
	private static final Path pathToPath = Paths.get(System.getProperty("user.home"), ".PBS");
	private final Path dbPath = Paths.get(pathToPath.toString(),"pbs.db");

	public PokemonDB() {
		try {
			Files.createDirectories(dbPath.getParent());

			if(!Files.exists(dbPath)){
				try{
					InputStream ist = PokemonDB.class.getResourceAsStream("/pbs.db");
					// Since JAR files are read-only, we gotta copy the pbs.db and put it somewhere else
					Files.copy(ist,dbPath,StandardCopyOption.REPLACE_EXISTING);
					
				}catch(IOException e){
					// :p
				}
			}
			
		} catch (IOException e) {
			// rip
		}
		this.url = "jdbc:sqlite:" + dbPath.toAbsolutePath();
	}
	
	public void forceReplaceSavedDB(){
		// this is to force-update the contents :)
		try {
			InputStream ist = PokemonDB.class.getResourceAsStream("/pbs.db");
			Files.copy(ist, dbPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// :p
		}
	}

	public void getPokemonNamesInDB() {
		String sql = "SELECT 'name' FROM Pokemon WHERE 1";

		try (var conn = DriverManager.getConnection(this.url)) {
			try (var stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

				while (rs.next()) {
					System.out.println("pokemon in db: " + rs.getString("name"));
				}

			} catch (SQLException e) {
				System.err.print(e.getMessage());
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String[] getTypesVectorInDB(){
		String sql = "SELECT type FROM Types";
		String[] typesVector = null;
		
		try (var conn = DriverManager.getConnection(this.url)) {
			try (var stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int cc = rsmd.getColumnCount();
				ArrayList<String> rsList = new ArrayList<>(cc);
				
				//super cool iterator
				while (rs.next()) {
					int i = 1;
					while (i <= cc) {
						rsList.add(rs.getString(i++));
					}
				}
				//convert arraylist to string[]
				typesVector = new String[rsList.size()];
				typesVector = rsList.toArray(typesVector);

			} catch (SQLException e) {
				System.err.print(e.getMessage());
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return typesVector;
	}
	
	public static Path getSaveFilePath(){
		return pathToPath;
	}

}
