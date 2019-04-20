package alltest;

import model.Issue;
import model.Project;
import model.User;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Formatter;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestClassFromModel extends Assert {

    @Rule public final TemporaryFolder folder = new TemporaryFolder();

    private File logUser;
    private File logProject;
    private File logIssue;
    private User user;
    private Project project;
    private Issue issue;

    @Before
    public void setUp() throws IOException {
        File dir = folder.newFolder("bagtracker");
        dir.mkdir();
        logUser = new File(dir,"users.txt");
        logProject = new File(dir,"project.txt");
        logIssue = new File(dir,"issues.txt");
        user = new User("user");
        project = new Project("project");
        issue = new Issue("issue",project,user);
    }

    //проверяем корректность работы со временными файлами
    @Test
    public void test01() {

        assertEquals("users.txt",logUser.getName());
        assertEquals("bagtracker",logUser.getParentFile().getName());

        assertEquals("project.txt",logProject.getName());
        assertEquals("bagtracker",logProject.getParentFile().getName());

        assertEquals("issues.txt",logIssue.getName());
        assertEquals("bagtracker",logIssue.getParentFile().getName());
    }

    //тестируем конструктор и инициализацию переменных класса
    @Test
    public void test02(){

        assertNotNull(user);
        assertNotNull(user.getName());
        assertEquals(user.getName(),"user");

        assertNotNull(project);
        assertNotNull(project.getName());
        assertEquals(project.getName(),"project");

        assertNotNull(issue);
        assertNotNull(issue.getName());
        assertEquals(issue.getName(),"issue");
    }

    //тестируем writeToFile и exists
    @Test
    public void test03() throws IOException {
        PrintWriter fr = new PrintWriter(new FileWriter(logUser), true);
        fr.println(user.getName());
        assertEquals(Files.lines(logUser.toPath()).filter((n)->n.equals(user.getName())).count(),1);

        fr = new PrintWriter(new FileWriter(logProject,true), true);
        fr.println(project.getName());
        assertEquals(Files.lines(logProject.toPath()).filter((n)->n.equals(project.getName())).count(),1);

        fr = new PrintWriter(new FileWriter(logIssue,true), true);
        Formatter formatter = new Formatter();
        formatter.format("%s %s %s",issue.getName(), issue.getProject().getName(), issue.getUser().getName());
        fr.println(formatter);
        assertEquals(Files.lines(logIssue.toPath()).filter((n)->n.indexOf(issue.getName())!=-1).count(),1);
        fr.close();
    }

    @After
    public void ternDown(){
        logUser = null;
        logProject = null;
        logIssue = null;
        user = null;
        project = null;
        issue = null;
    }
}
