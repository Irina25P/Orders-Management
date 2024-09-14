package start;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import bll.ProductBLL;
import bll.OrderBLL;
import model.Client;
import model.Product;
import model.Order;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

//	public static void main(String[] args) throws SQLException {
//
//		Client client = new Client("dummy name", "dummy address", "dummy@address.co");
//
//		ClientBLL clientBll = new ClientBLL();
//		int id = clientBll.insertClient(client);
//		if (id > 0) {
//			clientBll.findClientById(id);
//		}
//
//		// Generate error
//		try {
//			clientBll.findClientById(1);
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//
//
//		//obtain field-value pairs for object through reflection
//		ReflectionExample.retrieveProperties(client);
//	}

	public static void main(String[] args) {
		try {
			// Testing Client Functionality
			ClientBLL clientBll = new ClientBLL();
			Client client = new Client("John Doe", "1234 Street", "john@example.com");
			int clientId = clientBll.insertClient(client);
			System.out.println("Inserted Client ID: " + clientId);

			Client retrievedClient = clientBll.findClientById(clientId);
			System.out.println("Retrieved Client: " + retrievedClient);

			// Testing Product Functionality
			ProductBLL productBll = new ProductBLL();
			Product product = new Product("Laptop", 999.99, 10);
			int productId = productBll.insertProduct(product);
			System.out.println("Inserted Product ID: " + productId);

			Product retrievedProduct = productBll.findProductById(productId);
			System.out.println("Retrieved Product: " + retrievedProduct);

			// Testing Order Functionality
			OrderBLL orderBll = new OrderBLL();
			Order order = new Order(0, clientId, productId, 2);
			int orderId = orderBll.insertOrder(order);
			System.out.println("Inserted Order ID: " + orderId);

			Order retrievedOrder = orderBll.findOrderById(orderId);
			System.out.println("Retrieved Order: " + retrievedOrder);

			// Update and List Tests
			retrievedClient.setEmail("newemail@example.com");
			clientBll.updateClient(retrievedClient);
			System.out.println("Updated Client: " + clientBll.findClientById(clientId));

			List<Client> allClients = clientBll.getAllClients();
			System.out.println("All Clients: " + allClients);

			List<Product> allProducts = productBll.getAllProducts();
			System.out.println("All Products: " + allProducts);

			List<Order> allOrders = orderBll.getAllOrders();
			System.out.println("All Orders: " + allOrders);

		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Error", ex);
		}
	}
}
