package com.jdam.logic;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * 
 * @author Andre Bernecker und Martin Solderer
 * Test für den URLParser
 *
 */
public class JTestsURLParser {

	@Test
	public void test_input() {
		UrlParser up = new UrlParser();		
		assertTrue(!up.checkInput("$from=innsbruck&to=Wien"));
	}
	
	@Test
	public void test_input2() {
		UrlParser up = new UrlParser();		
		assertTrue(!up.checkInput("str$from=&to=Wien"));
	}
	
	@Test
	public void test_input3() {
		UrlParser up = new UrlParser();		
		assertTrue(!up.checkInput("latlng$from=dfj&to=sdf"));
	}
	
	@Test
	public void test_input4() {
		UrlParser up = new UrlParser();		
		assertTrue(up.checkInput("latlng$from=342.34,234.453&to=2323.2354356345435,2.0000000"));
	}
	
	@Test
	public void test_input5() {
		UrlParser up = new UrlParser();		
		assertTrue(up.checkInput("str$from=Innsbruck&to=Wien"));
	}
	
	@Test
	public void test_input6() {
		UrlParser up = new UrlParser();		
		assertTrue(up.checkInput("latlngstr$from=22.3,323.3&to=Wien"));
	}
	
	@Test
	public void test_input7() {
		UrlParser up = new UrlParser();
		String expected = "innsbruck";
		assertEquals(expected, up.parserFrom("str$from=innsbruck&to=wien"));
	}
	
	@Test
	public void test_input8() {
		UrlParser up = new UrlParser();
		String expected = "Wien";
		assertEquals(expected, up.parserTo("str$from=Innsbruck&to=Wien"));
	}
	
	@Test
	public void test_input9() {
		UrlParser up = new UrlParser();
		String expected = "from=+innsbruck+innstrasse";
		assertEquals(expected, up.delSpace("from= innsbruck innstrasse"));
	}

}
