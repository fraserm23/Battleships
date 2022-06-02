package classTest;

import javax.swing.JOptionPane;

public class ClassTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//create variables
		String finalOutput, outputPart1, outputPart2, outputPart3;
		int storeNum, highestStoreNum, lowestStoreNum, validatedStoreNum, copiesNum, highestCopiesNum, totalCopiesNum, numberOfStoresEntered, response;
		float averageSold;
		
		//initialise variables
		highestStoreNum = 999;
		lowestStoreNum = 100;
		highestCopiesNum = 0;
		totalCopiesNum = 0;
		numberOfStoresEntered = 0;
		outputPart1 = "";
		
		//call welcome method
		welcome();
		
		//start do-while loop to enter details
		do {
			//input store number
			storeNum = storeInput(highestStoreNum, lowestStoreNum);
			//validate store number
			validatedStoreNum = validateStore(storeNum, highestStoreNum, lowestStoreNum);
			//input copies sold
			copiesNum = copiesInput();
			//check for highest selling store
			if(copiesNum > highestCopiesNum) {
				highestCopiesNum = copiesNum;
				outputPart1 = "The highest selling store number is: " +validatedStoreNum +" which sold " +highestCopiesNum +" copies";
			}
			
			//calculate total number of copies sold
			totalCopiesNum = totalCopiesNum + copiesNum;
			//increment number of stores entered
			numberOfStoresEntered++;
			
			//check if user wants to enter another store
			response = JOptionPane.showConfirmDialog(null, "Do you wish to enter another store?", "Another Store", JOptionPane.YES_NO_OPTION);
			
			
		} while (response == 0);
		
		//calculate average number of copies sold
		averageSold = totalCopiesNum / numberOfStoresEntered;
		
		//create sections of output
		outputPart2 = "The total number of copies sold in all stores is: " +totalCopiesNum;
		outputPart3 = "The average number of copies sold per store is: " +averageSold;
		
		//create final output message
		finalOutput = outputPart1 +"\n" +outputPart2 +"\n" +outputPart3;
		
		//display results window
		JOptionPane.showMessageDialog(null, finalOutput, "Results", JOptionPane.INFORMATION_MESSAGE);
		}
	

	

    public static void welcome() {
    	String output = "Welcome to Jamazon. On the next screen, \nplease enter your shop number and how many copies of \n\"Jamie Cooks Italy\" have been sold.";
    	
    	JOptionPane.showMessageDialog(null, output, "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }
    public static int storeInput(int highestStoreNum, int lowestStoreNum) {
    	String storeAsString;
    	int storeNum; 
    	
    	//enter store number as string
    	storeAsString = JOptionPane.showInputDialog("Please enter a store number between " +lowestStoreNum +" and " +highestStoreNum);
    	//convert to integer
    	storeNum = Integer.parseInt(storeAsString);
    	
    	return storeNum;
    }
    public static int validateStore(int storeNum, int highestStoreNum, int lowestStoreNum) {
    	String storeAsString;
    	int validatedStoreNum;
    	
    	//check store number is in range
    	while (storeNum < lowestStoreNum || storeNum > highestStoreNum) {
    		storeAsString = JOptionPane.showInputDialog("Out of range - Please enter a store number between " +lowestStoreNum +" and " +highestStoreNum);
        	storeNum = Integer.parseInt(storeAsString);
    	}
    	
    	validatedStoreNum = storeNum;
    	
    	return validatedStoreNum;
    	
    }
    public static int copiesInput() {
    	String copiesAsString;
    	int copiesNum;
    	
    	//enter copies as string
    	copiesAsString = JOptionPane.showInputDialog("Please enter how many copies this store sold.");
    	//convert to integer
    	copiesNum = Integer.parseInt(copiesAsString);
    	
    	return copiesNum;
    }
    
}
    

