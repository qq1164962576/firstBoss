package com.ithiema.utils;

import org.springframework.util.DigestUtils;

public class MD5Util {
	public static String MD5_tops(String str){
		for(int i=0;	i<10;	i++){
			str =DigestUtils.md5DigestAsHex(str.getBytes());
		}
		return str;
	}
}
