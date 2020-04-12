package relations.data;

import java.util.function.Consumer;
import java.util.function.Predicate;

import relations.core.IRelation;

import static relations.core.RelationCombinators.*;
import static relations.core.SimpleRelations.*;

/**
 * A node for storing data in a relational list
 * 
 * @author ben
 *
 * @param <T>
 *            The data stored in the list
 */
public class RelNode<T> {
	private T			data;
	private RelNode<T>	next;

	/**
	 * Create a new node in a relational list
	 * 
	 * @param data
	 *            The data to be contained in this node
	 * @param next
	 *            The next node in this relational list
	 */
	public RelNode(T data, RelNode<T> next) {
		this.data = data;
		this.next = next;
	}

	/**
	 * Append a value to the end of this node chain
	 * 
	 * @param val
	 *            The value to append
	 */
	public void append(T val) {
		if (next == null) {
			next = new RelNode<T>(val, null);
		} else {
			next.append(val);
		}
	}

	/**
	 * Gets the data stored in this node
	 * 
	 * @return the data stored in this node
	 */
	public T getData() {
		return data;
	}

	/**
	 * Gets the next node in the chain
	 * 
	 * @return the next node in the chain
	 */
	public RelNode<T> getNext() {
		return next;
	}

	/**
	 * Returns a new list with one less copy of the specified value
	 * 
	 * @param dat
	 *            The value to remove from the list
	 * @return A list that has one less occurrance of the specified value
	 */
	public RelNode<T> remove(T dat) {
		if (dat.equals(data)) {
			return getNext();
		}
		
		if(next == null) {
			return this;
		}
		
		return new RelNode<T>(data, next.remove(dat));
	}

	/**
	 * Returns a new list without values that match the specified predicate
	 * 
	 * @param p
	 *            The predicate to remove matching elements of
	 * @return A new list without any elements that match the predicate
	 */
	public RelNode<T> removeMatching(Predicate<T> p) {
		RelNode<T> rem = null;

		if (next != null) {
			rem = next.removeMatching(p);
		}

		if (p.test(data)) {
			return rem;
		}
		
		return new RelNode<T>(data, rem);
	}

	/**
	 * Evaluate a consumer for each element of the list
	 * 
	 * @param c
	 *            The consumer to evaluate
	 */
	public void forEach(Consumer<T> c) {
		c.accept(data);
		
		if (next != null) {
			next.forEach(c);
		}
	}

	/**
	 * Bind the key to each value in the list in turn
	 * 
	 * @param k
	 *            The key to bind each value to
	 * @return A relation that binds the key to each value in turn
	 */
	public IRelation items(String k) {
		return or(unify(k, data),
			and(defined(next), () -> next.items(k)));
	}
}
