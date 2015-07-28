package relations.core;

import java.util.HashMap;
import java.util.Map;

import relations.instances.FalseRelation;
import relations.instances.TrueRelation;

/**
 * Contains various things doable with relations
 * @author ben
 *
 */
public class RelationStatements {
	/**
	 * Creates a new, fresh enviroment
	 * @return A new enviroment with no bindings
	 */
	public static Map<String, Object> newEnv() {
		return new HashMap<>();
	}
	/**
	 * Conditionally executes one of two relations.
	 * 
	 * The condition and the statement relation will share an enviroment
	 * @param condition The relation that defines which one to execute
	 * @param ifTrue The relation to execute on the condition succeding
	 * @param ifFalse The relation to execute on the condition failing
	 */
	public static boolean doIf(IRelation condition, IRelation ifTrue, IRelation ifFalse
			, Map<String, Object> env) {
		if(condition.process(env, new TrueRelation())) {
			return ifTrue.process(env, new TrueRelation());
		} else {
			return ifFalse.process(env, new TrueRelation());
		}
	}

	/**
	 * Conditionally execute a relation
	 * 
	 * The condition and ifTrue relations share an enviroment
	 * 
	 * @param condition The condition to use
	 * @param ifTrue The relation to execute if the statement is true
	 * @param env The enviroment to execute the relations in
	 * @return The status of the relation if the condition was met, false otherwise
	 */
	public static boolean doIf(IRelation condition, IRelation ifTrue, Map<String, Object> env) {
		if(condition.process(env, new TrueRelation())) {
			return ifTrue.process(env, new TrueRelation());
		} else {
			return false;
		}
	}

	/**
	 * Step through a relation until a stopping condition is reached
	 * @param domain The relation to step through
	 * @param exp The relation to evaluate each step
	 * @param stop The stopping condition
	 * @param env The enviroment to evaluate it in
	 * @return The final state of the step relation
	 */
	public static boolean doFor(IRelation domain, IRelation exp, IRelation stop,
			Map<String, Object> env) {
		return domain.process(env, new IRelation() {
			
			@Override
			public boolean process(Map<String, Object> env, IRelation chain) {
				exp.process(env, new TrueRelation());
				return stop.process(env, new TrueRelation());
			}
		});
	}

	/**
	 * Step through all of the values in a relation
	 */
	public static boolean doFor(IRelation domain, IRelation exp, Map<String, Object> env) {
		return doFor(domain, exp, new FalseRelation(), env);
	}
}
