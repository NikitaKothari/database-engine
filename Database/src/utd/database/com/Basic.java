package utd.database.com;

import java.io.RandomAccessFile;

/**
 * @author nikitakothari Basic functionalities
 */
public class Basic {

	Utility utility = Utility.getInstance();

	public void showTables(String userCommand) {
		try {
			RandomAccessFile database = new RandomAccessFile(utility.getSeletedDatabase() + ".tables.tbl", "rw");
			boolean isRecordPresent = false;
			while (database.getFilePointer() < database.length()) {
				int isDeleted = database.readByte();
				byte length = database.readByte();
				byte[] bytes = new byte[length];
				database.read(bytes, 0, bytes.length);
				if (isDeleted == 0) {
					isRecordPresent = true;
					System.out.println(new String(bytes));
				}
				database.readInt();
			}
			if (!isRecordPresent)
				System.out.println("No table is present for " + utility.getSeletedDatabase() + " database");
			database.close();
		} catch (Exception e) {
			System.out.println("Error, while fetching values from database");
		}
	}

	public void describeTable(String userCommand) {
		try {
			String[] userCommandTokens = userCommand.trim().split(" ");
			java.util.List<Column> columns = utility.getColumns(userCommandTokens[1].trim());
			for (Column column : columns) {
				System.out.print(column.getColumnName());
				System.out.print(" " + column.getDataType());
				if (column.isNotNullable()) {
					System.out.println("Not null");
				}
				if (column.isPrimary()) {
					System.out.println("Primary key");
				}
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Error, while fetching values from database");
		}
	}

	public void showDatabases(String userCommand) {
		try {
			java.io.File file = new java.io.File("all.databases.tbl");
			if ((file.exists()) && (!file.isDirectory())) {
				RandomAccessFile databases = new RandomAccessFile("all.databases.tbl", "rw");
				boolean isRecordPresent = false;
				while (databases.getFilePointer() < databases.length()) {
					int isDeleted = databases.readByte();
					byte length = databases.readByte();
					byte[] bytes = new byte[length];
					databases.read(bytes, 0, bytes.length);
					if (isDeleted == 0) {
						System.out.println(new String(bytes));
						isRecordPresent = true;
					}
				}
				if (!isRecordPresent)
					System.out.println("No databasse is present in the system");
				databases.close();
			} else {
				System.out.println("No database is present");
			}
		} catch (Exception e) {
			System.out.println("Error, while fetching values from database");
		}
	}

	public void useDatabase(String userCommand) {
		try {
			String[] userCommandTokens = userCommand.trim().split(" ");
			String databaseName = userCommandTokens[1].trim();
			RandomAccessFile databases = new RandomAccessFile("all.databases.tbl", "rw");
			boolean isDatabasePresent = false;
			while (databases.getFilePointer() < databases.length()) {
				int isDeleted = databases.readByte();
				byte length = databases.readByte();
				byte[] bytes = new byte[length];
				databases.read(bytes, 0, bytes.length);
				if ((databaseName.equals(new String(bytes).trim())) && (isDeleted == 0)) {
					utility.setSeletedDatabase(databaseName);
					System.out.println("Switched to database " + databaseName);
					isDatabasePresent = true;
				}
			}
			databases.close();
			if (!isDatabasePresent)
				System.out.println("Database " + databaseName + " is not present in the system");
		} catch (Exception e) {
			System.out.println("Error, while fetching values from database");
		}
	}
}
