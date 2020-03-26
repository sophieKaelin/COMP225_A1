import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.io.*;

// These tests illustrate some basic properties of the methods you have been asked
// to implement. They are not exhaustive.
// For assessment there will also be cases where various combinations of
// the methods will be tested. You are strongly recommended to create some tests for
// yourself to ensure that your implementations satisfy all the specifications. 


public class AssignmentTests {

	@Test
	public void testSimplified() {
		
		int degs[]= {2,1,0};  // Array of degrees
		float coeffs[]= {2, -1, 6};		 // Corresponding array of coefficients
		ArrayList<PNode> testList= createList(degs, coeffs);
		PNode input= testList.get(0);
		boolean result= input.simplified(input);
		assertTrue(result);
		
		int degs1[]= {1,5,0};
		float coeffs1[]= {2, -1, 6};
		
		ArrayList<PNode> testList1= createList(degs1, coeffs1);	
		PNode input1= testList1.get(0);
		boolean result1= input1.simplified(input1);
		assertFalse(result1);
		
		int degs2[]= {2,1,0};
		float coeffs2[]= {2, 0, 6};
		
		ArrayList<PNode> testList2= createList(degs2, coeffs2);		
		PNode input2= testList2.get(0);
		boolean result2= input1.simplified(input2);
		assertFalse(result2);		
	}	
	
	@Test
	public void testSimplify(){
		
		int degs[]= {2,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> simplifiedList= createList(degs, coeffs);				
		
		
		int degs1[]= {4, 2, 1, 1, 0};
		float coeffs1[]= {0, 2,-2,1, 6};		
		ArrayList<PNode> testList= createList(degs1, coeffs1);
		
		PNode start= testList.get(0);
		PNode nResult= start.simplify(start);
		PNode temp= nResult;
		
		for(int i=0; i<3; i++){
			assertEquals(temp.deg, simplifiedList.get(i).deg);
			assertEquals(temp.coeff, simplifiedList.get(i).coeff, 0.2);
			temp=temp.next;
		}		
	}
	
	@Test
	public void testPriorityAdd() {
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		
		int degs1[]= {3,2, 1,0};
		float coeffs1[]= {2,1,  -1, 6};		
		ArrayList<PNode> targetList= createList(degs1, coeffs1);
		
		PNode start= testList.get(0);
		PNode nResult= start.priorityAdd(start, 2, 1);
		PNode temp= nResult;
		
		for(int i=0; i<4; i++){
			assertEquals(temp.deg, targetList.get(i).deg);
			assertEquals(temp.coeff, targetList.get(i).coeff, 0.2);
			temp=temp.next;
		}		
	}
			
	@Test
	public void testGetDegree()	{
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		
		PolyList pp= new PolyList(testList.get(0));
		
		int x= pp.getDegree();
		assertEquals(x, 3);
	}
	
	@Test
	public void testgetConstant()	{
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		
		PolyList pp= new PolyList(testList.get(0));
		
		float x= pp.getConstant();
		assertEquals(x, 6, 0.2);
	}
	
	@Test
	public void testIsConstant(){
		
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		
		PolyList pp= new PolyList(testList.get(0));
		assertFalse(pp.isConstant());
		
		int degs1[]= {0};
		float coeffs1[]= {6};		
		ArrayList<PNode> testList1= createList(degs1, coeffs1);		
		
		PolyList qq= new PolyList(testList1.get(0));
		assertTrue(qq.isConstant());		
	}
	
	@Test
	public void testEvaluate(){
		
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		
		PolyList pp= new PolyList(testList.get(0));
		assertEquals(7, pp.evaluate(1), 0.2);		
	}
	
	@Test
	public void testMultiplyByX(){
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		PolyList pp= new PolyList(testList.get(0));
		
		pp.multiplyByX(2);
		
		int degs1[]= {5, 3, 2};
		float coeffs1[]= {2, -1, 6};		
		ArrayList<PNode> targetList= createList(degs1, coeffs1);
				
		PNode temp= pp.first;
		for(int i=0; i<3; i++){
			assertEquals(temp.deg, targetList.get(i).deg);
			assertEquals(temp.coeff, targetList.get(i).coeff, 0.2);
			temp=temp.next;
		}
	}
	
	@Test
	public void testAdd() {
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		PolyList pp= new PolyList(testList.get(0));
		
		int degs1[]= {2, 1, 0};
		float coeffs1[]= {4, 1, 1};		
		ArrayList<PNode> testList1= createList(degs1, coeffs1);
		PolyList qq= new PolyList(testList1.get(0));
		
		int degs2[]= {3, 2, 0};
		float coeffs2[]= {2, 4, 7};		
		ArrayList<PNode> targetList= createList(degs2, coeffs2);
		
		pp.add(qq);
		
		PNode temp= pp.first;
		for(int i=0; i<3; i++){
			assertEquals(temp.deg, targetList.get(i).deg);
			assertEquals(temp.coeff, targetList.get(i).coeff, 0.2);
			temp=temp.next;
		}		
	}
	
	@Test
	public void testDifferentiate() {
		
		int degs[]= {3,1,0};
		float coeffs[]= {2, -1, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		PolyList pp= new PolyList(testList.get(0));
		
		pp.differentiate();
		
		int degs2[]= {2, 0};
		float coeffs2[]= {6, -1};		
		ArrayList<PNode> targetList= createList(degs2, coeffs2);
		
		PNode temp= pp.first;		
		for(int i=0; i<2; i++){
			assertEquals(temp.deg, targetList.get(i).deg);
			assertEquals(temp.coeff, targetList.get(i).coeff, 0.2);
			temp=temp.next;
		}		
	}

	@Test
	public void testPGCD(){
		int degs[]= {2,1,0};
		float coeffs[]= {1, -5, 6};		
		ArrayList<PNode> testList= createList(degs, coeffs);
		PolyList pp= new PolyList(testList.get(0));
		
		int degs1[]= {2, 1, 0};
		float coeffs1[]= {1, -3, 2};		
		ArrayList<PNode> testList1= createList(degs1, coeffs1);
		PolyList qq= new PolyList(testList1.get(0));
		
		int degs2[]= {1, 0};
		float coeffs2[]= {1, -2};		
		ArrayList<PNode> targetList= createList(degs2, coeffs2);		
		
		PolyList answer= pp.pGCD(pp, qq);
		
		PNode temp= answer.first;
		for(int i=0; i<2; i++){
			assertEquals(temp.deg, targetList.get(i).deg);
			assertEquals(temp.coeff, targetList.get(i).coeff, 0.2);
			temp=temp.next;
		}		
	}

	
	public ArrayList<PNode> createList(int[] degs, float[] coeffs){
		ArrayList<PNode> list = new ArrayList<>();
		if (degs.length > 0){
			list.add(new PNode(degs[0], coeffs[0], null));
			for (int i = 1; i < degs.length; i++){
				PNode toAdd = new PNode(degs[i], coeffs[i], null);
				list.add(toAdd);
				list.get(i-1).next = toAdd;
			}
		}
		return list;
	}	
}