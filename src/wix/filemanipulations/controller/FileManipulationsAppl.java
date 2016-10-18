package wix.filemanipulations.controller;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import wix.filemanipulations.io.LinesInputOutput;
import wix.filemanipulations.manipulations.IManipulations;
import wix.filemanipulations.manipulations.Manipulations;

public class FileManipulationsAppl {

	public static void main(String[] args) throws IOException {
		BufferedReader input = getInput(args[0]);
		PrintWriter output = getOutput(args[1]);
				
		HashMap<String,IManipulations> mapManip = getMapManipulations();
		System.out.println("All manipulations: " + mapManip.keySet());
		
		String manipulation = getManipulation();
		if (manipulation == null || manipulation.equals("exit")) {
			System.out.println("I'll glad to see you late.");
			return;
		}

		try {
			manipulationAction(mapManip, manipulation, input, output);
		} catch (IOException e) {
			System.out.println("incorrect input/output");
		}

		System.out.println("good bye");
	}

	private static PrintWriter getOutput(String str) {
		PrintWriter output = null;
		try {
			output = new PrintWriter(str);
		} catch (FileNotFoundException e) {
			System.out.println("output file not found");
		}
		return output;
	}

	private static BufferedReader getInput(String str) {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(str));
		} catch (FileNotFoundException e) {
			System.out.println("input file not found");
		}
		return input;
	}

	private static String getManipulation() throws IOException {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please write manipulation or exit");
		return console.readLine();
	}

	private static HashMap<String, IManipulations> getMapManipulations() {
		AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml");
		Manipulations manipulations = (Manipulations) ctx.getBean("manipulations");
		HashMap<String,IManipulations> res = manipulations.getManipulations();
		ctx.close();
		return res;
	}

	private static void manipulationAction(HashMap<String,IManipulations> mapManip, 
			String manipulation, BufferedReader input, PrintWriter output) throws IOException{
		LinesInputOutput lines = new LinesInputOutput(input, output);
		List<String> linesInput = lines.getLines();
		IManipulations manipul = mapManip.get(manipulation);
		if(manipul == null)
			System.out.println("incorrect manipulate");
		List<String> resLines = manipul.manipulate(linesInput);
		lines.putLines(resLines);
	}
}
