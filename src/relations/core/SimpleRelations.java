package relations.core;

import static relations.core.RelationCombinators.condAssign;

import java.util.function.Predicate;

import relations.instances.TrueRelation;

/**
 * Contains methods to produce various simple 'leaf' relations (don't call
 * their chain)
 * 
 * @author ben
 *
 */
public class SimpleRelations {
	/**
	 * Create a relation checking the binding status of a particular key
	 * 
	 * @param key
	 *            The key to check binding status for
	 * @return A relation that will return if the key is bound or not
	 */
	public static IRelation isBound(String key) {
		return (env, chain) -> env.containsKey(key);
	}

	/**
	 * Creates a new relation that checks if a key in the enviroment is a)
	 * bound and b) satisfies a predicate
	 * 
	 * @param key
	 *            The key to test
	 * @param pred
	 *            The predicate to test the key with
	 * @return A relation that returns whether the key exists & satisfies
	 *         the predicate
	 */
	public static IRelation matches(String key, Predicate<Object> pred) {
		return (env, chain) -> {
			if(env.containsKey(key)) {
				return pred.test(env.get(key));
			}
			
			return false;
		};
	}

	/**
	 * Creates a new relation that prints a key in the enviroment
	 * 
	 * @param key
	 *            The key to print
	 * @return A relation that prints the value of the key if it is bound,
	 *         or a message if not
	 */
	public static IRelation printKey(String key) {
		return (env, chain) -> {
			if(env.containsKey(key)) {
				System.out.print(env.get(key));
			} else {
				System.out.print("key not bound");
			}
			
			return true;
		};
	}

	/**
	 * Creates a new relation that prints a string
	 * 
	 * @param s
	 *            The string to print
	 * @return A relation that prints the given string
	 */
	public static IRelation printString(String s) {
		return (env, chain) -> {
			System.out.print(s);
			
			return true;
		};
	}

	/**
	 * Tries to unify the two given values. If key is bound in the
	 * enviroment it will be evaluated in, check for equality. Else,
	 * conditionally bind the value to the key, and continue the chain
	 * 
	 * @param key
	 *            The key to attempt to unify to
	 * @param val
	 *            The value to attemt to unify
	 * @return A relation that will unify the two specified values
	 */
	public static IRelation unify(String key, Object val) {
		return (env,
				chain) -> (env.containsKey(key) ? env.get(key).equals(val)
						: condAssign(key, val).process(env, chain));
	}

	/**
	 * Convert a boolean value into a relation
	 * 
	 * @param b
	 *            The boolean to convert
	 * @return A relation created from the boolean
	 */
	public static IRelation boolAsRel(boolean b) {
		return (env, chain) -> b && chain.process(env, new TrueRelation());
	}

	/**
	 * Returns a relation that checks if a given object is defined
	 * 
	 * @param a
	 *            The value to check definition for
	 * @return A relation that checks if a object is defined
	 */
	public static IRelation defined(Object a) {
		return boolAsRel(a != null);
	}
}
