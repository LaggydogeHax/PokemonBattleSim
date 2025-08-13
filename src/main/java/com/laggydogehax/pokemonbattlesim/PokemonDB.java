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
	}//constructor ends
	
	public void forceReplaceSavedDB(){
		// this is to force-update the contents :)
		try {
			InputStream ist = PokemonDB.class.getResourceAsStream("/pbs.db");
			Files.copy(ist, dbPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// :p
		}
	}
	
	private String[] fetchStuff(String sql){
		String[] fetchString = null;
		
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
				fetchString = new String[rsList.size()];
				fetchString = rsList.toArray(fetchString);

			} catch (SQLException e) {
				System.err.print(e.getMessage());
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return fetchString;
	}

	public String[] getPokemonNamesInDB() {
		String sql = "SELECT name FROM Pokemon";
		return this.fetchStuff(sql);
	}
	
	public String[] getSecretPokemonNamesInDB() {
		String sql = "SELECT name FROM Pokemon_Secret";
		return this.fetchStuff(sql);
	}
	
	public String[] getTypesVectorInDB(){
		String sql = "SELECT type FROM Types";
		String[] typesVector = this.fetchStuff(sql);
		
		//someone had the brilliant idea of adding a 19th type NULL in the db because of foreign key constrains
		String[] typesVectorWithoutNULL = new String[18];
		//today i learned that System.arraycopy exists
		System.arraycopy(typesVector, 0, typesVectorWithoutNULL, 0, typesVectorWithoutNULL.length);
		
		return typesVectorWithoutNULL;
	}
	
	public String[] getMovesOfType(String typ){
		String sql = "";
		if(typ.equals("status")){
			sql = "SELECT a.name FROM Moves a INNER JOIN Types b ON a.id_type LIKE b.id_type WHERE b.type LIKE 'NULL'";
		}else{
			sql = "SELECT a.name FROM Moves a INNER JOIN Types b ON a.id_type LIKE b.id_type WHERE b.type LIKE '"+typ+"'";
		}
		return this.fetchStuff(sql);
	}
	
	public String[] getPokemonData(String pkmnName){
		//wth
		String sql = "SELECT a.name, b.type,b1.type,a.base_atk,a.base_def,a.base_hp,a.base_speed FROM Pokemon a INNER JOIN Types b ON b.id_type LIKE a.id_type INNER JOIN Types b1 ON b1.id_type LIKE a.id_type2 WHERE a.name LIKE '"+pkmnName+"'";
		return this.fetchStuff(sql);
	}
	
	public String[] getSecretPokemonData(String pkmnName){
		String sql = "SELECT a.name, b.type,b1.type,a.base_atk,a.base_def,a.base_hp,a.base_speed FROM Pokemon_Secret a INNER JOIN Types b ON b.id_type LIKE a.id_type INNER JOIN Types b1 ON b1.id_type LIKE a.id_type2 WHERE a.name LIKE '"+pkmnName+"'";
		return this.fetchStuff(sql);
	}
	
	public String[] getPokemonMovelist(String pkmnName){
		String sql = "SELECT a.name,b.name,c.name,d.name FROM Pokemon x INNER JOIN Moves a ON x.id_move1 LIKE a.id_move INNER JOIN Moves b ON x.id_move2 LIKE b.id_move INNER JOIN Moves c ON x.id_move3 LIKE c.id_move INNER JOIN Moves d ON x.id_move4 LIKE d.id_move WHERE x.name LIKE '"+pkmnName+"'";
		return this.fetchStuff(sql);
	}
	
	public String[] getSecretPokemonMovelist(String pkmnName){
		String sql = "SELECT a.name,b.name,c.name,d.name FROM Pokemon_Secret x INNER JOIN Moves a ON x.id_move1 LIKE a.id_move INNER JOIN Moves b ON x.id_move2 LIKE b.id_move INNER JOIN Moves c ON x.id_move3 LIKE c.id_move INNER JOIN Moves d ON x.id_move4 LIKE d.id_move WHERE x.name LIKE '"+pkmnName+"'";
		return this.fetchStuff(sql);
	}
	
	public String[] getMoveData(String move){
		String sql = "SELECT b.type,a.is_attack FROM Moves a INNER JOIN Types b ON a.id_type LIKE b.id_type WHERE a.name LIKE '"+move+"'";
		return this.fetchStuff(sql);
	}
	
	public static Path getSaveFilePath(){
		return pathToPath;
	}

}
