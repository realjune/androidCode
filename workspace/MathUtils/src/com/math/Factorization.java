package com.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Factorization {
	public void division(int input) {
		for (int i = 2; i <= input / 2; i++) {
			if (input % i == 0) {
				System.out.print(i + "*");
				division(input / i);
			}
		}
		System.out.print(input);
		System.exit(0);// ����û����䣬�������ᱨ��
	}

	public static void main(String[] args) {
		Factorization f = new Factorization();
		String s = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			s = in.readLine();
		} catch (IOException e) {
		}
		int input = Integer.parseInt(s);
		System.out.print(input + "�ķֽ�������Ϊ��" + input + "=");
		f.division(input);
	}
}
