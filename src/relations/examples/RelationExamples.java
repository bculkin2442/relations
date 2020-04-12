package relations.examples;

import static relations.core.RelationStatements.*;
import static relations.core.RelationCombinators.*;
import static relations.core.SimpleRelations.*;

import java.util.Map;

import relations.core.IRelation;
import relations.core.SimpleRelations;
import relations.data.RelList;

/**
* Contains several examples on how to use relations
* 
* @author ben
*
*/
public class RelationExamples {
	/**
	* Simple example testing conditional assignment and execution
	*/
	public static void example1() {
		IRelation testBind = SimpleRelations.isBound("a");
		
		boolean aIsBound = doIf(condAssign("a", 7), testBind, newEnv());
		
		System.out.println("Binding status of a is "+ aIsBound);
	}
	
	/**
	* Simple example testing successful conjunction of relations
	*/
	public static void example2() {
		IRelation condition = and(condAssign("a", 5), condAssign("b", 3));
		
		doIf(condition, (env, chain) -> {
				int a = (Integer) env.get("a");
				int b = (Integer) env.get("b");
				
				System.out.println("a + b = " + (a + b));
				
				return true;
		} , newEnv());
	}
	
	/**
	* Simple example testing failing conjunction of relations
	* 
	*/
	public static void example3() {
		IRelation match = matches("a", t -> ((Integer) t) < 2);
		IRelation condition = and(condAssign("a", 5), match);
		
		doIf(condition,
			printKey("a"),
			printString("a isn't less than 2"),
			newEnv());
		
		System.out.println();
	}
	
	/**
	* Simple example testing backtracking for relations
	*/
	public static void example4() {
		IRelation match = matches("a", t -> ((Integer) t) < 3);
		IRelation backtrack = or(condAssign("a", 5), condAssign("a", 2));
		
		IRelation condition = and(backtrack, match);
			
		doIf(condition,
			printKey("a"),
			printString("neither 5 or 2 is < 3"),
			newEnv());
			
		System.out.println();
	}
	
	/**
	* Simple example testing domain iteration
	*/
	public static void example5() {
		IRelation sequence = seq(printKey("a"), printString("\n"));
		
		doFor(multiAssign("a", 5, 7, 2), sequence, newEnv());
	}
	
	/**
	* Simple example testing relational lists
	*/
	private static void example6() {
		RelList<Integer> r = new RelList<>(null);
		
		r.prepend(1);
		r.prepend(2);
		r.prepend(3);
		
		Map<String, Object> env = newEnv();
		
		IRelation domain = r.items("item");
		
		IRelation exp = seq(printKey("item"), printString("\n"));
		
		doFor(domain, (env1, chain) -> {
				System.out.println("I was eval'd");
				return exp.process(env1, chain);
		} , env);
		
		env.forEach((t, u) -> System.out
			.println("Key: " + t + ", Value: " + u));
	}
	
	public static void main(String[] args) {
		System.out.println("Example 1: ");
		
		example1();
		
		System.out.println("\n\nExample 2: ");
		example2();
		
		
		System.out.println("\n\nExample 3: ");
		example3();
		
		
		System.out.println("\n\nExample 4: ");
		example4();
		
		
		System.out.println("\n\nExample 5: ");
		example5();
		
		
		System.out.println("\n\nExample 6: ");
		example6();
	}
}