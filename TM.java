import java.io.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;



public class TM {

	ArrayList<String> al = new ArrayList<String>();

	void Usage() {

		System.err.println("To use this program you must run it by one of the following:");
		System.err.println("TM Start <Task>");
		System.err.println("TM Stop <Task>");
		System.err.println("TM Describe <Task> <Description, Using Quotes>");
		System.err.println("TM Summary <Task>");
		System.err.println("TM Summary");

	}

	void appMain(String args[]) throws IOException {

	String cmd = "";
	String data = "";
	String desc = "";

	Log log = new Log();

	LocalDateTime timeRN = LocalDateTime.now();

	try {
		cmd = args[0];
		data = args[1];

		cmd = cmd.toUpperCase();

		if(cmd.equals("DESCRIBE")){

			desc = args[2];

		}
		else {
			desc = null;
		}
	}
	catch (ArrayIndexOutOfBoundsException err) {

		Usage();

	}

		switch(cmd){

			case "STOP": cmdStop(data, log, cmd, timeRN);
				break;
			case "START": cmdStart(data, log, cmd, timeRN);
				break;
			case "SUMMARY": cmdSummary(data, log, timeRN);
				break;
			case "DESCRIBE": cmdDescribe(data, log, cmd, timeRN, desc);
				break;
			}
		}



	void cmdStart(String data, Log log, String cmd, LocalDateTime timeRN) throws IOException{

		String line = (timeRN + " " + data + " " + cmd);
		log.writeLine(line);


	}

	void cmdStop(String data, Log log, String cmd, LocalDateTime timeRN) throws IOException{

		String line = (timeRN + " " + data + " " + cmd);
		log.writeLine(line);

	}


	void cmdSummary(String data, Log log, LocalDateTime timeRN) throws IOException{

		String line = (timeRN + " " + data + " Completed Summary");

		Scanner mine = null;
		File myfile = new File("TM.log");
		mine = new Scanner(myfile);
		while(mine.hasNext()) {

			String lineOfFile = mine.nextLine();
			if(lineOfFile.contains(data)) {

				if(lineOfFile.contains(" DESCRIBE ")) {

					System.out.println(lineOfFile);

				}

			}

		}
		log.writeLine(line);

		
		log.readLog();
		
	}


	void cmdDescribe(String data, Log log, String cmd, LocalDateTime timeRN, String desc) throws IOException {

		String line = (timeRN + " " + data + " " + cmd + " " + desc);
		log.writeLine(line);

	}


	public static void main(String[] args) throws IOException {

		new TM().appMain(args);

		/**System.out.println("Test Number 1");

		System.out.println("args[0] = " + args[0]);

		System.out.println("args[1] = " + args[1]);
		*/
	}

	class Log {

		public FileWriter fileWrite;
		public PrintWriter outputFile;

		void writeLine(String line) throws IOException{

			outputFile.println(line);
			outputFile.close();

		}
		
		
		
		void readLog() throws IOException {
			
			//Credit goes to Luca Davanzo + Athif Shaffy on StackOverfow.com
			FileInputStream fstream = new FileInputStream("TM.log");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine = "";
			StringTokenizer st = new StringTokenizer(strLine);

			int count = 0;
			while((strLine = br.readLine()) != null) {
				System.out.println(strLine);
				while(st.hasMoreTokens()) {
					
					al.add(st.nextToken());
					count++;
					
					System.out.println(al);
					
				}
			}

			fstream.close();
			
		}

		public Log() throws IOException {

			fileWrite = new FileWriter("TM.log", true);
			outputFile= new PrintWriter(fileWrite);

		}


	}



	class LogEntry{

		LocalDateTime timeRN;
		String command;
		String name;
		String data;
		
		public LogEntry(String taskLine) {

			StringTokenizer stok = new StringTokenizer(taskLine, " ");
			int nTokens = stok.countTokens();
			timeRN = LocalDateTime.parse(stok.nextToken());

		}

	}


	//Probably missing some LONG vars
	class TaskDuration {

		public LocalDateTime start, stop;
		long taskDuration(LocalDateTime start, LocalDateTime stop){
			this.start = start;

			return ChronoUnit.SECONDS.between(start,stop);

		}
	}

}


class TimeUtil {

	static String toElapsedTime(long totSecs) {

		long hours = totSecs/3600;
		long mins = (totSecs % 3600) / 60;
		long secs = (totSecs % 60);

		return (String.format("%02d:%02d:%02d", hours, mins, secs));

	}
}