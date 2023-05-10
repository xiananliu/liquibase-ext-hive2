package liquibase.ext.metastore.sqlgenerator;

import liquibase.database.Database;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.DatabaseDataType;
import liquibase.ext.metastore.database.HiveMetastoreDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.RenameColumnGenerator;
import liquibase.statement.core.RenameColumnStatement;

public class MetastoreRenameColumnGenerator extends RenameColumnGenerator {

    @Override
    public boolean supports(RenameColumnStatement statement, Database database) {
        return database instanceof HiveMetastoreDatabase && super.supports(statement, database);
    }

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public Sql[] generateSql(RenameColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String alterTable = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());

        // add "MODIFY"
        alterTable += " " + "CHANGE COLUMN" + " ";

        // add column name
        String newColumnName = database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getNewColumnName());
        String oldColumnName = database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getOldColumnName());
        alterTable += oldColumnName+" "+newColumnName;

        alterTable += " "; // adds a space if nothing else

        // add column type
        DatabaseDataType newDataType = DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType(), database).toDatabaseDataType(database);

        alterTable += newDataType;

        return new Sql[] {
                new UnparsedSql(alterTable, getAffectedOldColumn(statement), getAffectedNewColumn(statement))
        };
    }
}
