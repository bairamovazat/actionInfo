package ru.itis.azat.ojs.model.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.property.NumericProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;

/**
 * Class _ActionInfoData was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _ActionInfoData extends BaseDataObject {

    private static final long serialVersionUID = 1L;

    public static final String ID_PK_COLUMN = "id";

    public static final StringProperty<String> ACTION = PropertyFactory.createString("action", String.class);
    public static final NumericProperty<Long> ACTION_ID = PropertyFactory.createNumeric("actionId", Long.class);
    public static final NumericProperty<Long> CONTEXT_ID = PropertyFactory.createNumeric("contextId", Long.class);
    public static final NumericProperty<Long> DATE = PropertyFactory.createNumeric("date", Long.class);
    public static final StringProperty<String> PARAMS = PropertyFactory.createString("params", String.class);
    public static final StringProperty<String> PAYLOAD = PropertyFactory.createString("payload", String.class);
    public static final StringProperty<String> TYPE = PropertyFactory.createString("type", String.class);
    public static final NumericProperty<Long> USER_ID = PropertyFactory.createNumeric("userId", Long.class);

    protected String action;
    protected Long actionId;
    protected Long contextId;
    protected Long date;
    protected String params;
    protected String payload;
    protected String type;
    protected Long userId;


    public void setAction(String action) {
        beforePropertyWrite("action", this.action, action);
        this.action = action;
    }

    public String getAction() {
        beforePropertyRead("action");
        return this.action;
    }

    public void setActionId(Long actionId) {
        beforePropertyWrite("actionId", this.actionId, actionId);
        this.actionId = actionId;
    }

    public Long getActionId() {
        beforePropertyRead("actionId");
        return this.actionId;
    }

    public void setContextId(Long contextId) {
        beforePropertyWrite("contextId", this.contextId, contextId);
        this.contextId = contextId;
    }

    public Long getContextId() {
        beforePropertyRead("contextId");
        return this.contextId;
    }

    public void setDate(Long date) {
        beforePropertyWrite("date", this.date, date);
        this.date = date;
    }

    public Long getDate() {
        beforePropertyRead("date");
        return this.date;
    }

    public void setParams(String params) {
        beforePropertyWrite("params", this.params, params);
        this.params = params;
    }

    public String getParams() {
        beforePropertyRead("params");
        return this.params;
    }

    public void setPayload(String payload) {
        beforePropertyWrite("payload", this.payload, payload);
        this.payload = payload;
    }

    public String getPayload() {
        beforePropertyRead("payload");
        return this.payload;
    }

    public void setType(String type) {
        beforePropertyWrite("type", this.type, type);
        this.type = type;
    }

    public String getType() {
        beforePropertyRead("type");
        return this.type;
    }

    public void setUserId(Long userId) {
        beforePropertyWrite("userId", this.userId, userId);
        this.userId = userId;
    }

    public Long getUserId() {
        beforePropertyRead("userId");
        return this.userId;
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "action":
                return this.action;
            case "actionId":
                return this.actionId;
            case "contextId":
                return this.contextId;
            case "date":
                return this.date;
            case "params":
                return this.params;
            case "payload":
                return this.payload;
            case "type":
                return this.type;
            case "userId":
                return this.userId;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "action":
                this.action = (String)val;
                break;
            case "actionId":
                this.actionId = (Long)val;
                break;
            case "contextId":
                this.contextId = (Long)val;
                break;
            case "date":
                this.date = (Long)val;
                break;
            case "params":
                this.params = (String)val;
                break;
            case "payload":
                this.payload = (String)val;
                break;
            case "type":
                this.type = (String)val;
                break;
            case "userId":
                this.userId = (Long)val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.action);
        out.writeObject(this.actionId);
        out.writeObject(this.contextId);
        out.writeObject(this.date);
        out.writeObject(this.params);
        out.writeObject(this.payload);
        out.writeObject(this.type);
        out.writeObject(this.userId);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.action = (String)in.readObject();
        this.actionId = (Long)in.readObject();
        this.contextId = (Long)in.readObject();
        this.date = (Long)in.readObject();
        this.params = (String)in.readObject();
        this.payload = (String)in.readObject();
        this.type = (String)in.readObject();
        this.userId = (Long)in.readObject();
    }

}