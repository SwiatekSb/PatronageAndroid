package pl.ps.patronagetodo.database;

public class TaskDB {

	public final class TaskTable{
		public final class Column{
			public static final String ID = "_id";
			public static final String DESCRPTION = "decsription";
			public static final String DATE = "date";
			public static final String STATUS = "status";
		}
		
		public final class ColumnID{
			public static final int ID = 0;
			public static final int DESCRIPTION = 1;
			public static final int DATE = 2;
			public static final int STATUS = 3;	
		}
		
	}
}
