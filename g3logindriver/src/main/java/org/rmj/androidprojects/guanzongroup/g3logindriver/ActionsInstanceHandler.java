package org.rmj.androidprojects.guanzongroup.g3logindriver;

public class ActionsInstanceHandler {
    private G3DriverInstanceAction instanceAction;

    public ActionsInstanceHandler(G3DriverInstanceAction action){
        this.instanceAction = action;
    }

    public G3DriverInstanceAction getInstanceAction() {
        return instanceAction;
    }
}
