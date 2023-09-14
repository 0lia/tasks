package task5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyShop {
    private Connection connection;
    private Statement statement;

    public Connection createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myshop", "root", "1234");
        return connection;
    }

    public Statement createStatement() throws SQLException {
        return statement = connection.createStatement();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void closeStatement() throws SQLException {
        statement.close();
    }

    public void createTableSuppliers() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS Suppliers(" +
                "SupplierID int PRIMARY KEY auto_increment, " +
                "SupplierName varchar(50)," +
                "City varchar(20)," +
                "Country varchar(20))"
        );
    }

    public void createTableCategories() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS Categories(" +
                "CategoryID int PRIMARY KEY auto_increment, " +
                "CategoryName varchar(30), " +
                "Description varchar(200))"
        );
    }

    public void createTableProducts() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS Products(" +
                "ProductID int PRIMARY KEY auto_increment, " +
                "ProductName varchar(50), " +
                "SupplierID int REFERENCES Suppliers(SupplierID), " +
                "CategoryID int REFERENCES Categories(CategoryID), " +
                "Price double)"
        );
    }

    public void insertIntoSuppliers() throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("insert into Suppliers(SupplierName, City, Country) values (?, ?, ?);");
        statement1.setString(1, "Exotic Liquid");
        statement1.setString(2, "London");
        statement1.setString(3, "UK");
        statement1.executeUpdate();

        statement1.setString(1, "New Orleans Cajun Delights");
        statement1.setString(2, "New Orleans");
        statement1.setString(3, "USA");
        statement1.executeUpdate();

        statement1.setString(1, "Grandma Kelly’s Homestead");
        statement1.setString(2, "Ann Arbor");
        statement1.setString(3, "USA");
        statement1.executeUpdate();

        statement1.setString(1, "Tokyo Traders");
        statement1.setString(2, "Tokyo");
        statement1.setString(3, "Japan");
        statement1.executeUpdate();

        statement1.setString(1, "Cooperativa de Quesos ‘Las Cabras’");
        statement1.setString(2, "Oviedo");
        statement1.setString(3, "Spain");
        statement1.executeUpdate();

        statement1.close();
    }

    public void insertIntoCategories() throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("insert into Categories(CategoryName, Description) values (?, ?);");
        statement1.setString(1, "Beverages");
        statement1.setString(2, "Soft drinks, coffees, teas, beers, and ales");
        statement1.executeUpdate();

        statement1.setString(1, "Condiments");
        statement1.setString(2, "Sweet and savory sauces, relishes, spreads, and seasonings");
        statement1.executeUpdate();

        statement1.setString(1, "Confections");
        statement1.setString(2, "Desserts, candies, and sweet breads");
        statement1.executeUpdate();

        statement1.setString(1, "Dairy Products");
        statement1.setString(2, "Cheeses");
        statement1.executeUpdate();

        statement1.setString(1, "Grains/Cereals");
        statement1.setString(2, "Breads, crackers, pasta, and cereal");
        statement1.executeUpdate();

        statement1.close();
    }

    public void insertIntoProducts() throws SQLException {
        PreparedStatement statement1 = connection
                .prepareStatement("insert into Products(ProductName, SupplierID, CategoryID, Price) values (?, ?, ?, ?);");
        statement1.setString(1, "Chais");
        statement1.setInt(2, 1);
        statement1.setInt(3, 1);
        statement1.setDouble(4, 18.00);
        statement1.executeUpdate();

        statement1.setString(1, "Chang");
        statement1.setInt(2, 1);
        statement1.setInt(3, 1);
        statement1.setDouble(4, 19.00);

        statement1.executeUpdate();statement1.setString(1, "Aniseed Syrup");
        statement1.setInt(2, 1);
        statement1.setInt(3, 2);
        statement1.setDouble(4, 10.00);
        statement1.executeUpdate();

        statement1.setString(1, "Chef Anton’s Cajun Seasoning");
        statement1.setInt(2, 2);
        statement1.setInt(3, 2);
        statement1.setDouble(4, 22.00);
        statement1.executeUpdate();

        statement1.setString(1, "Chef Anton’s Gumbo Mix");
        statement1.setInt(2, 2);
        statement1.setInt(3, 2);
        statement1.setDouble(4, 21.35);
        statement1.executeUpdate();

        statement1.close();
    }

    public List<Product> getProductsStartingWithC() throws SQLException {
        List<Product> list = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM Products WHERE ProductName LIKE 'C%'");
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("ProductID"));
            product.setProductName(rs.getString("ProductName"));
            product.setSupplierId(rs.getInt("SupplierID"));
            product.setCategoryId(rs.getInt("CategoryID"));
            product.setPrice(rs.getDouble("Price"));
            list.add(product);
        }
        return list;
    }

    public Product getProductWithSmallestPrice() throws SQLException {
        Product product = new Product();
        ResultSet rs = statement.executeQuery("SELECT * FROM Products ORDER BY Price ASC LIMIT 1;");
        if(rs.next()){
            product.setId(rs.getInt("ProductID"));
            product.setProductName(rs.getString("ProductName"));
            product.setSupplierId(rs.getInt("SupplierID"));
            product.setCategoryId(rs.getInt("CategoryID"));
            product.setPrice(rs.getDouble("Price"));
        }
        return product;
    }

    public List<Double> getCostsFromUSA() throws SQLException {
        List<Double> list = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT price FROM Products p JOIN Suppliers s on p.SupplierID = s.SupplierID WHERE Country = 'USA';");
        while (rs.next()) {
            list.add(rs.getDouble("Price"));
        }
        return list;
    }

    public List<Supplier> getSuppliersCondiments() throws SQLException{
        List<Supplier> list = new ArrayList();
        ResultSet rs = statement.executeQuery("SELECT distinct s.* FROM Products p JOIN Suppliers s on p.SupplierID = s.SupplierID JOIN Categories c on p.CategoryID = c.CategoryID WHERE CategoryName = 'Condiments';");
        while (rs.next()) {
            Supplier supplier = new Supplier();
            supplier.setId(rs.getInt("SupplierID"));
            supplier.setName(rs.getString("SupplierName"));
            supplier.setCity(rs.getString("City"));
            supplier.setCountry(rs.getString("Country"));
            list.add(supplier);
        }
        return list;
    }

    public void addNewSupplier() throws SQLException {
        int supplierId = 0, categoryId = 0;
        PreparedStatement statement1 = connection.prepareStatement("insert into Suppliers(SupplierName, City, Country) values (?, ?, ?);");
        statement1.setString(1, "Norske Meierier");
        statement1.setString(2, "Lviv");
        statement1.setString(3, "Ukraine");
        statement1.executeUpdate();

        ResultSet rs = statement.executeQuery("SELECT SupplierID FROM Suppliers WHERE SupplierName = 'Norske Meierier';");
        if(rs.next()){
            supplierId = rs.getInt("SupplierID");
        }

        rs = statement.executeQuery("SELECT CategoryID FROM Categories WHERE CategoryName = 'Beverages';");
        if(rs.next()){
            categoryId = rs.getInt("CategoryID");
        }

        statement1 = connection.prepareStatement("insert into Products(ProductName, SupplierID, CategoryID, Price) values (?, ?, ?, ?);");
        statement1.setString(1, "Green tea");
        statement1.setInt(2, supplierId);
        statement1.setInt(3, categoryId);
        statement1.setDouble(4, 10.00);
        statement1.executeUpdate();
        statement1.close();
    }


    public static void main(String[] args) {
        MyShop myShop = new MyShop();
        try {
            myShop.createConnection();
            myShop.createStatement();

            myShop.createTableSuppliers();
            myShop.createTableCategories();
            myShop.createTableProducts();
            myShop.insertIntoSuppliers();
            myShop.insertIntoCategories();
            myShop.insertIntoProducts();

            System.out.println("Products with name that begins with ‘C’:");
            for(Product product : myShop.getProductsStartingWithC())
                System.out.println(product);

            System.out.println("\nProduct with smallest price:\n" + myShop.getProductWithSmallestPrice());
            System.out.println("\nCost of all products from the USA:\n" + myShop.getCostsFromUSA());

            System.out.println("\nSuppliers that supply Condiments:");
            for(Supplier supplier : myShop.getSuppliersCondiments())
                System.out.println(supplier);

            myShop.addNewSupplier();

            myShop.closeStatement();
            myShop.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
