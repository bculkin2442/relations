package relations.core;

import static relations.core.RelationCombinators.condAssign;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Contains methods to produce various simple 'leaf' relations (don't call their chain)
 * @author ben
 *
 */
public class SimpleRelations {
	/**
	 * Create a relation checking the binding status of a particular key
	 * @param key The key to check binding status for
	 * @return A relation that will return if the key is bound or not
	 */
	public static IRelation isBound(String key) {
		return new IRelation() {
			
			@Override
			public boolean process(Map<String, Object> env, IRelation chain) {
				return env.containsKey(key);
			}
		};
	}
	
	/**
	 * Creates a new relation that checks if a key in the enviroment is
	 * 	a) bound
	 *  b) satisfies a predicate
	 * @param key The key to test
	 * @param pred The predicate to test the key with
	 * @return A relation that returns whether the key exists & satisfies the predicate
	 */
	public static IRelation matches(String key, Predicate<Object> pred) {
		return new IRelation() {
			
			@Override
			public boolean process(Map<String, Object> env, IRelation chain) {
				return env.containsKey(key) ? pred.test(env.get(key)) : false;
			}
		};
	}

	/**
	 * Creates a new relation that prints a key in the enviroment
	 * @param key The key to print
	 * @return A relation that prints the value of the key if it is bound, or a message if not
	 */
	public static IRelation printKey(String key) {
		return new IRelation() {
			
			@Override
			public boolean process(Map<String, Object> env, IRelation chain) {
				System.out.print(env.containsKey(key) ? env.get(key) : "key not bound");
				return true;
			}
		};
	}

	/**
	 * Creates a new relation that prints a string
	 * @param s The string to print
	 * @return A relation that prints the given string
	 */
	public static IRelation printString(String s) {
		return new IRelation() {
			
			@Override
			public boolean process(Map<String, Object> env, IRelation chain) {
				System.out.print(s);
				return true;
			}
		};
	}

	public static IRelation unify(String key, Object val) {
		return new IRelation() {
			
			@Override
			public boolean process(Map<String, Object> env, IRelation chain) {
				if(env.containsKey(key)) {
					return env.get(key).equals(val);
				} else{
					// XXX I am not entirely sure that this is correct.
					// 		If it isn't, I probably have to rethink this method
					return condAssign(key, val).process(env, chain);
				}
			}
		};
	}
}
