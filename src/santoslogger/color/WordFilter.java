/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santoslogger.color;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author justdasc
 */
public class WordFilter {
    
    private HashMap<String, Color> wordColor = new HashMap<>();
    private HashMap<String, Font> wordFont = new HashMap<>();
    private HashMap<String, String> wordReplace = new HashMap<>();
    private Boolean wholeWord = true;
    private Boolean ignoreCapitalization = false;
  
    public WordFilter()
    {
        
    }
    
    public WordFilter(String s, Color c)
    {
        addWordColor(s, c);
    }
    
    public WordFilter(String s, Font f)
    {
        addWordFont(s, f);
    }
    
    public WordFilter(String s, Color c, Font f)
    {
        addWordColor(s, c);
        addWordFont(s, f);
    }
    
    public WordFilter(ArrayList<String> l, Color c)
    {
        addWordColor(l, c);
    }
    
    public WordFilter(ArrayList<String> l, Font f)
    {
        addWordFont(l, f);
    }
    
    public WordFilter(ArrayList<String> l, Color c, Font f)
    {
        addWordColor(l, c);
        addWordFont(l, f);
    }
    
    public void addWordColor(String s, Color c)
    {
        wordColor.put(s, c);
    }
    
    public void removeWordColor(String s)
    {
        wordColor.remove(s);
    }
    
    public void clearWordColor()
    {
        wordColor.clear();
    }
    
    public void addWordFont(String s, Font f)
    {
        wordFont.put(s, f);
    }
    
    public void removeWordFont(String s)
    {
        wordFont.remove(s);
    }
    
    public void clearWordFont()
    {
        wordFont.clear();
    }
    
    public void removeWord(String s)
    {
        wordColor.remove(s);
        wordFont.remove(s);
    }
    
    public void addWord(String s, Color c, Font f)
    {
        wordColor.put(s, c);
        wordFont.put(s, f);
    }
    
    public void clearWords()
    {
        wordColor.clear();
        wordFont.clear();
    }
    
    public void addWordColor(ArrayList<String> l, Color c)
    {
        for(String s : l)
        {
            wordColor.put(s, c);
        }
    }
    
    public void removeWordColor(ArrayList<String> l)
    {
        for(String s : l)
        {
            wordColor.remove(s);
        }
    }
    
    public void addWordFont(ArrayList<String> l, Font f)
    {
        for(String s : l)
        {
            wordFont.put(s, f);
        }
    }
    
    public void removeWordFont(ArrayList<String> l)
    {
        for(String s: l)
        {
            wordFont.remove(s);
        }
    } 
    
   public void ignoreCapitalization(Boolean b)
   {
       ignoreCapitalization = b;
   }
   
   public void wholeWord(Boolean b)
   {
       wholeWord = b;
   }
   
   public void appendFilteredString(String s, JTextPane p)
   {
       StyledDocument sd = p.getStyledDocument();
   }
   
   public void appendWord(String s, Style style, JTextPane p)
   {
       if(wordReplace.containsKey(s))
           s = wordReplace.get(s);
       StyledDocument sd = p.getStyledDocument();
        try 
        {
           MutableAttributeSet mas = p.getInputAttributes();
           if(wordColor.containsKey(s))
               StyleConstants.setForeground(mas, wordColor.get(s));
           if(wordFont.containsKey(s))
           {
               StyleConstants.setFontFamily(mas, wordFont.get(s).getFamily());
               StyleConstants.setFontSize(mas, wordFont.get(s).getSize());
           }
           int sLen = s.length()+1;
           int sdLen = sd.getLength();
           
           sd.setCharacterAttributes(sd.getLength(), sd.getLength() + 1, mas, false);
           sd.insertString(sd.getLength(), s, null);
           
           
        } catch (BadLocationException ex) {
            Logger.getLogger(WordFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
    
}
