/*
 * FormatFloat.java
 *
 * Created on July 1, 2007, 12:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseClasses;


/**
 *
 * @author Todd Brenneman
 */
public class FormatFloat {
	
    public FormatFloat()    {
    }

    public String floatConvert(float x, int leader, int dec)    {
        int count;
        String output = new String("");
        String temp = "" + x;
        int c = temp.indexOf('.');
        char temp1[] = temp.toCharArray();
        int s = temp1.length;

        if(c >= leader)
        {
            for(count = 0; count < c; count++)
                output = output + temp1[count];
        }
        else
        {
            int c1 = leader - c;
            for(count = 0; count < c1; count++)
                output = output + " ";

            for(count = 0 ; count < c; count++)
                output = output + temp1[count];
        }
        output = output + ".";
        count++;
        int t = dec + count;
        if((s-c) > dec)
        {
            for( ; count < t; count++)
                output = output + temp1[count];
        }
        else
        {
            for( ; count < s; count++)
                output = output + temp1[count];
            int w = c + dec;
            for( ; count <= w; count++)
                output = output + "0";
        }

        return output;
    }		

    public String floatConvert(String temp, int leader, int dec)    {
        int count;
        String output = new String("");
        int c = temp.indexOf('.');
        char temp1[] = temp.toCharArray();
        int s = temp1.length;

        if(c >= leader)
        {
            for(count = 0; count < c; count++)
                output = output + temp1[count];
        }
        else
        {
            int c1 = leader - c;
            for(count = 0; count < c1; count++)
                output = output + " ";

            for(count = 0 ; count < c; count++)
                output = output + temp1[count];
        }
        output = output + ".";
        count++;
        int t = dec + count;
        if((s-c) > dec)
        {
            for( ; count < t; count++)
                output = output + temp1[count];
        }
        else
        {
            for( ; count < s; count++)
                output = output + temp1[count];
            int w = c + dec;
            for( ; count <= w; count++)
                output = output + "0";
        }

        return output;
    }		
}
