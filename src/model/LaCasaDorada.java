package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LaCasaDorada {
	
	public final static String SAVE_PATH_FILE1 = "Employee-data.csv";
	public final static String SAVE_PATH_FILE2 = "Customer-data.csv";
	public final static String SAVE_PATH_FILE3 = "Product-data.csv";
	
	private static final String SEPARATE=",";
	
	private List<ClientAccount> clients;
	private List<EmployeeAccount> employees;
	private List<RestaurantProduct> products;
	private List<RestaurantIngredient> ingredients;
	private List<RestaurantTypeOfProduct> types;
	private List<Order> orders;
	private List<Size> sizes;
	private List<ProductQuantity> productQuantity;
	private int numberList;
	private double priceTotal;

	
	public LaCasaDorada() {
		
		clients = new ArrayList<>();
		employees = new ArrayList<>();
		products = new ArrayList<>();
		ingredients = new ArrayList<>();
		types = new ArrayList<>();
		orders = new ArrayList<>();
		sizes = new ArrayList<>();
		productQuantity = new ArrayList<>();
		numberList=0;
		setPriceTotal(0);
	}
	
	public void addClient(String firstName, String lastName, String id, String address, String phoneNumber, String observations) throws IOException {
		clients.add(new ClientAccount(firstName, lastName, id, address, phoneNumber, observations));
		saveCustomerrData();
	}
	
	public void addEmployee(String userName, String password, String firstName, String lastName, String id) throws IOException {
		employees.add(new EmployeeAccount(userName, password, firstName, lastName, id));
		saveEmployeeData();
	}
	
	public void addProduct(String name, String typeOfProduct, String[] ingredientsOfProduct, String sizeOfProduct, double priceOfProduct) throws IOException {
		products.add(new RestaurantProduct(name, typeOfProduct, ingredientsOfProduct, sizeOfProduct, priceOfProduct));
		saveProductData();
	}
	
	public void addIngredient(String ingredientName) {
		ingredients.add(new RestaurantIngredient (ingredientName));
	}

	public void addTypeOfProduct(String typeName) {
		types.add(new RestaurantTypeOfProduct(typeName));
	}
	
	public void addSize(String sizeName) {
		sizes.add(new Size(sizeName));
	}
	
	public void addOrder(ClientAccount client, RestaurantProduct product, EmployeeAccount employee, String code, LocalDate date, LocalTime time, double quantity, String observations, int number) {
		orders.add(new Order(client, product, employee, code, date, time, quantity, observations, number));
	}
	
	public void addProductQuantity(RestaurantProduct p, double quantity, RestaurantProduct pr) {
		productQuantity.add(new ProductQuantity(p, quantity, pr));
	}
	
	public List<ClientAccount> getClients(){
		return clients; 
	}
	
	public List<EmployeeAccount> getEmployees(){
		return employees; 
	}
	
	public List<Order> getOrders(){
		return orders; 
	}
	
	public List<RestaurantProduct> getProducts(){
		return products; 
	}
	
	public List<RestaurantIngredient> getIngredients(){
		return ingredients; 
	}
	
	public List<RestaurantTypeOfProduct> getTypeOfProducts(){
		return types; 
	}
	
	public List<ProductQuantity> getProductQuantity(){
		return productQuantity;
	}
	
	public int getNumberList() {
		return numberList;
	}
	
	public void setNumberList(int numberList) {
		this.numberList=numberList;
	}
	
	public double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(double priceTotal) {
		this.priceTotal = priceTotal;
	}

	
/*	public boolean verifyRemoveProductInOrder(RestaurantProduct product) {
		boolean found=false;
		for (int i=0; i<orders.size() && !found; i++) {
			String[] arrayProducts=orders.get(i).
					
		}
		return found;
	}  */
	
	public boolean verifyRemoveIngredientInOrder(RestaurantIngredient ingredient) {
		boolean found=false;
		for(int i=0; i<products.size() && !found; i++) {
			String [] arrayIngredients=products.get(i).getIngredientsOfProductArray();
			for(int j=0; j<arrayIngredients.length && !found; j++) {
				if (arrayIngredients[j].equals(ingredient.getIngredientName())) 
					found=true;
			}
		}
		return found;
	}
	
	public boolean validateOrder(String code) {
		boolean validate=false;
		for(int i=0; i<orders.size() && !validate;i++) {
			Order order = orders.get(i);
			if(order.getCode().equals(order.getCode())) {
				validate=true;
			}
		}
		return validate;
	}
	
	public boolean validateClient(String id) {
		boolean validate=false;
		for(int i=0; i<clients.size() && !validate;i++) {
			ClientAccount client = clients.get(i);
			if(client.getFirstName().equals(client.getFirstName()) && client.getLastName().equals(client.getLastName())) {
				validate=true;
			}
		}
		return validate;
	}
	
	public boolean validateIngredient(String ingredientName) {
		boolean validate=false;
		for(int i=0; i<ingredients.size() && !validate;i++) {
			RestaurantIngredient ingredient = ingredients.get(i);
			if(ingredient.getIngredientName().equals(ingredientName)) {
				validate=true;
			}
		}
		return validate;
	}
	
	public boolean validateProduct(String name) {
		boolean validate=false;
		for(int i=0; i<products.size() && !validate;i++) {
			RestaurantProduct product = products.get(i);
			if(product.getName().equals(name)) {
				validate=true;
			}
		}
		return validate;
	}
	
	public boolean validateTypeOfProduct(String typeName) {
		boolean validate=false;
		for(int i=0; i<types.size() && !validate;i++) {
			RestaurantTypeOfProduct type = types.get(i);
			if(type.getTypeOfProductName().equals(typeName)) {
				validate=true;
			}
		}
		return validate;
	}

	public boolean validateEmployee(String userName, String password) {
		boolean validate=false;
		for(int i=0; i<employees.size() && !validate;i++) {
			EmployeeAccount employee = employees.get(i);
			if(employee.getUserName().equals(userName) && employee.getPassword().equals(password)) {
				validate=true;
			}
		}
		return validate;
	}
	

	public void importEmployeeData(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while(line!=null) {
			String[] parts = line.split(",");
			addEmployee(parts[0], parts[1], parts[2], parts[3], parts[4]);
			line = br.readLine();
		}
		br.close();
	}

	public void importCustomerData(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while(line!=null) {
			String[] parts = line.split(",");
			addClient(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
			line = br.readLine();
		}
		br.close();
	}

	public void importProductData(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while(line!=null) {
			String[] parts = line.split(",");
			double priceOfProduct = Double.parseDouble(parts[4]); 
			String[] ingredientOfProduct = parts[2].split("-");
			addProduct(parts[0], parts[1], ingredientOfProduct, parts[3], priceOfProduct);
			line = br.readLine();
		}
		br.close();
		
	} 
	
	public void exportEmployeeData(String fileName) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(fileName);
        for(EmployeeAccount empl : employees){
          pw.println(empl.getFirstName() +SEPARATE+empl.getLastName() +SEPARATE+empl.getId()+SEPARATE+empl.getEmployeeStatus());
        }
        pw.close();
    }
	
	public void exportProductData(String fileName) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(fileName);
        for(RestaurantProduct prod : products){
          pw.println(prod.getName()+SEPARATE+prod.getTypeOfProduct()+SEPARATE+prod.getSizeOfProduct()+SEPARATE+prod.getPriceOfProduct()+SEPARATE+prod.getIngredientsOfProduct());
        }
        pw.close();
    }
	
	public ClientAccount findClient(String firstName){
		ClientAccount tempName=null;
		for (int i=0; i < clients.size();i++) {
			if(clients.get(i).getFirstName().equals(firstName)) {
				tempName = clients.get(i);
			}
		}
		return tempName;
	}
	
	public EmployeeAccount findEmployee(String firstName){
		EmployeeAccount tempName=null;
		boolean found = false;
		System.out.println(firstName);
		for (int i=0; i < employees.size() && !found;i++) {
			if(employees.get(i).getFirstName().equals(firstName)) {
				tempName = employees.get(i);
				found = true;
				System.out.println("Encontr� empleado");
			}
		}
		return tempName;
	}
	
	public RestaurantProduct findProduct(String name){
		boolean found = false;
		RestaurantProduct tempProduct=null;
		System.out.println(name);
		for (int i=0; i < products.size() && !found;i++) {
			if(products.get(i).getName().equals(name)) {
				tempProduct = products.get(i);
				found = true;
				System.out.println("Encontr� Producto");
			}
		}
		return tempProduct;
	}
	
	public RestaurantIngredient findIngredient(String ingredientName) {
		boolean found = false;
		RestaurantIngredient tempIng = null;
		for(int i=0; i <ingredients.size() && !found; i++) {
			if(ingredients.get(i).getIngredientName().equals(ingredientName)) {
				tempIng = ingredients.get(i);
			}
		}
		return tempIng;
	}
	

	public RestaurantProduct findPrice(double priceOfProduct) {
		RestaurantProduct tempPrice= null;
		System.out.println(priceOfProduct);
		for(int i=0; i<products.size(); i++) {
			if(products.get(i).getPriceOfProduct()==priceOfProduct) {
				tempPrice = products.get(i);
				System.out.println("Encontre el precio ");
			}
		}
		return tempPrice;
	}


	public void saveEmployeeData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (SAVE_PATH_FILE1));
		oos.writeObject(employees);
		oos.close();
	}
	
	public void saveCustomerrData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (SAVE_PATH_FILE2));
		oos.writeObject(clients);
		oos.close();
	}
	
	public void saveProductData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (SAVE_PATH_FILE3));
		oos.writeObject(products);
		oos.close();
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadEmployeeData() throws IOException, ClassNotFoundException{
		File f = new File(SAVE_PATH_FILE1);
		boolean loaded = false;
		if(f.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			employees = (List<EmployeeAccount>)ois.readObject();
			ois.close();
			loaded = true;
		}
		return loaded;
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadCustomerData() throws IOException, ClassNotFoundException{
		File f = new File(SAVE_PATH_FILE2);
		boolean loaded = false;
		if(f.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			clients = (List<ClientAccount>)ois.readObject();
			ois.close();
			loaded = true;
		}
		return loaded;
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadProductData() throws IOException, ClassNotFoundException{
		File f = new File(SAVE_PATH_FILE3);
		boolean loaded = false;
		if(f.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			products = (List<RestaurantProduct>)ois.readObject();
			ois.close();
			loaded = true;
		}
		return loaded;
	}



	/*public double burbleSort() {
		double bestPrice;
		boolean found = true;
			for(int i=1; i < products.size() && found; i++ ) {
			found = false;
				for (int j=0; j<products.size()-1; j++) {
				if(products.get(j).getPriceOfProduct()>products.get(j+1).getPriceOfProduct()) {
					double temp = products.get(j).getPriceOfProduct();
					array[j]=array[j+1];
					array[j+1]=temp;
					num++;
					changed = true;
					
				}			
			}
		}
		
		return bestPrice;
	}*/

}