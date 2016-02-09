package fuzzyMod.fuzzyLogic;

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
	 String fileName = FCLTester.class.getResource("fcl/test.fcl").getPath();
	 
     FIS fis = FIS.load(fileName,true);
    
     // Error while loading?
     if( fis == null ) { 
         System.err.println("Can't load file: '" + fileName + "'");
         return;
     }

     // Show 
     FunctionBlock functionBlock = fis.getFunctionBlock(null);
//     JFuzzyChart.get().chart(functionBlock);
     
     // Set inputs
     fis.setVariable("Health", 6);
     fis.setVariable("PlayerHealth", 20);
     fis.setVariable("ArrowCount", 20);
//     System.out.println(functionBlock.getRuleBlocks());
     // Evaluate
     fis.evaluate();

     // Show output variable's chart
     Variable out1 = functionBlock.getVariable("AttackNearestEnemyWithMeleeWeapon");
     Variable out2 = functionBlock.getVariable("AttackNearestEnemyWithArrows");
     Variable out3 = functionBlock.getVariable("AttackPlayersTargetWithMeleeWeapon");
          
     Iterator<RuleBlock> it = functionBlock.iterator();
     Iterator<Rule> ruleit;
     if (it.hasNext()) {
    	 ruleit = it.next().iterator();
    	 while (ruleit.hasNext()) {
        	 Rule rule = ruleit.next();
        	 Iterator <RuleTerm> termit = rule.getConsequents().iterator();
        	 while (termit.hasNext()) {
        		 RuleTerm outcome = termit.next();
        		 System.out.println(outcome.getVariable().getName() + " " + outcome.getVariable().getMembership("Yes"));
        	 }
         }
     }   

	}
}
