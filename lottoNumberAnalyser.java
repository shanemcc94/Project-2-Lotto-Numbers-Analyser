import java.text.*; 
import java.util.*;
import java.io.*;
public class group8LottoAnalysisFinal
{ 
	public static String filename = "SampleLottoData.txt";
	
	public static void main(String [] args)throws IOException, ParseException
	{	
		String pattern1 = "[1-2]{1}";//BONUS INCLUDED YES/NO
		String pattern2 = "[A|a|R|r|SP|sp|L1|l1|L2|l2]{1,3}";//TYPE OF DRAW
		String pattern3 = "[B|b|W|w|S|s]{1}";//WED/SAT/BOTH DAYS
		String pattern4 = "[0-9]{4}";//YEAR
		String pattern5 = "[0-9]{1,2}";//NUMBER/BONUS NUMBER
		boolean fileExists;
		boolean truthValues [] = new boolean [11];
		String [] argsCopy = new String [args.length];
		String helpMsg;
		helpMsg = "\nUsage: java group8LottoAnalysis [options1] [options2] [options3] [options4]..\n"
			+ "\n n1 n2 n3 n4 n5 n6 [n7] : Lotto numbers (7th is optional bonus number)"
			+ "\n                          [in a range of 1 to 45 inclusive]\n"
			+ "\n1st option            1 : Jackpot numbers only."
			+ "\n                      2 : Jackpot including bonus numbers.\n"
			+ "\n                      R : Filter regular draws only."
			+ "\n                      SP : Filter special draws only."
			+ "\n                     L1 : Filter Lotto Plus 1 draws only."
			+ "\n                     L2 : Filter Lotto Plus 2 draws only.\n"
			+ "\n3rd option            B : Show draws for both days."
			+ "\n                      W : Wednesday's draws only."
			+ "\n                      S : Saturday's draws only.\n"
			+ "\n4th option         yyyy : (Short date)Filter draws from start of the given years"
			+ "\n                          to date."
			+ "\n4th-5th      dd/mm/yyyy : (Long dates)Filter draws between two range of dates.\n"
			+ "\n                          If date a entered as dd/mm/yyyy format then two dates"
			+ "\n                          are expected in chronicle order.\n"
			+ "\n6th-7th                 : Numbers 0-45 expected. Max number will effect"
			+ "\n                          from what date lotto numbers are analysed.\n";  

			
		//call a  method to check if the file exists. if file does not exist no action is taken.	
		fileExists = verifyfile();
		if (fileExists == true)
		{
		  if ((args.length > 0) && (args.length <= 7))
			  {
			    for (int a =0; a< args.length; a++)
			       {
			         argsCopy[a]= args[a].toUpperCase();	            
	               }
				if ((args.length == 1) && (args[0].matches(pattern1)))
				    {
						getMethod( truthValues, argsCopy);
						if(!args[0].matches(pattern1))
						  { 
							System.out.println(helpMsg);
						  }
				    }
				if (args.length == 2)
				   {
					if ((args[0].matches(pattern1)) && (args[1].matches(pattern2)))
					   {
					    getMethod( truthValues, argsCopy);
					   }
					   else 
					 System.out.println(helpMsg);	
				   }	 
				if (args.length == 3)
				   {
					if ((args[0].matches(pattern1)) && (args[1].matches(pattern2)) && (args[2].matches(pattern3)))
					   {
					     getMethod( truthValues, argsCopy);
					   }

				   }  
				 
				if (args.length == 4)
				   {
					if ((args[0].matches(pattern1)) && (args[1].matches(pattern2)) && (args[2].matches(pattern3))&& (args[3].matches(pattern4)))
					   { 
					    getMethod( truthValues, argsCopy);
					   }
				   }
				if (args.length == 5)
				   {
					if ((args[0].matches(pattern1)) && (args[1].matches(pattern2)) && (args[2].matches(pattern3)))
					   {
					     // evaluate if arg[3] is valid date and lower than args [4] and that args[4] is a valid date!
					     //call evaluateclArgs()
						 String startDate=args[3];
			             String endDate=args[4];
			             boolean validDates = dateValid(startDate, endDate);
			             if(validDates!=true)
			             System.out.println("\nInvalid dates entered");
			             else
						 getMethod( truthValues, argsCopy);
						
					   }
					  
				  }
		      }
		      if(args.length > 7)
		      {
			      System.out.print(helpMsg);
		      }
	
			  if ((args.length == 6) || (args.length == 7))
				 {
				  // from here a if numbers enetered at args are indeed numbers pass the array to the validate numbers array. then return the ordered/validated array
				  //to here and pass them on to be checked. 
				  int lottoArray [] = new int [args.length];
				  int count = 0;
				  boolean sorted = false;				
				  //loop to check that all CLargs entered by user match the pattern
				  for(int i = 0; i < args.length; i++)
					 {
					  if(args[i].matches(pattern5))
					  count++;						
					  }
				  //if counter matches args.length then all values match pattern
				  if (count == args.length)
					 {//loop to parse CLargs string to integer array
					   for(int i = 0; i < args.length; i++)
					      {
						   lottoArray[i] = Integer.parseInt(args[i]);
					      }
					    sorted = validateNumbers(lottoArray);//call to a method to sort the numbers in ascending sequence and check they are a valid range
					  }
				  if (sorted == true)
					  {//if sorted and in valid range pass array to ceck numbers
					    checkWinningNumbers(lottoArray);
					  }
				  else
					  {
					    System.out.println("CLargs : User entry error numbers 6/7 1 - 45");
					  }
		 		  }  
	           }
 		   }    
          ////////////////////////////////////////  getMethod Method  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
           public static void getMethod(boolean [] truthValues, String [] argsCopy ) throws IOException, ParseException
           {
	       	  /* This Method evaluates CommandLine argument with a Boolean Array
	       	     and a String Array, it takes into consideration the amount
	       	              and various possibilities of user choice    
	       	                                                                  */
	          // String, integer and Boolean assignments!
	         int commandLineArgument = argsCopy.length;
	         String [] values= {"2","A","R","SP","L1","L2","B","W","S"};
	         //call getDrawTypemethod to retrieve Boolean values for user input
	         boolean [] evaluatedArguments= getDrawtype(commandLineArgument, argsCopy, values);
	         int bonusNumber=6;
	       	 String dType="";
	       	 String days="";
	       	 String year="";
	       	 String startDate="";
	       	 String endDate="";
       	    //String assignments that will be passed into the get drawtypeMethod!
       	    /*args = 0BN 1 Dtype 2 days 3 year 3 && 4 StarDate EndDate */
       	    //loop index!
       	    int index; 
	        //check how many arguments have been entered!
	        if ( argsCopy.length == 4  ) // Year validation.
          	   {
          	 	//assignment Statement for year!
                year=argsCopy[3];
               }  
            //loop to evaluate the more comples choices!
         	for (index =0; index < argsCopy.length; index++ )
               {
               	   //switch allows every iteration to have a inbuild loop to evaluate arguments!
               	   switch (index)
               	          {

		                     case 0 : 
		                              if ( index== 0 && evaluatedArguments[index] == true)
		                              	 {
		                              	 	bonusNumber = 7;
		                              	 }
		                     break;
		                     case 1 : 
		                              for (int x=1; x < values.length - 3; x++ )
		                              	   {
		                                     if( evaluatedArguments[x] == true  )
		                              	   	 dType=values[x];
		                              	   }
		                     break;
		                     case 2:
		                              for (int y=6; y < values.length -1; y++ )
		                              	   {
		                                     if( evaluatedArguments[y] == true)
		                              	   	 days=values[y];
		                              	   }

		                     break;
                           }
               }
				       
		  returnFrequenciesOfNumbers(argsCopy, bonusNumber, dType, year, startDate, endDate );
        } 
		///////////////////////////////   Check If Date Is Valid  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\				 
 		public static boolean dateValid(String startDate, String endDate) throws IOException, ParseException
 		{
	 		Boolean datesValid = false, validStart = false, validEnd = false;
	 		String pattern ="[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
	 		Date actualStartDate, actualEndDate, currentDate;
	 		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	 		
			if (startDate.matches(pattern))
				validStart= true;
			else		
				System.out.println("Format of" + startDate + "is incorrect.");
			if (endDate.matches(pattern))
			validEnd = true;
			else		
				System.out.println("Format of" + endDate + "is incorrect.");				

			if(validStart && validEnd)
			{
				validStart=validEnd=false;
				if(isValidDate(startDate))		
					validStart = true;
				else
					System.out.println(startDate + "is not a valid date.");
				if(isValidDate(endDate))		
					datesValid = true;
				else
					System.out.println(endDate + "is not a valid date.");						
			}
			//gets information on current date from user computer
			GregorianCalendar aCalendar = new GregorianCalendar();
			String dateParts = aCalendar.get(Calendar.DATE) + "/" + ((aCalendar.get(Calendar.MONTH))+1)+ "/" + aCalendar.get(Calendar.YEAR);
			System.out.println(dateParts);
			
										
			currentDate = (Date)dateFormatter.parse(dateParts);
			actualStartDate = (Date)dateFormatter.parse(startDate);
			actualEndDate = (Date)dateFormatter.parse(endDate);
			//compare the actual/current and end dates to ensure they are logical. ie. startdate < enddate and enddate <= currentdate
			if(!((currentDate.compareTo(actualStartDate) >= 0) && (currentDate.compareTo(actualEndDate) >= 0) && (actualStartDate.compareTo(actualEndDate) <= 0)))
			datesValid = false;//if not return false as incorrect dates entered.
			
			return datesValid;		 			 			 	
		}
 
		
		public static boolean isValidDate(String startEndDates)
		{
			int positionFirstSlash, positionLastSlash;
			int ddInt, mmInt, yyInt;
			int[] daysArray = {31,28,31,30,31,30,31,31,30,31,30,31}	;
			boolean dateIsValid = true;
			positionFirstSlash = startEndDates.indexOf("/");
			positionLastSlash = startEndDates.lastIndexOf("/");
			ddInt = Integer.parseInt(startEndDates.substring(0, positionFirstSlash));
			mmInt = Integer.parseInt(startEndDates.substring( positionFirstSlash+1,positionLastSlash));			
			yyInt = Integer.parseInt(startEndDates.substring(positionLastSlash+1));
			if((ddInt==0)||(mmInt==0)||(yyInt==0))
				dateIsValid = false;
			else if(mmInt>12)
				dateIsValid = false;
			else if((ddInt==29)&&(mmInt==2)&&((((yyInt%4==0)&&(yyInt%100!=0))||(yyInt%400==0))))				
				dateIsValid = false;	
			else if(ddInt>daysArray[mmInt-1])							
				dateIsValid = false;
			return dateIsValid;
		}				 				 				 
	      ///////////////////////////////   returnFrequenciesOfNumbers Method  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		public static void returnFrequenciesOfNumbers(String[] argsCopy,int bonusNumber, String dType, String year, String startDate, String endDate) throws IOException, ParseException
        { 
           /*This method reads a file according to the userChoice enterd in the coomand line
              we assigned String values int the getMethod method corresponding to and/or with the boolean and String Array which
              have been evaluated in paralel so that any value that may hold true in the boolean array corresponds
              to the same index in the arguments String array which than was assigned to the corresponding String */

          //call method to assign boolean value to find file and/or/not!
          boolean fileExist= verifyfile();
          File aFile = new File (filename);
          
          if (!fileExist)
          	 {
          	 System.out.println("File Does not exist");
          	 }
      	 else
      	    {
             Scanner in = new Scanner (aFile);
             String lineFromFile;
             int [] lottoNumber45= new int [45];
             // stardDate = argsCopy[3];
             //String endedDate = argsCopy[4];
             int a45 =45;
             int b36=36;
             int even = 0;
			 int evenCount = 0;
             int odd = 0;
			 int oddCount = 0;
			 int highNum=0;
			 int lowNum=0;
			 int lowCount=0;
             int [] lottoNumber36 = new int [36];
             String result4 ="";
             String [] lineFromFileCopy	= new String [bonusNumber];
             ArrayList<Integer> numbersOfList = new ArrayList<Integer>();
             ArrayList<String> LinesOfList = new ArrayList<String>();
             ArrayList<Integer> numbersOfDates = new ArrayList<Integer>();
             //create array that holds all Numbers from 1 -45
             for ( int a=0; a< a45; a++)
			      {
			       lottoNumber45[a]= (a+1) ;
			      }
			 if (dType == "SP")
                {
                 dType = dType.replaceAll("P","");
                }
			/////////////////////////THIS EVALUATES 2 or less CLArgs\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\		   
            //start reading and evaluating file! 
			if(argsCopy.length < 3 && dType =="A")//evalueates for 2 CLargs only
	            {
	              while (in.hasNext())
	             	  {            
	                    lineFromFile=in.nextLine();
	                     /* we start reading here */
	                      //String assignment dType referenced from last item on the line 
	                          lineFromFileCopy = lineFromFile.split(",");
	                          for ( int i=1; i< bonusNumber; i++)
	                              {
	                                numbersOfList.add(Integer.parseInt(lineFromFileCopy[i]));
	                              } 
	                         			                             				                            
	                  }
	            }       
		   
             if(argsCopy.length < 3)//evalueates for 2 CLargs only
	            {
	              while (in.hasNext())
	             	  {            
	                    lineFromFile=in.nextLine();
	                     /* we start reading here */
	                      //String assignment dType referenced from last item on the line
	                     if ( lineFromFile.endsWith(dType))
	                        {
	                          lineFromFileCopy = lineFromFile.split(",");
	                          for ( int i=1; i< bonusNumber; i++)
	                              {
	                                numbersOfList.add(Integer.parseInt(lineFromFileCopy[i]));
	                              } 
	                         }			                             				                            
	                  }
	            } 
                       /////////////////////////THIS EVALUATES 3 CLArgs\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
             if(argsCopy.length == 3)//Evaluates for 3 or more CLargs to allow for checking dates from files.
             {
               while (in.hasNext())
                    {      
                     boolean isDateOk = false;   
         	  		 boolean isDateBetween = false;   
                     lineFromFile=in.nextLine();
                     /* we start reading here */
                      //String assignment dType referenced from last item on the line
                     if (lineFromFile.endsWith(dType))
                        {
	                      lineFromFileCopy = lineFromFile.split(",");
	                      isDateOk = DateToWeekDay(lineFromFileCopy[0], argsCopy[2]);
	                      if(isDateOk == true)//if true, carry out analysis of numbers. if not skip and continue while loop
	                        {
	                          for ( int i=1; i< bonusNumber; i++)
	                              {
	                                numbersOfList.add(Integer.parseInt(lineFromFileCopy[i]));		                                                 						                                
	                              }				                                   
	                        }
				          if(isDateBetween==true)
						    {
                              for ( int i=1; i< bonusNumber; i++)
                                  {
                                    numbersOfList.add(Integer.parseInt(lineFromFileCopy[i]));
                                  }												   
							   
						    }
	                               		 	
		                }			                             				                            
                    }
             }
                      
                     ///////////////////////USE THIS TO EVALUATE 4CLARGS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                      
         if(argsCopy.length == 4)//Evaluates for 4 CLargs to allow for year checking.
           {

            while (in.hasNext())
         	      {
					// int countWinTimes = countLowMajorityWins(lineFromFileCopy, argsCopy[3]);                             	  
	         	    boolean isDateOk = false;   
	     	  		boolean isDateBetween = false;   
	                lineFromFile=in.nextLine();
	                // we start reading here 
	                 //String assignment dType referenced from last item on the line
	                if (lineFromFile.endsWith(dType))
		               {
		                 lineFromFileCopy = lineFromFile.split(",");
		                 isDateOk = DateToWeekDay(lineFromFileCopy[0], argsCopy[2]);
		                 String [] getYearFromArgs = lineFromFileCopy[0].split("/");
		                 int fileYear = Integer.parseInt(getYearFromArgs[2]);
		                 int userYear = Integer.parseInt(argsCopy[3]);  
		                 if((isDateOk == true) && (fileYear == userYear))//if true, carry out analysis of numbers. if not skip and continue while loop
		                   {
		                     for ( int i=1; i< bonusNumber; i++)
		                          {
		                            numbersOfList.add(Integer.parseInt(lineFromFileCopy[i]));		                                                 						                                
		                            LinesOfList.add(lineFromFileCopy[i]);		                                                 						                                
		                          }				                                   
		                   }
						 			                             				                            
		      	   	   }
	                      
	               }
         int countWinTimes = countLowMajorityWins(LinesOfList, argsCopy[3]); 
         if(countWinTimes > 0)
           {
             result4 +="\n\nIn the year " + argsCopy[3] + " the number of \"Low Winning numbers\" exceeded the number of \"High winning numbers\" " + countWinTimes + " time(s). \n";
           }                                
         }     
                      
	     /////////////////////////THIS EVALUATES 5 CLArgs\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	     if(argsCopy.length == 5)//Evaluates 5 CLargs to allow for checking start/end date.
            {
              while (in.hasNext())
                    {   
                     boolean isDateOk = false;   
         	  		 boolean isDateBetween = false;   
                     lineFromFile=in.nextLine();
                     /* we start reading here */
                      //String assignment dType referenced from last item on the line
                     if (lineFromFile.endsWith(dType))
                        {
                           lineFromFileCopy = lineFromFile.split(",");
                           isDateOk = DateToWeekDay(lineFromFileCopy[0], argsCopy[2]);
                           isDateBetween = checkDatesBetween(lineFromFileCopy[0], argsCopy[3], argsCopy[4]);
                           if((isDateOk == true) && (isDateBetween==true))//if true, carry out analysis of numbers. if not skip and continue while loop
                              {
                                for ( int i=1; i< bonusNumber; i++)
                                    {
                                     numbersOfList.add(Integer.parseInt(lineFromFileCopy[i]));		                                                 						                                
	                                }				                                   
                              }
	                    }
                    }
            }        
                      
                                 
                              
         in.close();
         int counter =1;
         String result = "";
         for ( int d=0; d< numbersOfList.size(); d++)
             {       	
               if(numbersOfList.get(d)%2==0)
                 {
                  evenCount++;
                  if(evenCount==bonusNumber)   
                    {
                      even++;
                      evenCount=0;
                    }	                                   
                 }
                else
	            evenCount=0;
                if(numbersOfList.get(d)%2!=0)
                  {
	               oddCount++;
	               if(oddCount==bonusNumber)   
   	                 {
                 	  odd++;
                      oddCount=0;
	                 }
    
                  }
              else
	          oddCount=0;                                		                                		
              counter ++;	  	                            	                                       
              if (counter == bonusNumber)
                 {		               		                                            
                    counter =0;	                                  	                                   
                 }	                                	                               
             } 
		                    //loop to report on frquencies of Numbers!
         for (int index = 0; index < lottoNumber45.length; index++)  
             {       						                       
           	   counter=0;  
               for (int index2 =0; index2 < numbersOfList.size(); index2 ++)
               	   {
               	   	 if (lottoNumber45[index] == numbersOfList.get(index2))
                       	 {
                           counter = (counter + 1);
                       	 }
                    	  
               	   }
               result +="The Number " + (index +1) +" occured " + counter + " times\n";                                          
             }
		       //   result +="The number of low winning numbers exceeded the number of high winning numbers " + lowCount +" times \n";				                      
           if(argsCopy.length==1)             
             {
              result +="\nThe frequency of winning numbers which are even is " + even + "\n"; 
              result +="The frequency of winning numbers which are odd is " + odd + "\n"; 				                       
             }
             System.out.println(result);
             System.out.println(result4);                        			                                                     	  	   	  
        
                   
        }
    }
                    
