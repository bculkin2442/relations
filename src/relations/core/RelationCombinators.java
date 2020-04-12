package relations.core;

import java.util.function.Supplier;

import relations.instances.TrueRelation;

/**
* Creates relations that do invoke their chain
* @author ben
*
*/
public class RelationCombinators {
	
	/**
	* Conditionally bind a value for the computation of a relation
	* 
	* @param key
	*            The key to bind the value to
	* @param val
	*            The value to bind to the key
	* @return A relation that will bind the value to the given key and execute
	*         the given relation, keeping the binding if it succedes, and
	*         breaking it if not
	*/
	public static IRelation condAssign(String key, Object val) {
		return (env, chain) -> {
			env.put(key, val);
			
			if (chain.process(env, new TrueRelation())) {
				return true;
			}
			
			env.remove(key);
			
			return false;
		};
	}
	
	/**
	* Conditionally bind a value for a relation, rebinding to the next until it succedes
	* @param key The key to bind a value to
	* @param val The values to try to bind to the key
	*/
	public static IRelation multiAssign(String key, Object... val) {
		return (env, chain) -> {
			for (Object object : val) {
				env.put(key, object);
				
				if (chain.process(env, new TrueRelation())) {
					return true;
				}
			}
			
			env.remove(key);
			
			return false;
		};
	}
	
	/**
	* Sequence two relations, returning true only if both succede
	* 
	* @param left
	*            The first relation to sequence
	* @param right
	*            The second relation to sequence
	* @return
	*/
	public static IRelation and(IRelation left, IRelation right) {
		return (env, chain) -> {
			IRelation chainRel = (env1, chain1) -> right.process(env1, chain1);
			
			return left.process(env, chainRel);
		}
	}
	
	/**
	* Sequence two relations, returning true only if both succede
	* 
	* @param left
	*            The first relation to sequence
	* @param right
	*            The second relation to sequence
	* @return
	*/
	public static IRelation and(IRelation left, Supplier<IRelation> right) {
		return (env, chain) -> {
			IRelation chainRel = (env1, chain1) -> right.get().process(env1, chain1);
			
			return left.process(env, chainRel);
		}
	}
	
	/**
	* Sequence two relations, returning true if at least one succedes
	*/
	public static IRelation or(IRelation left, IRelation right) {
		return (env, chain) -> {
			if(left.process(env, chain)) {
				return true;
			}
			
			return right.process(env, chain);
		};
	}
	
	/**
	* Evaluate a string of relations sequentially, ignoring results
	* @param rels The relations to execute
	* @return A relation that will execute the given relations in sequence
	*/
	public static IRelation seq(IRelation... rels) {
		return (env, chain) -> {
			for (IRelation iRelation : rels) {
				iRelation.process(env, chain);
			}
			
			return true;
		};
	}
}