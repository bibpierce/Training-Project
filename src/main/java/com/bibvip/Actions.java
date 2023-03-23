package com.bibvip;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

import static com.bibvip.TableColumnNames.*;

public class Actions {

    DbConnection dbConnection = new DbConnection();
    Connection con = null;
    Scanner in = new Scanner(System.in);


    {
        try {
            con = dbConnection.readConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteData() throws SQLException {

        Validation valid = new Validation();
        PreparedStatement preparedStatement = null;
        try {
            System.out.print("Enter an ID to delete: ");
            String query = "DELETE FROM user_info WHERE id = ?";
            int del = Integer.parseInt(in.next());
            if (valid.isNotExisting(del)) {
                System.out.println("ID is not EXISTING!");
                return;
            }

            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, del);
            preparedStatement.execute();
            System.out.println("ID " + del + " is deleted!");
        } catch (Exception e) {
            System.out.println("Invalid Input!");
        } finally {
            assert preparedStatement != null;
            preparedStatement.close();
        }
    }

    public void singleExport() throws SQLException {


        ExcelUtils wdl1 = new ExcelUtils();
        ExcelUtils whl1 = new ExcelUtils();
        String filePath1 = "Singe-Excel-export.xlsx";

        Statement statement = null;
        try {

            Validation valid = new Validation();
            statement = con.createStatement();
            System.out.print("Enter an ID Number: ");
            int idNum = Integer.parseInt(in.next());

            if (valid.isNotExisting(idNum)) {
                System.out.println("ID is not EXISTING!");
                return;
            }

            String sql = "SELECT * FROM `user_info` WHERE id = " + idNum;
            ResultSet result = statement.executeQuery(sql);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reviews");

            whl1.writeHeaderLine(sheet);
            wdl1.writeDataLines(result, workbook, sheet);

            FileOutputStream outputStream = new FileOutputStream(filePath1);
            workbook.write(outputStream);
            workbook.close();

            System.out.println("Export Complete!");


        }  catch (Exception e) {
            System.out.println("Invalid Input!");
        } finally {
            assert statement != null;
            statement.close();
        }

    }

    public void exportToExcel() throws SQLException, IOException {

        ExcelUtils wdl = new ExcelUtils();
        ExcelUtils whl = new ExcelUtils();
        String filePath = "Excel-export.xlsx";

        Statement statement = null;
        XSSFWorkbook workbook = null;
        try {
            String sql = "SELECT * FROM user_info";


            statement = con.createStatement();

            ResultSet result = statement.executeQuery(sql);

            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Reviews");

            whl.writeHeaderLine(sheet);
            wdl.writeDataLines(result, workbook, sheet);


            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);

            System.out.println("Export Complete!");


        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO Error: ");
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            assert workbook != null;
            workbook.close();
        }
    }


    public void insertData() throws SQLException {

        Validation valid = new Validation();

        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO user_info (NAME, picture, " +
                    "vac_card, primary_id, secondary_id, picture_file_name," +
                    "vac_file_name, primary_file_name, secondary_file_name) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";


            System.out.println("You are now Connected");

            System.out.print("Enter Name: ");
            String name = in.nextLine();
            if (valid.isExisting(name)) {
                System.out.println("The name is already existing");
            } else {
                System.out.println("Unique Name, Please proceed");

                System.out.print("Enter path of the 2x2: ");
                String picPath = in.nextLine();

                System.out.print("Enter path of the Vaccination Card: ");
                String vacPath = in.nextLine();

                System.out.print("Enter path of the Primary ID: ");
                String prmPath = in.nextLine();

                System.out.print("Enter path of the Secondary ID: ");
                String secPath = in.nextLine();


                FileInputStream picture = new FileInputStream(picPath);
                FileInputStream vacCard = new FileInputStream(vacPath);
                FileInputStream primaryId = new FileInputStream(prmPath);
                FileInputStream secondaryId = new FileInputStream(secPath);

                preparedStatement = con.prepareStatement(query);

                preparedStatement.setString(1, name);

                preparedStatement.setBlob(2, picture);
                preparedStatement.setBlob(3, vacCard);
                preparedStatement.setBlob(4, primaryId);
                preparedStatement.setBlob(5, secondaryId);

                preparedStatement.setString(6, getFileName(picPath));
                preparedStatement.setString(7, getFileName(vacPath));
                preparedStatement.setString(8, getFileName(prmPath));
                preparedStatement.setString(9, getFileName(secPath));

                preparedStatement.execute();
                System.out.println("User Added!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("INVALID FILE PATH!");
        } finally {
            assert preparedStatement != null;
            preparedStatement.close();
        }

    }


    public String getFileName(String path) {
        File f1 = new File(path);
        return f1.getName();
    }

    public void viewUser() throws SQLException {

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `user_info`");


        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String name = resultSet.getString(NAME);
            String picFileName = resultSet.getString(PICTURE_FILE_NAME);
            String vacFileName = resultSet.getString(VAC_FILE_NAME);
            String primaryFileName = resultSet.getString(PRIMARY_FILE_NAME);
            String secondaryFileName = resultSet.getString(SECONDARY_FILE_NAME);

            UserInfo userInfo = new UserInfo(id, name, picFileName, vacFileName, primaryFileName, secondaryFileName);

            System.out.println(userInfo);

        }

    }

    public void selectUser() throws SQLException {
        Statement statement = null;
        try {
            statement = con.createStatement();
            Validation valid = new Validation();
            System.out.print("Enter an ID Number: ");
            int idNum = Integer.parseInt(in.next());
            if (valid.isNotExisting(idNum)) {
                System.out.println("Id is not Existing");
                return;
            }

            String qry = "SELECT * FROM `user_info` WHERE id = " + idNum;
            ResultSet resultSet = statement.executeQuery(qry);


            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(NAME);
                String picFileName = resultSet.getString(PICTURE_FILE_NAME);
                String vacFileName = resultSet.getString(VAC_FILE_NAME);
                String primaryFileName = resultSet.getString(PRIMARY_FILE_NAME);
                String secondaryFileName = resultSet.getString(SECONDARY_FILE_NAME);

                UserInfo selectUser = new UserInfo(id, name, picFileName, vacFileName, primaryFileName, secondaryFileName);

                System.out.println(selectUser);
            }
        } catch (Exception e) {
            System.out.println("Invalid Input!");
        } finally {
            assert statement != null;
            statement.close();
        }

    }


}