		public static int countLowMajorityWins( ArrayList<String> lineList, String year)                    
		{
			
		 int highestNum;
		 int yearNums = Integer.parseInt(year);
		 if (yearNums >=2006)
			highestNum = 45;
		 else if (yearNums>=1994)
			highestNum = 42; 
		 else if (yearNums>=1992)
			highestNum = 39;
		 else
		 	highestNum = 6;
		 int countMaj = 0;
	   	 int lowNumbers = 10;
		 int highNumbers = highestNum-10;
		 int i = 0;
		 String line;
		 String [] numbers;
		 while (i< lineList.size())
			   {				
				int countLow = 0;
				int countHigh = 0;
				line = lineList.get(i);
				numbers = line.split(",");
				for (int j = 0; j< numbers.length;j++)
					{
					 int num = Integer.parseInt(numbers[j]);
					 if(num<=lowNumbers)
					 countLow++;
					 else if(num >= highNumbers)
					 countHigh++;
					}
					if (countLow > countHigh)
						countMaj++;
					   
					i++;
			   }
		 return countMaj;
			
		}
		////////////////////////////////////////////	Zeller's Congruence Method  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
         
        public static boolean DateToWeekDay(String date, String daysRequired)
  		{
  	      String pattern = "[S|s]";//pattern for saturday
  		  String pattern2 = "[W|w]";
  		  String pattern3 = "[B|b]";
  		  boolean thingToReturn = false;
		  int a, b, d, m, y, dayOfWeek;
		  String [] newDate = date.split("/");//split the date into an array
		  d = Integer.parseInt(newDate[0]); //assign each date part to ann integer
		  m = Integer.parseInt(newDate[1]);
		  y = Integer.parseInt(newDate[2]);
	      if (m == 1 || m == 2)
		     {
  		      m += 12; y -=  1;
		     }
		  a = y % 100;  b = y / 100;
		  dayOfWeek = ((d + (((m + 1)*26)/10)+ a + (a/4) + (b/4)) + (5*b)) % 7;
		  //sets boolean depending on CLarg values entered by the user		
		  if ((dayOfWeek == 0) && (daysRequired.matches(pattern)))
		    thingToReturn = true;//sets true if date is a saturday, and user wants saturdays
		  if ((dayOfWeek == 4) && (daysRequired.matches(pattern2)))
		     thingToReturn = true;//sets true if date is a wednesday, and user wants swednesdays
		  if (((dayOfWeek == 0) || (dayOfWeek == 4)) && (daysRequired.matches(pattern3)))
		    thingToReturn = true;//sets true user wants all dates validated
	   		   		
		 return thingToReturn;  
		}	
  			
  			
  			
