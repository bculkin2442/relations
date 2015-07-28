package relations.core;

import java.util.Map;

/**
 * Core interface for a relation
 * 
 * Based very heavily off of the logic programming support in LEDA
 * @author ben
 *
 */
public interface IRelation {
	/**
	 * Evaluate this relation
	 * @param env A map containing relationally bound variables
	 * @param chain The relation to chain after this one
	 * @return Whether or not this relation is true
	 */
	boolean process(Map<String, Object> env, IRelation chain);
}
