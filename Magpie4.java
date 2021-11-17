/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
import java.util.Scanner;

public class Magpie4
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */
  int confusion = 0;
  int excitement = 0;
  public static void main(String[] args) {

    Magpie4 magpie = new Magpie4();
    Scanner scan = new Scanner(System.in);
    String statement = "";
    

    System.out.println("Dynamic Simulations is working");
    System.out.println(magpie.getGreeting());
    while (statement != "conversation will never end"){
        statement = scan.nextLine();
        System.out.println(magpie.getResponse(statement));
    }

    }	
	public String getGreeting()
	{
		return "Hello Human, how do you do? I am designed for interaction on Dynamic Simulations";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (statement.length() == 0)
		  {
			response = "Cat got your tounge?";
		  }

    else if (findKeyword(statement, "name") >=0)
    {
      if (findKeyword(statement, "name?")>=0){
        response = "I am Dynamic Simulations Hypeman! I get you excited about Dynamic Simulations.";
      }
      else
      {
        response = "What an adorable name you have";
      }
    }
    
    else if (findKeyword(statement, "?") >= 0
				  || findKeyword(statement, "confused") >= 0
				  || findKeyword(statement, "learning") >= 0
          || findKeyword(statement, "what") >= 0)
      {
        if(confusion == 3){
          response = "If you want to get into this field, you should study mathematics(differential equations) and a strong understanding of the modeled phenomena, be it a chemical reaction or a mechanical device.";
          
        }
        if(confusion ==2){
          response = "People in the field use programs like: \nAnylogic\nMATLAB\nSimScale\nSimul8\nAnd many more!";
          confusion++;
        }
        if(confusion ==1){
          response = "Using math, computer scientists are able to portray real-world phenomena";
          confusion++;
        }
        if(confusion==0){
          response = "Dynamic simulations use computer programming to simulate real world phenomena,from the flaps of a cloak to the motion of a piston or even chemical activity.";
          confusion++;
        }

      }
    else if(findKeyword(statement,"Forget it")>=0){
      confusion = 0;
      response = "Alright";
    }

		else if (findKeyword(statement, "no") >= 0 || findKeyword(statement,"can't")>=0||findKeyword(statement, "impossible")>=0)
		{
			response = "Don't worry about it!";
		}
   
    else if (findKeyword(statement, "cool") >= 0
				|| findKeyword(statement, "interesting") >= 0
				|| findKeyword(statement, "wow") >= 0
				|| findKeyword(statement, "amazing") >= 0
        || findKeyword(statement, "incredible") >= 0)
		  {
      excitement = findKeyword(statement,"cool")+findKeyword(statement,"interesting")+findKeyword(statement,"wow")+findKeyword(statement,"amazing")+findKeyword(statement,"incredible");
      System.out.println(excitement);
      if (excitement>=63){
			response = "If you think that's cool, I can direct you to some resources: https://web.mit.edu/yoric-locker/BartonDynamicSimNotes.pdf";
      }
      else if(excitement>=33){
        response = "Pretty neat, right? Check this out:https://www.academia.edu/Documents/in/Dynamic_Simulation";
      }
      else if(excitement>=19){
        response = "If you like that, this is sure to get your loins a-boilin':https://www.digitalrefining.com/article/1000649/dynamic-simulation-a-tool-for-engineering-problems";
      }
      else if(excitement>=11){
        response="Surely you will love this:https://www.youtube.com/playlist?list=PLujxSBD-JXgnnd16wIjedAcvfQcLw0IJI";
      }
      else if(excitement>=1){
        response = "I bet an enthusiast like you would get a kick from this: https://en.wikipedia.org/wiki/Dynamic_simulation";
      }
      else if(excitement<0){
        response="Good on you, bub. Glad for the enthusiasm";
      }
      
      }
		
    //Hello Anish, are you active?
		
		// Responses which require transformations
		else if (findKeyword(statement, "How to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

		else
		{
			response = getRandomResponse();
		}
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("?"))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "How to", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "You can consult the internet to " + restOfStatement+".";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "Why would it ever cross your mind that I" + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 10;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		if (whichResponse == 0)
		{
			response = "Can we just stay on topic?";
		}
		else if (whichResponse == 1)
		{
			response = "Try elaborating on that";
		}
		else if (whichResponse == 2)
		{
			response = "I don't think so";
		}
		else if (whichResponse == 3)
		{
			response = "That's quite foolish to say";
		}
    else if (whichResponse == 4)
		{
			response = "Interesting take";
		}
    else if (whichResponse == 5)
		{
			response = "What do you mean by that";
		}
    else if (whichResponse == 6)
		{
			response = "I don't understand, Sorry";
		}
    else if (whichResponse == 7)
		{
			response = "Really?";
		}
    else if (whichResponse == 8)
		{
			response = "What in the world";
		}
    else if (whichResponse == 9)
		{
			response = "That's nice to hear";
		}
    return response;
	}

}
