import java.io.BufferedInputStream;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


// Import any package as required


public class HuffmanSubmit implements Huffman {
  
	// Feel free to add more methods and variables as required. 
 
	@SuppressWarnings("unlikely-arg-type")
	public void encode(String inputFile, String outputFile, String freqFile){
		// TODO: Your code here
		
		//first read the characters and create the frequency file based on that
		BinaryIn  readFile = new BinaryIn(inputFile);
		ArrayList<HuffLeafNode>readList = new ArrayList<HuffLeafNode>();
		
		while(readFile.readBoolean()==true) {
		char tempChar = readFile.readChar();
		
		boolean charExists = false;// check whether character already exists.
		
		for(int i=0; i<readList.size(); i++) {
			if(readList.get(i).equals(tempChar)) {
				readList.get(i).frequency++;
				charExists = true;
				break;
			}
			
		}
		
		if(charExists == false) {
			readList.add(new HuffLeafNode(tempChar,0,0));//add new character to the leafNode list, update weight later
			
		}
		
		
		}
		try {
		BinaryOut writeFile = new BinaryOut();
		// first try to read file from local file system
		File file = new File(freqFile);
		Writer wr = new FileWriter(freqFile);
		
		for(HuffLeafNode node : readList) {
			writeFile.write((char)node.element);
			wr.write(":");
			wr.write(node.frequency);
			wr.write("\n");
		}
		}catch(IOException e) {
			System.err.println("An IOException was caught :"+e.getMessage());
		}
        
		
		
		
		
		//with the freqFile, we can build our tree and give each character a codeword
		
		tree treeTemp = new tree();
		treeTemp.buildTree(readList);
		
		
		
		
		
		
		//encode into output file the codewords according to character!
		
   }
	
	


	

   public void decode(String inputFile, String outputFile, String freqFile){
		// TODO: Your code here
	   
   }
   
   /** Huffman tree node implementation: Base class */
   public interface HuffBaseNode<E> {
   public boolean isLeaf();
   public int weight();
   }
   
   /** Huffman tree node: Leaf class */
   class HuffLeafNode<E> implements HuffBaseNode<E> {
   private E element; // Element for this node
   private int weight; // Weight for this node
   private int frequency; // this holds the frequency of the char
   
   /** Constructor */
   public HuffLeafNode(E el, int wt, int freq)
   { element = el; weight = wt; freq = frequency; }
   /** @return The element value */
   public E element() { return element; }
   /** @return The weight */
   public int weight() { return weight; }
   /** Return true */
   public boolean isLeaf() { return true; }
   
   /**Return the frequency*/
   public int frequency() {
	   return frequency;
   }
   }
   
   
   /** Huffman tree node: Internal class */
   class HuffInternalNode<E> implements HuffBaseNode<E> {
   private int weight; // Weight (sum of children)
   private HuffBaseNode<E> left; // Pointer to left child
   private HuffBaseNode<E> right; // Pointer to right child
   /** Constructor */
   public HuffInternalNode(HuffBaseNode<E> l,
   HuffBaseNode<E> r, int wt)
   { left = l; right = r; weight = wt; }
   /** @return The left child */
   public HuffBaseNode<E> left() { return left; }
   /** @return The right child */
   public HuffBaseNode<E> right() { return right; }
   /** @return The weight */
   public int weight() { return weight; }
   /** Return false */
   public boolean isLeaf() { return false; }
   
   
   
   }
   class tree{
	   HuffInternalNode<?> access;
	   
	   
	   int index = 3;
	   HuffInternalNode<?> buildTreeHelper(ArrayList<HuffLeafNode> LeafArray, HuffInternalNode<?> internal){
		   //end condition
		   if(LeafArray.get(index+1) == null) {
			   access = new HuffInternalNode<>(LeafArray.get(index),internal,internal.weight+LeafArray.get(index).frequency());
			   //reset index
			   index = 3;
			   return null;
		   }
		   //recursive steps
		   HuffInternalNode<?> temp = new HuffInternalNode<>(LeafArray.get(index),internal,internal.weight+LeafArray.get(index).frequency());
		   index++;
		   return buildTreeHelper(LeafArray, temp);
	   }
	   
	   /** Build a Huffman tree from list hufflist */
	   void buildTree(ArrayList<HuffLeafNode> LeafArray) {
		    //sort the array!
		    for(int i = 0; i < LeafArray.size(); i++) {
		    	for(int j = 0; j < LeafArray.size()-i; j++) {
		    		if(LeafArray.get(j).frequency > LeafArray.get(j).frequency) {
		    			swap(LeafArray.get(j),LeafArray.get(j+1));
		    		}
		    	}
		    }
		    
		    HuffInternalNode<?> temp = new HuffInternalNode<>(LeafArray.get(2), LeafArray.get(1), LeafArray.get(2).frequency+LeafArray.get(1).frequency);
		    buildTreeHelper(LeafArray,temp);
	//   HuffTree tmp1, tmp2, tmp3 = null;
	//   while (Hheap.size > 1) { // While two items left
	//   tmp1 = Hheap.extractMax();
	//   tmp2 = Hheap.extractMax();
	//   tmp3 = new HuffTree<Character>(tmp1.root(), tmp2.root(),
	//   tmp1.weight() + tmp2.weight());
	//   Hheap.insert(tmp3); // Return new tree to heap
	//   }
	
	//   return tmp3; // Return the tree
	   }
	   
	   
	   public String search(char x, int frequency) {
		   
		   
		   
		   return null;
	   }
	   
	   public void searchHelper(HuffInternalNode node, Character x, int frequency) {
		   
		   
	   }
	   
   }
   
   
   /** A Huffman coding tree */
   class HuffTree<E> implements Comparable<HuffTree<E>>{
   private HuffBaseNode<E> root; // Root of the tree
   /** Constructors */
   public HuffTree(E el, int wt){ 
	   root = new HuffLeafNode<E>(el, wt,0); //we used zero subject to change
	   }
   
   public HuffTree(HuffBaseNode<E> l,HuffBaseNode<E> r, int wt){
	   root = new HuffInternalNode<E>(l, r, wt); 
	  }
   
   public HuffBaseNode<E> root() { 
   			return root; 
   }
   public int weight() // Weight of tree is weight of root
   { return root.weight(); }
   
   public int compareTo(HuffTree<E> that) {
   if (root.weight() < that.weight()) return -1;
   else if (root.weight() == that.weight()) return 0;
   else return 1;
   }
   }
   
   public void swap(HuffLeafNode<String> a, HuffLeafNode<String> b) {
	   HuffLeafNode<String> temp = new HuffLeafNode("a",0,0);
	   
	   if(a.frequency>b.frequency) {
		   temp = a;
		   a = b;
		   b = temp;
	   }   
   }
   



   public static void main(String[] args) {
      Huffman  huffman = new HuffmanSubmit();
		huffman.encode("ur.jpg", "ur.enc", "freq.txt");
		huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
		// After decoding, both ur.jpg and ur_dec.jpg should be the same. 
		// On linux and mac, you can use `diff' command to check if they are the same. 
   }

}
