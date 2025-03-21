package org.example.functions;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.sql.annotation.SQLTrigger;

import java.util.logging.Level;

public class SqlTriggerBindingJava {
    // Visit https://aka.ms/sqltrigger to learn how to use this trigger binding
    @FunctionName("SqlTriggerBindingJava")
    public void run(
            @SQLTrigger(
                    name = "changes",
                    tableName = "[dbo].[table1]",
                    connectionStringSetting = "<value>")
            SqlChangeToDoItem[] changes,
            ExecutionContext context) {

        context.getLogger().log(Level.INFO, "SQL Changes: " + new Gson().toJson(changes));
    }

    static class SqlChangeToDoItem {
        private SqlChangeOperation Operation;
        private TodoItem Item;

        public SqlChangeToDoItem() {
        }

        public SqlChangeToDoItem(SqlChangeOperation operation, TodoItem item) {
            this.Operation = operation;
            this.Item = item;
        }

        public SqlChangeOperation getOperation() {
            return Operation;
        }

        public void setOperation(SqlChangeOperation operation) {
            this.Operation = operation;
        }

        public TodoItem getItem() {
            return Item;
        }

        public void setItem(TodoItem item) {
            this.Item = item;
        }
    }

    static enum SqlChangeOperation {
        @SerializedName("0")
        Insert,
        @SerializedName("1")
        Update,
        @SerializedName("2")
        Delete
    }

    class TodoItem {
        public String Id;
        public int Priority;
        public String Description;

        public TodoItem(String id, int priority, String description) {
            Id = id;
            Priority = priority;
            Description = description;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public int getPriority() {
            return Priority;
        }

        public void setPriority(int priority) {
            Priority = priority;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }
    }
}
