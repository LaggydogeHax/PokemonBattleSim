package com.laggydogehax.pokemonbattlesim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class PBSFileReader{
        private final String path = Paths.get(System.getProperty("user.home"), ".PBS", "PBS_Config.txt").toString();
        private final Path pathToPath = Paths.get(System.getProperty("user.home"), ".PBS");
	boolean noErrors = true;
	
	public int[] configList = new int[2];
	
	File saveFile = new File(this.path);
	
	public PBSFileReader(){
		//Scanner sc = new Scanner(saveFile);
		
		if(checkFile()){
			configList=readConfigs();
			if(configList==null){
				noErrors=false;
			}
		}else{
			if(!makeFile()){
				noErrors=false;
			}else{
				configList=readConfigs();
				if(configList==null){
					noErrors=false;
				}
			}
		}
	}
        
        public String getSaveFilePath(){
            return this.path;
        }
	
	public boolean checkFile(){
		if(saveFile.exists()){
			return true;
		}else{
			return false;
		}
	}
        
	private boolean makeFile(){
		try{
                        Files.createDirectories(this.pathToPath);
			FileWriter fw = new FileWriter(saveFile,true);

			fw.write("// PokemonBattleSim config save file //"+"\n");
			fw.write("// dont add or remove lines if you dont wanna break this :p //"+"\n");
			fw.write("//"+"\n");
			fw.write("Team size: 3"+"\n");
			fw.write("Battle Animations: 1"+"\n");

			fw.close();
			return true;
		}catch(IOException e){
                    System.err.print(e);
			return false;
		}
	}
	
	private int[] readConfigs(){
		String[] lines = new String[2];
		Scanner sc = null;
		try{
			sc = new Scanner(saveFile);
			int j=0;
			for(int i=0;i<5;i++){
				if(sc.hasNextLine()){
					lines[j]=sc.nextLine();
					if(lines[j].contains("//")==false){
						j++;
					}
				}else{
					throw new NullPointerException("AUGH!!!");
				}
			}
			
			//grabs the numbah
			int teemSice = Integer.parseInt(lines[0].substring(11,12)); //Team size: 3
			int animations = Integer.parseInt(lines[1].substring(19,20)); //Battle Animations: 1
			
			if(!(teemSice==3 || teemSice==6)){
				return null;
			}if(!(animations==1 || animations==0)){
				return null;
			}
			
			
			int[] config = new int[]{teemSice,animations};
			
			return config;
			
		}catch(Exception e){
			if(sc!=null){
				sc.close();
			} 
			return null;
		}finally{
			if(sc!=null){
				sc.close();
			}
		}
	}
	
	public void saveSettingsToFile(int teemSice,int animations){
		try{
			if(checkFile()){
				this.saveFile.delete();
			}
			
			FileWriter fw = new FileWriter(saveFile,true);

			fw.write("// PokemonBattleSim config save file //"+"\n");
			fw.write("// dont add or remove lines if you dont wanna break this :p //"+"\n");
			fw.write("//"+"\n");
			fw.write("Team size: "+teemSice+"\n");
			fw.write("Battle Animations: "+animations+"\n");

			fw.close();
		}catch(IOException e){
			
		}
	}
	
}