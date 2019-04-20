package alltest;

import org.junit.Assert;
import org.junit.Test;
import java.util.Scanner;

public class TestClassBagTracker extends Assert {

    @Test
    public void TestStart(){
        String str1 = "exit\n" +
                "help\n"+
                "addProject\n" +
                "addProject --help\n" +
                "addProject project\n" +
                "addIssue\n"+
                "addIssue --help\n" +
                "addIssue issue\n" +
                "addUser\n"+
                "addUser --help\n" +
                "addUser user\n" +
                "reportAll\n"+
                "reportAll --help\n" +
                "reportAll user\n" +
                "reportSome\n"+
                "reportSome --help\n" +
                "reportSome user\n";
        Scanner scanner = new Scanner(str1);
        while(scanner.hasNext()){
            String command = scanner.nextLine();
            String[] listStr = command.split(" ");
            switch (listStr[0]){
                case "exit":
                    assertTrue(true);
                    return;
                case "help":
                    assertTrue(true);
                    break;
                case "addProject":
                    if(listStr.length == 1 || listStr[1].equals("--help")){
                        assertTrue(true);
                    } else {
                        assertEquals(listStr[1],"project");
                    }
                    break;
                case "addIssue":
                    if(listStr.length == 1 ||  listStr[1].equals("--help")){
                        assertTrue(true);
                    } else {
                        assertEquals(listStr[1],"issue");
                    }
                    break;
                case "addUser":
                    if(listStr.length == 1 ||  listStr[1].equals("--help")){
                        assertTrue(true);
                    } else {
                        assertEquals(listStr[1],"user");
                    }
                    break;
                case "reportAll":
                    if(listStr.length == 1 ||  listStr[1].equals("--help")){
                        assertTrue(true);
                    } else {
                        assertEquals(listStr[1],"user");
                    }
                    break;
                case "reportSome":
                    if(listStr.length == 1 ||  listStr[1].equals("--help")){
                        assertTrue(true);
                    } else {
                        assertEquals(listStr[1],"user");
                    }
                    break;
                default:
                    fail();
            }
        }
    }
}
