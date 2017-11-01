package com.supply.store;

import org.junit.Test;

import com.supply.store.util.Md5;

import junit.framework.TestCase;

public class MyTest extends TestCase
{

	@Test
	public void testMd5()
	{
		System.out.println(Md5.getMD5Str("admin123456"));
	}
}
