package fx.sunjoy.test;

import java.util.List;
import java.util.regex.Pattern;

import fx.sunjoy.utils.ReTool;

public class TestRe {
	public static void main(String[] args){
		List<List<String>> result = ReTool.getMatches(".\\d+.", "he45f", Pattern.DOTALL);
		System.out.println(result);
	}
}
