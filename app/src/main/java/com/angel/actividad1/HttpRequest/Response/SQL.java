package com.angel.actividad1.HttpRequest.Response;

public class SQL {
    int fieldCount;
    int affectedRows;
    int insertId;
    int serverStatus;
    int warningCount;
    String message;
    Boolean protocol41;
    int changedRows;

    public SQL(int fieldCount, int affectedRows, int insertId, int serverStatus, int warningCount, String message, Boolean protocol41, int changedRows) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.serverStatus = serverStatus;
        this.warningCount = warningCount;
        this.message = message;
        this.protocol41 = protocol41;
        this.changedRows = changedRows;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public int getInsertId() {
        return insertId;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getProtocol41() {
        return protocol41;
    }

    public int getChangedRows() {
        return changedRows;
    }
}
