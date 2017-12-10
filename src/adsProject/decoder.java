package adsProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class decoder {
    Node node = new Node();
    Node root = node;

    static class Node  {
        private String chars = "";
        private int frequence = 0;
        private Node parent;
        private Node leftNode;
        private Node rightNode;

        public boolean isLeaf() {
            return chars.length() == 1;
        }

        public boolean isRoot() {
            return parent == null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.leftNode;
        }

        public int getFrequence() {
            return frequence;
        }

        public void setFrequence(int frequence) {
            this.frequence = frequence;
        }

        public String getChars() {
            return chars;
        }

        public void setChars(String chars) {
            this.chars = chars;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }
    }
    public Map<String,String> readCodeTable(String filename) throws IOException
    {
   		String file = filename;
   		BufferedReader in = new BufferedReader(new FileReader(file));
   		String line = in.readLine();
   		String []code = new String[2];
   		Map <String,String> codeTable = new Hashtable<String,String>();
    	while(line!=null&&!line.isEmpty())
   		{
    		code = line.split(" ");
    		codeTable.put(code[0], code[1]);
   			line = in.readLine();
   		}
    	in.close();
    	//printcode(codeTable);
    	return codeTable;
   	}

    public void printcode(Map<String,String> codeTable)
    {
    	System.out.println(codeTable.size());
    }
    public void printcode(String s)
    {
    	System.out.println(s.length());
    }

    public void TreeConstruction(String file) throws IOException
    {
    	String filename = file;
    	Map<String,String> codetable = readCodeTable(filename);
    	Set <String> key = codetable.keySet();
    	for(String s:key)
    	{
    		buildtree(s,codetable.get(s));
    	}
    }
	public void DataRecovery(String file) throws IOException{
		StringBuffer decode=new StringBuffer();
		String code = Readbyte(file);
		printcode(code);
		for(int i=0;i<code.length();i++){
			if(code.charAt(i)=='0'){
				node=node.leftNode;
				if(node.leftNode==null&&node.rightNode==null)
				{decode.append(node.chars+"\n");
				node=root;
				}
			}
			else{
				node=node.rightNode;
				if(node.rightNode==null&&node.rightNode==null){
					decode.append(node.chars+"\n");
					node=root;
				}
			}
		}
		WriteToText(decode.toString(),"/Users/huchenyang/Desktop/decoded.txt");
	}

    public void buildtree(String key,String code)
    {
		for(int i=0;i<code.length();i++){
			if(code.charAt(i)=='0'){
				if(i==code.length()-1){
					node.leftNode=new Node();
					node=node.leftNode;
				    node.chars=key;
				    node=root;
				}
				else if(node.leftNode!=null){
					node=node.leftNode;
				}
				else{
				node.leftNode=new Node();
				node=node.leftNode;
				}
			}
			else{
				if(i==code.length()-1){
					node.rightNode=new Node();
					node=node.rightNode;
				    node.chars=key;
				    node=root;
				}
				else if(node.rightNode!=null){
					node=node.rightNode;
				}
				else{
				node.rightNode=new Node();
				node=node.rightNode;
				}
			}
		}
    }
	public void WriteToText(String string,String fileName) {
		File file = new File(fileName);
		try {
			FileWriter fWriter = new FileWriter(file);
			fWriter.write(string);
			fWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private String Readbyte(String filename) throws IOException {
		 String sContent=null;
		 byte [] buffer =null;
		 File a_file = new File(filename);
		 try
		 {
		 FileInputStream fis = new FileInputStream(filename);
		 int length = (int)a_file.length();
		 buffer = new byte [length];
		 fis.read(buffer);
		 fis.close();
		 }
		 catch(IOException e)
		 {
		 e.printStackTrace();
		 }
		 sContent = toBinary(buffer);
		 return sContent;
		 }
	public String toBinary( byte[] bytes )
	{
	    StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
	    for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
	        sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
	    return sb.toString();
	}
	public static void main(String[] args) throws Exception{
		decoder decoder = new decoder();
		decoder.TreeConstruction("/Users/huchenyang/Desktop/codetable.txt");
		decoder.DataRecovery("/Users/huchenyang/Desktop/encoded.bin");
	}
}
