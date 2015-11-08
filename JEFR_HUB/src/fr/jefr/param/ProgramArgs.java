package fr.jefr.param;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ProgramArgs {
	private Options options = new Options();
	private CommandLine cmd;
	
	public ProgramArgs(String[] args){
		this.setOptions();
		try {
			CommandLineParser parser = new DefaultParser();
			this.cmd = parser.parse( this.options, args);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Unexpected exception:" + e.getMessage());
		}
	}
	
	private void setOptions(){
		this.options.addOption("h", "help",false, "print this message");
		this.options.addOption("d", true, "directory pictures path");
		this.options.addOption("c", true, "camera index (0 by default)");
	}
	
	public boolean askHelp(){
		if (cmd != null){
			try{
				if (this.cmd.hasOption("h")){
					HelpFormatter formatter = new HelpFormatter();
					formatter.printHelp("JEFR", options, true );
					return (true);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return (false);
	}
	
	public CommandLine getArgs(){
		return (this.cmd);
	}
	
}
