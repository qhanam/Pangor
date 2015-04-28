package ca.ubc.ece.salt.sdjsb.test.analysis;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ca.ubc.ece.salt.sdjsb.alert.Alert;
import ca.ubc.ece.salt.sdjsb.alert.CallbackErrorAlert;
import ca.ubc.ece.salt.sdjsb.analysis.callbackerror.CallbackErrorAnalysis;

public class TestCallbackError extends TestAnalysis {
	
	private void runTest(String[] args, List<Alert> expectedAlerts, boolean printAlerts) throws Exception {
		CallbackErrorAnalysis analysis = new CallbackErrorAnalysis();
		super.runTest(args, expectedAlerts, printAlerts, analysis);
	}

	@Test
	public void testCallbackErrorHandling() throws Exception {
		String src = "./test/input/callback_error/cbe_old.js";
		String dst = "./test/input/callback_error/cbe_new.js";
		List<Alert> expectedAlerts = new LinkedList<Alert>();
		expectedAlerts.add(new CallbackErrorAlert("CB", "donePrinting", "(err)", "err"));
		this.runTest(new String[] {src, dst}, expectedAlerts, true);
	}

	@Test
	public void testRealWorld() throws Exception {
		String src = "./test/input/callback_error/CLI_old.js";
		String dst = "./test/input/callback_error/CLI_new.js";
		List<Alert> expectedAlerts = new LinkedList<Alert>();
		expectedAlerts.add(new CallbackErrorAlert("CB", "[anonymous]", "(err,processes)", "err"));
		this.runTest(new String[] {src, dst}, expectedAlerts, true);
	}

	/**
	 * This test fails right now because it's a bit limited by AST
	 * differencing. When we implement CFG analysis we should be able to
	 * make this test pass.
	 */
	@Test
	public void testMultiCheck() throws Exception {
		String src = "./test/input/callback_error/cbe_multi_check_old.js";
		String dst = "./test/input/callback_error/cbe_multi_check_new.js";
		List<Alert> expectedAlerts = new LinkedList<Alert>();
		this.runTest(new String[] {src, dst}, expectedAlerts, true);
	}

}