package fuzzyMod.fuzzyLogic;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FCLTester {
	public static void main (String [] args) {
	 String fileName = FCLTester.class.getResource("fcl/tipper.fcl").getPath();
	 System.out.println(FCLTester.class.getResource("fcl/tipper.fcl").getPath());
     FIS fis = FIS.load(fileName,true);
    
     // Error while loading?
     if( fis == null ) { 
         System.err.println("Can't load file: '" + fileName + "'");
         return;
     }

     // Show 
     FunctionBlock functionBlock = fis.getFunctionBlock(null);
     JFuzzyChart.get().chart(functionBlock);

     // Set inputs
     fis.setVariable("service", 9);
     fis.setVariable("food", 9);

     // Evaluate
     fis.evaluate();

     // Show output variable's chart
     Variable tip = functionBlock.getVariable("tip");
     JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);
     System.out.println(functionBlock);

     // Print ruleSet
   //  System.out.println(fis);
	}
}
