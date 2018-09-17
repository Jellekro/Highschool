package morsecode;
import java.util.*;
import java.io.*;
public class MorseTree {
    
    private MorseNode mRoot = new MorseNode("/",null);
    private MorseNode lRoot = new MorseNode("M","--");
    private String result = "";
    
    public MorseTree(Scanner read)throws FileNotFoundException{
        Scanner key = new Scanner(new File("key.txt"));
        while(key.hasNext()){
            String code = key.next();
            String morse = key.next();
            mRoot = add(mRoot,code,morse);
            lRoot = add(lRoot,code,morse,0);
        }
        while(read.hasNext()){
            String temp = read.next();
            if(temp.indexOf(".")+temp.indexOf("-")==-2) result += translate(lRoot,temp,0);
            else result += translate(mRoot,temp);
        }
    }
    
    private MorseNode add(MorseNode curr, String code, String morse){
        if(morse.isEmpty()) return new MorseNode(code,null);
        else if(morse.charAt(0)=='.') curr.left = add(curr.left,code,morse.substring(1));
        else if(morse.charAt(0)=='-') curr.right = add(curr.right,code,morse.substring(1));
        return curr;
    }
    
    private MorseNode add(MorseNode curr, String code, String morse, int blank){
        if(curr==null) return new MorseNode(code,morse);
        else if(code.compareTo(curr.code)<=0) curr.left = add(curr.left,code,morse,0);//no action for code's being equal
        else if(code.compareTo(curr.code)>0) curr.right = add(curr.right,code,morse,0);
        return curr;
    }
    
    private String translate(MorseNode curr, String morse){
        if(curr==null) throw new IllegalArgumentException("Your file contains an invalid morse.");
        else if(morse.isEmpty()) return curr.code+" ";
        else if(morse.charAt(0)=='.') return translate(curr.left,morse.substring(1));
        return translate(curr.right,morse.substring(1));
    }
    
    private String translate(MorseNode curr, String code, int blank){
        if(curr==null) throw new IllegalArgumentException("Your file contains an invalid code.");
        else if(curr.code.equals(code)) return curr.morse+" ";
        else if(curr.code.charAt(0)>code.charAt(0)) return translate(curr.left,code,0);
        return translate(curr.right,code,0);
    }
    
    public String getResult(){
        return result;
    }
    
    private class MorseNode {
    
        String code;
        String morse;
        MorseNode left;
        MorseNode right;
        
        public MorseNode(String code, String morse){
            this.code = code;
            this.morse = morse;
        }
    }
}
