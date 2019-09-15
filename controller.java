package application;
/*
 * Tongxu Ge,20054696
 * This program is used to control the FXML file.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.LineItem;
import application.Pizza;
import application.IllegalPizza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton small;

    @FXML
    private ToggleGroup sizeGroup;

    @FXML
    private RadioButton medium;

    @FXML
    private RadioButton large;

    @FXML
    private RadioButton single;

    @FXML
    private ToggleGroup cheeseGroup;

    @FXML
    private RadioButton Double;

    @FXML
    private RadioButton triple;

    @FXML
    private CheckBox Ham;

    @FXML
    private CheckBox Pineapple;

    @FXML
    private CheckBox GreenPepper;

    @FXML
    private Button Confirm;

    @FXML
    private Button Add;

    @FXML
    private TextArea totalOrder1;

    @FXML
    private Button Calculate;

    @FXML
    private Label Cost;

    @FXML
    private Label Total;

    @FXML
    private Slider Number1;
    
    private Label Number;

    private Pizza pizza;  //set pizza object
    private String cheese; //set cheese 
    private String ham = "none";  //set ham
    private String pineapple = "none";  //set pineapple
    private String greenPepper = "none";  //set greenPepper
    private String size;  //set size
    private int number = 1;  //set pizza number
    private LineItem item;  //set LineItem object
    private static ArrayList<LineItem> orders = new ArrayList<>();  //set orders to store each order in the order list
	private static boolean orderSavedFlag = true;
	
	//search of the orders collection for a particular Pizza and return -1 if not found
	private static int searchOrders(Pizza pizza) {
		for (int line = 0; line < orders.size(); line++)
			if (orders.get(line).getPizza().equals(pizza))
				return line;
		return -1;
	} 
	
	//Adds a new LineItem to the order list
	private static boolean addItem (int number, Pizza pizza) {
		int orderLocation = searchOrders(pizza);
		if (orderLocation < 0)
			try {
				orders.add(new LineItem(number, pizza));
			} catch (IllegalPizza ip) {
				System.out.println(ip.getMessage());
				return false;
			}
		else {
			LineItem item = orders.get(orderLocation);
			try {
				item.setNumber(item.getNumber() + number);
			} catch (IllegalPizza ip) {
				System.out.println(ip.getMessage());
				return false;				
			}
		}
		orderSavedFlag = false;
		return true;	
	} 
	
	//get size is small
    @FXML
    void smallPizza(ActionEvent event) {
    	size = "small";
    }
   
    //get size is medium
    @FXML
    void mediumPizza(ActionEvent event) {
    	size = "medium";
    }
    
    //get size is large
    @FXML
    void largePizza(ActionEvent event) {
    	size = "large";
    }
    
    //get cheese is single
    @FXML
    void singleCh(ActionEvent event) {
    	cheese = "single";
    }
    
    //get cheese is double
    @FXML
    void doubleCh(ActionEvent event) {
    	cheese = "double";
    }
    
    //get cheese is triple
    @FXML
    void tripleCh(ActionEvent event) {
    	cheese = "triple";
    }    

    //set ham is single
    @FXML
    void setHam(ActionEvent event) {
    	ham = "single";
    }

    //set pineapple is single
    @FXML
    void setPineapple(ActionEvent event) {
    	pineapple = "single";
    }

    //set greenPepper is single
    @FXML
    void setgreenPepper(ActionEvent event) {
    	greenPepper = "single";
    }
    
    //put attributes to a pizza object and throw illegal pizza
    void getPizza() {
		try {
			pizza = new Pizza(size, cheese, pineapple, greenPepper, ham);
		} catch (IllegalPizza ip) {
			System.out.println(ip.getMessage());  //return 
		}
	}
     
    //get the cost of each pizza ordered
    @FXML
    void getPerPizzacost(ActionEvent event) {
    	float price = 0;
    	getPizza();
    	price = pizza.getCost();
    	Cost.setText(String.format("$%.2f", price));  //show the label "Cost" the price
    }

    //strict the number of the pizza from to 1 to 100  
    void setNumber()throws IllegalPizza{
    	if(Number1.getValue()< 1 || Number1.getValue() > 100) {
    		throw new IllegalPizza("Illegal number of pizzas!");
    	}
    	number = (int) Number1.getValue();	 //get the slider value
    }
    
    //get the LineItem object for the given pizza
    void getLineItem(int number) throws IllegalPizza {
    	try {
			item = new LineItem(number,pizza);
		} catch (IllegalPizza ip) {
			System.out.println(ip.getMessage());
		}
    }
    
    //get the total cost of the configured pizza with the given number ordered
    @FXML
    void getTotalPizzas(ActionEvent event) throws IllegalPizza {
    	float price = 0;
    	setNumber();
    	getLineItem(number);
    	price=item.getCost();
    	Total.setText(String.format("$%.2f", price));
    }

    //get the total order list
    @FXML
    void showOrder(ActionEvent event) {
    	String result = "";
    	addItem(number, pizza);
    	float totalCost = 0;
		int line = 1;
		if (orders.size() == 0)
			totalOrder1.setText("No items yet!");
		else {
			orders.sort(null);
			for (LineItem order : orders) {  //for loop for get each order
				totalCost += order.getCost();
				result += String.format("\nLine %d\tOrder:\n"+order.toString()+"\nTotal Cost:$%.2f", line, totalCost);
				line++;
			}
		}	
		totalOrder1.setText(result);  //show the result to the textArea
    }

    //addListener for all radio button and check box
    @FXML
    void initialize() {
    	sizeGroup.selectedToggleProperty().addListener((observableValue, oldText, newText)->
    	{
    		getPizza();
    	});
    	cheeseGroup.selectedToggleProperty().addListener((observableValue, oldText, newText)->
    	{
    		getPizza();
    	});
    	Pineapple.selectedProperty().addListener((observableValue, oldText, newText)->
    	{
    		getPizza();
    	});
    	Ham.selectedProperty().addListener((observableValue, oldText, newText)->
    	{
    		getPizza();
    	});
    	GreenPepper.selectedProperty().addListener((observableValue, oldText, newText)->
    	{
    		getPizza();
    	});
    	Number1.valueProperty().addListener((observableValue, oldText, newText)->
    	{
    		Number1.setValue((double) newText);  //set the slider value to the value 
    		
    	});
    	
        assert small != null : "fx:id=\"small\" was not injected: check your FXML file 'FXML.fxml'.";
        assert sizeGroup != null : "fx:id=\"sizeGroup\" was not injected: check your FXML file 'FXML.fxml'.";
        assert medium != null : "fx:id=\"medium\" was not injected: check your FXML file 'FXML.fxml'.";
        assert large != null : "fx:id=\"large\" was not injected: check your FXML file 'FXML.fxml'.";
        assert single != null : "fx:id=\"single\" was not injected: check your FXML file 'FXML.fxml'.";
        assert cheeseGroup != null : "fx:id=\"cheeseGroup\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Double != null : "fx:id=\"Double\" was not injected: check your FXML file 'FXML.fxml'.";
        assert triple != null : "fx:id=\"triple\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Ham != null : "fx:id=\"Ham\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Pineapple != null : "fx:id=\"Pineapple\" was not injected: check your FXML file 'FXML.fxml'.";
        assert GreenPepper != null : "fx:id=\"GreenPepper\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Confirm != null : "fx:id=\"Confirm\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Add != null : "fx:id=\"Add\" was not injected: check your FXML file 'FXML.fxml'.";
        assert totalOrder1 != null : "fx:id=\"totalOrder\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Calculate != null : "fx:id=\"Calculate\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Cost != null : "fx:id=\"Cost\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Total != null : "fx:id=\"Total\" was not injected: check your FXML file 'FXML.fxml'.";
        assert Number != null : "fx:id=\"Number\" was not injected: check your FXML file 'FXML.fxml'.";

    }
}
