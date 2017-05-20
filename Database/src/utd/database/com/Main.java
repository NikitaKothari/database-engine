package utd.database.com;

import java.util.ArrayList;

public class Main {
	public Main() {
	}

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
		ArrayList<String> commandTokens = new ArrayList(java.util.Arrays.asList(userCommand.split(" ")));
		boolean isDatabasePresent = (utility.getSeletedDatabase() != null) && (!utility.getSeletedDatabase().isEmpty());
		boolean showErrorMessage = false;
		String str;
		switch ((str = (String) commandTokens.get(0)).hashCode()) {
		case -1352294148:
			if (str.equals("create")) {
			}
			break;
		case -1335458389:
			if (str.equals("delete")) {
			}
			break;
		case -1183792455:
			if (str.equals("insert")) {
			}
			break;
		case -906021636:
			if (str.equals("select")) {
			}
			break;
		case 116103:
			if (str.equals("use"))
				break;
			break;
		case 3092207:
			if (str.equals("drop")) {
			}
			break;
		case 3127582:
			if (str.equals("exit")) {
			}
			break;
		case 3198785:
			if (str.equals("help")) {
			}
			break;
		case 3482191:
			if (str.equals("quit")) {
			}
			break;
		case 3529469:
			if (str.equals("show")) {
			}
			break;
		case 351608024:
			if (str.equals("version")) {
			}
			break;
		case 1018214091:
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

										help.help();

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
		label678: System.out.println("Check syntax");

		label687: if (showErrorMessage) {
			System.out.println("Select Database");
		}
	}
}
