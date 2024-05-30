package code;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Validations {

	public boolean yearValidation(String input) {
		
		String format = "yyyy.mm.dd.";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		try {
		    java.util.Date date = sdf.parse(input);
		    if (!sdf.format(date).equals(input)) {
		        
		    	return false;
		    }
		    return true;
		} catch (ParseException ex) {
		    
			JOptionPane.showMessageDialog(null, "Wrong date format (yyyy.mm.dd.)", "Message", 0);
			return false;
		}
	}
	
	public boolean emailValidation(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
	
	
	public boolean IntFormatChecker(int Value, boolean Smt) {
		boolean res=true;
		
		if(Value == (int)Value && Value>=0 && Smt==true) {
			return res;
		}
		else {
			res=false;
		}
		
		
		return res;
		
	}
	
	public boolean IntformatChecker(String test) {
		try {
			
			int res=Integer.parseInt(test);
			if(res<1) {
				return false;
			}
			return true;
		}
		
		catch(NumberFormatException e)
		{
			return false;
		}
		
		
		
	}
	
	public boolean StringFormat(String str){
		boolean res=true;
		if(str != null && !str.trim().isEmpty()) { 
			return res;
			}
		else {
			res=false;
		}
		return res;
	}
}
