package com.example.ambodhconverter;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import android.view.inputmethod.InputMethodManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.*;
import android.widget.*;
import android.view.*;
import android.content.Context;

import android.graphics.Color;


public class MainActivity extends AppCompatActivity {
static String num;
static int curr_base,precision,dest_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);



//        Button buttonobj=(Button)findViewById(R.id.button);
//        buttonobj.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                EditText et = (EditText) findViewById(R.id.editText);
//                num= et.getEditableText().toString();
//
//                EditText et1 = (EditText) findViewById(R.id.editText2);
//                curr_base= Integer.parseInt(et1.getEditableText().toString());
//
//                EditText et2 = (EditText) findViewById(R.id.editText3);
//                precision= Integer.parseInt(et2.getEditableText().toString());
//
//                EditText et3 = (EditText) findViewById(R.id.editText4);
//                dest_base= Integer.parseInt(et3.getEditableText().toString());
//                // function calling
//                converter(num,curr_base,precision,dest_base);
//
//            }
//        });

    }


    public void submit(View v){
        try{
        EditText et = (EditText) findViewById(R.id.editText);
                num= et.getEditableText().toString();

                EditText et1 = (EditText) findViewById(R.id.editText2);
                curr_base= Integer.parseInt(et1.getEditableText().toString());

                EditText et2 = (EditText) findViewById(R.id.editText3);
                precision= Integer.parseInt(et2.getEditableText().toString());

                EditText et3 = (EditText) findViewById(R.id.editText4);
                dest_base= Integer.parseInt(et3.getEditableText().toString());
              converter(num,curr_base,precision,dest_base);}
        catch (Exception e){
            setContentView(R.layout.activity_main);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText("Please enter valid input!");
        }
        return;
    }

    public void converter(String num,int curr_base,int precision,int dest_base) {
        String answer=fromDecimal(toDecimal(num,curr_base,precision),dest_base,precision);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("OUTPUT: "+answer);
        return;
    }
    public static String setComplex(String inp,int complex){
        int flag=0,i=0;
        for(i=0;i<inp.length();i++){
            if(inp.charAt(i)=='.')
                flag=1;
            else if(flag==1&&complex!=0)
                complex--;
            else if(flag==0)
                continue;
            else
                break;
        }
        if(inp.charAt(i-1)=='.')
            return (inp.substring(0,i-1));
        else
            return (inp.substring(0,i));
    }

    public static boolean validation(String inp,int base){
        char[] cond = new char[]{'[','0','-','0','.','0','-','0','0','-','0',']','+'};
        if(base==0)
            return (false);
        else if(base<11)
            cond[10]=(char)(base+47);
        else if (base<36){
            cond[10]='9';
            cond[1]='A';
            cond[3]=(char)(base+54);
            cond[5]='a';
            cond[7]=(char)(base+86);
        }
        else
            return (false);
        String con = String.valueOf(cond);
        return (inp.matches(con));
    }

    public static String toDecimal(String inp,int base,int complex){
        if(!validation(inp,base))
            return ("\n\t\terror : ValidityBreachException");
        String[] num = inp.split("\\.");
        double Dec=0;
        int power=num[0].length(),i=0;
        while (i<power){
            Dec+=Character.getNumericValue(num[0].charAt(i))*Math.pow(base,power-i-1);
            i++;
        }
        System.out.println(Dec+"dec");
        if (num.length==2){
            i=1;
            power=num[1].length();
            while (i<=power){
                Dec+=Character.getNumericValue(num[1].charAt(i-1))*(1/Math.pow(base,i));
                i++;
            }
        }		System.out.println(Dec+"deci");

        inp=Double.toString(Dec);
        return (setComplex(inp,complex));
    }


    static char letters(long mod)
    {
        if(mod>9)
            return (char)(mod+55);
        else
            return (char)(mod+48);
    }
    static String fromDecimal(String num,int base,int precision)
    {String nums[]=num.split("\\.");
        long temp=Integer.parseInt(nums[0]),mod;
        String ret="";
        if(temp==0)ret="0";
        else
            while(temp!=0)
            {mod=temp%base;
                ret=letters(mod)+ret;
                temp=temp/base;
            }

        if(nums.length!=1&&precision!=0)
        {
            temp=Long.parseLong(nums[1]);
            ret+='.';
            long p=temp,count=0;
            count=nums[1].length();
            long modulo=(long)Math.pow(10,count);
            for(int i=0;i<precision;i++)
            {temp*=base;
                ret+=letters((int)(temp/modulo));
                temp=temp%modulo;
            }
        }
        else if(nums.length==1 && precision!=0)
        {   ret+='.';
            for(int i=0;i<precision;i++)
                ret+='0';
        }
        return ret;

    }
}


