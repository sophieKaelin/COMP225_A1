class PNode {
	int deg;  // The degree of a term
	float coeff;  // The coefficient of a term
	PNode next;
	
	PNode (int d, float c) {  // Constructor: builds a node with given data
		next= null;
		deg= d;
		coeff= c;
	}
	
	PNode (int d, float c, PNode n){ // Constructor: builds a node with given reference
		next = n;
		deg= d;
		coeff= c;
	}
	
	// Basic node operations	
	void scale(float k) { // scales coeff by k
		coeff= coeff*k;
	}
	
	void multiplyByX(int d) { // increases the degree by d
		 deg= deg + d;
	}

	boolean simplified(PNode p) {
	// PRE: p is the first node of a list of PNodes.
	// POST: Returns true iff the nodes are sorted (descending) according to their degree field	
	// AND all nodes have distinct deg fields (no repeats)
    // AND all nodes have a non-zero coefficient field.
		PNode temp = p;
		if(temp.next == null) {
			return true;
		}
		else if(temp.deg > temp.next.deg && temp.coeff != 0) {
			return simplified(temp.next);
		}
		return false;
	}
	
	
	PNode simplify(PNode p) {
	// PRE: p is the first node of a list of PNodes. All nodes in the list are already
	// Sorted descending according to their deg field
	// POST:  Rearranges the list so that it is simplified but equivalent to the original representation,
  	// i.e. ensures that each node has distinct deg field (by using polynomial arithmetic)
	// AND removes any nodes with coeff field set to 0.
	// The simplified representation must be mathematically equivalent (as a polynomial)
  	// to the original representation.
	// Returns the first node of the now rearranged list
		
		// If it is already simplified, return the list.
		if(simplified(p) == true) {
			return p;
		}
		// Set the first element of the list to the first Node with non 0 coefficient.
		while(p.coeff == 0) {
			p = p.next;
		}
		
		PNode temp = p;
		while (temp.next != null) {
			// Check if there are multiple Nodes with the same degree and condense
			while(temp.deg == temp.next.deg) {
				temp.coeff = temp.coeff + temp.next.coeff;
				temp.next = temp.next.next;
			}
			// Check if the coefficient is 0.
			if(temp.coeff == 0) {
				temp.next = temp.next.next;
			}
			temp = temp.next;
		}
		// Check if the last term has a coefficient of 0
		if (temp.coeff == 0) {
			temp.next = null;
		}
		return p;
		
	}
	
	PNode priorityAdd(PNode p, int d, float c) {
   	// PRE: the given list p list is simplified (simplified returns TRUE); 
   	// POST: Creates a new PNode with the given data (deg = d, coeff=c) and adds it to the
   	// current list so that the result is also simplified.
	// Returns the first node of the list with the new addition
		PNode newNode = new PNode(d, c, null);
		PNode temp = p;
		// Check if the Node should be the first Node
		if(temp.deg < d) {
			newNode.next = temp;
			simplify(p);
			return p;
		}
		
		// Find the correct place of newNode by finding First Node whose Degree is less than d.
		while(temp.next.coeff >= d) {
			temp = temp.next;
		}
		newNode.next = temp.next;
		temp.next = newNode;
		
		simplify(p);
		return p;	
	}
}

 class PolyList {
	 PNode first= null;
	 // Class invariant -- an instance of PolyList must always satisfy the condition: 
	 // The list is empty OR 
	 // (The list is not empty AND
	 // All nodes have distinct deg fields AND
	 // There are no nodes having coeff equal to 0 AND
	 // All nodes are ordered from first to last with decreasing deg field. )
	 
	 PolyList(PNode p) { // Constructor that sets its first node to the given PNode p.
		 first= p;
	 }
	 
	 int getDegree() { 
		 // Returns the largest degree in the list representation.
		 int largestDeg = first.deg;
		 PNode temp = first;
		 while(temp != null) {
			 if(temp.deg > largestDeg) {
				 largestDeg = temp.deg;
			 }
			 temp = temp.next;
		 }		 
		 return largestDeg;
	 }
	 
	 float getConstant() {
		 // Returns the coefficient of the node with zero degree
		 PNode temp = first;
		 while(temp != null) {
			 if(temp.deg == 0) {
				 return temp.coeff;
			 }
			 temp = temp.next;
		 }		 
		 return 0;
	 }
	 
	 boolean isConstant() {
		 // Returns true iff the polynomial is constant (as a function).
		 return first.deg == 0;
	 }
	 
	 float evaluate(float a) {
		 // returns the result of the polynomial evaluated at a.
		 PNode temp = first;
		 float evaluation = 0;
		 while(temp != null) {
			 evaluation += (temp.coeff * (float)(Math.pow(a, temp.deg)));
			 temp = temp.next;
		 }
		 return evaluation;	 
	 }
	 
	 
	void multiplyByX(int d) {
		// Multiplies the polynomial by x^d (x to the power of d).
		PNode temp = first;
		while(temp != null) {
			temp.multiplyByX(d);
			temp = temp.next;
		}
	}
	
	void add(PolyList p) {
		// Adds the given polynomial p to the current polynomial.
		// Check if P is empty
		if (p == null) {
			return; 
		}
		
		// Check if our list is empty
		if(first == null) {
			first = p.first;
			return;
		}
		
		PNode tempP = p.first;
		PNode tempF = first;
		PNode tempNext;		
		
		while (tempP!=null) {
			// Add all the large elements of P to start of List.
			if(tempP.deg > tempF.deg) {
				tempNext = tempP.next;
				tempP.next = tempF;
				tempP = tempNext;
			}
			// Adds equal degrees
			else if(tempP.deg == tempF.deg) {
				tempF.coeff += tempP.coeff;
				tempP = tempP.next;
			}
			// adds element if it exist between elements.
			else if (tempF.next != null &&(tempP.deg < tempF.deg && tempP.deg > tempF.next.deg)) {
				tempNext = tempP.next;
				tempP.next = tempF.next;
				tempF.next = tempP;
				tempP = tempNext;
			}
			// If we are at the end of our list, add all remaining elements of P to the end.
			else if (tempF.next == null) {
				tempF.next = tempP;
				tempP = null;
			}
			else {
				tempF = tempF.next;
			}
		}
		
		// Check if the coefficient is 0.
		PNode temp = first;
		while (temp!=null) {
			if(temp.coeff == 0) {
				temp.next = temp.next.next;
			}
			temp = temp.next;
		}
		
	}
	
	void differentiate() {
		// Differentiates the current polynomial.
		PNode temp = first;
		while(temp!=null) {
			// Remove all Elements with degree of 0.
			while(temp.next != null && temp.next.deg == 0) {
				temp.next = temp.next.next;
			}
			temp.coeff *= temp.deg;
			temp.deg = temp.deg -1;
			temp = temp.next;
		}
	}
	
	PolyList pGCD(PolyList p, PolyList q) { //TODO
		                                    // PRE: p, q are polynomials with leading term having coeff field equal to 1 
											// Returns the GCD of p and q. 
		                                    // Ensure that the coeff field of the leading term is 1. 
		return null;
	}		
}