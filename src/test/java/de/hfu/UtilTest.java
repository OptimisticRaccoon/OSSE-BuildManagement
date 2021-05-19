package de.hfu;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {

	@Test
	public void test() {
		for(int i = 1; i < 12; i++) {
			if(i < 7)
				assertTrue(Util.istErstesHalbjahr(i));
			if(i > 7)
				assertFalse(Util.istErstesHalbjahr(i));
		}
	}

}
