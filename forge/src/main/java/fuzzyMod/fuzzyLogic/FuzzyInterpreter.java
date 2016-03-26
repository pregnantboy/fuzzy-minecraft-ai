package fuzzyMod.fuzzyLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;
/**
 * Reads rules file and returns output action string
 */
public class FuzzyInterpreter {

	private FIS fis;
	
	/**
	 * Constructor method for FuzzyInterpretor
	 * @param slotNo Slot number to read the rules from.
	 */
	public FuzzyInterpreter(int slotNo) {
		InputStream inputFileStream = this.getClass().getResourceAsStream("fcl/test.fcl");
		InputStream ruleFileStream = this.getClass().getResourceAsStream("fcl/slot" + slotNo + ".fcl");

		SequenceInputStream stream = new SequenceInputStream(inputFileStream, ruleFileStream);

		fis = FIS.load(stream, true);

		// Error while loading?
		if (fis == null) {
			System.err.println("Can't load files");
			return;
		}

	}
	
	/**
	 * Sets the value of an input variable in the Fuzzy Inference System.
	 * @param variable Input variable to set.
	 * @param value Value to pass into the variable.
	 */
	public void setInput(String variable, double value) {
		fis.setVariable(variable, value);
	}
	
	/**
	 * Evaluates the FIS and return the outcomes and their respective degree of support.
	 */
	public Map<String, Double> evaluate() {
		FunctionBlock functionBlock = fis.getFunctionBlock(null);

		// Show Charts
		// JFuzzyChart.get().chart(functionBlock);

		// Set inputs
		// System.out.println(functionBlock.getRuleBlocks());

		// Evaluate
		fis.evaluate();

		// Show output variable's membership value
		Iterator<RuleBlock> it = functionBlock.iterator();
		Iterator<Rule> ruleit;
		Map<String, Double> out = new HashMap<String, Double>();
		if (it.hasNext()) {
			ruleit = it.next().iterator();
			while (ruleit.hasNext()) {
				Rule rule = ruleit.next();
				Iterator<RuleTerm> termit = rule.getConsequents().iterator();
				while (termit.hasNext()) {
					RuleTerm outcome = termit.next();
					System.out.println(
							outcome.getVariable().getName() + " " + outcome.getVariable().getMembership("Yes"));
					out.put(outcome.getVariable().getName(), outcome.getVariable().getMembership("Yes"));
				}
			}
		}
		return out;
	}
	
	/**
	 * Calls evaluate method and returns sorted outcome.
	 */
	public ArrayList<String> getActions() {
		Map<String, Double> out = this.evaluate();
		return sortByValue(out);
	}
	
	/**
	 * Prints input in console for debugging. 
	 */
	public void printInputs() {
		System.out.println(fis.getFunctionBlock(null).getVariables().toString());
	}
	
	/**
	 * Sorts the map in descending order.
	 */
	public static <K, V extends Comparable<? super V>> ArrayList<K> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return -(o1.getValue()).compareTo(o2.getValue());
			}
		});

//		Map<K, V> result = new LinkedHashMap<K, V>();
//		for (Map.Entry<K, V> entry : list) {
//			result.put(entry.getKey(), entry.getValue());
//		}
		ArrayList <K> sortedList = new ArrayList<K>();
		for (Map.Entry<K, V> entry : list) {
			sortedList.add(entry.getKey());
		}
		return sortedList;
	}

}
