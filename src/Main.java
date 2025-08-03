import java.sql.*;
import java.util.Scanner;

public class Main {
    // THIS IS JUST FOR STUDY PURPOSE IF YOU WANT TO UNDERSTAND IN DETAIL REFER ANY YT VIDEOS
    private static final String url = "jdbc:mysql://localhost:3306/bankaccount";//use your database name
    private static final String username="root";// set according to your configurations
    private static final String password="";// use your password
    public static void main(String[] args)  {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
         Connection conn = DriverManager.getConnection(url,username,password);
         conn.setAutoCommit(false);
//         Scanner in = new Scanner(System.in);
//         String query = "INSERT INTO students(name,age,marks) VALUES(?,?,?)";
//         PreparedStatement ps = conn.prepareStatement(query);

//         INSERT OPERATION USING PREPARED STATEMENT:
//         String query = "INSERT INTO students(name,age,marks) VALUES(?,?,?)";

//         SELECT OPERATION USING PREPARED STATEMENT:
//         String query = "SELECT * FROM students";
//         PreparedStatement ps = conn.prepareStatement(query);
//         ResultSet rs = ps.executeQuery();

//         UPDATE OPERATION USING PREPARED STATEMENT:
//           String query = "UPDATE students SET marks = ? WHERE id = ?";
//           String query1 = "DELETE FROM students WHERE id = ?";
//           PreparedStatement ps = conn.prepareStatement(query);
//           ps.setDouble(1,87.5);
//           ps.setInt(2,2);


//            Statement st = conn.createStatement();
//            String query = "select * from students";
//            ResultSet rs = st.executeQuery(query);
//            ps.setString(1,"Ankita");
//            ps.setInt(2,21);
//            ps.setDouble(3,93.5);
//            while(rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                int age = rs.getInt("age");
//                double marks = rs.getDouble("marks");
//                System.out.println("ID: "+id);
//                System.out.println("NAME: "+name);
//                System.out.println("AGE: "+age);
//                System.out.println("MARKS: "+marks);
//            }
//            int rowsAffected = ps.executeUpdate();
//            if(rowsAffected>0){
//                System.out.println("Data updated");
//            }else{
//                System.out.println("Data updation failed");
//            }

//            Batch Processing:
//            while(true){
//                System.out.print("Enter name: ");
//                String name = in.next();
//                System.out.print("Enter age: ");
//                int age = in.nextInt();
//                System.out.print("Enter marks: ");
//                double marks = in.nextDouble();
//                System.out.print("Enter more data(Y/N): ");
//                String choice = in.next();
//                ps.setString(1,name);
//                ps.setInt(2,age);
//                ps.setDouble(3,marks);
////                st.addBatch(query);
//                ps.addBatch();
//                if(choice.equalsIgnoreCase("N")) break;
//
//            }
//            int[] arr = ps.executeBatch();
//            for(int i=0;i<arr.length;i++){
//                if(arr[i]==0){
//                    System.out.println("Query "+i+" not executed.");
//                }
//            }

//            TRANSACTION HANDLING: ONLY FOR STUDYING THE BASICS OF HOW TO HANDLE TRANSACTION THE LOGIC CAN BE IMPROVED.
            String debit = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            String credit = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement debitPs = conn.prepareStatement(debit);
            PreparedStatement creditPs = conn.prepareStatement(credit);
            Scanner in = new Scanner(System.in);
            System.out.print("Enter account_number: ");
            int account_number = in.nextInt();
            System.out.print("Enter amount: ");
            double amount = in.nextDouble();
            debitPs.setDouble(1,amount);
            debitPs.setDouble(2,account_number);
            creditPs.setDouble(1,amount);
            creditPs.setInt(2,102);
            debitPs.executeUpdate();
            creditPs.executeUpdate();
            if(isSufficient(conn,account_number,amount)){
                conn.commit();
                System.out.println("Transaction Success");
            }else{
                conn.rollback();
                System.out.println("Transaction Failed");
            }


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    static boolean isSufficient(Connection connection,int account_number,double ammount) throws SQLException{
        String query = "SELECT balance FROM accounts WHERE account_number = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,account_number);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            double current_balance = rs.getDouble("balance");
            return !(ammount > current_balance);
        }
        return false;
    }
}
