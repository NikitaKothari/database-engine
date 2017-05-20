package utd.database.com;

import java.util.ArrayList;

/**
 * 
 * @author nikitakothari
 *
 *         Main Class
 */

public class Main {

	public static boolean isExit = false;
	public static java.util.Scanner scanner = new java.util.Scanner(System.in).useDelimiter(";");

	static Utility utility = Utility.getInstance();
	static Help help = new Help();
	static Select select = new Select();
	static SelectWhere selectWhere = new SelectWhere();
	static Create create = new Create();
	static Basic basic = new Basic();
	static Insert insert = new Insert();
	static Drop drop = new Drop();
	static Delete delete = new Delete();

	public static void main(String[] args) {
		Utility.splashScreen();
		String userCommand = "";
		while (!isExit) {
			System.out.print(utility.getPromt());
			userCommand = scanner.next().replace("\n", "").replace("\r", "").trim().toLowerCase();
			parseUserCommand(userCommand);
		}
		System.out.println("Exiting...");
	}

	private static void parseUserCommand(String userCommand) {
		ArrayList<String> commandTokens = new ArrayList<String>(java.util.Arrays.asList(userCommand.split(" ")));
		boolean isDatabasePresent = (utility.getSeletedDatabase() != null) && (!utility.getSeletedDatabase().isEmpty());
		boolean showErrorMessage = false;
		String str = commandTokens.get(0);
		switch (str) {
		case "create":
			if (commandTokens.get(1).equals("table"))
				create.table(userCommand);
			else
				create.database(userCommand);
			break;

		case "delete":
			delete.delete(userCommand);
			break;

		case "insert":
			insert.insertRecord(userCommand);
			break;

		case "select":
			if (userCommand.contains("where"))
				selectWhere.selectWhere(userCommand);
			else
				select.select(userCommand);
			break;

		case "use":
			utility.setSeletedDatabase(commandTokens.get(1));
			break;
		case "drop":
			if (commandTokens.get(1).equals("table"))
				drop.table(userCommand);
			else
				drop.database(userCommand);
			break;

		case "exit":
			System.exit(0);
			break;

		case "help":
			help.get();
			break;
		case "quit":
			System.exit(0);
			break;

		case "show":
			if (commandTokens.get(1).equals("table"))
				basic.showTables(userCommand);
			else
				basic.showDatabases(userCommand);
			break;

		case "version":
			System.out.println(utility.getVersion());
			break;

		case "describe":
			if (!str.equals("describe")) {
				if (commandTokens.size() != 2)
					basic.useDatabase(userCommand);

			} else if (isDatabasePresent) {
				basic.describeTable(userCommand);
			} else {
				showErrorMessage = true;

				if (((String) commandTokens.get(1)).equals("tables")) {
					basic.showTables(userCommand);
				} else if ((isDatabasePresent) && (((String) commandTokens.get(1)).equals("databases"))) {
					basic.showDatabases(userCommand);
				} else {
					showErrorMessage = true;

					if ((isDatabasePresent) && (((String) commandTokens.get(1)).equals("table"))) {
						create.table(userCommand);
					} else if (((String) commandTokens.get(1)).equals("database")) {
						create.database(userCommand);
					} else {
						showErrorMessage = true;

						if ((isDatabasePresent) && (userCommand.contains("where"))) {
							selectWhere.selectWhere(userCommand);
						} else if (isDatabasePresent) {
							select.select(userCommand);
						} else {
							showErrorMessage = true;

							if (isDatabasePresent) {
								delete.delete(userCommand);
							} else {
								showErrorMessage = true;

								if (((String) commandTokens.get(1)).equals("database")) {
									drop.database(userCommand);
								} else if ((isDatabasePresent) && (((String) commandTokens.get(1)).equals("table"))) {
									drop.table(userCommand);
								} else {
									showErrorMessage = true;

									if (isDatabasePresent) {
										insert.insertRecord(userCommand);
									} else {
										showErrorMessage = true;
										help.get();
										System.out.println("DavisBaseLite Version " + utility.getVersion());
										isExit = true;
										isExit = true;
									}
								}
							}
						}
					}
				}
			}
			break;
		}
	}
}
