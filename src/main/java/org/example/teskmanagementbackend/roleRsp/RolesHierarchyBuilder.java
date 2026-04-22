package org.example.teskmanagementbackend.roleRsp;

public class RolesHierarchyBuilder {
    private final StringBuilder builder = new StringBuilder();
    public RolesHierarchyBuilder append (String upLineRow, String downLineRow){
        builder.append(String.format("ROLE_%s > ROLE_%s\n", upLineRow, downLineRow));
        return this;
    }

    public String build(){
        return builder.toString();
    }
}
