package wot.raspberry.rid;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import arces.unibo.SEPA.client.pattern.ApplicationProfile;
import arces.unibo.SEPA.client.pattern.Producer;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;

public class App extends Producer {
	
	public App(ApplicationProfile appProfile, String updateID) {
		super(appProfile, updateID);
	}

	private static final String APP_TITLE = "RaspberryPiWoT";
	private static final Logger logger = LogManager.getLogger(APP_TITLE);
	
	protected static String sap_file = "";
	
	private static final ApplicationProfile generic_thing = new ApplicationProfile();
	private static final ApplicationProfile rpi_context = new ApplicationProfile();
	


    public static void main(String[] args) {
    	ArgumentParser parser = ArgumentParsers.newArgumentParser(APP_TITLE)
    			.defaultHelp(true)
    			.description("RaspberryPi WoT thing server");
    	parser.addArgument("--behaviour")
    			.help("Can be all-context, small-context, clear");
    	parser.addArgument("-tstart","-tStart").action(Arguments.storeTrue())
    			.help("Start WOT thing server");
    	
    	Namespace res = parser.parseArgsOrFail(args);
    	
    	if (res.getString("behaviour")!=null) {
    		rpi_context.load(App.class.getClassLoader().getResource("rpi_context.sap").getPath());
		    switch (res.getString("behaviour")) {
		    case "all-context":
		    	logger.info("Detected all-context");
		    	new App(rpi_context,"INSERT_CONTEXT").update(null);
		    	break;
		    case "small-context":
		    	logger.info("Detected small-context");
		    	new App(rpi_context,"INSERT_SMALL_CONTEXT").update(null);
		    	break;
		    case "clear":
		    	logger.info("Detected clear");
		    	new App(rpi_context,"CLEAR_ALL").update(null);
		    	break;
	    	default:
	    		logger.fatal("Wrong --behaviour parameter");
	    		parser.printHelp();
		    }
    	}
    	else {
    		
    	}
    	
//    	logger.info("RaspberryPi WoT thing server is now running!");
//        
//    	try {
//    		System.out.println(App.class.getClassLoader().getResource("generic_thing.sap").getPath());
//    		sap_file = IOUtils.toString(App.class.getClassLoader().getResourceAsStream("generic_thing.sap"));
//    	} catch (IOException e) {
//    		e.printStackTrace();
//    	}
//
//    	JSONObject sap_json = new JSONObject(sap_file.replace('\n', ' '));
//    	JSONObject parameters = (JSONObject) sap_json.get("parameters");
    	//System.out.println("Parameters detected:\n"+parameters.toString().replace(',', '\n').replaceAll("[\"{}]", "").replace(":", ":\t"));
    
    	//generic_thing.load(App.class.getClassLoader().getResource("generic_thing.sap").getPath());
    }
}
