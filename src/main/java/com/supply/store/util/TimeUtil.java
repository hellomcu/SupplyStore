package com.supply.store.util;

import java.sql.Timestamp;

public class TimeUtil
{
	public static Timestamp getCurrentTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static Timestamp toTimestamp(String time) throws Exception
	{
		return Timestamp.valueOf(time);
	}
}
