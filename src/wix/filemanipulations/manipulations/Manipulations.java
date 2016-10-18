package wix.filemanipulations.manipulations;

import java.util.HashMap;

public class Manipulations {

	private HashMap<String,IManipulations> manipulations;

	public HashMap<String, IManipulations> getManipulations() {
		return manipulations;
	}

	public Manipulations(HashMap<String, IManipulations> manipulations) {
		super();
		this.manipulations = manipulations;
	}
}
