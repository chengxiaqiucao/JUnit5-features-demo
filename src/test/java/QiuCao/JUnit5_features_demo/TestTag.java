package qiucao.JUnit5_features_demo;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Tag("ClassTag")
class TestTag {

	@Test
	@Tag("tagDemo")
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	@Tag("test-tag")
	@Tag("tagDemo")
	void testTag1() {
		assertTrue(true);
	}
	
	@Test
	@Tag("test-tag")
	void testTag2() {
		assertTrue(true);
	}
	
	@Test
	@Tags({@Tag("tagDemo"),@Tag("test-tag")})
	void testTag3() {
		assertTrue(true);
	}	
	
	@Test
    @Tag("ShowInfo")
    void GetTagFromInfo(TestInfo info) {
        System.out.println(info.getTags());
    }
	
	@QiucaoTag
	void customTag(TestInfo info) {
        System.out.println(info.getTags());
    }
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("qiucao")
@Test
@interface QiucaoTag {}
