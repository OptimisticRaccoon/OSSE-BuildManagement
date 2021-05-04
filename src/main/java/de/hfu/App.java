package de.hfu;

/**
 * Hello world!
 *
 */
public class App 
{
	/**
	 * Converts console Input to uppercase and prints it out.
	 * Method returns after output and exits the program.
	 * 
	 * @param args for future use
	 */
    public static void main( String[] args )
    {
		String input = System.console().readLine();
		System.out.println(input.toUpperCase());
    }
}
