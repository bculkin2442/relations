package relations.instances;

import java.util.Map;

import relations.core.IRelation;

/**
 * A relation that will always return true
 * @author ben
 *
 */
public final class TrueRelation implements IRelation {

	@Override
	public boolean process(Map<String, Object> env, IRelation chain) {
		return true;
	}
}