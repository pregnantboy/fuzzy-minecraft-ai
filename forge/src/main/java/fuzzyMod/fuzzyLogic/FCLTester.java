package fuzzyMod.fuzzyLogic;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Iterator;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FCLTester {
	public static void main (String [] args) {
		 InputStream inputFileStream = FCLTester.class.getResourceAsStream("fcl/test.fcl");
		 InputStream ruleFileStream = FCLTester.class.getResourceAsStream("fcl/slot4.fcl");
		 
		 SequenceInputStream stream = new SequenceInputStream(inputFileStream, ruleFileStream);
		 
	     FIS fis = FIS.load(stream,true);
	    
	     // Error while loading?
	     if( fis == null ) { 
	         System.err.println("Can't load files");
	         return;
	     }

     // Show 
     FunctionBlock functionBlock = fis.getFunctionBlock(null);
//     JFuzzyChart.get().chart(functionBlock);
     
     // Set inputs
     fis.setVariable("Health", 5);
     fis.setVariable("PlayerHealth", 16);
     fis.setVariable("PlayerAttackerStrength", 7);
     System.out.println(functionBlock.getRuleBlocks());
     // Evaluate
     fis.evaluate();

//     // Show output variable's chart

     System.out.println(functionBlock.getRuleBlocks());
     Iterator<RuleBlock> it = functionBlock.iterator();
     Iterator<Rule> ruleit;
     if (it.hasNext()) {
    	 ruleit = it.next().iterator();
    	 while (ruleit.hasNext()) {
        	 Rule rule = ruleit.next();
    		 System.out.println("rule");
        	 Iterator <RuleTerm> termit = rule.getConsequents().iterator();
        	 while (termit.hasNext()) {
        		 RuleTerm outcome = termit.next();
        		 System.out.println(outcome.getVariable().getName() + " " + outcome.getVariable().getMembership("Yes"));
        	 }
         }
     }   

	}
}
