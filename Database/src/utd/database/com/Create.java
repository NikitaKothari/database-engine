package utd.database.com;

import java.io.RandomAccessFile;

/**
 * 
 * @author nikitakothari
 *
 *         Create table and database
 */

public class Create {

	Utility utility = Utility.getInstance();

	public String[] getTokens(String userCommand) {
		userCommand = userCommand.replace('(', '#').replace(',', '#').replace(')', ' ').trim();
		return userCommand.split("#");
	}

	public void table(String userCommand) {
		try {
			String database = utility.getSeletedDatabase();
			String[] tokens = getTokens(userCommand);
			String tableName = tokens[0].trim().split(" ")[2];
			if (!utility.isTablePresent(tableName, false)) {
				tokens[0] = "rowid int";
				RandomAccessFile tables = new RandomAccessFile(database + ".tables.tbl", "rw");
				tables.seek(tables.length());
				tables.writeByte(0);
				tables.writeByte(tableName.length());
				tables.writeBytes(tableName);
				tables.writeInt(0);
				tables.close();

				RandomAccessFile columns = new RandomAccessFile(database + ".columns.tbl", "rw");
				columns.seek(columns.length());
				for (String token : tokens) {
					token = token.trim();
					if ((token != null) && (!token.isEmpty())) {
						columns.writeByte(0);
						if (token.contains("primary key")) {
							token = token.replace("primary key", "primarykey");
						}
						if (token.contains("not nullable")) {
							token = token.replace("not nullable", "notnullable");
						}
						String columnDefination = tableName + "#"
								+ token.replaceAll("  ", " ").replaceAll(" ", "#").trim();
						columns.writeByte(columnDefination.length());
						columns.writeBytes(columnDefination);
					}
				}
				columns.close();
				RandomAccessFile table = new RandomAccessFile(database + "." + tableName + ".tbl", "rw");
				table.close();
				System.out.println("Record is created Successfully");
			} else {
				System.out.println("Table is already created");
			}
		} catch (Exception e) {
			System.out.println("Error, while Creating a table");
		}
	}

	public void database(String userCommand) {
		String[] userCommandTokens = userCommand.trim().split(" ");
		try {
			String schemaname = userCommandTokens[2];
			java.io.File file = new java.io.File(schemaname + ".tables.tbl");
			if ((file.exists()) && (!file.isDirectory())) {
				System.out.println("Database " + schemaname + " is already present");
			} else if ((schemaname != null) && (!schemaname.isEmpty())) {
				RandomAccessFile database = new RandomAccessFile("all.databases.tbl", "rw");
				database.seek(database.length());
				database.writeByte(0);
				database.writeByte(schemaname.length());
				database.writeBytes(schemaname);
				database.close();
				database = new RandomAccessFile(schemaname + ".tables.tbl", "rw");
				database.close();
				database = new RandomAccessFile(schemaname + ".columns.tbl", "rw");
				database.close();
				System.out.println("Database is created Successfully");
			}
		} catch (Exception e) {
			System.out.println("Error, while Creating a Database");
		}
	}
}
