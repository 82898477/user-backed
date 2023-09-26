package com.example.userbacked;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserBackedApplicationTests {
	@Test
		public  void encryptPassword() {
		String password = "X123";
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				byte[] hashBytes = messageDigest.digest(password.getBytes());
				StringBuilder stringBuilder = new StringBuilder();
				for (byte b : hashBytes) {
					stringBuilder.append(String.format("%02x", b));
				}
				System.out.println(stringBuilder);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}