		//////////////////////////////////////////// Check if Date Is Between a Certain Range	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
         
         public static boolean checkDatesBetween(String dateFromFile, String startDate, String endDate) throws IOException, ParseException
  		 {	
	  		
	  		Date userStartDate, userEndDate, fileDate;
	 		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); 
	  		   	
	  		boolean isBetween= false;
									
			fileDate = (Date)dateFormatter.parse(dateFromFile);
			userStartDate = (Date)dateFormatter.parse(startDate);
			userEndDate = (Date)dateFormatter.parse(endDate);
			
			if((fileDate.compareTo(userStartDate) >= 0) && (fileDate.compareTo(userEndDate) <= 0))
			  {
				isBetween=true;
			  }
    		return isBetween;  
  			}	  			
  			   
          ////////////////////////////////////////  returnDrawTypeMethod   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        public static boolean [] getDrawtype(int commandLineArgument, String [] argsCopy,  String [] values)
        {    
	      /*This method evaluates commandline Arguments  and returns a boolean Array back to getmethod method!
	                   */
	      boolean [] bonusNumberdrawType = new boolean [12];
	      String pattern1 = "[0-9]{4}";
	      String pattern2 = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
          boolean year = false;
	      boolean startEndate =false;
	      boolean bonusNumber=false;
	      // boolean values! index BN=0 A=1, R=2, S=3, L1=4, L2=5 
	      // assign length of clArgs to int loopCon 

	       
	      if (commandLineArgument== 4)
	         {
	           bonusNumberdrawType[10] =true;
	           commandLineArgument = (commandLineArgument -1);
	         }
	                
	                 
		  if (commandLineArgument == 5)
		     {
		       bonusNumberdrawType[10] =true;
		       bonusNumberdrawType[11] =true;
		       commandLineArgument = (commandLineArgument -2);
			 }
			            
	      int j=0;
	      for ( int i =0; i< commandLineArgument; i++)
	          {// loops through the args array
	             for ( j=0; j< values.length; j++)
	               	 {
	               	    //loops through the String array
	                  if ( argsCopy[i].equals(values [j]) )
	                     {
	                    	/* see is arg [1] in String array"
	                    	if it is assign the value to true in
	                    	boolean array!*/
	                        bonusNumberdrawType[j]=true;						                           
	                     }
		                    
	                             
	               	    }
	               	    
	          }
	     /* now we have commandline Truth values!
	   	  we can evaluate these truth values int the 
	   	  get method METHOD but how without having to much 
	   	  if else statements?*/
	     return bonusNumberdrawType;
        }               
	     //////////////////////////////////////////  validateNumbers   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		public static boolean validateNumbers(int [] LottoArray)
		{	//create a new array to assign the int values to for sorting
			int [] LottoNumbers = new int [6];
			//loop to assign the first 6 values passed down to an empty aray
			for(int j = 0; j < LottoNumbers.length; j++)
				{
				  LottoNumbers[j] = LottoArray[j];
				}
			//sort the values in the local array, in ascending order.
		 	int pass, comparison, temp, max = 0, min;
			boolean sorted = false;
			for (pass = 1; pass <= LottoNumbers.length - 1 && !sorted; pass++)
			{
	  			sorted = true;
	  			for (comparison = 1; comparison <= LottoNumbers.length - pass; comparison++)
	  			{
		  			if (LottoNumbers[comparison - 1] > LottoNumbers[comparison])
	    			{
	      			temp = LottoNumbers[comparison];
	      			LottoNumbers[comparison] = LottoNumbers[comparison - 1];
	      			LottoNumbers[comparison - 1] = temp;
	      			sorted = false;
        			}  
		 		}
		 		
	 		}
	 		//this will assign the sorted values back to the original array in the first 6 positions, leaving the 
	 		//7th number (bonus number) alone
	 		for(int i = 0; i < LottoNumbers.length; i++)
		 		{
			 	  LottoArray[i] = LottoNumbers[i];
			 	}
		 	min = LottoArray[0];
		 	//selects max/min number entered by the user
		 	for(int i = 0; i < LottoArray.length; i++)
		 		{
			 	  if(LottoArray[i] > max)
			 	  max = LottoArray[i];
			 	  if(LottoArray[i] < min)
			 	  min = LottoArray[i];
			 	}
		 	//if the values which are ordered are in the valid range set sorted to true.
		 	if(max <= 45 && min >= 1)
		 	  sorted = true;
		 	//loop to check for duplicates
			for (int i = 0; i < LottoArray.length; i++)
			    {
				  for (int j = i + 1; j < LottoArray.length; j++)
				      {
					   if (LottoArray[i] == LottoArray[j])
					   sorted = false;
				      }
			   }
 		    return sorted;
 		}
 		 /////////////////////////////////////////check file exists method\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
 		public static boolean verifyfile() throws IOException
 		{
	 		boolean exists = false;
	 		File aFile = new File(filename);	
	 		if (aFile.exists())
	 		   exists = true;
	 		return exists;
 		}		
 		//////////////////////////////////////////Check lotto numbers enetered method\\\\\\\\\\\\\\\\\\\\\\\\\\\\
 		public static void checkWinningNumbers(int [] lottoArray) throws IOException, ParseException
 		{
	 		int [] sixNums = new int [6];
	 		int count, bonusNum = 0, bonusCount = 0, max = 0;
	 		int [] occasionWon = new int [7];
	 		int [] wonWithBonus = new int [7];
	 		boolean bonus = false, isGreater = false;
	 		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	 		String lottoDate = "16/04/1988", result = "";

        	//selects max/min number entered by the user
		 	for(int i = 0; i < lottoArray.length; i++)
		 		{
			 	 if(lottoArray[i] > max)
			 	 max = lottoArray[i];
			 	}
		 	//Dates below are the dates from which certain ranges of numbers can be analysed from.
		 	if (max >= 36)
		 	   lottoDate = "22/08/1992";
		 	if (max >= 39)
		 	   lottoDate = "24/09/1994";
		 	if(max >= 42)
		 	   lottoDate = "04/11/2006";
			//if statement checks if a bonus number was entered by a user or not	
	 		if(lottoArray.length == 7)
		 	  {
			 	bonusNum = lottoArray[6];
			 	bonus = true;
		 	  }
	 		FileReader aFileReader = new FileReader(filename);
	 		Scanner in = new Scanner(aFileReader);
	 		String aLineFromFile;
	 		while(in.hasNext())
	 		   {
		 		isGreater = false;
		 		count = 0;
		 		aLineFromFile = in.nextLine();
		 		String [] wordArray = aLineFromFile.split(",");	 		 		 		
		 		//assign the 6 winning lotto numers into an array for checking against user enetred numbers
		 		for(int i = 0; i < sixNums.length; i++)
		 		   {
			 		sixNums[i] = Integer.parseInt(wordArray[i + 1]);	
		 		   }
		 		//This if compares the date drawn from the file to the date from which to start analysing the numbers
	        	if ((df.parse(wordArray[0]).compareTo(df.parse(lottoDate)) >= 0)) 
        		   {
           		     isGreater = true;//if the date from file is greater than lottodate then 
        		   }
        		//if the date from file is greater than lottoDate then it allows the an analysis of the numbers on the particular itertion of the 
        		//loop
        		if(isGreater == true)
        		  {		 		
		 		   //if user did not enter a bonus number a nested for loop compares the numbers to check if any matches are found
		 		    if (bonus == false)
		 		       {
				 		 for(int i = 0; i < sixNums.length; i++)
				 		    {
					 		 for(int j = 0; j < sixNums.length; j++)
					 		   {
						 		 if(sixNums[i] == lottoArray[j])
						 		   {
					 			     count++;//if matches are found a counter is increased
				 				   }
					 		   }
					 	   }
						    occasionWon[count]++;//an array position based on that count is incremented by 1
					   }//if user did enter a bonus number a nested for loop compares the numbers to check if any matches are found
					if(bonus == true)
					  {
			 		    for(int i = 0; i < sixNums.length; i++)
			 		      {
				 		    for(int j = 0; j < sixNums.length; j++)
				 		       {
					 		     if(sixNums[i] == lottoArray[j])
							 		{
						 			count++;//if matches are found a counter is increased
					 				}
			 			       }
	 			 	       }
			 			 	occasionWon[count]++;//an array position based on that count is incremented by 1
			 			 	//an if statement checks if the bonus number is present for this iteration and if the counter is greater than 3.
			 		   if((count >= 3) && (bonusNum == Integer.parseInt(wordArray[7]))) //
			 			  {	
			 			    occasionWon[count]--;//it decreases the array which matches the 6 numbers by 1
							wonWithBonus[count]++;//it then increased a correspoding position in a second array if 3/4/5/6 numbers won with the bonus
					      }
	 			 					
			 		  }
	 	          }
 		
 			   }
		 	if(bonus == true)//Print out of any winning numbers with or without the bonus if the bonus was entered by a user
		 	  {
			 	result += ("\nAs max number entered is " + max + " only lotto numbers from " + lottoDate + "\ncould be analysed\n\n");
			    for(int i = 3; i < occasionWon.length; i++)
			 		{
					  if(occasionWon[i] > 0)
					  result += (occasionWon[i] + " times " + i + " winning numbers appeared\n");
				 	  if(wonWithBonus[i] > 0)
				 	  result += (wonWithBonus[i] + " times " + i + " winning numbers appeared with the bonus\n"); 
			 		}
		 	  }	
		 	if(bonus == false)//print out of any winning numbers if only 6 numbers entered by the user.
			  {
				result += ("\nThe max number entered is " + max + "\nlotto numbers from " + lottoDate + " onwards could be analysed\n\n");
			 	for(int i = 3; i < occasionWon.length; i++)
				 	{
					  if(occasionWon[i] > 0)
						{
					 	 result += (occasionWon[i] + " times " + i + " winning numbers appeared\n");
					 	}
			 		}
	 		in.close();
	 		aFileReader.close();
 			}
 			if(isGreater ==false)
 			result += ("\nNo values could be analysed as dates fall outside of valid range for the max number entered");
 			System.out.print(result);
 		}
}

 
		
