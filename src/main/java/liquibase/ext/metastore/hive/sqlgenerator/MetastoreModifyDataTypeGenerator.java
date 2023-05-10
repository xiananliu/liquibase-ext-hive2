package liquibase.ext.metastore.hive.sqlgenerator;

import liquibase.database.Database;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.DatabaseDataType;
import liquibase.ext.metastore.database.HiveMetastoreDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.ModifyDataTypeGenerator;
import liquibase.statement.core.ModifyDataTypeStatement;

public class MetastoreModifyDataTypeGenerator extends ModifyDataTypeGenerator {

    @Override
    public boolean supports(ModifyDataTypeStatement statement, Database database) {
        return database instanceof HiveMetastoreDatabase && super.supports(statement, database);
    }

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public Sql[] generateSql(ModifyDataTypeStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String alterTable = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());

        // add "MODIFY"
        alterTable += " " + "CHANGE COLUMN" + " ";

        // add column name
        String columnName = database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName());
        alterTable += columnName+" "+columnName;

        alterTable += " "; // adds a space if nothing else

        // add column type
        DatabaseDataType newDataType = DataTypeFactory.getInstance().fromDescription(statement.getNewDataType(), database).toDatabaseDataType(database);

        alterTable += newDataType;

        return new Sql[]{new UnparsedSql(alterTable, getAffectedTable(statement))};
    }
}
