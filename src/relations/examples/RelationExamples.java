package relations.examples;

import static relations.core.RelationStatements.*;
import static relations.core.RelationCombinators.*;
import static relations.core.SimpleRelations.*;
import java.util.Map;

import relations.core.IRelation;
import relations.core.SimpleRelations;

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
		System.out.println("Binding status of a is " + doIf(condAssign("a", 7),
				testBind, newEnv()));
	}

	/**
	 * Simple example testing successful conjunction of relations
	 */
	public static void example2() {
		doIf(and(condAssign("a", 5), condAssign("b", 3)), new IRelation() {
			
			@Override
			public boolean process(Map<String, Object> env, IRelation chain) {
				System.out.println("a + b = " + (((Integer)env.get("a")) + ((Integer)env.get("b"))));
				return true;
			}
		}, newEnv());
	}
	
	/**
	 * Simple example testing failing conjunction of relations
	 * 
	 */
	public static void example3() {
		doIf(and(condAssign("a", 5), matches("a", t -> ((Integer)t) < 2)),
				printKey("a"), printString("a isn't less than 2"), newEnv());
		System.out.println();
	}
	
	/**
	 * Simple example testing backtracking for relations
	 */
	public static void example4() {
		doIf(and(or(condAssign("a", 5), condAssign("a", 2)), matches("a", t -> ((Integer)t) < 3)),
				printKey("a"), printString("neither 5 or 2 is < 3")
				, newEnv());
		System.out.println();
	}

	/**
	 * Simple example testing domain iteration
	 */
	public static void example5() {
		doFor(multiAssign("a", 5, 7, 2), seq(printKey("a"), printString("\n")), newEnv());
	}
	
	public static void main(String[] args) {
		example1();
		example2();
		example3();
		example4();
		example5();
	}
}
