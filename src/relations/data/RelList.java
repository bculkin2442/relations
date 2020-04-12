package relations.data;

import java.util.function.Consumer;
import java.util.function.Predicate;

import relations.core.IRelation;
import static relations.core.RelationCombinators.*;
import static relations.core.SimpleRelations.*;

/**
 * A list with support for relational iteration
 * 
 * @author ben
 *
 * @param <T>
 *            The type of data stored in the list
 */
public class RelList<T> {
	private RelNode<T> data;

	/**
	 * Create a new list from a node in an existing one. Executes in O(1)
	 * time
	 * 
	 * @param next
	 *            The node to create the list from
	 */
	public RelList(RelNode<T> next) {
		data = next;
	}

	/**
	 * Prepend a node to this list. Executes in O(1) time
	 * 
	 * @param val
	 *            The value to append to this list
	 * @return This list for call chaining
	 */
	public RelList<T> prepend(T val) {
		data = new RelNode<T>(val, data);
		
		return this;
	}

	/**
	 * Append a node to this list. Executes in O(n) time
	 * 
	 * @param val
	 *            The value to append to the list
	 * @return This list for call chaining
	 */
	public RelList<T> append(T val) {
		if (data == null) {
			data.append(val);
		} else {
			prepend(val);
		}
		
		return this;
	}

	/**
	 * Check if this list is empty. Executes in O(1) time
	 * 
	 * @return Whether this list is empty or not
	 */
	public boolean isEmpty() {
		return data == null;
	}

	/**
	 * Gets the data at the head of this list. Executes in O(1) time
	 * 
	 * @return The data at the head of this list
	 */
	public T head() {
		return data.getData();
	}

	/**
	 * Gets a new list that doesn't contain the head. Executes in O(1)
	 * time.
	 * 
	 * @return A new list without the head
	 */
	public RelList<T> tail() {
		return new RelList<T>(data.getNext());
	}

	/**
	 * Remove the first item from the list and return it.
	 * 
	 * @return The first item in the list, or null if the list is empty
	 */
	public T first() {
		if (data == null) {
			return null;
		}
		
			T tmp = data.getData();
			data = data.getNext();
			return tmp;
	}

	/**
	 * Remove one copy of the specified element from the list
	 * 
	 * @param dat
	 *            The element to remove one occurance of.
	 */
	public void remove(T dat) {
		data = data.remove(dat);
	}

	/**
	 * Remove all elements that match the specified predicate
	 * 
	 * @param p
	 *            The predicate to emove matches for
	 */
	public void removeMatching(Predicate<T> p) {
		data = data.removeMatching(p);
	}

	/**
	 * Evaluate a consumer on each node of the tree
	 * 
	 * @param c
	 *            The consumer to evaluate on each node
	 */
	public void forEach(Consumer<T> c) {
		data.forEach(c);
	}

	/**
	 * Returns a relation that binds the given key to each value in turn
	 * 
	 * @param k
	 *            The key to bind values to
	 * @return A relation that binds each value to the key
	 */
	public IRelation items(String k) {
		return and(defined(data), data.items(k));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");

		forEach(t -> sb.append(t + ", "));

		sb.deleteCharAt(sb.length() - 1);

		sb.append(" ]");

		return sb.toString();
	}
}