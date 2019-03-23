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
		//we need a search or find method to do this?
		
   }
	
	
	
	
	


	

   public void decode(String inputFile, String outputFile, String freqFile){
		// TODO: Your code here
	   
   }
   
   /** Huffman tree node implementation: Base class */
   public interface HuffBaseNode<E> {
	   public boolean isLeaf();
	   public int weight();
	   public int frequency();
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
	   
	   public boolean equals(HuffLeafNode node) {
		if(node.weight == this.weight) {
			return true;
		}
		   return false;
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
	   
	   public int frequency() {return -1;}
	   /** Return false */
	   public boolean isLeaf() { return false; }
   }
   
   HuffTree<?> buildTreeHelper(ArrayList<HuffTree> TreeArray){
	   //end condition
	   if(TreeArray.size()==2) {
		   HuffTree<Character> tree;
		   if(TreeArray.get(0).compareTo(TreeArray.get(1))==1) {
			   tree = new HuffTree<Character>(TreeArray.get(0).root,TreeArray.get(1).root, TreeArray.get(0).weight()+TreeArray.get(1).weight());
		   }
		   else {
			   tree = new HuffTree<Character>(TreeArray.get(1).root,TreeArray.get(0).root, TreeArray.get(0).weight()+TreeArray.get(1).weight());
		   }
		   return tree;
	   }
	   
	   //recursive steps
	   HuffTree<Character> tempTree;
	   if(TreeArray.get(0).compareTo(TreeArray.get(1))==1) {
		   tempTree = new HuffTree<Character>(TreeArray.get(0).root,TreeArray.get(1).root, TreeArray.get(0).weight()+TreeArray.get(1).weight());
	   }
	   else {
		   tempTree = new HuffTree<Character>(TreeArray.get(1).root,TreeArray.get(0).root, TreeArray.get(0).weight()+TreeArray.get(1).weight());
	   }
	   TreeArray.remove(1);
	   TreeArray.remove(0);
	   int i;
	   for(i = 0; i<TreeArray.size(); i++) {
		   if(tempTree.compareTo(TreeArray.get(i))==1){
			   TreeArray.add(i-1, tempTree);
			   break;
		   }
	   }
	   return buildTreeHelper(TreeArray);
   }
   
   public HuffTree<?> HuffmanSubmit(ArrayList<HuffLeafNode> LeafArray){
	   ArrayList<HuffTree> TreeArray = new ArrayList<HuffTree>();
	   for(HuffLeafNode N:LeafArray) {
		   HuffTree<?> temptree = new HuffTree<>(N.element,N.frequency);
		   TreeArray.add(temptree);
	   }
	   
	   HuffTree<?> tree = buildTreeHelper(TreeArray);
	   return tree;
   }
   
   
   /** A Huffman coding tree */
   class HuffTree<E> implements Comparable<HuffTree<E>>{
	   private HuffBaseNode<E> root; // Root of the tree
	   /** Constructors */
	   public HuffTree(E el, int frequency){ 
		   root = new HuffLeafNode<E>(el, 0,frequency); //we used zero subject to change
		   }
	   
	   public HuffTree(HuffBaseNode<E> l,HuffBaseNode<E> r, int wt){
		   root = new HuffInternalNode<E>(l, r, wt); 
		  }

	public HuffBaseNode<E> root() { 
	   			return root; 
	   }
	   public int weight() // Weight of tree is weight of root
	   { 
		   if(!this.root.isLeaf()) {
			   return root.weight(); 
		   }
		   else {
			   return root.frequency();
		   }
	   }
	   
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
