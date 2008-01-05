package fx.sunjoy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReTool {
	public static List<List<String>> getMatches(String pattern, String text , int flag){
		Pattern pt = Pattern.compile(pattern,flag);
		Matcher mc = pt.matcher(text);
		List<List<String>> result = new ArrayList<List<String>>();
		while(mc.find()){
			List<String> oneMC = new ArrayList<String>();
			for(int i=0; i<mc.groupCount()+1; i++){
				oneMC.add(mc.group(i));
			}
			result.add(oneMC);
		}
		return result;
	}
	
	public static List<Integer[]> getMatchLocation(String pattern, String text, int flag){
		Pattern pt = Pattern.compile(pattern,flag);
		Matcher mc = pt.matcher(text);
		List<Integer[]> result = new ArrayList<Integer[]>();
		while(mc.find()){
			Integer[] location = new Integer[2];
			location[0] = mc.start();
			location[1] = mc.end() - mc.start();
			result.add(location);
		}
		return result;
	}
}

