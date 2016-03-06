package fuzzyMod.fuzzyLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyInterpreter {
	
	private FIS fis;
	
	public FuzzyInterpreter (int slotNo) {
		 InputStream inputFileStream = this.getClass().getResourceAsStream("fcl/test.fcl");
		 InputStream ruleFileStream = this.getClass().getResourceAsStream("fcl/slot"+slotNo+".fcl");
		 
		 SequenceInputStream stream = new SequenceInputStream(inputFileStream, ruleFileStream);
		 
	     fis = FIS.load(stream,true);
	    
	     // Error while loading?
	     if( fis == null ) { 
	         System.err.println("Can't load files");
	         return;
	     } 

	}
	
	public void setInput (String variable, double value) {
	     fis.setVariable(variable, value);
	}
	
	public Map <String, Double> evaluate() {
	     FunctionBlock functionBlock = fis.getFunctionBlock(null);

	     //Show Charts
//	     JFuzzyChart.get().chart(functionBlock);
	     
	     // Set inputs
//	     System.out.println(functionBlock.getRuleBlocks());
	     
	     // Evaluate
	     fis.evaluate();

	     // Show output variable's membership value	          
	     Iterator<RuleBlock> it = functionBlock.iterator();
	     Iterator<Rule> ruleit;
	     Map <String, Double> out = new HashMap <String, Double>();
	     if (it.hasNext()) {
	    	 ruleit = it.next().iterator();
	    	 while (ruleit.hasNext()) {
	        	 Rule rule = ruleit.next();
	        	 Iterator <RuleTerm> termit = rule.getConsequents().iterator();
	        	 while (termit.hasNext()) {
	        		 RuleTerm outcome = termit.next();
	        		 System.out.println(outcome.getVariable().getName() + " " + outcome.getVariable().getMembership("Yes"));
	        		 out.put(outcome.getVariable().getName(), outcome.getVariable().getMembership("Yes"));
	        	 }
	         }
	     } 
	     return out;
	}
	
	public String getAction() {
		Map <String, Double> out = this.evaluate();
		Map.Entry<String, Double> maxEntry = null;
		for (Map.Entry<String, Double> entry : out.entrySet())
		{
		    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		    }
		}
		return maxEntry.getKey();
	}
	
	
	public void printInputs() {
		System.out.println(fis.getFunctionBlock(null).getVariables().toString());
	}
}
