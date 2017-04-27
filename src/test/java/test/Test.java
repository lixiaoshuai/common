package test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	
	public static void main(String[] args) {
		
		String s =" ";
		String b = null;
		
		for (int i = 0; i < 10; i++) {
			if (s.trim()==null||"".equals(s.trim())) {
				System.out.println("  1");
				continue;
			}
		}
	}

}
