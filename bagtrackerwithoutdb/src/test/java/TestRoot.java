import alltest.TestClassBagTracker;
import alltest.TestClassFromModel;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestClassBagTracker.class,
        TestClassFromModel.class
})
public class TestRoot {
}
