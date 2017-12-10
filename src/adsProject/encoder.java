package adsProject;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class encoder {
	static Huffman hm;
	encoder(){
		hm =new Huffman();
	}

	public void writeCodeTable(String path,Map<String, String> Code_Table) throws IOException{
		File f=new File(path);
		FileOutputStream out=new FileOutputStream(f);
		for(String key:Code_Table.keySet())
		{
			String a = key + " "+Code_Table.get(key)+"\n";
			out.write(a.getBytes());
			//out.write('\r');
			//out.write('\n');
		}
		out.close();
	}

	public void build_tree_using_fourway_heap(Map<String, Integer> freq_table) throws Exception
	{
		long startTime = System.currentTimeMillis();
		for (int i = 0;i<10;i++)
			hm.buildTreein4WayHeap(freq_table);
		long endTime = System.currentTimeMillis();
		System.out.println("Time using fourway heap (microsecond):"+(endTime-startTime)/10);
	}
	public void build_tree_using_binary_heap(Map<String, Integer> freq_table) throws Exception
	{
		long startTime = System.currentTimeMillis();
		for (int i = 0;i<10;i++)
			hm.buildTreeinBinaryHeap(freq_table);
		long endTime = System.currentTimeMillis();
		System.out.println("Time using binary heap (microsecond):"+(endTime-startTime)/10);
	}
	public void build_tree_using_pairing_heap(Map<String, Integer> freq_table) throws Exception
	{
		long startTime = System.currentTimeMillis();
		for (int i = 0;i<10;i++)
			hm.buildTreeInPairingHeap(freq_table);
		long endTime = System.currentTimeMillis();
		System.out.println("Time using pairing heap (microsecond):"+(endTime-startTime)/10);
	}
	public String[] getInputStrings(String filename) throws Exception
	{
		String file = filename;//"/Users/huchenyang/Desktop/Advanced Data Structure/project/sample2/sample_input_large.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = in.readLine();
		String[] number;
		ArrayList<String> str = new ArrayList<String>();
		while(line!=null&&!line.isEmpty())
		{
			str.add(line);
			line = in.readLine();
		}
		number = (String[])str.toArray(new String[str.size()]);
		in.close();
		return number;
	}

	private byte[] toByteArray(String input){
	    char[] preBitChars = input.toCharArray();
	    int bitShortage=0;
	    if(preBitChars.length%8!=0){
	    bitShortage = (8 - (preBitChars.length%8));
	    }
	    char[] bitChars = new char[preBitChars.length + bitShortage];
	    System.arraycopy(preBitChars, 0, bitChars, 0, preBitChars.length);

	    for (int  i= 0;  i < bitShortage;  i++) {
	        bitChars[preBitChars.length + i]='0';
	    }
	    byte[] byteArray = new byte[bitChars.length/8];
	    for(int i=0; i<bitChars.length; i++) {
	        if (bitChars[i]=='1'){
	            byteArray[byteArray.length - (i/8) - 1] |= 1<<(i%8);
	        }
	    }
	    return byteArray;
	}

	public void writetoBin(byte[] bytes)
	{
	    String fileName="/Users/huchenyang/Desktop/encoded.bin";
	    try
	    {
	        DataOutputStream out=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
	        out.write(bytes);
	        out.close();
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) throws Exception{
		encoder main = new encoder();
		String[] input = main.getInputStrings("/Users/huchenyang/Desktop/Advanced Data Structure/project/sample1/sample_input_small.txt");
		Map <String,Integer> statistics = hm.statistics(input);
		hm.buildleafs(statistics);
		main.build_tree_using_binary_heap(statistics);
		main.build_tree_using_fourway_heap(statistics);
		main.build_tree_using_pairing_heap(statistics);
		//write encoded.bin
		main.writetoBin(main.toByteArray(hm.encode(input,hm.statistics(input))));
		//write codetable
		main.writeCodeTable("/Users/huchenyang/Desktop/codetable.txt",hm.getCode_Table());
	}
}
