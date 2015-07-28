package relations.instances;

import java.util.Map;

import relations.core.IRelation;

public final class FalseRelation implements IRelation {
	@Override
	public boolean process(Map<String, Object> env, IRelation chain) {
		return false;
	}
}