package kata;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MathEvaluator {
	public static void main(String[] args) {
		System.out.println(calculate("2 ^ 5"));
	}

	public static double calculate(String expression) {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
		Object result = null;
		try {
			result = scriptEngine.eval(expression);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		if (result instanceof Integer) {
			Integer res = (Integer) result;
			return res;
		}
		return (double) result;
	}

}