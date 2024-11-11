package model;

/**
 * Interface implemented by Visitor Acceptors
 * to accept Visitor's visit.
 */
public interface VisitorAcceptor {
	/**
	 * Accept method for Visitor Pattern,
	 * used to encapsulation of moving algorithms of characters.
	 * An object accept a visitor and the visit method of the Visitor
	 * activates it specific overloading visit method ,
	 * consistent with the polymorphism of the acceptor object
	 * @param visitor visitor to accept
	 */
	public void accept(Visitor visitor); 
	
}
