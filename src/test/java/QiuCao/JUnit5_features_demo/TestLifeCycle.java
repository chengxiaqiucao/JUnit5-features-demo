package QiuCao.JUnit5_features_demo;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TestLifeCycle {

    @BeforeAll
    static void initAll() {
    	System.out.println("*******BeforeAll********");
    }

    @BeforeEach
    void init() {
    	System.out.println("------BeforeEach-------");
    }

    @Test
    void succeedingTest() {
    	System.out.println("成功执行测试！");
    }

    @Test
    void failingTest() {
    	System.out.println("测试执行失败！");
        fail("a failing test");
    }

    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }

    @Test
    void abortedTest() {
        System.out.println("退出执行！");
        assumeTrue("abc".contains("Z"));
        fail("test should have been aborted");
    }

    @AfterEach
    void tearDown() {
    	System.out.println("------AfterEach-------");
    }

    @AfterAll
    static void tearDownAll() {
    	System.out.println("*******AfterAll********");
    }

}
