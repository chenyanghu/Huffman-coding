package adsProject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Huffman {
	static Map<String,String> codetable;
	static List<Node> leafNodes;

	static class Tree {
        private Node root;

        public Node getRoot() {
            return root;
        }
        public void setRoot(Node root) {
            this.root = root;
        }
    }
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
    public Map<String, Integer> statistics(String[] stringArray) {
        Map<String,Integer> map = new LinkedHashMap<String, Integer>();
        for (String string:stringArray) {
            if (map.containsKey(string)) {
                map.put(string, map.get(string) + 1);
            } else {
                map.put(string, 1);
            }
        }
        return map;
    }
    public Map<String,String> getCode_Table()
    {
    	return codetable;
    }
    public void buildleafs(Map<String, Integer> statistics){
    	String[]keys = statistics.keySet().toArray(new String[statistics.size()]);
    	leafNodes = new ArrayList<Node>();
   	 	for (String s : keys) {
   	 		Node node = new Node();
   	 		node.chars = s;
   	 		node.frequence = statistics.get(s);
   	 		leafNodes.add(node);
   	 	}
    }

    public Tree buildTreeinBinaryHeap(Map<String, Integer> statistics)
    {
    	 BinaryHeap binaryheap = new BinaryHeap(statistics.size());
    	 for(int i=0;i<leafNodes.size();i++)
    		binaryheap.insert(leafNodes.get(i));
         int size = statistics.size();
         for (int i = 1; i <= size - 1; i++) {
        	 Node node1 = binaryheap.deleteMin();
        	 Node node2 = binaryheap.deleteMin();

        	 Node sumNode = new Node();
        	 sumNode.chars = node1.chars + node2.chars;
        	 sumNode.frequence = node1.frequence + node2.frequence;

        	 sumNode.leftNode = node1;
        	 sumNode.rightNode = node2;

        	 node1.parent = sumNode;
        	 node2.parent = sumNode;

        	 binaryheap.insert(sumNode);
         }

         Tree tree = new Tree();
         tree.root = binaryheap.deleteMin();
         return tree;
     }

    public Tree buildTreein4WayHeap(Map<String, Integer> statistics)
    {
    	 DHeap fwheap = new DHeap(statistics.size());
    	 for(int i=0;i<leafNodes.size();i++)
    		fwheap.insert(leafNodes.get(i));
         int size = statistics.size();
         for (int i = 1; i <= size - 1; i++) {
        	 Node node1 = fwheap.deleteMin();
        	 Node node2 = fwheap.deleteMin();

        	 Node sumNode = new Node();
        	 sumNode.chars = node1.chars + node2.chars;
        	 sumNode.frequence = node1.frequence + node2.frequence;

        	 sumNode.leftNode = node1;
        	 sumNode.rightNode = node2;

        	 node1.parent = sumNode;
        	 node2.parent = sumNode;

        	 fwheap.insert(sumNode);
         }

         Tree tree = new Tree();
         tree.root = fwheap.deleteMin();
         return tree;
     }

    public Tree buildTreeInPairingHeap(Map<String, Integer> statistics)
    {
    	PairingHeap pheap = new PairingHeap();
    	 for(int i=0;i<leafNodes.size();i++)
    		pheap.insert(leafNodes.get(i));
         int size = statistics.size();
         for (int i = 1; i <= size - 1; i++) {
             Node node1 = pheap.getNode();
             pheap.deleteMin();
             Node node2 = pheap.getNode();
             pheap.deleteMin();

             Node sumNode = new Node();
             sumNode.chars = node1.chars + node2.chars;
             sumNode.frequence = node1.frequence + node2.frequence;

             sumNode.leftNode = node1;
             sumNode.rightNode = node2;

             node1.parent = sumNode;
             node2.parent = sumNode;

             pheap.insert(sumNode);
         }

         Tree tree = new Tree();
         tree.root = pheap.getNode();
         return tree;
     }
    public String encode(String[] originalStr,
            Map<String, Integer> statistics) {
        if (originalStr == null || originalStr.equals("")) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        //buildTreeInPairingHeap(statistics);
        Map <String,String> encodInfo = buildEncodingInfo(leafNodes);
        for (String s:originalStr) {
        	buffer.append(encodInfo.get(s));
        }
        //leafNodes.clear();
        return buffer.toString();
    }

    private Map<String, String> buildEncodingInfo(List<Node> leafNodes) {
        Map<String,String> codewords = new LinkedHashMap<String, String>();
        for (Node leafNode : leafNodes) {
            String string = new String(leafNode.getChars());
            Node currentNode = leafNode;
            String codeword = new String();
            do {
                if (currentNode.isLeftChild()) {
                    codeword = "0" + codeword;
                } else {
                    codeword = "1" + codeword;
                }
                currentNode = currentNode.parent;
            } while (currentNode.parent != null);

            codewords.put(string, codeword);
        }
        codetable = codewords;
        return codewords;
    }
}
