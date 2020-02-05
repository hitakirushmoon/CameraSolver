package yeet;

import java.util.Arrays;
import java.util.Base64;

public class Base64Tests {
	public static void main(String[] args) {
		byte[] arr = Base64.getDecoder().decode("gf/OYeRRrZ//ZVesvY/kwRnVLPxbqmpqyFQA666Q0HX+fZH4HCdU/FFwlnmpXWYwRx6JK6o7uC6HrOK3Xi7Uuy6YCZsPtjCp+moZniAxWghDgWqY57hIH4bCnf/gVIgE0EjhAnWY+w7nZEdXSEDiliUU3THhVmjnHD93kSWxqWrVLMI77IUyuax0htCs4UjLRABPI4AkK9LYTLAXG7KVwIHFy6M0n2bPwNvOmGZId43K/JNi+8hlI5pf5gtpa6PC");
		System.out.println(Arrays.toString(arr));
		System.out.println(new String(arr));
	}
}
