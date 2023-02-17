import java.rmi.server.RemoteServer;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Main {

	public static void main(String a[]) {
		RConnection connection = null;

		try {
			/*
			 * Create a connection to Rserve instance running on default port 6311
			 */
			// connection = new RConnection("127.0.0.1", 6311);
			connection = new RConnection("192.46.239.178", 6311);

			String vector = "c(1,2,3,4)";
			connection.eval("sumVal=sum(" + vector + ")");

			connection.eval("pdf(file=\"/home/histogram1.pdf\")");
			connection.eval("hist(airquality$Temp)");
			connection.eval("dev.off()");

			connection.eval("library(\"Cairo\")");
			connection.eval(
					"Cairo(file=\"/home/testfile2.jpg\",type=\"png\",bg=\"white\",units=\"px\", width=400, height=300, pointsize=12, dpi=\"auto\")");
			connection.eval("hist(airquality$Temp)");
			connection.eval("dev.off()");

			
			double mean = connection.eval("sumVal").asDouble();
			System.out.println("The sum is=" + mean);
			String nm[] = connection.eval("rnorm(100,0,1)").asStrings();

			for (short t = 0; t < nm.length; t++) {
				System.out.println(t + " = " + nm[t]);
			}

			connection.eval("source(\"/home/graph.r\")");
			REXP is_aba_palindrome = connection.eval("palindrome(20)");
			System.out.println("resultat script: " + is_aba_palindrome.asInteger());

			connection.eval("source(\"/home/graph2.r\")");
		 
		} catch (RserveException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

}
