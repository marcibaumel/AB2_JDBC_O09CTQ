package code;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDateTime;  
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class DbMethods {

    private Statement s = null;
    private Connection conn= null;
    private ResultSet rs= null;
    private PreparedStatement ps;
    private EmpTM UserEtm;
    private EmpTM ProductEtm;
    private EmpTM BasementEtm;
    
    public void Connect() {
        Reg();
        try {
        	//Tábla abszolút helye
            String url="jdbc:sqlite:D:/Program Files (x86)/SQLite/Basement.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection OK!");

        } catch (SQLException e) {

        	System.out.println("Unsuccessful connection!"+e.getMessage());
        }

    }

    public void Reg() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Successful driver registration!");

        } catch (ClassNotFoundException e){

        	System.out.println("Unsuccessful driver registration!"+e.getMessage());
        }

    }
    
    public String getCurrentDate() {
    	String res="";
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.");  
    	Date date = new Date();  
    	System.out.println(formatter.format(date)); 
    	res=formatter.format(date);
    	return res;
    }
      
    public void deletUserById(String id){
        
    	String sqlp="DELETE FROM UserTable WHERE (UserId="+id+");";
        try{
            s=conn.createStatement();
            s.execute(sqlp);
            
            SM("Delete OK!");

        }catch(SQLException e){
            SM("JDB delete: "+ e.getMessage());
        }
        deletBasementFromUser(id);
    }
      
	public void deletProductById(String id){
        
    	String sqlp="DELETE FROM Product WHERE (ProductID="+id+");";
        try{
            s=conn.createStatement();
            s.execute(sqlp);
            
            SM("Delete OK!");

        }catch(SQLException e){
            SM("JDB delete: "+ e.getMessage());
        }
        
    }
 
	public void deletBasementFromUser(String id){
        
    	String sqlp="DELETE FROM BasementTable WHERE (UserId="+id+");";
        try{
            s=conn.createStatement();
            s.execute(sqlp);
            //SM("Delete OK!");

        }catch(SQLException e){
            //SM("JDB delete: "+ e.getMessage());
        }
    }
    
	public void deletBasementFromProduct(String id){
     
 	String sqlp="DELETE FROM BasementTable WHERE (ProductId="+id+");";
     try{
         s=conn.createStatement();
         s.execute(sqlp);
         //SM("Delete OK!");

     }catch(SQLException e){
         //SM("JDB delete: "+ e.getMessage());
    }
 }
 
	
	public void deleteBasementById(String id) {
		String sqlp="delete from  BasementTable where (BasementId= '"+id+"');";
		
		try{
            s=conn.createStatement();
            s.execute(sqlp);
            
            SM("Delete OK!");

        }catch(SQLException e){
            SM("JDB delete: "+ e.getMessage());
        }
		
	}
	
	public void priceRange(String min, String max) {
	 String name="", source="", text="", x="\n", t="    ";
	 int price; 
	 
	 String sqlp="select ProductName, Price, Source from Product WHERE Price BETWEEN "+min+" AND "+max+" Order By Price ASC;";
	 
		try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			
    			
    			source=rs.getString("Source");
    			name=rs.getString("ProductName");
    			price=rs.getInt("Price");
    			
    			text+=name+t+source+t+price+x;
    			
    			
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
		SM(text);
 }
 
 	public void priceRange2(String min, String max) {
	 String name="", source="", text="", x="\n", t="    ";
	 int price; 
	 int minimum=Integer.parseInt(min);
	 int maximum=Integer.parseInt(max);
	 String sqlp="select ProductName, Price, Source from Product WHERE Price >? and Price <=? Order By Price ASC;";
	 
		try {
    		ps=conn.prepareStatement(sqlp);
    		ps.setInt(1, minimum);
    		ps.setInt(2, maximum);
    		
    		ResultSet rs=ps.executeQuery();
    		
    		while(rs.next()) {
    			
    			
    			source=rs.getString("Source");
    			name=rs.getString("ProductName");
    			price=rs.getInt("Price");
    			
    			text+=name+t+source+t+price+x;
    			
    			
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
		
		SM("Result: \n\n"+text);
 }
    
    public EmpTM ReadAllUserData2() {
    	Object UserEmptmn[] = {"BOX","UserId", "Username", "Email", "Password", "Birthday"};
		UserEtm=new EmpTM(UserEmptmn, 0);
		
		int UserId=0;
    	String Username="", Email="", Password="", Birthday="";
    	
    	String sqlp="select UserId, Username, Email, Password, Birthday from UserTable";
    	try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			UserId=rs.getInt("UserId");
    			Username=rs.getString("Username");
    			Email=rs.getString("Email");
    			Password=rs.getString("Password");
    			Birthday=rs.getString("Birthday");
    			//System.out.println(UserId+Username+Email+Password+Birthday);
    			UserEtm.addRow(new Object[] {false,UserId, Username, Email, Password, Birthday});
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
    	return UserEtm;
    }
    
    public EmpTM ReadAllBasement(int UserId) {
    	Object BasementEmptmn[] = {"BOX", "BasementId", "Name", "Last modified", "Quantity", "Price"};
    	BasementEtm=new EmpTM(BasementEmptmn, 0);
		
		int BasementId=0, Quantity=0, Price=0;
    	String Name="", Modified="";
    	
    	String sqlp="select BasementId, Date, Quantity, ProductId from BasementTable where UserId="+UserId+";";
    	try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			BasementId=rs.getInt("BasementId");
    			Name=getProductNameFromId(rs.getInt("ProductId"));
    			Modified=rs.getString("Date");
    			Quantity=rs.getInt("Quantity");
    			Price= Quantity * getProductPrice(rs.getInt("ProductId"));
    			
    			
    			BasementEtm.addRow(new Object[] {false, BasementId, Name, Modified, Quantity, Price});
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
    	return BasementEtm;
    }
    
    public boolean ExistingUser(String name) {
    	int res=-1;
    	String sqlp="select UserId from UserTable where Username='"+name+"';"; 
    	
    	try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			res=rs.getInt("UserId");
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
    	
    	if(res == -1) {
    		
    		return true;
    		
    	}
    	SM("Choose another name!");
    	return false;
    }
    
    public void editBasemenet(String id, String quantity) {
    	String sqlp="update BasementTable set Quantity="+quantity+" where BasementId="+id+";";
    	try{
            s=conn.createStatement();
            s.execute(sqlp);
            

        }catch(SQLException e){
            SM("JDB Update: "+ e.getMessage());
        }
    }
    
    public void insertBasement(int Uid, String Pid, String quantity) {
    	int Bid=CountBasement();
    	String sqlp="insert into BasementTable values("+Bid+", '"+getCurrentDate()+"', "+quantity+", "+Pid+", "+Uid+");";
    	try{
            s=conn.createStatement();
            s.execute(sqlp);
            

        }catch(SQLException e){
            SM("JDB Update: "+ e.getMessage());
        }
    }
    
    public boolean ExistingItem(int uId, int pId) {
    	
    	int BasementId=0;
    	String sqlp="select BasementId from BasementTable where UserId= "+uId+"AND ProductId= "+pId+";";
    	
    	try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			BasementId=rs.getInt("BasementId");
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
    	
    	if(BasementId!=0) {
    		return true;
    	}
    	
    	return false;
    }
    
    public EmpTM ReadAllProduct() {
    	Object ProductEmptmn[] = {"Checker","ProductId","Source", "ProductName","ProductType", "Price"};
		ProductEtm=new EmpTM(ProductEmptmn, 0);
		
		int ProductId=0, Price=0;
    	String ProductName="", ProductType="", Source="";
    	
    	
    	
    	String sqlp="select ProductId, ProductName, Price, ProductType, Source from Product";
    	try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			ProductId=rs.getInt("ProductId");
    			System.out.println(ProductId);
    			Source=rs.getString("Source");
    			
    			
    			
    			ProductType=rs.getString("ProductType");
    			ProductName=rs.getString("ProductName");
    			Price=rs.getInt("Price");
    			
    			ProductEtm.addRow(new Object[] {false , ProductId, Source, ProductName, ProductType, Price });
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
    	return ProductEtm;
    }
    
    public void ReadAllUserData() {
    	int UserId=0;
    	String Username="", Email="", Password="", Birthday="";
    	
    	String sqlp="select UserId, Username, Email, Password, Birthday from UserTable";
    	try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			UserId=rs.getInt("UserId");
    			Username=rs.getString("Username");
    			Email=rs.getString("Email");
    			Password=rs.getString("Password");
    			Birthday=rs.getString("Birthday");
    			System.out.println(UserId+Username+Email+Password+Birthday);
    			
    		}
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());}
    }
    
    public int CheckLogin(String userName, String pasw) {
    	Connect();
    	int pc=-1;
    	String sqlp= "select count(*) pc from UserTable where Username='"+userName+"' AND Password='"+pasw+"';";

    	try {
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		
    		 while(rs.next()){
            	 pc=rs.getInt("pc");
            }
            rs.close();
            return pc;
            
            
        }catch (SQLException e) {
            SM(e.getMessage());
             }
    	DisConnect();

        return pc;
    }
    
    public int UserLoged(String userName) {
    	
    	int pc=0;
        String sqlp="select UserId from UserTable where Username='"+userName+"';";

        try{
            s= conn.createStatement();
            rs= s.executeQuery(sqlp);
            
            while(rs.next()){
            	 pc=rs.getInt("UserId");
                 //SM(""+pc);
            }
            rs.close();
            return pc;
            
            
        }catch (SQLException e) {
            SM(e.getMessage());
             }

        return pc;
    	
    }
    
    public int Identification(String name, String pswd){

        
        int pc=-1;
        String sqlp="select * pc from UserTable where Username='"+name+"' and Password='"+pswd+"';";

        try{
            s= conn.createStatement();
            rs= s.executeQuery(sqlp);
            
            while(rs.next()){
            	 pc=rs.getInt("pc");
                 SM("Loged!");
            }
            rs.close();
            return pc;
            
            
        }catch (SQLException e) {
            //SM(e.getMessage());
        	
             }

        return pc;
    }
    
    public void DisConnect(){
        try{
            conn.close();
            SM("Disconected");
        }catch (SQLException e){

        }
    }

    public void CreateANewUser(int id, String Username, String Email, String Password, String Birthday){
        String sqlp="insert into UserTable values (" +id+", '"+ Username+"', '"+ Email+"', '"+ Password+"', '"+ Birthday+"')";
        try{
            s=conn.createStatement();
            s.execute(sqlp);
            SM(Username+" has been created!");

        }catch(SQLException e){
            SM("JDB Insert: "+ e.getMessage());
        }
    }
    
    public void EditUser(String id, String Email, String Password, String Birthday){
        String sqlp="update UserTable set Email='"+Email+"', Password='"+Password+"', Birthday='"+Birthday+"' where UserId='"+id+"';";
        try{
            s=conn.createStatement();
            s.execute(sqlp);
            

        }catch(SQLException e){
            SM("JDB Update: "+ e.getMessage());
        }
    }
    
    public void EditProduct(String id, String price, String type, String source){
        String sqlp="update Product set Price='"+price+"', ProductType='"+type+"', Source='"+source+"' where ProductID='"+id+"';";
        try{
            s=conn.createStatement();
            s.execute(sqlp);
            

        }catch(SQLException e){
            SM("JDB Update: "+ e.getMessage());
        }
    }
    
    public int CountUser() {
    	int returned=0;

        try
        {
            PreparedStatement stat;
            ResultSet rs;
            String sql="select max(UserId) as max_id from UserTable";
            stat=conn.prepareStatement(sql);
            rs=stat.executeQuery();
            while(rs.next())
            {
                returned = rs.getInt("max_id")+1;
                System.out.println(returned);
            }
        }
        catch (Exception e)
        {
            System.out.println(""+e);
        }
        return returned;
    
    }
    
    public String getProductNameFromId(int id) {
    	String res="";
    	try
        {
            PreparedStatement stat;
            ResultSet rs;
            String sql="select ProductName as name from Product where ProductID="+id+";";
            stat=conn.prepareStatement(sql);
            rs=stat.executeQuery();
            while(rs.next())
            {
                res = rs.getString("name");
                System.out.println(res);
            }
        }
        catch (Exception e)
        {
            System.out.println(""+e);
        }
    	return res;
    }
    
    public int getProductPrice(int id) {
    	int res=0;
    	
    	try
        {
            PreparedStatement stat;
            ResultSet rs;
            String sql="select Price as price from Product where ProductID="+id+";";
            stat=conn.prepareStatement(sql);
            rs=stat.executeQuery();
            while(rs.next())
            {
                res = rs.getInt("price");
                System.out.println(res);
            }
        }
        catch (Exception e)
        {
            System.out.println(""+e);
        }
    	
    	return res;
    }
       
    public void WriteAllProductDataToFile(String file) {
    	Connect();
    	int ProductId=0, Price=0;
    	String ProductName="", ProductType="", Source="";
    		
    	
    	String sqlp="select ProductId, ProductName, Price, ProductType, Source from Product";
    	try {
    		PrintStream out= new PrintStream(new FileOutputStream(file));
    		
    		s=conn.createStatement();
    		rs=s.executeQuery(sqlp);
    		while(rs.next()) {
    			ProductId=rs.getInt("ProductId");
    			System.out.println(ProductId);
    			Source=rs.getString("Source");
    			
    			
    			
    			ProductType=rs.getString("ProductType");
    			ProductName=rs.getString("ProductName");
    			Price=rs.getInt("Price");
    			
    			out.println(ProductId+"	"+ Source+"	"+ ProductName+"	"+ ProductType+"	"+ Price );
    		}
    		out.close();
    		rs.close();
    	}
    	catch(SQLException e) {SM(e.getMessage());} 
    	
    	catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
    	
    	
    }
    
    public int CountBasement() {
    	int returned=0;

        try
        {
            PreparedStatement stat;
            ResultSet rs;
            String sql="select max(BasementId) as max_id from BasementTable";
            stat=conn.prepareStatement(sql);
            rs=stat.executeQuery();
            while(rs.next())
            {
                returned = rs.getInt("max_id")+1;
                System.out.println(returned);
            }
        }
        catch (Exception e)
        {
            System.out.println(""+e);
        }
        return returned;
    
    }
    
    public int CountProduct() {
    	int returned=0;

        try
        {
            PreparedStatement stat;
            ResultSet rs;
            String sql="select max(ProductId) as max_id from Product";
            stat=conn.prepareStatement(sql);
            rs=stat.executeQuery();
            while(rs.next())
            {
                returned = rs.getInt("max_id")+1;// just want a new id for new product
                System.out.println(returned);
            }
        }
        catch (Exception e)
        {
            System.out.println(""+e);
        }
        return returned;
    
    }
      
    public void InsertNewProduct(int id, String ProductName, int Price, String ProductType, String Source) {
    	
    	String sqlp="insert into Product values (" +id+", '"+ ProductName +"', '"+ Price+"', '"+ ProductType +"', '"+ Source +"')";
        try{
            s=conn.createStatement();
            s.execute(sqlp);
            SM(ProductName+" has been created!");

        }catch(SQLException e){
            SM("JDB Insert: "+ e.getMessage());
        }
    	
    }

    public void GetMetaaData(String fileName, String tableName) {
    	Connect();
    	String file=fileName+".txt";
    	
    	if(tableName=="UserTable") {
    		
    		String sor="", nam="", tip="", nul="", def="", pky="",  x="\n";
    		String sqlp="PRAGMA table_info("+tableName+");";
    		
    		try {
    			PrintStream out= new PrintStream(new FileOutputStream(file));
    			s=conn.createStatement();
    			rs = s.executeQuery(sqlp);
    			out.println("USERTABLE: \n\n");
    			while(rs.next()) {
    				sor=rs.getString(1);
    				nam=rs.getString(2);
    				tip=rs.getString(3);
    				nul=rs.getString(4);
    				def=rs.getString(5);
    				pky=rs.getString(6);
    				
    				out.println("Cid: " +sor + x + "Name: "  + nam + x + "Type: " + tip + x+ "NotNull: " + nul + x+ "Def_val: " + def + x + "PrimaryKey: " + pky +x+x);
    			}
    			SM("Check the folder for "+ file+ " file");
    			out.close();
        		rs.close();
        		
    		}catch(SQLException | FileNotFoundException e) {
    			SM("Wrong meta");
    		}
    		
    	}
    	
    	else if(tableName=="Product") {
    		
    		String sor="", nam="", tip="", nul="", def="", pky="", x="\n";
    		String sqlp="PRAGMA table_info("+tableName+");";
    		
    		try {
    			PrintStream out= new PrintStream(new FileOutputStream(file));
    			s=conn.createStatement();
    			rs = s.executeQuery(sqlp);
    			out.println("PRODUCT: \n\n");
    			while(rs.next()) {
    				sor=rs.getString(1);
    				nam=rs.getString(2);
    				tip=rs.getString(3);
    				nul=rs.getString(4);
    				def=rs.getString(5);
    				pky=rs.getString(6);
    				out.println("Cid: " +sor + x + "Name: "  + nam + x + "Type: " + tip + x+ "NotNull: " + nul + x+ "Def_val: " + def + x + "PrimaryKey: " + pky +x+x);
    			}
    			SM("Check the folder for "+ file+ " file");
    			out.close();
        		rs.close();
        		
    		}catch(SQLException | FileNotFoundException e) {
    			SM("Wrong meta");
    		}
    	}
    	
    	else if(tableName=="BasementTable") {
    		String sor="", nam="", tip="", nul="", def="", pky="", x="\n";
    		String sqlp="PRAGMA table_info("+tableName+");";
    		
    		try {
    			PrintStream out= new PrintStream(new FileOutputStream(file));
    			s=conn.createStatement();
    			rs = s.executeQuery(sqlp);
    			out.println("BASEMENT_TABLE: \n\n");
    			while(rs.next()) {
    				sor=rs.getString(1);
    				nam=rs.getString(2);
    				tip=rs.getString(3);
    				nul=rs.getString(4);
    				def=rs.getString(5);
    				pky=rs.getString(6);
    				out.println("Cid: " +sor + x + "Name: "  + nam + x + "Type: " + tip + x+ "NotNull: " + nul + x+ "Def_val: " + def + x + "PrimaryKey: " + pky +x+x);
    			}
    			SM("Check the folder for "+ file+ " file");
    			out.close();
        		rs.close();
        		
    		}catch(SQLException | FileNotFoundException e) {
    			SM("Wrong meta");
    		}
    	}
    	
    	else {
    		SM("Something wrong!");
    	}
    	
    	
    	
    }
 
    public void SM(String msg) { JOptionPane.showMessageDialog(null, msg, "Message", 2);}

    public void ESM(String msg) {JOptionPane.showMessageDialog(null, "You can't insert this!", "Message", 0);}

}
