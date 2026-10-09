package guiAddUpdateLesson;

public class AddUpdateLessonTestingAutomation {

/*******
 * <p> Title: AddUpdateLessonTestingAutomation Class. </p>
 * 
 * <p> Description: A Java demonstration for semi-automated tests </p>
 * 
 * <p> Copyright: Alan G. Shimp © 2026 </p>
 * 
 * @author Alan G. Shimp
 * 
 * @version 1.00	2026-10-05 A set of semi-automated test cases
 * 
 */	
	static int numPassed = 0;	// Counter of the number of passed tests
	static int numFailed = 0;	// Counter of the number of failed tests
	
	private static String shortTitle = "abc";
	
	private static String goodTitle1 = "Looking to form a group (preferably able to meet in " +
			"evenings Pacific time)";
	
	private static String goodTitle2 = "HW2 Testing Clarification";
	
	private static String goodTitle3 = "UPDATED: " + goodTitle1;
	
	private static String goodTitle4 = "UPDATED: " + goodTitle2;
	
	private static String longTitle = "The Life and Strange Surprizing Adventures of Robinson "
			+ "Crusoe, of York, Mariner: Who lived Eight and Twenty Years, all alone in an "
			+ "un-inhabited Island on the Coast of America, near the Mouth of the Great River of "
			+ "Oroonoque; Having been cast on Shore by Shipwreck, wherein all the Men perished but "
			+ "himself. With An Account how he was at last as strangely deliver'd by Pyrates. "
			+ "Written by Himself.";
	
	private static String shortInfo = "abc";
	
	private static String goodInfo1 = "Dear Professor Carter,\r\n"
			+ "I’m a student in your CSE 360 Online course, and I’m reaching out because I just "
			+ "noticed an issue with my Team Project group assignment in Canvas.\n"
			+ "Canvas has been showing me in “Proposed Team Project Group 11”, and because of "
			+ "that, I have been communicating and working with the students in that group. My "
			+ "teammates and I believed that I was part of their team based on what Canvas "
			+ "displayed.\n"
			+ "However, I just noticed that Canvas also lists me under “Online Project Team 17,” "
			+ "which appears to be a different group. I was not aware of this discrepancy until "
			+ "today, and Group 11 has already been working together with the understanding that "
			+ "I was a member.\n"
			+ "Could you please let me know what I should do? If possible, I would really "
			+ "appreciate being allowed to remain with Proposed Team Project Group 11 since I "
			+ "have already been working with them and they have been including me as part of the "
			+ "team.\n"
			+ "I apologize for contacting you on short notice. I wanted to bring this to your "
			+ "attention as soon as I discovered it so that I can resolve it immediately and "
			+ "avoid causing problems for either group.\n"
			+ "Thank you very much for your help.\n"
			+ "Best,\n"
			+ "Aditya Dubey\n"
			+ "CSE 360 Online – Fall 2026";
	
	private static String goodInfo2 = "I just wanted to get some clarification regarding the "
			+ "testing portion of the HW2. Are we required to implement code for testing using "
			+ "JUnit or a class like that of the testbeds in the floating point recognizer and "
			+ "email address validator or are we suppose to just show where our code validates "
			+ "the tests that we wrote in our test case document? For example like if we were "
			+ "checking input length of a lesson field using and if statement to make sure it is "
			+ "not blank would we have to just show in our code the if statement we used and "
			+ "discuss the result of that statement or would we have to implement a testbed for "
			+ "each of these situations and then use that in our screen case and such?";
	
	private static String goodInfo3 = "UPDATED: " + goodInfo1;
	
	private static String goodInfo4 = "UPDATED: " + goodInfo2;
	
	private static String longInfo = goodInfo1 + goodInfo1;

	/*
	 * This mainline displays a header to the console, performs a sequence of
	 * test cases, and then displays a footer with a summary of the results
	 */
	public static void main(String[] args) {
		/************** Test cases semi-automation report header **************/
		System.out.println("______________________________________");
		System.out.println("\nTesting Automation");

		/************** Start of the test cases **************/
		
		// POSITIVE
		performTestCase(1, goodTitle1, goodInfo1, true, true);
		performTestCase(2, goodTitle2, goodInfo2, true, true);
		performTestCase(3, goodTitle3, goodInfo3, true, true);
		performTestCase(4, goodTitle4, goodInfo4, true, true);
		
		// NEGATIVE
		performTestCase(5, shortTitle, goodInfo1, false, true);
		performTestCase(6, longTitle, goodInfo1, false, true);
		performTestCase(7, goodTitle1, shortInfo, true, false);
		performTestCase(8, goodTitle1, longInfo, true, false);
		
		/************** End of the test cases **************/
		
		/************** Test cases semi-automation report footer **************/
		System.out.println("____________________________________________________________________________");
		System.out.println();
		System.out.println("Number of tests passed: "+ numPassed);
		System.out.println("Number of tests failed: "+ numFailed);
	}
	
	/*
	 * This private method sets up the input value for the test from the input parameters, displays
	 * the test execution information, invokes precisely the same recognizer that the interactive
	 * JavaFX GUI mainline uses, interprets the returned value, displays the interpreted result to
	 * the console.
	 */
	private static void performTestCase(int testCase, String inputTitle, String inputInfo,
			boolean expectedPassTitle, boolean expectedPassInfo) {
				
		// Display an individual test case header
		System.out.println(
				"____________________________________________________________________________" +
				"\n\nTest case: " + testCase);
		System.out.println("Title: \"" + inputTitle + "\"");
		System.out.println("______________");
		System.out.println("\nCore Information: \"" + inputInfo + "\"");
		System.out.println("______________");
		System.out.println("\nFinite state machine execution trace:");
		
		// Call the validator functions to process the input
		boolean titleGood = ControllerAddUpdateLesson.validateTitle(inputTitle);
		boolean infoGood = ControllerAddUpdateLesson.validateCoreInfo(inputInfo);
		boolean titleSuccess = ((titleGood && expectedPassTitle) || (!titleGood && !expectedPassTitle));
		boolean infoSuccess = ((infoGood && expectedPassInfo) || (!infoGood && !expectedPassInfo));
		boolean overallSuccess = (titleSuccess && infoSuccess);
						
		// Interpret the result and display that interpreted information
		System.out.println();
		
		// Report the success or failure
		if (overallSuccess) {
			System.out.println("***Success***\n");
			numPassed++;
		}
		else {
			System.out.println("***Failure***\n");
			numFailed++;
		}
		
		// Report whether the title is valid and if not, why not.
		if (titleGood) {
			System.out.println("The title is valid, and ");
		}
		else if (inputTitle.length() < 4) {
			System.out.println("The title is invalid because it is too short, and ");
		}
		else {
			System.out.println("The title is invalid because it is too long, and ");
		}
		
		// Report the expected result.
		if (expectedPassTitle) {
			System.out.println("it was expected to be valid.\n");
		}
		else {
			System.out.println("it was expected to be invalid.\n");
		}
		
		// Report whether the core information is valid and if not, why not.
		if (infoGood) {
			System.out.println("The core information is valid, and ");
		}
		else if (inputInfo.length() < 4) {
			System.out.println("The core information is invalid because it is too short, and ");
		}
		else {
			System.out.println("The core information is invalid because it is too long, and ");
		}
		
		//Report the expected result.
		if (expectedPassInfo) {
			System.out.println("it was expected to be valid.\n");
		}
		else {
			System.out.println("it was expected to be invalid.\n");
		}
	}
}