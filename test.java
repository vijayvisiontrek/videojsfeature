import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class test{

    public static Connection getDb(){

    	Connection	conn=null;
    	String password="genr@&y&123";
    	
try {

    Class.forName("com.mysql.jdbc.Driver").newInstance();
//			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/htgames?serverTimeCzone=UTC&autoReconnect=true","root","gloadmin123");
			conn=DriverManager.getConnection("jdbc:mysql://5.189.146.57:3306/htgames?autoReconnect=true","root",password);

			System.out.println("Connected..............");
       
   } catch (Exception e) {
       e.printStackTrace();
   }
return conn;

    }
    
    public static Connection getlocalhost(){

    	Connection	conn=null;
    	String password="genr@&y&123";
    	
try {

    Class.forName("com.mysql.jdbc.Driver").newInstance();
//			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Web_info?serverTimeCzone=UTC&autoReconnect=true","root","gloadmin123");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/report?autoReconnect=true","root","root");

			System.out.println("Connected..............");
       
   } catch (Exception e) {
       e.printStackTrace();
   }
return conn;

    }
    
    public static void  getReport(Connection conn,String service,Connection  con)
    {
    	String ani="",servicetype="",subdate="",status="",revenue="",unsubdate="",mact;
		int totalactive=0;
		String query = "select m_act,ani,service_type,date(sub_date_time) as date,status from tbl_subscription where date(sub_date_time)>='2021-01-01'and  date(sub_date_time)<='2021-03-20'";
		System.out.println(query);
		try {
    	ResultSet rs=getResultSet(conn, query);
    	while(rs.next())
    	{
//    		System.out.println(rs.getString(1));
    		ani=rs.getString("ani");
    		subdate=rs.getString("date");
    		servicetype=rs.getString("service_type");
    		status=rs.getString("status");
			if(status==null) status="NA";
    		mact=rs.getString("m_act");
    		String query1="select sum(deducted_amount)as revenue from tbl_billing_success where ani='"+ani+"' and servicename='"+servicetype +"' ";
    		System.out.println(query1);
    		ResultSet rs1=getResultSet(conn, query1);
    		
//    		System.out.println(ani+""+subdate);
    		
    		if(rs1.next())
    		{
    			revenue=rs1.getString("revenue");
    			if( revenue == null)
				{
				revenue="NA";
				}

    			String query2="Select date(unsub_date_time) from tbl_subscription_unsub where ani='"+ani+"'  ";
    			System.out.println(query2);
    			
    			ResultSet rs2=getResultSet(conn, query2);
    			if(rs2.next())
    			{
    				unsubdate=rs2.getString(1);
        			System.out.println(ani+"="+subdate+"="+revenue+"="+status+"=="+unsubdate);
        			

    			}
    			else
    			{
    				unsubdate="NA";
        			

    			}

				if(unsubdate.equalsIgnoreCase("null") || unsubdate.equalsIgnoreCase("NA"))
				{
					String query3="SELECT DATEDIFF(date(now()), '"+subdate+"') AS totalactive";
                      System.out.println(query3);
					ResultSet rs3=getResultSet(con, query3);
					if(rs3.next())
					{
						totalactive=rs3.getInt("totalactive");
					}
				
				}
				else if(!unsubdate.equalsIgnoreCase("null") || !unsubdate.equalsIgnoreCase("NA"))
				{
					String query4="SELECT DATEDIFF('"+unsubdate+"', '"+subdate+"') AS totalactive"; 

					ResultSet rs4=getResultSet(con, query4);
					if(rs4.next())
					{
						totalactive=Math.abs(rs4.getInt("totalactive"));
					}
				}
    		}
    		String query3="insert into tbl_report (ani,servicename,status,unsubdate,subdate,revenue,mact,totalactivedays) values ('"+ani+"','"+servicetype+"','"+status+"','"+unsubdate+"','"+subdate+"','"+revenue+"','"+mact+"','"+totalactive+"')";
    		System.out.println(query3);
    	PreparedStatement ps=con.prepareStatement(query3);
    	ps.execute();


		System.out.println("***************************#############***************************");
    	}
	} catch (Exception e) {
		
		e.printStackTrace();
	}
		
		
    }
    
    public static ResultSet getResultSet(Connection con,String query)
    {
    	
    	ResultSet rs=null;
    	
    	try {
    		
    		PreparedStatement ps=con.prepareStatement(query);
                rs=ps.executeQuery();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return rs;
    }
//    
//public static void main(String[] args) {
//    
//Connection conn=getDb();
//
//getReport(conn,"cashbattle",getlocalhost());
//
//
//}


}