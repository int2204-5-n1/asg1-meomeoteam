package ReadDictionary;

import MainDictionary.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadDic {
    public HashMap<String, String> data= new HashMap <>() ;
    public ArrayList <String> keys = new ArrayList<>();
    public void ReadDicEV(){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("E:\\asg1-meomeoteam\\Dictionary\\src\\data\\E_V.txt"));
            String line, word, def;
            int wordsNum = 0;
            while ((line = reader.readLine()) != null) {
                int index = line.indexOf("<html>");
                int index2 = line.indexOf("<ul>");

                if (index2 != -1 && index > index2) {
                    index = index2;
                }
                if (index != -1) {
                    word = line.substring(0, index);
                    word = word.trim();
                    keys.add(word);
                    def = line.substring(index);
                    data.put(word, def);
                    wordsNum++;
                }
            }
            reader.close();

            System.out.println(wordsNum + " words");


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
